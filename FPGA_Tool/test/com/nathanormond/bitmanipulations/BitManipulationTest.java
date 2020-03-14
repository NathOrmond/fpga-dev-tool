package com.nathanormond.bitmanipulations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.nathanormond.bitmanipulations.BitManipulations;

public class BitManipulationTest {
	
	private BitManipulations packetReader;
	
	@Before
	public void preTest() { 
		this.packetReader = null;
		this.packetReader = new BitManipulations();
	}

	@Test
	public void testPadLeft() {
		int values[] = {10, 100, 10000, 100000}; 	// Hex : A, 64, 2710,186A0
		String expectedHex[] = {Integer.toHexString(values[0]), Integer.toHexString(values[1]), Integer.toHexString(values[2]), Integer.toHexString(values[3])};
		String returnedHex[] = {packetReader.padLeft(values[0]), packetReader.padLeft(values[1]), packetReader.padLeft(values[2]), packetReader.padLeft(values[3])};
		
		int expectedHexLengths[] = {expectedHex[0].length(), expectedHex[1].length(), expectedHex[2].length(), expectedHex[3].length()};
		int returnedHexLengths[] = {returnedHex[0].length(), returnedHex[1].length(), returnedHex[2].length(), returnedHex[3].length(),};
		
		assertNotEquals(expectedHexLengths[0], returnedHexLengths[0]);
		assertNotEquals(expectedHexLengths[1], returnedHexLengths[1]);
		assertNotEquals(expectedHexLengths[2], returnedHexLengths[2]);
		assertNotEquals(expectedHexLengths[3], returnedHexLengths[3]);
		
		assertNotEquals(expectedHexLengths[0], expectedHexLengths[1]);
		assertNotEquals(expectedHexLengths[1], expectedHexLengths[2]);
		assertNotEquals(expectedHexLengths[2], expectedHexLengths[3]);
		assertNotEquals(expectedHexLengths[3], expectedHexLengths[0]);
		
		assertEquals(returnedHexLengths[0], returnedHexLengths[1]);
		assertEquals(returnedHexLengths[1], returnedHexLengths[2]);
		assertEquals(returnedHexLengths[2], returnedHexLengths[3]);
		assertEquals(returnedHexLengths[3], returnedHexLengths[0]);
	}

	@Test
	public void testReturnBitFieldValue() {
		Random r = new Random();
		int ival[]= {r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt()};
		ByteBuffer bb = ByteBuffer.allocate(32*5);
		
		for(int i = 0; i < ival.length; i++) { 
			bb.putInt(ival[i]);
		}
		
		for(int i = 0; i < ival.length; i++) { 
			assertEquals(ival[i], bb.getInt(i*4));
		}
	}

	@Test
	public void testGetBits() {
		int highest = Integer.MAX_VALUE;
		int lowest = Integer.MIN_VALUE;	
		int lowerHalfOn = 65535;			
		
		boolean[] highestBits = packetReader.getBits(highest);
		boolean[] lowestBits = packetReader.getBits(lowest);
		boolean[] lowerHalfOnBits = packetReader.getBits(lowerHalfOn);
		
		for(int i = 0; i < (lowerHalfOnBits.length/2); i++) { 
			assertEquals(true, lowerHalfOnBits[i]);
		}
		
		for(int i = lowerHalfOnBits.length/2; i < lowerHalfOnBits.length; i++) { 
			assertEquals(false, lowerHalfOnBits[i]);
		}
		
		for(int i = 0; i < highestBits.length; i++) { 
			if(i != (highestBits.length - 1)) {	
				assertEquals(true, highestBits[i]);
				assertEquals(false, lowestBits[i]);
			}
		}
		
	}

	@Test
	public void testBitMask() {
		assertEquals(packetReader.bitMask(255,255), 255);
		assertEquals(packetReader.bitMask(65535,255), 255);
		assertEquals(packetReader.bitMask(Integer.MAX_VALUE,255), 255);
		assertEquals(packetReader.bitMask(Integer.MIN_VALUE,255), 0);
		assertEquals(packetReader.bitMask( -2147483648, 255), 0);
		assertEquals(packetReader.bitMask( -2147483648, -2147483648), -2147483648);
	}
	
	@Test
	public void testGetSingleMask(){ 
		for(int i  = 0; i < 31; i++) { 
			assertEquals(packetReader.getSingleMask(i), (int) Math.pow(2, i));
		}
	}

	@Test
	public void testHexToString() {
		assertEquals(packetReader.hexToString("50"), "P");
		assertEquals(packetReader.hexToString("45"), "E");
		assertEquals(packetReader.hexToString("4E"), "N");
	}

	@Test
	public void testHexToDecimal() {
		assertEquals((int) packetReader.hexToDecimal("A"), 10);
		assertEquals((int) packetReader.hexToDecimal("FF"), 255);
		assertEquals((int) packetReader.hexToDecimal("BB"), 187);
	}

}
