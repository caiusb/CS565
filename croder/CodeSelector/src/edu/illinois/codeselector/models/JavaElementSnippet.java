package edu.illinois.codeselector.models;

import java.util.LinkedList;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JavaElement;

public class JavaElementSnippet extends Snippet {

	private IJavaElement javaElement;

	public JavaElementSnippet(IJavaElement snippetTarget) {
		super(snippetTarget);
		this.javaElement = snippetTarget;
	}

	@Override
	public String getCode() {
		LinkedList<ISourceReference> sourceReferences = new LinkedList<ISourceReference>();
		retrieveSourceReferences(javaElement, sourceReferences);

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
				System.err.println(iSourceReference.getClass());
			}
		}

		return sb.toString();
	}

	private void retrieveSourceReferences(IJavaElement javaElement, LinkedList<ISourceReference> sourceReferences) {
		JavaElement elem = (JavaElement) javaElement;

		if (elem instanceof ISourceReference) {
			sourceReferences.add((ISourceReference) elem);
		} else {
			collectFromChildren(elem, sourceReferences);
		}
	}

	private void collectFromChildren(JavaElement elem, LinkedList<ISourceReference> sourceReferences) {
		try {
			for (IJavaElement child : elem.getChildren()) {
				retrieveSourceReferences(child, sourceReferences);
			}
		} catch (JavaModelException e) {
		}
	}

}
