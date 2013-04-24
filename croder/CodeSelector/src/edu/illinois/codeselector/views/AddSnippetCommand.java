package edu.illinois.codeselector.views;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import edu.illinois.codeselector.models.SnippetService;
import edu.illinois.codeselector.models.snippets.Snippet;
import edu.illinois.codeselector.views.exceptions.UnknownSelectionException;

public class AddSnippetCommand extends AbstractHandler {

	private ICompilationUnit activeICU;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelectionService ss = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService();
		ISelection selection = ss.getSelection();

		if (selection == null) {
			displayMessage("No selection detected");
			return null;
		}

		activeICU = getActiveICU();

		try {
			Object selectionObject = getSelectionObject(selection);
			// displayMessage(selectionObject.getClass().getName() + "\n" +
			// selectionObject.toString());
			Snippet snippet = handleSelectionObject(selectionObject, selection);
		} catch (UnknownSelectionException e) {
			displayMessage("Cannot handle selection of type " + e.getMessage());
		}

		return null;
	}

	private Snippet handleSelectionObject(Object selectionObject, ISelection selection)
			throws UnknownSelectionException {
		return SnippetService.getInstance().addSnipetForObject(selectionObject, activeICU,
				selection);
	}

	private Object getSelectionObject(ISelection selection) throws UnknownSelectionException {
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;

			return treeSelection.getFirstElement();
		}

		else if (selection instanceof TextSelection) {
			TextSelection textSelection = (TextSelection) selection;
			try {
				IResource resource = activeICU.getCorrespondingResource();
				IMarker marker = resource.createMarker("edu.illinois.croder.snippetMarker");
				marker.setAttribute(IMarker.MESSAGE, "selected for review");
				marker.setAttribute(IMarker.CHAR_START, textSelection.getOffset());
				marker.setAttribute(IMarker.CHAR_END,
						textSelection.getLength() + textSelection.getOffset());
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			} catch (CoreException e) {
			}
			return textSelection.getText();
		}

		throw new UnknownSelectionException(selection.getClass().getName());
	}

	private ICompilationUnit getActiveICU() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IEditorPart editor = page.getActiveEditor();
				IJavaElement iunit = JavaUI.getEditorInputJavaElement(editor.getEditorInput());
				if (iunit instanceof ICompilationUnit)
					return (ICompilationUnit) iunit;
			}
		}

		return null;
	}

	private void displayMessage(String message) {
		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "CodeSelector",
				message);
	}
}
