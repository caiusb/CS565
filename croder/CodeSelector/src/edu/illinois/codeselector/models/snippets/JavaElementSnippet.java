package edu.illinois.codeselector.models.snippets;

import java.util.LinkedList;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JavaElement;

public class JavaElementSnippet extends Snippet {

	private LinkedList<ISourceReference> sourceReferences;

	public JavaElementSnippet(IJavaElement snippetTarget) {
		super(snippetTarget, "");
		sourceReferences = new LinkedList<ISourceReference>();
		retrieveSourceReferencesFor(super.getJavaElementForSnippet(), sourceReferences);
	}

	@Override
	public String computeCode() {

		return convertSourceReferencesToSourceCode(sourceReferences);
	}

	private String convertSourceReferencesToSourceCode(LinkedList<ISourceReference> sourceReferences) {
		StringBuffer sb = new StringBuffer();

		for (ISourceReference iSourceReference : sourceReferences) {
			try {
				String source = iSourceReference.getSource();

				if (source != null) {
					sb.append(source);
					sb.append("\n");
				}
			} catch (JavaModelException e) {
				System.err.println("Cannot get source code for " + iSourceReference.getClass());
			}
		}

		return sb.toString();
	}

	private void retrieveSourceReferencesFor(IJavaElement javaElement, LinkedList<ISourceReference> sourceReferences) {
		JavaElement elem = (JavaElement) javaElement;

		if (elem instanceof ISourceReference) {
			ISourceReference sourceElement = (ISourceReference) elem;
			ISourceRange sourceRange;
			try {
				sourceRange = sourceElement.getSourceRange();
				setOffset(sourceRange.getOffset());
				setLength(sourceRange.getLength());
			} catch (JavaModelException e) {
			}
			sourceReferences.add(sourceElement);
		} else {
			collectFromChildren(elem, sourceReferences);
		}
	}

	private void collectFromChildren(JavaElement elem, LinkedList<ISourceReference> sourceReferences) {
		try {
			for (IJavaElement child : elem.getChildren()) {
				retrieveSourceReferencesFor(child, sourceReferences);
			}
		} catch (JavaModelException e) {
			System.err.println("cannot get children for " + elem);
		}
	}
}
