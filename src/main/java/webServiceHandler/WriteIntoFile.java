package webServiceHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseclasses.PublicContext;
import reporting.Logging;

public class WriteIntoFile extends Logging {
	
	public List<String> write(String requestString, String responseString, String requestFileName,String responseFileName, String fileType)
	{
		String[] requestResult=requestFileName.split("_");
		String[] responseResult=responseFileName.split("_");
	String folderStructure1 = PublicContext.locationPath+File.separator+"WebServiceResponse";	
	String requestStructure = folderStructure1+File.separator+requestResult[0];
	String responseStructure = folderStructure1+File.separator+responseResult[0];
				File makdir1 = new File(folderStructure1);
				File makdir2 = new File(requestStructure);
				File makdir3 = new File(responseStructure);
				if (!makdir2.exists()) {
					makdir1.mkdir();
					makdir2.mkdir();
					makdir3.mkdir();
				}
				
				requestFileName = requestFileName.replace("Request_", "");
				
				String requestOutPutFileName = requestStructure + File.separator+ requestFileName+".xml";
				logger1.info(requestResult[0]+" File is available at :" +requestOutPutFileName);

				try (BufferedWriter bw = new BufferedWriter(new FileWriter(requestOutPutFileName))) {
					bw.write(requestString);
				} catch (IOException e) {
					e.printStackTrace();
				}
		

				responseFileName = responseFileName.replace("Response_", "");
				String responseOutPutFileName = responseStructure + File.separator+ responseFileName+"."+fileType;
				logger1.info(responseResult[0]+" File is available at : "+ responseOutPutFileName);
				
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(responseOutPutFileName))) {
						bw.write(responseString);
					} catch (IOException e) {
						e.printStackTrace();
					}

				List<String> result=new ArrayList<String>();
				requestOutPutFileName=requestResult[0]+File.separator+ requestFileName+".xml";
				responseOutPutFileName=responseResult[0]+File.separator+responseFileName+"."+fileType;
				
				result.add(requestOutPutFileName);
				result.add(responseOutPutFileName);
			
		return result;
	}
	
}