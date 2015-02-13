package org.iungo.id.api;

import java.util.Random;


public class IDSequence extends IDFactory {
	
	private static final Random random = new Random();
	
	private volatile Long sequence = (long) random.nextInt(1024);
	
	public IDSequence(final String root, final String name) {
		super(root, name);
	}
	
	public Long getSequence() {
		return sequence;
	}

	public synchronized ID next() {
		if (sequence.equals(Long.MAX_VALUE)) {
			sequence = 0L;
		}
		sequence++;
		return new ID(getRoot(), getName(), sequence.toString());
	}

	@Override
	public String toString() {
		return String.format("root [%s] name [%s] sequence [%d]", getRoot(), getName(), sequence);
	}
}
