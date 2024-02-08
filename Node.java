package application;

public class Node implements Comparable<Node> {// this class node that it represent the minheap node that implement
												// comparable depends on frequency to represent it on a tree

	private char ch; // represents the character that ill represent on the tree
	int frequency;// represent the frequency that its an important part to store it in min heap
					// and tree and their order that here the nodes will compare between the
					// frequencies
	private Node left;// represents the left child
	private Node right;// represents the right child

	public Node(int frequency, char ch) {// argument constructor that take the frequency and the character as a
											// parameter in it
		this.frequency = frequency;
		this.ch = ch;
		this.left = null;
		this.right = null;
	}
	public Node(Node leftNode, Node rightNode) {
        this.left = leftNode;
        this.right = rightNode;
        this.frequency = leftNode.getFreq() + rightNode.getFreq();
    }
	public void incrementFrequency() {
        frequency++;
    }

	public Node() {// no argument constructor that will take nth as a paremeter
	}

	public int getFreq() {// get the frequency.
		return frequency;
	}

	public void setFreq(int freq) {// set the frequency
		this.frequency = freq;
	}

	public char getCh() {// get the character
		return ch;
	}

	public void setCh(char ch) {// set the character
		this.ch = ch;
	}

	public Node getLeft() {// get the left child
		return left;
	}

	public void setLeft(Node left) {// set the left child
		this.left = left;
	}

	public Node getRight() {// get the right child
		return right;
	}

	public void setRight(Node right) {// set the right child
		this.right = right;
	}

	@Override
	public String toString() {// override the to string that its represents the node that it contain the
								// character and its frequency.
		return "Node -->[" + "ch=" + " ch" + "freq=" + frequency + "]";
	}

	@Override
	public int compareTo(Node other) {// override the compare to method that compere between the nodes based on their
										// frequencies.
		return Integer.compare(frequency, other.getFreq());
	}
}