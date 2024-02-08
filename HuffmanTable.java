package application;

public class HuffmanTable implements Comparable<HuffmanTable> {// this class will represent the huffman table and its
	// implement comparable depends on the huffman codes
	private char ch;// the character that ill find for it the frequency and it will represented on
// the tree
	private int frequency;// counter thet represent the frequency
	private String code;// the huffman code that it contain 0 and 1s
	private int length;// the huffman code length that i will needed it for equation to make it easy
// when encoding and decoding

	public HuffmanTable(char ch) {// argument constructor for the huff man table that it will take the character
		this.ch = ch;
	}

	public HuffmanTable(char ch, int frequency) {// and other argument constructor that will take the character and
// the counter that it represent the frequency for the specified
// character
		this.ch = ch;
		this.frequency = frequency;
	}

	public void setFrequency(int frequency) {// set counter
		this.frequency = frequency;
	}

	public void setHuffCode(String code) {// set huffmen code
		this.code = code;
	}

	public void setCodeLength(int length) {// set the huff man coode length
		this.length = length;
	}

	public char getCharacter() {// get character
		return ch;
	}

	public int getFrequency() {// get counter that is the frequency
		return frequency;
	}

	public String getHuffCode() {// get the huffman code
		return code;
	}

	public int getCodeLength() {// get the huff man code length
		return length;
	}

	@Override
	public String toString() {// override the to string method that will represent the table for me thet it
// contains character frequency huffman cobe and its length
		return "HuffCode{" + "character=" + (int) ch + ", frequency=" + frequency + ", huffCode=" + code
				+ ", Huffman code Length=" + length + '}';
	}

	@Override
	public int compareTo(HuffmanTable t) {// override the compare to method that it compares depends on the huffman code
		return code.compareTo(t.code);
	}
}