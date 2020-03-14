package com.nathanormond.network.components;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TransmitterTest {

	@Test
	public void parseHexAndASCIICombinedCommandsTest() {
		String scap = "SCAP";
		List<String> commands = new ArrayList<>();
		commands.add(scap);
		commands.add("0xFFFFFFFF");
		
		Transmitter transmitter = null;
		try {
			transmitter = new Transmitter("127.0.0.1", 500);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		
		ByteBuffer bb = null;
		
		try {
			bb = ByteBuffer.wrap(transmitter.parseHexAndASCIICombinedCommands(commands));
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		char[] chars = scap.toCharArray();
		
		for(int index = 0; index < chars.length; index++) { 
			assertEquals(chars[index] , (char) bb.get(index));
		}
		assertEquals(0xFFFFFFFF, bb.getInt(4));
	}

}
