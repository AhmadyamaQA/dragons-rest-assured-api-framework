package tek.api.sqa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.APITestConfig;
import tek.api.utility.EndPoints;

public class TokenVerifyTest extends APITestConfig {
	@Test
	public void verifyValidToken() {
		
		// generate valid token
		String ValidToken = getValidToken();
		RequestSpecification request = RestAssured.given();
		request.queryParam("token", ValidToken);
		request.queryParam("username", "supervisor");

		Response response = request.when().get(EndPoints.TOKEN_VERIFY.getValue());
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
