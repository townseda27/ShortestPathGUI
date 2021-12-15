import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Priority Queue implementation using a Heap
 * 
 * @author Daniel Townsend
 */
public class HeapPriorityQueue<T extends Comparable<? super T>> {
	
	//===================================================== Properties
	
	private static final int DEFAULT_CAPACITY = 11;
	private T[] heap;
	private int size;
	
	//===================================================== Constructors
	
	public HeapPriorityQueue() {
		this(DEFAULT_CAPACITY);
	}
	
	public HeapPriorityQueue(int initialSize) {
		if(initialSize <= 0) initialSize = DEFAULT_CAPACITY;
		
		heap = (T[]) new Comparable[initialSize];
		size = 0;
	}
	
	public HeapPriorityQueue(T[] entries) {
		heap = (T[]) new Comparable[entries.length];
		for(int i = 0; i < entries.length; i++) {
			add(entries[i]);
		}
		
		size = entries.length;
	}
	
	//===================================================== Helper Methods
	
	private void verifyCapacity() {
		if(size == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length * 2);
		}
	}
	
	private void swap(int index1, int index2) {
		T tmp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = tmp;
	}
	
	private void reheapUp(int index) {
		int parentIndex = index / 2;
		
		if(index < 2 || heap[index].compareTo(heap[parentIndex]) <= 0) return;
		
		swap(index, parentIndex);
		reheapUp(parentIndex);
	}
	
	private void reheapDown(int index) {
		int indexOfMaxChild = -1;
		int indexOfLeftChild = index * 2;
		int indexOfRightChild = index * 2 + 1;
		
		// if we have no children, we're done
		if(indexOfLeftChild > size && indexOfRightChild > size) {
			return;
		} else if(indexOfLeftChild > size) {
			indexOfMaxChild = indexOfRightChild;
		} else if(indexOfRightChild > size) {
			indexOfMaxChild = indexOfLeftChild;
		} else {
			// if both children are smaller than heap[index], we're done
			if(heap[index].compareTo(heap[indexOfLeftChild]) >= 0 && 
		       heap[index].compareTo(heap[indexOfRightChild]) >= 0) {
				return;
			}
	
			// determine greater child
			if(heap[indexOfLeftChild].compareTo(heap[indexOfRightChild]) < 0) {
				indexOfMaxChild = indexOfRightChild;
			} else {
				indexOfMaxChild = indexOfLeftChild;
			}
		}
		
		// if current item < child then we need to swap them and continue
		// reheaping downwards
		if(heap[index].compareTo(heap[indexOfMaxChild]) <= 0) {
			swap(index, indexOfMaxChild);
			reheapDown(indexOfMaxChild);
		}
		
	}
	
	//===================================================== Implemented Methods

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return false;
	}
	
	public void clear() {
		size = 0;
		Arrays.fill(heap, 0, size, null);
	}

	public int size() {
		return size;
	}

	public void add(T newEntry) {
		verifyCapacity();
		heap[++size] = newEntry;
		reheapUp(size);
	}

	public T peek() {
		if(isEmpty()) {
			return null;
		}
		return heap[1];
	}

	public T remove() {
		if(isEmpty()) throw new NoSuchElementException();
		
		T ret = heap[1];
		heap[1] = heap[size];
		heap[size--] = null;
		reheapDown(1);
		return ret;
	}

	public String toString() {		
		if(size == 0) {
			return "[null]";
		}
		
		StringBuilder str = new StringBuilder();
		str.append("[null, ");
		
		for(int i = 1; i <= size-1; i++) {
			str.append(heap[i] + ", ");
		}
		
		if(heap[size] != null) {
			str.append(heap[size] + "]");
		}
		
		return str.toString();
	}

}
