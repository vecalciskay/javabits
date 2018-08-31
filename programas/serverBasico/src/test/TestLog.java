package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLog {

	private static final Logger log = LogManager.getLogger();

	public static void main(String[] args) {
		TestLog obj = new TestLog();

		obj.m();
	}
	
	public void m() {
		log.debug("Aqui lo que va con debug");
		log.info("Aqui lo que va con info");
		log.warn("Aqui lo que va con warn");
		log.error("Aqui lo que va con error");
	}
}
