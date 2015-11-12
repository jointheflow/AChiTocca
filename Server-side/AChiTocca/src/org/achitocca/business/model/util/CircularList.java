package org.achitocca.business.model.util;

import java.util.ArrayList;
import java.util.Iterator;

public class CircularList extends ArrayList<Short> {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	@Override
	public Short get(int index) {
        return super.get(index % size());
    }
	@Override
	public Iterator<Short> iterator() {
		return new CircularIterator(this);
		
	}
	
	public Iterator<Short> iteratorFrom(int index) {
		return new CircularIterator(this, index);
		
	}
}
