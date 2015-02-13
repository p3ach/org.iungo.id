package org.iungo.id.api;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

public class ID implements Comparable<ID>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final Pattern ROOT_PATTERN = Pattern.compile("([a-z]|[A-Z]|[0-9]|.|-|_)+");
	
	public static final Pattern NAME_PATTERN = Pattern.compile("|([a-z]|[A-Z]|[0-9]|.|-|_)+(/([a-z]|[A-Z]|[0-9]|.|-|_)+)*");
	
	public static final Pattern FRAGMENT_PATTERN = Pattern.compile("([a-z]|[A-Z]|[0-9]|.|-|_)+");

	private final Long timestamp = System.currentTimeMillis();

	private final String root;
	
	private final String name;
	
	private final String fragment;
	
	private final String id;

	/**
	 * Lazy set in setGroupID if getGroupID finds groupID to be null.
	 */
	protected volatile ID groupID = null;
	
	public ID(final String root, final String name, final String fragment) {
		// Check that root is non null and matches root pattern.
		if (!ROOT_PATTERN.matcher(Objects.requireNonNull(root)).matches()) {
			throw new UnsupportedOperationException(String.format("Root [%s] does not match pattern [%s].", ROOT_PATTERN));
		}
		this.root = root;
		// Check that name is null or matches name pattern.
		if ((name != null) && !NAME_PATTERN.matcher(name).matches()) {
			throw new UnsupportedOperationException(String.format("Name [%s] does not match pattern [%s].", NAME_PATTERN));
		}
		this.name = name;
		// Check that fragment is null or matches fragment pattern.
		if ((fragment != null) && !FRAGMENT_PATTERN.matcher(fragment).matches()) {
			throw new UnsupportedOperationException(String.format("Fragment [%s] does not match pattern [%s].", FRAGMENT_PATTERN));
		}
		this.fragment = fragment;
		// Generate id from root, name and fragment.
		this.id = String.format("%s:%s#%s", root, (name == null ? "" : name), (fragment == null ? "" : fragment));
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public String getRoot() {
		return root;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFragment() {
		return fragment;
	}

	public String getID() {
		return id;
	}
	
	public ID getGroupID() {
		ID groupID = this.groupID;
		return (groupID == null ? setGroupID() : groupID);
	}

	/**
	 * Called by getGroupID() if groupID is found to be null.
	 * @return
	 */
	protected synchronized ID setGroupID() {
		if (groupID == null) { // Check in case we were in a race condition.
			groupID = new ID(root, name, null);
		}
		return groupID;
	}

	@Override
	public int compareTo(ID o) {
		return 0;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return (obj == null ? false : (obj instanceof ID ? id.equals(((ID) obj).getID()) : false));
	}

	@Override
	public String toString() {
		return id;
	}
}
