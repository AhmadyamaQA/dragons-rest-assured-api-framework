package tek.api.sqa.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.APITestConfig;
import tek.api.utility.EndPoints;

public class SecurityTest extends APITestConfig {

	@Test
	public void testGenerateTokenValidUsername() {

		// first step to set base URL done at BeforeMethod Annotation
		// 2) Prepare Request
		// create request body 2 ways
		// option 1) creating a map
		// option 2) creating encapsulated object
		// option 3) string as JSON object (Not Recommended)

		// option 1. Map
		Map<String, String> tokenRequestBody = new HashMap<>();

		tokenRequestBody.put("username", "supervisor");
		tokenRequestBody.put("password", "tek_supervisor");
		// Given Prepare request
		RequestSpecification request = RestAssured.given().body(tokenRequestBody);
		// set content type
		request.contentType(ContentType.JSON);
		// when sending request to end point
		Response response = request.when().post(EndPoints.TOKEN_GENERATION.getValue());
		// optinal to to print response in console
		response.prettyPrint();

		Assert.assertEquals(response.getStatusCode(), 200);

		// assert token is not null
		// using jsonpath we can get value of any entity in response
		String generatedToken = response.jsonPath().get("token");
		Assert.assertNotNull(generatedToken);
	}

	// create test to generate token with invalid username
	// Assert errorMessage "User not found"
	
	@Test(dataProvider = "invalidTokenData")
	public void testGenerateTokenWihtInValidUsername(String username, String password, 
			String expectedErrorMessage) {
		
		Map<String, String> requestbody = new HashMap<>();

		requestbody.put("username", username);
		requestbody.put("password", password);

		// Given Prepare request
		RequestSpecification send = RestAssured.given().body(requestbody);

		// set content type
		send.contentType(ContentType.JSON);
		// when sending request to end point
		Response response = send.when().post(EndPoints.TOKEN_GENERATION.getValue());
		response.prettyPrint();

		Assert.assertEquals(response.getStatusCode(), 400);
		
		String errorMessage = response.jsonPath().get("errorMessage");
		Assert.assertEquals(errorMessage, expectedErrorMessage);
	}

		@DataProvider(name = "invalidTokenData")
		private Object [][] invalidTokenData(){
			Object data [][] = {
					{"WrongUsername", "tek_supervisor", "User not found"},
					{"supervisor", "WrongPassword", "Password Not Matched"}
			
			};
			return data;
		}
}
