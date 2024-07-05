import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ResusableMethod;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// validate if Add place api working or not
		//given()- give all the input details
		//when()- Submit api request
		//then()- do the validation
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(Payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		JsonPath js= new JsonPath(response);
		String placeId=js.getString("place_id");
		System.out.println(placeId);
		
		// update place api
		String newAddress= "Summer Walk, Africa";
		given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json").
		body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"").
		when().put("maps/api/place/update/json").
		then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		// Get Place
		String getPlaceResponse=given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId).
		when().get("maps/api/place/get/json").
		then().assertThat().statusCode(200).extract().response().asString();
		JsonPath js1=ResusableMethod.rawToJson(getPlaceResponse);
		String updatedPlace=js1.getString("address");
		System.out.println(updatedPlace);
		Assert.assertEquals(updatedPlace, newAddress);
		
	}

}
