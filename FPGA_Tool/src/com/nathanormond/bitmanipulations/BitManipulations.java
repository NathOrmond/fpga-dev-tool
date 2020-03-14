package com.nathanormond.bitmanipulations;

import java.nio.ByteBuffer;

public class BitManipulations {

	/*****************************************************************
	 * MESSAGE BYTE CONVERSION AND EXTRACTION METHODS
	 **/

	/**
	 * Returns error count per channel as string
	 * 
	 * @param byteBuffer
	 * @param bytePosition
	 * @return error count (hex)
	 */
	public static String getHexPrint(ByteBuffer byteBuffer, int offsetByte) {
		return padLeft(byteBuffer.getInt(offsetByte));
	}

	/**
	 * formatting for hex to keep returned characters same length when printed
	 * (stops window perpetually changing size)
	 * 
	 * @param s
	 * @return formatted string
	 */
	public static String padLeft(int s) {
		return String.format("%08X", bitMask(s, 0xffffffff));
	}
	

	/**
	 * 
	 * @param significantValue
	 * @param byteBuffer
	 * @param significantByte
	 * @param hexMask
	 * @return
	 */
	public static String calculateMezzianeBiteFlags(boolean trueValue, ByteBuffer byteBuffer, int significantByte, int hexMask) {
		int significantValue = bitMask(byteBuffer.getInt(significantByte), hexMask);
		boolean rv = false;
		if (trueValue) {
			rv = (significantValue != 0);
		} else {
			rv = (significantValue == 0);
		}
		return rv ? "true" : "false";
	}

	/**
	 * Extracts the entirety (32 bits) from the offset byte within the packet. 
	 * From within that extracts start bit position to end bit position : 
	 * 
	 * @param byteBuffer
	 * @param offsetByte
	 * @param startBitPos
	 * @param endBitPos
	 * @return extractedValue
	 */
	public static int returnBitFieldValue(ByteBuffer byteBuffer, int offsetByte, int startBitPos, int endBitPos) {
		int target = byteBuffer.getInt(offsetByte);
		int mask = (1 << endBitPos) - 1;
		
		return bitMask(target, mask) >> startBitPos;
	}
	
	/**
	 * 
	 * Returns a bit array (as boolean []) for given integer
	 * 
	 * @param input integer
	 * @return bits
	 */
	public static boolean[] getBits(int input) { 
		boolean[] bits;
		bits = new boolean[32];
	    for (int i = 31; i >= 0; i--) {
	        bits[i] = (input & (1 << i)) != 0;
	    }
	  
		return bits;
	}
	


	/**
	 * 
	 * @param position
	 * @return mask
	 */
	@SuppressWarnings("unused")
	public static int getSingleMask(int position) { 
		int[] masks = new int[32];
		for (int n = 0; n < 32; n++) {
		    masks[n] = 1 << n;
		}
		return masks[position];
	}
	
	/**
	 * @param hex
	 * @param hexMask
	 * @return
	 */
	public static int bitMask(int hex, int hexMask) {
		return hex & hexMask;
	}

	/**
	 * Converts String of hex values to its String value
	 * 
	 * @param hex
	 * @return
	 */
	public static String hexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < hex.length() - 1; i += 2) {		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
			String output = hex.substring(i, (i + 2)); 		// grab the hex in pairs
			int decimal = Integer.parseInt(output, 16); 	// convert hex to decimal
			sb.append((char) decimal);						// convert the decimal to character
			temp.append(decimal);
		}
		return sb.toString();
	}

	/**
	 * Converts a string of hex to its decimal value equivalent
	 * 
	 * @param s
	 * @return
	 */
	public static double hexToDecimal(String hex) {
		if(hex.contains("0x")) { 
			hex = hex.replace("0x", "");
		}
		String digits = "0123456789ABCDEF";
		hex = hex.toUpperCase();
		double val = 0;
		for (char c : hex.toCharArray()) {
			int d = digits.indexOf(c);
			val = 16 * val + d;
		}
		return val;
	}
	
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	

	/**
	 * Returns temperature of core by converting information from packet Conversion
	 * Mechanism : Byte shift, Apply Bit Mask Multiply by Coefficient Add constant
	 * 
	 * @param byteBuffer
	 * @param bytePositions
	 * @return temperature
	 */
	public static float calculateTemperature(int packetContent) {

		/* See FPGA documentation for conversion */
		float multiplicationConst = 0.123f;
		int offset = -273;
		int bitShift = 4;

		int temp = packetContent >> bitShift; // Bit wise shift of bitShift(int)
		return (temp * multiplicationConst) + offset;
	}

	/**
	 * Calculates Voltage as per documented calculation
	 * 
	 * @param byteBuffer
	 * @param significantByte
	 * @param multiplicationConst
	 * @return
	 */
	public static double calculateVoltage(ByteBuffer byteBuffer, int significantByte, double multiplicationConst) {
		int bitShift = 4;
		int offset = 0;

		double voltage = byteBuffer.getInt(significantByte) >> bitShift; // Bit wise shift of bitShift(int)
		return (voltage * multiplicationConst) + offset;
	}

}
