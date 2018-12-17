package validators;

import java.util.HashMap;
import java.util.Map;

public class ActionValidationMap {
	
	static Map<String, Integer> collection()
	{
		Map<String, Integer> validation =new HashMap<String, Integer>();
		
		validation.put("SendKeysTo",2);
		validation.put("ClickOn",1);
		validation.put("explicitlyWaitingforElememt",1);
		validation.put("SwitchToWindow",1);
		validation.put("SwitchToFrame",1);
		validation.put("SwitchToDefaultFrame",0);
		validation.put("CommonSteps",1);
		validation.put("SelectFromDropDown",2);
		validation.put("GetTextOn",1);
		validation.put("Eval",1);
		validation.put("StoreText",2);
		validation.put("VerifyText",2);
		validation.put("VerifyString",2);
		validation.put("ClickOnCheckBox",1);
		validation.put("IsCheckBoxEnabled",1);
		validation.put("GetSize",2);
		validation.put("Clear",1);
		validation.put("Refresh",0);
		validation.put("IfTrue",1);
		validation.put("IfNotTrue",1);
		validation.put("EndIF",0);
		validation.put("WhileTrue",1);
		validation.put("EndWhile",0);
		validation.put("CompareDate",1);
		validation.put("Sleep",1);
		validation.put("CallingWebservice",1);
		validation.put("ExecuteScript",2);
		validation.put("EnterOn", 1);
		validation.put("VerifyXML", 2);
		return validation;
	}
	
	public static boolean validate(String key,int size) {
		boolean	status=false;
		for(String vkey:collection().keySet())
		{
			if(key.equalsIgnoreCase(vkey) && collection().get(vkey).equals(size-1))
			{
				status=true;
				break;
			}
		}
	return status;		
	}
}
