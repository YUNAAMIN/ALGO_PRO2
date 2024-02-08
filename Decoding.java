package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decoding {// this class that will decompresss decode the files .huff form

	public static void deCompress(File infile) {
		try {
			String fileName = infile.getPath();// the fie name the path
			FileInputStream scan = new FileInputStream(new File(fileName));// file input stream to read from the file
			byte[] buffer = new byte[1024];// buffer to holds the chunks of the data each of time
			String originalFileName = read(scan, buffer);// get the original file name
			long nbChar = Long.parseLong(read(scan, buffer));// get the number of the character on the file
			int loopSize = Integer.parseInt(read(scan, buffer));// get the number of the frequented chatacter
			HuffmanTable[] array = readHuff(scan, buffer, loopSize);// read the huffman code
			TreeNode tree = buildHuffmanTree(array);// build the huffman tree
			decData(scan, buffer, nbChar, tree, originalFileName);// decompress decoding the data
		} catch (Exception e) {
			System.out.println(e.getMessage());// print out an error message
		}
	}

	private static String read(FileInputStream scan, byte[] buffer) throws IOException {// this method will read
																						// line by line from the
																						// file input stream
		int size = scan.read(buffer, 0, 1024);// read the data with in the buffer of size 1024 each time
		StringBuilder line = new StringBuilder();// string builder to store the characters of the line
		for (int i = 0; i < size; i++) {// walk throught the buffer to extract the characters
			char c = (char) buffer[i];
			if (c == '\n') {// break the loop if there is o new line is appeared
				break;
			}
			line.append(c);// append the character to the string builder
		}
		return line.toString();// i convert the string builder using to string method to make it string and
								// return the result
	}

	private static HuffmanTable[] readHuff(FileInputStream scan, byte[] buffer, int size) throws IOException {// this
																												// method
																												// will
																												// read
																												// the
																												// huffman
																												// table
																												// that
																												// represent
																												// in
																												// the
																												// array
																												// from
																												// the
																												// file
																												// input
																												// stream.
		HuffmanTable[] array = new HuffmanTable[size];// create an array of huffman table in size of the loop size to
														// store huffman table entries
		for (int i = 0; i < size; i++) {// walk throught the size to read each entry
			char character = (char) Byte.toUnsignedInt(buffer[shift(scan, buffer)]);// read character
			int codeLength = buffer[shift(scan, buffer)];// read thhe huffman coed length
			int l = buffer[shift(scan, buffer)];// additional datafrom the buffer
			if (l == 0) {// if the buffer entry got the varue of zero then read another byte that is next
							// byte
				shift(scan, buffer);
			}
			long huffCode = readHuffCode(scan, buffer, l);// read the huffman code depends of the specific length
			array[i] = new HuffmanTable(character, codeLength);// create huffman table object and store it in the array
		}
		return array;// return the huffman table elements that it contaiend
	}

	private static int shift(FileInputStream scan, byte[] buffer) throws IOException {// this method to read the
																						// next byte from the
																						// file input stram
																						// thats in buffer
		int size = scan.read(buffer, 0, 1024);// read the first 1024 bytes that represented on t he buffer

		if (size != -1) {// check if the file input stream not eof
			return buffer[0];// return the first byte from the buffer
		} else {
			return -1;// if the end of the stream then return -1 that means the end of the fiel
		}
	}

	private static long readHuffCode(FileInputStream scan, byte[] buffer, int length) throws IOException {// this method
																											// is to
																											// read the
																											// huffman
																											// code
		long x = 0;// initialize an variaanle to store the huffman code
		for (int j = 0; j < length; j++) {// walk throught each bit of the huffman code
			x += Byte.toUnsignedLong(buffer[shift(scan, buffer)]) * (1L << 8 * j);// read the next bite using the shift
																					// method and convert it using long
		}
		return x;// return the huffman code elmetrakem 3endoh
	}

	private static TreeNode buildHuffmanTree(HuffmanTable[] array) {// this method used to build the huffamn tree from
																	// the huffman table
		TreeNode tree = new TreeNode();// craete a tree node that contains the huffman code and characters but here
										// empty one as the root of the huffman tree

		for (int i = 0; i < array.length; i++) {// walk throught array that represent the huffman table
			HuffmanTable table = array[i];// get the current huffman element

			// Insert the Huffman code into the tree with the corresponding character
			tree = TreeNode.insert(tree, table.getHuffCode(), 0, table.getCharacter());// insert the huffman tree with
																						// the spicific character and
																						// the 0 represents the starting
																						// point for insert
		}
		return tree;// return the root of the huffman tree
	}

	private static void decData(FileInputStream scan, byte[] buffer, long nbChar, TreeNode tree,
			String originalFileName) throws IOException {// this method to do the decompressing and write the output on
															// the new file
		int size = 0; // size of the data read into the buffer
		int index = 0; // index or position within the buffer array
		int bufferTracker = 0; // tracks the bit position within the current byte of the buffer
		boolean flag = false; // flag decides whether the end of the compressed data has been reached
		byte[] outputBuffer = new byte[1024];// output buffer that will store in it the decoded decompressed data
		int outputIndex = 0;// index or the position for tha outer buffer
		FileOutputStream output = new FileOutputStream(originalFileName);// file the file out but stream for the
																			// decodede output
		TreeNode root = tree;// save the root of the huffman tree that i represent the huffman table on it
		long count = 0;// counter for that tracking the number of the character that are decoded

		do {// loop to traverse the huffman tree and decoding the input

			// Traverse the Huffman tree to decode the input
			while (tree.getLeft() != null || tree.getRight() != null) {// traverse the huffman tree to decode the input
				if ((buffer[index] & (1 << 7 - bufferTracker)) == 0) {// decode depends on the bits of the compressed
																		// data
					// if the current bit is 0 move to the left child in the huffman tree
					tree = tree.getLeft();
				} else {
					// if the current bit is 1 move to the right child in the huffman tree
					tree = tree.getRight();
				}

				bufferTracker++;// move to the next bit in the compressed data

				if (bufferTracker == 8) {// check the whole byte has been processed
					bufferTracker = 0; // reset the buffer tracker to the beginning of the next byte
					index++; // move to the next byte in the buffer

					if (index == 1024) {// read a new chunk of compressed data if the buffer is exhausted
						size = scan.read(buffer, 0, 1024); // read 1024 bytes into the buffer
						index = 0; // reset the index to the beginning of the buffer

						if (size == -1) {// if the end of the compressed data is reached then break
							flag = true;
						}
					}
				}
			}

			if (flag) {// if the end of the compressed data was reched then braek
				break;
			}

			outputBuffer[outputIndex++] = (byte) tree.getCh();// store the decoded character in the buffer

			if (outputIndex == 1024) {// write the output buffer element to the file if its reaches its full that is
										// 1024
				output.write(outputBuffer);
				outputIndex = 0;// reset the value of the buffer to store athor
			}
			count++;// increment the character counter
			tree = root;// reset the root for next for loop

			if (count == nbChar) {// check here if the required and specified number of characters have been
									// decoded then break
				break;
			}

		} while (size != -1);// if ther estill an element
		output.write(outputBuffer, 0, outputIndex);// write the remaining data in the output buffer to the file
		output.close();// close the file out put stream
	}
}