package com.ewcms.plugin.crawler;

import static org.junit.Assert.*;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatternTest {
	
	private static final Logger logger = LoggerFactory.getLogger(PatternTest.class);
	
	@Test
	public void testRegex(){
		String[] keys = new String[]{"浔阳区","吴智俊|王伟"};
		String content = "我是在测试浔阳区的正测表达式，测试用例是吴智俊写的,也由王伟的加入";
		Boolean isKeyEntity = true;
		for (int i = 0 ; i < keys.length ; i++){
			Boolean result = Pattern.compile(keys[i]).matcher(content).find();
			if (!result){
				isKeyEntity = false;
				break;
			}
		}
		assertTrue(isKeyEntity);
	}
}
