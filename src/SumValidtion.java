import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidtion {
	@Test
	public void sumOfCourses()
	{
		JsonPath js= new JsonPath(Payload.CoursePrice());
		int count=js.getInt("courses.size()");
		System.out.println(count);
		int sum=0;
		for (int i=0;i<count;i++)
		{
			int priceOfTheCourse=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int amount=(priceOfTheCourse*copies);
			System.out.println(amount);
			sum=sum+ amount;
			
		}
		System.out.println(sum);
		int purchaseAmount= js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		Assert.assertEquals(sum,purchaseAmount);
	}

}
