package webServiceHandler;

import java.util.ArrayList;
import java.util.List;

import common.ReadProerties;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class WebServiceAPI{

	private static String protocallType = ReadProerties.propsObjectsSplit("protocallType") + "://";
	private static String envName = ReadProerties.propsObjectsSplit("envName");
	private static String wsPAth = ReadProerties.propsObjectsSplit("wsPAth");
	
	private static Response postAPI(String RequestString) {
		return RestAssured.given().contentType("application/xml").body(RequestString).post(protocallType + envName + wsPAth).then().extract().response();
	}

	@SuppressWarnings("unused")
	private static Response getAPI() {
		return RestAssured.given().contentType("application/xml").get(protocallType + envName + wsPAth).then().extract().response();
	}

	@SuppressWarnings("unused")
	private static Response putAPI(String RequestString) {
		return RestAssured.given().contentType("application/xml").body(RequestString).when()
				.get(protocallType + envName + wsPAth).then().extract().response();
	}

	@SuppressWarnings("unused")
	private static Response deleteAPI(String RequestString) {
		return RestAssured.given().contentType("application/xml").delete(protocallType + envName + wsPAth).then().extract().response();
	}
	
	public static ArrayList<String> response(String RequestString,String RequestFileName,String ResponseFileName,String FileType) {
		Response response = null;
		response = postAPI(RequestString);

		WriteIntoFile writeIntoFile=new WriteIntoFile();
		ArrayList<String> result=new ArrayList<String>();
		response.time();
		if(response.statusCode()==200)
		{
			List<String> resultTemp = writeIntoFile.write(RequestString,response.getBody().asString(),RequestFileName,ResponseFileName,FileType);
			for(String value:resultTemp)
			{
				value=value.replace("\\", "/");
				result.add(value);
				result.add(String.valueOf(response.time()));
			}
			result.add( String.valueOf(response.statusCode()));
			if(response.statusCode()==200)
			{
				result.add("Success");
				result.add(String.valueOf(response.time()));
			}
			return result;
		}

		else
		{
			List<String> resultTemp = writeIntoFile.write(RequestString,response.getBody().asString(),RequestFileName,ResponseFileName,FileType);
			for(String value:resultTemp)
			{
				value=value.replace("\\", "/");
				result.add(value);
				result.add(String.valueOf(response.time()));
			}
			result.add( String.valueOf(response.statusCode()));
			if(response.statusCode()==200)
			{
				result.add("Failed");
				result.add(String.valueOf(response.time()));
			}
			return result;
		}
	}
}
