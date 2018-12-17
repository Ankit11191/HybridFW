package validators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;

import baseclasses.PublicContext;
import common.ReadFromExcelFileColumnWise;
import reporting.Logging;

public class CallWebService {
	
	public static String calling(String RequestFilename){
		
		String CurrentURL = PublicContext.drivreturn.getCurrentUrl();

		String clientID=CurrentURL.split("//")[1].split("\\.")[0];
		Logging.logger1.info("https://c."+clientID+".visual.force.com/apex/getsessionid");
		
		PublicContext.drivreturn.get("https://c."+clientID+".visual.force.com/apex/getsessionid");
		Logging.logger1.info(PublicContext.drivreturn.findElement(By.xpath("//td[@id='bodyCell']")).getText());
		
		String sessionIdvalue= PublicContext.drivreturn.findElement(By.xpath("//td[@id='bodyCell']")).getText();
		PublicContext.drivreturn.navigate().back();
		
		Map<String, ArrayList<String>> columnWiseCalling = ReadFromExcelFileColumnWise.columnWiseCalling("WebServiceRequests\\"+RequestFilename);
		
		Map<String, String> token = new HashMap<>();
		
			for (Map.Entry<String, ArrayList<String>> ankit : columnWiseCalling.entrySet()) {
				token.put(ankit.getKey(), ankit.getValue().get(0));
			}
			token.put("sessionId", sessionIdvalue);
			String xmlrequest = ReadText.read("wscall.xml");
			
			String patternString = "\\$\\{(" + StringUtils.join(token.keySet(), "|") + ")\\}";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(xmlrequest);

			StringBuffer requestString = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(requestString, token.get(matcher.group(1)));
			}
			matcher.appendTail(requestString);
			
			HttpHost proxy = new HttpHost("proxy-user.wip.us.equifax.com",80);
			
			HttpClient cilent = HttpClientBuilder.create().setProxy(proxy).build();
			HttpPost post;
			HttpResponse response = null;
			try {

				post = new HttpPost("https://cs14.salesforce.com/services/Soap/class/CDTProcess");
				post.setEntity( new StringEntity(requestString.toString()));
				post.setHeader("Content-type","text/xml; charset=UTF-8");
				post.setHeader("SOAPAction","Wololo");
				response = cilent.execute(post);
			} catch (IOException e) {
				e.printStackTrace();
			}

			BufferedReader br;
			String line = "";
			StringBuffer sb = new StringBuffer();
			try {
				br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				while((line=br.readLine())!=null)
				{
					sb.append(line);
				}
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
			}
						
			Logging.logger1.info(sb.toString());
			
			return sb.toString();
	}
}
