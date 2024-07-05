import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js= new JsonPath(Payload.CoursePrice());
		//print number of courses return by API
		int count=js.getInt("courses.size()");
		System.out.println(count);
		
		// print purchase amount
		int totalAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		// print Title of the first course
		
		String title=js.get("courses[0].title");
		System.out.println(title);
		// print all course title and their respective prices
		for(int i=0; i<count;i++)
		{
			String titleOfTheCourse=js.get("courses["+i+"].title");
			int priceOfTheCourse=js.getInt("courses["+i+"].price");
			System.out.println("Course Title is:"+titleOfTheCourse);
			System.out.println("Course price is:"+priceOfTheCourse);
			
			System.out.println(js.get("courses["+i+"].title").toString());
			
			
		}
		// print no of copies sold by RPA course
//		int RpaCopies= js.getInt("courses[2].copies");
//		System.out.println(RpaCopies);
		for (int i=0;i<count;i++)
		{
			String titleOfTheCourse=js.get("courses["+i+"].title");
			if(titleOfTheCourse.equalsIgnoreCase("RPA")) {
				int RpaCopies1= js.getInt("courses["+i+"].copies");
				System.out.println("RPA copies sold:"+RpaCopies1);
				break;
			}
		}
		
		// verify if sum of all course prices matches with purchase amount
		int totalAmount1=0;
		for (int i=0;i<count;i++)
		{
			int priceOfTheCourse=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			totalAmount1=totalAmount1+(priceOfTheCourse*copies);
			
		}
		System.out.println(totalAmount1);
		Assert.assertEquals(totalAmount1, totalAmount);
	}

}
