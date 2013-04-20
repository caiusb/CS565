package edu.illinois.codeselector.models;

import java.util.ArrayList;
import java.util.List;

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

	public void addSnipetForObject(Object candidateSnippet) {
		snippets.add(constructSnippet(candidateSnippet));
		notifySnippetListeners();
	}

	private Snippet constructSnippet(Object candidateSnippet) {
		return new Snippet("hello");
	}
}
