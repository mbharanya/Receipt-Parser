package ch.bharanya.receipt_parser;

import org.junit.Test;

import ch.bharanya.receipt_parser.export.pushbullet.PushBulletUtil;

public class PushBulletUtilTest {
	@Test
	public void testError(){
		PushBulletUtil.pushError(new Exception("testError"));
	}
	
	@Test
	public void testErrorChain(){
		PushBulletUtil.pushError(new Exception("testErrorChain", new RuntimeException("RTE")));
	}
}
