package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

public class Huffman {// huffman class that will represents the operation that will done in the
	// encoding compressing or decoding decompressing
	public static String fname;// static that represent s the file name
	public static HuffmanTable[] array;// static public variable that represent the huffman table array that represents
										// the content of it in array
	public static int harrSize = 0;// static public variable that represents the huffman array size and give it
									// initialize value that is zero

	private static void HuffManTree() {// this method will build a huffmantree.
		Node[] nodes = new Node[harrSize];// create new node that its size is huffman array.
		MinHeap heap = new MinHeap(harrSize + 5);// create min heap and put it size more that any number i choose
													// randomly 20 as additional buffer

		for (int i = 0; i < harrSize; i++) {// fill the heap with nodes that contains frequencies and the character that
											// stored in huffman array;
			nodes[i] = new Node(array[i].getFrequency(), array[i].getCharacter());
			heap.insert(nodes[i]);
		}

		for (int i = 1; i <= nodes.length - 1; i++) {// to walk on the elemnt of the heap
			Node z = new Node();// new node
			Node x = heap.remove();// remove the min
			Node y = heap.remove();// remove second min
			z.setFreq(x.getFreq() + y.getFreq());// make z freq= to sum of both frequencies
			z.setLeft(x);// put the x on the left child
			z.setRight(y);// put y as a right child
			heap.insert(z);// insert the value of
		}

		codes(heap.get()[1], "");// it takes the root node that is [1] and an empty string as arguments and
									// traverses the tree
									// recursively, assigning binary codes based on the path taken0 for left, 1 for
									// right.
	}

	public static void codes(Node node, String st) {// this method use to traverse the huffman tree
		if (node.getLeft() != null && node.getRight() != null) {// is leaf
			// If the current node is not a leaf
			// It recursively calls itself on the left child appending a 0 to the current
			// string st.
			// It then recursively calls itself on the right child appending a 1 to the
			// current string st
			codes(node.getLeft(), st + "0");
			codes(node.getRight(), st + "1");
		}

		else {
			// if its leaf that means dont have right or left child
			for (int i = 0; i < harrSize; i++) {// loop that size from 0 to huffman array size
				if (array[i].getCharacter() == node.getCh()) {// checks if the character of the current node matches the
																// character stored in a HuffmanTable object
					array[i].setHuffCode(st);// if a match is found set the Huffman code and code length for that
												// character
					array[i].setCodeLength(st.length());

				}
			}
		}
	}

	private static void Huffarr() {// method that constructing an array codes that holds Huffman codes for each
									// character in the huffman array

		String[] code = new String[256];// Create an string array to hold Huffman codes for each character that i
		// assuming
		// there is 256 ASCII
		// characters

		// walk through the Huffman array
		for (int i = 0; i < harrSize; i++) {
			// get the ASCII value of the character stored in the current HuffmanTable
			// object
			int charIndex = (int) array[i].getCharacter();// by casting it

			// check if the character index is within the valid range 0 to 255
			if (charIndex >= 0 && charIndex < 256) {
				// assign the Huffman code to the corresponding index in the code array
				code[charIndex] = array[i].getHuffCode();
			} else {
				// Print a message if the character index is unexpected (outside the valid
				// range)
				System.out.println("Unexpected character index: " + charIndex);
			}
		}
	}

	private static void HeaderInfo(FileOutputStream stream, String path, int realFSize, int charFreq)
			throws IOException {// that give us the header information
		byte[] headerBuffer = new byte[512];// header buffer for storing the header data in it
		int index = 0;// initialize the index counter to 0

		// get the file Name
		for (int i = 0; i < path.length(); i++) {
			headerBuffer[index++] = (byte) path.charAt(i);// it stores the byte value of each character in the buffer
															// at
															// the current index position and increments stalker to
															// move to the next buffer position
		}
		headerBuffer[index++] = '\n';// end of the file name

		// get the number of the character in the original file
		String strFSize = String.valueOf(realFSize);// convert to string the file size original file
		for (int i = 0; i < strFSize.length(); i++) {
			headerBuffer[index++] = (byte) strFSize.charAt(i);// through each character in the string and stores its
																// byte value in the buffer
		}
		headerBuffer[index++] = '\n';// to separate the data

		// number of the frequented
		String strFreq = String.valueOf(charFreq);// convert the frequencies to string
		for (int i = 0; i < strFreq.length(); i++) {
			headerBuffer[index++] = (byte) strFreq.charAt(i);// writing each character byte from the string to the
																// buffer
		}
		headerBuffer[index++] = '\n';// to separate data

		stream.write(headerBuffer, 0, index);// write the whole buffer data on the file

		index = 0;// reset its value for the next excution

		// Huffman Code for Each Character
		for (int i = 0; i < harrSize; i++) {
			// write huffman code
			HuffManCodeToFile(headerBuffer, index, array[i].getHuffCode());// write the Huffman code sequence as bytes
																			// to the buffer
			index = 0;// reset it for the next character
		}
		// print out the header
		stream.write(headerBuffer, 0, index);// writes the information for each character's Huffman code character,
												// code length, and code sequence
	}

	public static String getHeaderInfo(File file) throws Exception {
		StringBuilder headerInfo = new StringBuilder();
		headerInfo.append("File header size: ").append(getOriginalFileSize(file)).append(" bytes\n");
		// String binaryFilePath = binary(fname);
		// headerInfo.append("File path: ").append(binaryFilePath).append("\n");

		return headerInfo.toString();
	}

	private static int getOriginalFileSize(File file) {
		File originalFile = new File(fname.replace(".huff", ""));
		return (int) fname.length();
	}

	private static void HuffManCodeToFile(byte[] buffer, int i, String code) {// writes the Huffman code for a
																				// character to a byte array buffer
																				// used for building the compressed
																				// file
		// Write the length of huffman code
		int length = code.length();// huffman code length
		buffer[i++] = (byte) length;// store the length in the buffer
		// Write the huffman code as bytes
		for (int j = 0; j < length; j++) {
			buffer[i++] = (byte) code.charAt(j);// get the current byte of the string code
		}
	}

	public static String header(String path, int realFSize, int charFreq) throws IOException {// header information
		StringBuilder headerInfo = new StringBuilder();

		String fileName = new File(path).getName(); // extract only the file name from the path
		headerInfo.append("Original file name and extension:" + fileName).append("\n");

		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1); // extract the file extension
		headerInfo.append("Extention:" + fileExtension).append('\n');

		headerInfo.append("Number of characters in the original file:" + realFSize).append("\n");// character of the
																									// original file

		headerInfo.append("Number of frequented characters:" + charFreq).append("\n");// number of the frequented
																						// characters
		headerInfo.append("Huffman code" + "\n");
		// Huffman Code for Each Character and Header Size
		int sum = 0;
		int lng = 0;
		for (int i = 0; i < harrSize; i++) {
			// Append Huffman code
			headerInfo.append(array[i].getHuffCode());
			sum += array[i].getFrequency();
			lng += array[i].getCodeLength();

		}
		headerInfo.append("\nTotal number of bits:\n" + sum);
		headerInfo.append("\nTotal code length:\n" + lng);
		int headerSize = headerInfo.length();
		headerInfo.append("\n");
		headerInfo.append(" \nThe full size : \n" + headerSize).append("\n");

		return headerInfo.toString();
	}

	public static void encoding(File file) throws Exception {// this method to encode the header and the content of the
																// file in the huffman code that will be the compressed
																// copy of the original file
		String path = file.getPath();// get the file path as the string
		FileInputStream stream = new FileInputStream(path);// file input straem to read the file in byte
		int realFile = stream.available();// save the original file size
		int size;// variable to read the file in parts
		int index = -1;// initialize to start
		byte[] buff = new byte[512];// buffer that will hold the 512 byte each time
		int[] frequency = new int[256];// integer array thet will hold the frequency on it
		try {
			do {
				size = stream.read(buff, 0, 512);// read file in 512 parts
				for (int i = 0; i < size; i++) {// process each byte in the buffer
					if (buff[i] > 0) {
						index = buff[i];// if positive put it in a specific position in the buffer

					} else {//// for the negative value we handle it using 256 add it to it
						index = buff[i] + 256;

					}
					if (frequency[index] == 0) {// if the frequency ==0 that is the first time counted than i must
												// increase the size of the huffman array size
						harrSize++;
					}
					frequency[index]++;// increament the frequency for the character to use in the under
				}
			} while (size > 0);// still have characters
			for (int i = 0; i < buff.length; i++) {// reset the buffer value to use it again and again
				buff[i] = 0;
			}
			array = new HuffmanTable[harrSize];// create an array of the huffman table with size of the huffman array
												// size to create an objects based on the charactars frequencies
			int countChar = 0;// counter for the characters
			int stalker = 0;// to stalk the character
			// Populate the HuffmanTable array with character frequencies
			for (int i = 0; i < 256; i++) {// fill the huffman table treee with the characters frequencies
				if (frequency[i] != 0) {// if the frequency is not equals to the zero
					array[stalker++] = new HuffmanTable((char) i, frequency[i]);// then add the frequency and the
																				// characters on the spicific position
																				// on the huffman table.
					countChar += frequency[i];// this count the number of the character on the file so each time add the
												// frequency to this counter
					frequency[i] = 0;// reset the frequency to see for the other characters frequeencies
				}
			}
			if (harrSize == 1) {// if there is only one chatracter the huffman ocde and the length ill set it to
								// 1 as an base case
				array[0].setHuffCode("1");
				array[0].setCodeLength(1);
			} else {// else here im gonna depends on the frequencies that i found i will build the
					// tree huffman
				HuffManTree();
			}
			Huffarr();// generete the huffman code for the each character and save it in the huffman
						// table array
			fname = new StringTokenizer(file.getAbsolutePath(), ".").nextToken() + ".huff";// create the compressed file
																							// with .huff extention
																							// using the file tokenizer
			File compressedFile = new File(fname);// create the new file called the compressed file the t teh the fname
													// with huff extention and created depends on it

			System.out.println("Original File Size: " + file.length() + " bytes");// to test print the original file
																					// size.

			try (FileOutputStream stream2 = new FileOutputStream(compressedFile)) {// file output straem to write in the
																					// file as bytes
				encodeContent(new FileInputStream(path), stream2);// here i will encode the content of the file by
																	// reading and write it in it in byrte
				HeaderInfo(stream2, path, realFile, harrSize);// write the header information to the compressed file

				System.out.println("huffman table size Size: " + harrSize);// prant the huffman tablesize size
			} catch (Exception e) {
				e.printStackTrace();// print oout an error
			}
		} catch (Exception e) {
			stream.close();// close the file input straem
		}
	}

	private static void encodeContent(FileInputStream inputStream, FileOutputStream outputStream) throws IOException {
		// this method will encode the content of the file
		byte[] buffer = new byte[512];// initialize the buffer that holds 512 chunks of byte each time
		int bytesRead; // variable to store the number of the bytes will be read from the input stream
		int currentByte = 0; // accumelator for the bits
		int numBitsFilled = 0; // number of the bits filled on the current byte
		// looping through file data
		while ((bytesRead = inputStream.read(buffer)) != -1) {// reads the chunk of data from input stream to put it on
																// the buffer until the eof
			for (int i = 0; i < bytesRead; i++) {// walk throught each byte in the buffer
				int charIndex;
				if (buffer[i] < 0) {
					charIndex = buffer[i];// other wise refers the value of it by convert the byte value
				} else {
					charIndex = buffer[i] + 256;//// if its an negative value we detect by addding 256

				}
				String huffmanCode = array[charIndex].getHuffCode();// get the huffman code for each character
																	// identifide the huffman code for each character
				char[] bitsArray = huffmanCode.toCharArray();// convert the huffman core to array fo bits ya3ny one byte
																// that contains the bits in it
				for (int j = 0; j < bitsArray.length; j++) {// walk throught each bit in the
															// huffman code
					char bit = bitsArray[j];// extract the current bit value

					if (bit == '1') {// if the bit is 1 set the corresponding bit in currentByte to 1 and shift the
										// byte left by one position
						currentByte = (currentByte << 1) | 1;// shift the byte to left to make space for the new bit on
																// position 0 and make mask to it for value 1 using |
					} else {// if the bit is 0 set the corresponding bit in currentByte to 0 and shift the
							// byte left by one position.
						currentByte = (currentByte << 1) | 0;// if the bit value equals to 0 then shift to left to make
																// a new pos in 0 the n put mask to it for value 0 using
																// |
					}
					numBitsFilled++;// increment the counter for total bits filled in currentByte

					if (numBitsFilled == 8) {// if there is full bits 8 bits then write it to the output straem
						outputStream.write(currentByte);
						currentByte = 0;// reset the current byte to use it for the next bit
						numBitsFilled = 0;// reset the number of the filled bits t ouse it for the next bit
					}
				}

			}
		}

		if (numBitsFilled > 0) {// if any bits remain unfilled in the last byte shift currentByte left by the
								// number of missing bits and write it to the output stream. that means not full
								// bits
			currentByte <<= (8 - numBitsFilled);// shifts currentByte left by the number of missing bits to pack them
												// into the rightmost positions of the byte
			outputStream.write(currentByte);// we write the last byte
		}
	}

}
