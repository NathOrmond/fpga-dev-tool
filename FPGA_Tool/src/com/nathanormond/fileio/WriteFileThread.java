package com.nathanormond.fileio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteFileThread implements Runnable {

	private BufferedWriter bufferedWriter = null;
	private FileWriter fileWriter = null;
	private String fileWritePath;
	private List<String> cacheStringsBuffer;
	public static List<String> cacheStrings;

	/**
	 * Constructor : 
	 * sets write file absolute path 
	 * stores cache to local object then clears global cache
	 * @param fileWritePath
	 */
	public WriteFileThread(String fileWritePath) {
		this.fileWritePath = fileWritePath;
		cacheStringsBuffer = cacheStrings;
		WriteFileThread.clearCache();
	}

	/**
	 * for running file writer on separate thread
	 */
	@Override
	public void run() {
		try {
			openWriter();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e) { 
			System.out.println("NO FILE CHOSEN FOR LOG");
		}
		
		for (String output : cacheStringsBuffer) {
			try {
				appendOutputFile(output);
			} catch (IOException e) {
				System.out.println("IF NO FILE CHOSEN FOR LOG ERROR IS OK");
				e.printStackTrace();
			}
		}
		
		closeWriter();
	}

	/**
	 * adds a string to the cache - attempts to update text area with string
	 * @param cacheData
	 * @param updateArea
	 */
	public static void addToCache(String cacheData) {
		WriteFileThread.cacheStrings.add(cacheData);
	}

	/**
	 * clears the cached string list
	 */
	public static void clearCache() {
		WriteFileThread.cacheStrings = new ArrayList<String>();
	}

	/**
	 * appends output file with a string passed as parameter
	 * @param output
	 * @throws IOException 
	 */
	public void appendOutputFile(String output) throws IOException {
			bufferedWriter.append(output);
	}
	
	/**
	 * Opens up file writer to specified file path
	 * @throws IOException 
	 */
	public void openWriter() throws IOException {
			fileWriter = new FileWriter(fileWritePath, true);
			bufferedWriter = new BufferedWriter(fileWriter);
	}

	/**
	 * closes file writer
	 */
	public void closeWriter() {
		try {
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
