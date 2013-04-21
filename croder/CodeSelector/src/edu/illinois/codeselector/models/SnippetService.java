package edu.illinois.codeselector.models;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;

import edu.illinois.codeselector.models.snippets.AbstractSnippet;
import edu.illinois.codeselector.views.exceptions.UnknownSelectionException;

public class SnippetService {
	private static class Instance {
		public static final SnippetService _instance = new SnippetService();
	}

	public static SnippetService getInstance() {
		return Instance._instance;
	}

	private List<SnippetListener> snippetListeners;
	private ArrayList<AbstractSnippet> snippets;

	private SnippetService() {
		snippetListeners = new ArrayList<SnippetListener>();
		snippets = new ArrayList<AbstractSnippet>();
	}

	public void registerSnippetListener(SnippetListener listener) {
		this.snippetListeners.add(listener);
	}

	private void notifySnippetListeners() {
		for (SnippetListener listener : snippetListeners) {
			listener.update();
		}
	}

	public void addSnipetForObject(Object snippetTarget, ICompilationUnit activeICU) throws UnknownSelectionException {
		AbstractSnippet snippet = AbstractSnippet.constructSnippetForTarget(snippetTarget, activeICU);
		snippets.add(snippet);
		notifySnippetListeners();
	}

	public List<AbstractSnippet> getSnippets() {
		return snippets;
	}
}
