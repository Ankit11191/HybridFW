package baseclasses;

import common.ReadProerties;
import reporting.Logging;

public class CreateXpathatRunTime {

	public static String createPath(String path)
	{
		ReadProerties.getObjectRepository("./PageElements");
		String[] pathParts=path.split(",");
		String xpath="";
		for(int i=0;i<pathParts.length;i++)
		{
			Logging.logger1.info(pathParts[i]+"-"+ReadProerties.propsObjectsSplits(pathParts[i])[1]);
			xpath+=ReadProerties.propsObjectsSplits(pathParts[i])[1];
		}
		
		return xpath;
	}
}
