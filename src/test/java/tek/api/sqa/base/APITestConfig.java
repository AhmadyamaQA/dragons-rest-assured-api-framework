package tek.api.sqa.base;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerAdapter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.utility.EndPoints;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ExtentITestListenerAdapter.class})
public class APITestConfig extends BaseConfig {

    @BeforeMethod
    public void setupApiTest() {
        System.out.println("Setting up Test");
        RestAssured.baseURI = getBaseUrl();
    }
    
    public String getValidToken() {
    	
    	Map<String, String> requestbody = new HashMap<>();
		requestbody.put("username", "supervisor");
		requestbody.put("password", "tek_supervisor");
		RequestSpecification send = RestAssured.given().body(requestbody);
		send.contentType(ContentType.JSON);
		Response response = send.when().post(EndPoints.TOKEN_GENERATION.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		String token = response.jsonPath().getString("token");
		return token;
		
    }
}
