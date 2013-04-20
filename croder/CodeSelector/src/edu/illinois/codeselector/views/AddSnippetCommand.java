package edu.illinois.codeselector.views;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

import edu.illinois.codeselector.models.SnippetService;
import edu.illinois.codeselector.views.exceptions.UnknownSelectionException;

public class AddSnippetCommand extends AbstractHandler {

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelectionService ss = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = ss.getSelection();
		
		if (selection == null){
			displayMessage("No selection detected");
			return null;
		}
			
		try {
			Object selectionObject = getSelectionObject(selection);
			//displayMessage(selectionObject.getClass().getName() + "\n" + selectionObject.toString());
			handleSelectionObject(selectionObject);
		} catch (UnknownSelectionException e) {
			displayMessage("Cannot handle selection of type " + e.getMessage());
		}
		
		return null;
	}

	private void handleSelectionObject(Object selectionObject) throws UnknownSelectionException {
		SnippetService.getInstance().addSnipetForObject(selectionObject);
	}

	private Object getSelectionObject(ISelection selection) throws UnknownSelectionException {
		if (selection instanceof TreeSelection){
			TreeSelection treeSelection = (TreeSelection) selection;
			
			return treeSelection.getFirstElement();
		}
		
		else if (selection instanceof TextSelection){
			TextSelection textSelection = (TextSelection) selection;
			
			return textSelection.getText();
		}
		
		throw new UnknownSelectionException(selection.getClass().getName());
	}

	private void displayMessage(String message) {
		MessageDialog.openInformation(Display.getCurrent().getActiveShell(),
				"CodeSelector", message);
	}
}
