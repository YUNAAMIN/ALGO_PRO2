package application;

public class TreeNode {// this is the tree class that represent the tree that ill reperesent it in the
						// huffman table
	private TreeNode left;// The left child of the current tree node
	private TreeNode right; // The right child of the current tree node
	private char ch; // The character represented by this tree node
	private String code; // The Huffman code associated with the character

	public TreeNode(char ch, String code) {// Argument constructor that initializes the Tree node with a character
											// and its Huffman code
		this.ch = ch;
		this.code = code;
		left = right = null;
	}

	public TreeNode() {// Default constructor that initializes an empty tree node
		ch = '\0';
		code = "";
		left = right = null;
	}

	public char getCh() {// Getter for the character
		return ch;
	}

	public void setCh(char ch) {// Setter for the character
		this.ch = ch;
	}

	public String getHuffCode() {// Getter for the Huffman code
		return code;
	}

	public void setHuffCode(String huffCode) {// Setter for the Huffman code
		this.code = huffCode;
	}

	public TreeNode getLeft() {// Getter for the left child
		return left;
	}

	public void setLeft(TreeNode left) {// Setter for the left child
		this.left = left;
	}

	public TreeNode getRight() {// Getter for the right child
		return right;
	}

	public void setRight(TreeNode right) {// Setter for the right child
		this.right = right;
	}

	public static TreeNode insert(TreeNode tree, String st, int ind, char ch) {
		if (ind < st.length()) {

			if (st.charAt(ind) == '0') {
				if (tree.left == null)
					tree.left = new TreeNode();
				tree.left = insert(tree.left, st, ind + 1, ch);
			} else {
				if (tree.right == null)
					tree.right = new TreeNode();
				tree.right = insert(tree.right, st, ind + 1, ch);
			}
			return tree;
		} else {
			tree.ch = ch;
			return tree;
		}

	}

	// Override toString method to provide a string representation of the Tree node
	@Override
	public String toString() {
		return "Tree [ch=" + ch + ", huffCode=" + code + ", left=" + left + ", right=" + right + "]";
	}
}