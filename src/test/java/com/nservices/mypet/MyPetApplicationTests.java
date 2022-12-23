package com.nservices.mypet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class MyPetApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void rndTest() {
		System.out.println(Math.random() < 0.5 ? 1 : 0);
	}

}
