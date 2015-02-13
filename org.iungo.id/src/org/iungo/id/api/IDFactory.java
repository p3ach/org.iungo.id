package org.iungo.id.api;

import java.util.Objects;


public class IDFactory {

	private final String root;
	
	private final String name;
	
	public IDFactory(final String root, final String name) {
		this.root = Objects.requireNonNull(root);
		this.name = name;
	}

	public String getRoot() {
		return root;
	}

	public String getName() {
		return name;
	}

	public ID create(final String name, final String fragment) {
		return new ID(root, name, fragment);
	}
	
	public ID create(final String fragment) {
		return new ID(root, name, fragment);
	}

	@Override
	public String toString() {
		return String.format("root [%s] name [%s]", root, name);
	}
}
