import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import Pojo.Api;
import Pojo.Courses;
import Pojo.GetCourse;
import Pojo.WebAutomation;
import io.restassured.path.json.JsonPath;
public class OAuthtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] CourseTitles={"Selenium Webdriver Java","Cypress","Protractor"};
		String response=given().formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust")
				.when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		System.out.println(response);
		JsonPath js= new JsonPath(response);
		String accesstoken=js.getString("access_token");
		System.out.println(accesstoken);
		
		GetCourse gc=given().queryParam("access_token",accesstoken)
		.when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		List<Api> apiCourses=gc.getCourses().getApi();
		

		
		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourses.get(i).getPrice());
				break;
			}
		}
		ArrayList<String> a= new ArrayList<String>();
		List<WebAutomation> w=gc.getCourses().getWebAutomation();
		for(int j=0; j<w.size();j++)
	    {
			a.add(w.get(j).getCourseTitle());
	    }
		List<String> e= Arrays.asList(CourseTitles);
		Assert.assertTrue(a.equals(e));
	
	}
		
}
