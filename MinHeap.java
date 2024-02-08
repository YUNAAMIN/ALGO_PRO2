package application;

public class MinHeap {// this is the min heap that is same as the priority queue that we take on the
						// lecture here i will represented that it represent as an array of Nodes and
						// that its as a tree on implicit way not explicit
	private Node[] node;// the minheap represents the array of the nodes
	private int size;// represents the size of elements nodes in the min heap array
	private int capacity;// represent the capacity of all the min heap array

	public MinHeap() {// no argument constructor
		this(1);
	}

	public MinHeap(int capacity) {// argument constructor that its take as input parameter capacity of the min
									// heap
		this.capacity = capacity;
		node = new Node[capacity];// create a new array node that its size is the capacity
		size = 0;// initialize the size =0
	}

	public int getSize() {// get the size
		return size;
	}

	public int getCapacity() {// get the capacity
		return capacity;
	}

	public Node[] getNode() {// get node
		return node;
	}

	public void setNode(Node[] node) {// set node
		this.node = node;
	}

	public void setSize(int size) {// set size
		this.size = size;
	}

	public void setCapacity(int capacity) {// set capacity
		this.capacity = capacity;
	}

	public boolean isEmpty() {// check if its empty or not
		if (size == 0)// if size equals to 0 then return true
			return true;
		else
			return false;// if the size not equals to 0 return false
	}

	public Node[] get() {// get that it will print out the heap array with its nodes values
		Node[] result = new Node[size + 1];// create new node
		for (int i = 0; i < size + 1; i++)// it will walk element by elemnt based on index and add the nodes values
			result[i] = node[i];
		return result;// return the result as entire array of nodes
	}

	public void insert(Node element) {// this method will take the node and add it to the heap array
		int i = ++size;// initialize index i thets start from 1
		while ((i > 1) && node[i / 2].getFreq() > element.getFreq()) {// While not at the root and the parent's
																		// frequency is greater than the new element's
																		// frequency.
			node[i] = node[i / 2]; //it swaps them and moves up the tree until the element reaches its correct position
			i /= 2;// move to the parents index
		}
		node[i] = element;// place the new element on the new correct position.
	}

	public Node remove() {// removes and returns the minimum element from the min heap that we will use it
							// in huffman.
		int child;// initialize the child index
		int i;// initialize the index
		Node last;// last element in the tree
		Node min = null;// initialize min and set it null initially
		if (size != 0) {// if the size not equal to 0 so its not empty
			min = node[1];// we suppose that the first element is the min
			last = node[size--];// and the last equal to the last element in the heap size-1
			for (i = 1; i*2 <= size; i = child) {
				child = i* 2;// represents the left child
				if (child < size && node[child].getFreq() > node[child + 1].getFreq())
					child++;// move to right if its smaller
				if (last.getFreq() > node[child].getFreq())
					node[i] = node[child];// move the smaller child up
				else
					break;
			}
			node[i] = last;// place the last element on the correct place.
		}
		return min;// return the deleted node that its the smallest and min value on the heap.
	}

}