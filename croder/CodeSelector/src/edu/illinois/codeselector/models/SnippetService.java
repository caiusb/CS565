package edu.illinois.codeselector.models;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.viewers.ISelection;

import edu.illinois.codeselector.models.snippets.Snippet;
import edu.illinois.codeselector.views.exceptions.UnknownSelectionException;

public class SnippetService {
	private static class Instance {
		public static final SnippetService _instance = new SnippetService();
	}

	public static SnippetService getInstance() {
		return Instance._instance;
	}

	private List<SnippetListener> snippetListeners;
	private ArrayList<Snippet> snippets;

	private SnippetService() {
		snippetListeners = new ArrayList<SnippetListener>();
		snippets = new ArrayList<Snippet>();
	}

	public void registerSnippetListener(SnippetListener listener) {
		this.snippetListeners.add(listener);
	}

	private void notifySnippetListeners() {
		for (SnippetListener listener : snippetListeners) {
			listener.update();
		}
	}

	public Snippet addSnipetForObject(Object snippetTarget, ICompilationUnit activeICU, ISelection selection) throws UnknownSelectionException {
		Snippet snippet = Snippet.constructSnippetForTarget(snippetTarget, activeICU, selection);
		snippets.add(snippet);
		notifySnippetListeners();
		return snippet;
	}

	public List<Snippet> getSnippets() {
		return snippets;
	}
}
