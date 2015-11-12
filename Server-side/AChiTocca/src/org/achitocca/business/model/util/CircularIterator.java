package org.achitocca.business.model.util;

import java.util.Iterator;

public class CircularIterator implements Iterator<Short> {
	int index = 0;
	CircularList lista;
	
	CircularIterator (CircularList childList) {
		lista = childList;
		
	}
	
	CircularIterator (CircularList childList, int index) {
		lista = childList;
		this.index = index;
		
	}
	
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Short next() {
		// TODO Auto-generated method stub
		Short el = lista.get(index);
		index++;
		return el;
	}

}
