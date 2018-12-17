package baseclasses;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import runner.searchFileName;


public class XMLTagsVerification {
	static Document document = null;
	public static Document fileRead()
	{
		String XmlFileName=searchFileName.findFileName();
		File xmlFilePath=new File(System.getProperty("user.dir")+File.separator+"DownloadedFiles"+File.separator+XmlFileName);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		document = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			document=dBuilder.parse(xmlFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.getDocumentElement().normalize();
		return document;
	}
	
	public static ArrayList<String> getParentNodeAttribute(String Name,ArrayList<String> OutPutData) {
		OutPutData.add(Name);
		if(fileRead().getDocumentElement().getAttribute(Name).length()!=0)
		{
			OutPutData.add(fileRead().getDocumentElement().getAttribute(Name));
		}
		else
		{
			OutPutData.add("No such elemnt found");
		}
		return OutPutData;
	}
	
	public static ArrayList<String> getChildNodeValue(String xpathName,ArrayList<String> OutPutData) {
		    XPath xpath = XPathFactory.newInstance().newXPath();
		    XPathExpression expr;
		    OutPutData.add(xpathName);
			try {
				expr = xpath.compile(xpathName);
				NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

				if(nodeList.getLength()!=0)
				{
					OutPutData.add(nodeList.item(0).getTextContent());
				}
				else
				{
					OutPutData.add("No such elemnt found");
				}
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}

		return OutPutData;
	}

	public static ArrayList<String> getChildNodeAttribute(String xpathName,ArrayList<String> OutPutData) {
	    XPath xpath = XPathFactory.newInstance().newXPath();
	    XPathExpression expr;
	    String[] xPATHSplit= xpathName.split("@");
	    String MainxPATH=xPATHSplit[0];
	    String xPATHChild=xPATHSplit[1];
	    OutPutData.add(xpathName);
		try {
			expr = xpath.compile(MainxPATH);
			NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

			if(nodeList.getLength()!=0)
			{
				Node node=nodeList.item(0);
				Element element=(Element) node;
				if(element.getAttribute(xPATHChild).length()!=0)
				{
					OutPutData.add(element.getAttribute(xPATHChild));
				}
				else
				{
					OutPutData.add("No such elemnt found");
				}
			}
			else
			{
				OutPutData.add("No such elemnt found");
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

	return OutPutData;
	}
}