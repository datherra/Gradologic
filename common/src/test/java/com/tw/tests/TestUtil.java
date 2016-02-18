package com.tw.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.tw.Util;

public class TestUtil {
	@Test
	public void getFileContent() {
		Util util = new Util();
		String content = Util.getFileContentToDisplay();
		assertEquals("test\n", content);
	}
}