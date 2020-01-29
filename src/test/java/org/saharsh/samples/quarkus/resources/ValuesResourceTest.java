package org.saharsh.samples.quarkus.resources;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class ValuesResourceTest {

	@Test
	public void testHelloEndpoint() {
		RestAssured.given()
			.when().get("/api/values")
			.then()
			.statusCode(200)
				.body(CoreMatchers.is("{}"));
	}

}