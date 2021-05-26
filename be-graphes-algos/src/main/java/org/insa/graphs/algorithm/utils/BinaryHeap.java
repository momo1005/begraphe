package org.insa.graphs.algorithm.utils;

import java.util.ArrayList;

/**
 * Implements a binary heap containing elements of type E.
 *
 * Note that all comparisons are based on the compareTo method, hence E must
 * implement Comparable
 * 
 * @author Mark Allen Weiss
 * @author DLB
 */
public class BinaryHeap<E extends Comparable<E>> implements PriorityQueue<E> {

    // Number of elements in heap.
    private int currentSize;

    // The heap array.
    protected final ArrayList<E> array;

    /**
     * Construct a new empty binary heap.
     */
    public BinaryHeap() {
        this.currentSize = 0;
        this.array = new ArrayList<E>();
    }

    /**
     * Construct a copy of the given heap.
     * 
     * @param heap Binary heap to copy.
     */
    public BinaryHeap(BinaryHeap<E> heap) {
        this.currentSize = heap.currentSize;
        this.array = new ArrayList<E>(heap.array);
    }

    /**
     * Set an element at the given index.
     * 
     * @param index Index at which the element should be set.
     * @param value Element to set.
     */
    private void arraySet(int index, E value) {
        if (index == this.array.size()) {
            this.array.add(value);
        }
        else {
            this.array.set(index, value);
        }
    }

    /**
     * @return Index of the parent of the given index.
     */
    protected int indexParent(int index) {
        return (index - 1) / 2;
    }

    /**
     * @return Index of the left child of the given index.
     */
    protected int indexLeft(int index) {
        return index * 2 + 1;
    }

    /**
     * Internal method to percolate up in the heap.
     * 
     * @param index Index at which the percolate begins.
     */
    private void percolateUp(int index) {
        E x = this.array.get(index);

        for (; index > 0
                && x.compareTo(this.array.get(indexParent(index))) < 0; index = indexParent(
                        index)) {
            E moving_val = this.array.get(indexParent(index));
            this.arraySet(index, moving_val);
        }

        this.arraySet(index, x);
    }

    /**
     * Internal method to percolate down in the heap.
     * 
     * @param index Index at which the percolate begins.
     */
    private void percolateDown(int index) {
        int ileft = indexLeft(index);
        int iright = ileft + 1;

        if (ileft < this.currentSize) {
            E current = this.array.get(index);
            E left = this.array.get(ileft);
            boolean hasRight = iright < this.currentSize;
            E right = (hasRight) ? this.array.get(iright) : null;

            if (!hasRight || left.compareTo(right) < 0) {
                // Left is smaller
                if (left.compareTo(current) < 0) {
                    this.arraySet(index, left);
                    this.arraySet(ileft, current);
                    this.percolateDown(ileft);
                }
            }
            else {
                // Right is smaller
                if (right.compareTo(current) < 0) {
                    this.arraySet(index, right);
                    this.arraySet(iright, current);
                    this.percolateDown(iright);
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public void insert(E x) {
        int index = this.currentSize++;
        this.arraySet(index, x);
        this.percolateUp(index);
    }

    @Override
    public void remove(E x) throws ElementNotFoundException {
        /* TODO:
         * Lever l'exception si la liste est vide
         * lever l'exception si l'element n'existe pas dans la liste
         * sinon
         * supprimer l'element ? 
         * le remplacer par le dernier de la liste
         * mettre a jour la taille
         * percolate downn pour le remettre a sa place ?
         */
    	
    	if (this.isEmpty()) {
    		//Le tas binaire est vide on ne peux pas remove d'element - On va lever l'exception
    		throw new ElementNotFoundException(x);
    	
    	}else {
    		//Le tas binaire n'est pas vide
    		//on doit faire indexOf sur notre liste : this.array (et non sur le tas = liste + taille)
    		int position = this.array.indexOf(x);
    		
    		//ATTENTION INDEXOF() regarde dans toute la liste meme après current size 
    		//on doit verifier qu'on est < currentSize (donc si on est superieur OU égale on est dans des cases qu'on veut pas toucher)
    		
    		if (position==-1 || position>=currentSize) {
        		throw new ElementNotFoundException(x);
    		
    		} else {
    			//L'element est dans la liste
    			//1.On remplace l'element par le derniers de la liste
    			//2.on met à jour la taille
    			//3.on percolate down/up la ou on a supp l'element
    			
    			//1.On remplace l'element par le derniers de la liste
    			this.array.set(position,this.array.get(this.currentSize-1));

    			//2.on met à jour la taille
    			this.currentSize=this.currentSize-1;
    			
    			//3.on percolate down la ou on a supp l'element
    			this.percolateDown(position);
    			this.percolateUp(position);
    		}
    		
    	}
    }

    @Override
    public E findMin() throws EmptyPriorityQueueException {
        if (isEmpty())
            throw new EmptyPriorityQueueException();
        return this.array.get(0);
    }

    @Override
    public E deleteMin() throws EmptyPriorityQueueException {
        E minItem = findMin();
        E lastItem = this.array.get(--this.currentSize);
        this.arraySet(0, lastItem);
        this.percolateDown(0);
        return minItem;
    }
    
    public boolean isValid() {
    	boolean result = false;
    	if (isEmpty()) {
    		result=true;
    	} else {
    		result=true;
    		for (int i=0; (i<this.currentSize); i++) {
    			int index_gauche = indexLeft(i) ; 
    			int index_droit= indexLeft(i) + 1 ; 
		    			
    			if (index_gauche<this.currentSize) {
		    		if (index_gauche < i) {
		    			result = false ; 
		    		}
    			} else if (index_droit<this.currentSize) {
		    		if (index_droit < i) {
		    			result = false ;
		    		}
    			}
    		}
    	}
	return result;
} 
    
    /**
     * Creates a multi-lines string representing a sorted view of this binary heap.
     * 
     * @return a string containing a sorted view this binary heap.
     */
    public String toStringSorted() {
        return BinaryHeapFormatter.toStringSorted(this, -1);
    }

    /**
     * Creates a multi-lines string representing a sorted view of this binary heap.
     * 
     * @param maxElement Maximum number of elements to display. or {@code -1} to
     *                   display all the elements.
     * 
     * @return a string containing a sorted view this binary heap.
     */
    public String toStringSorted(int maxElement) {
        return BinaryHeapFormatter.toStringSorted(this, maxElement);
    }

    /**
     * Creates a multi-lines string representing a tree view of this binary heap.
     * 
     * @return a string containing a tree view of this binary heap.
     */
    public String toStringTree() {
        return BinaryHeapFormatter.toStringTree(this, Integer.MAX_VALUE);
    }

    /**
     * Creates a multi-lines string representing a tree view of this binary heap.
     * 
     * @param maxDepth Maximum depth of the tree to display.
     * 
     * @return a string containing a tree view of this binary heap.
     */
    public String toStringTree(int maxDepth) {
        return BinaryHeapFormatter.toStringTree(this, maxDepth);
    }

    @Override
    public String toString() {
        return BinaryHeapFormatter.toStringTree(this, 8);
    }

}
