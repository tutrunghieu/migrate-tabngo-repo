package apps.tabngo.cmd;

import org.nebula.mongo.MongoAccess;
import org.nebula.util.DatabaseParams;

public class cmd 
{
	public static void main(String[] args) throws Exception 
	{
		args = new String[] { "links",  
				"-db", "data-trenzi105", 
				"-show", "true", 
				"-out", "out3-show-tree.txt" }; 

		DatabaseParams conf = MongoAccess.execArgs = new DatabaseParams(args);
				
		TargetController tar = findTargetController(args[0]);
		tar.mongoHost = conf.getHost();
		tar.mongoPort = conf.getPort();
		tar.mongoBase = conf.getDatabaseName("data-egg101");
		tar.mongoArgs = conf;
		
		tar.processRequest();
	}

	private static TargetController findTargetController(String url) 
	throws Exception
	{
		url = cmd.class.getPackage().getName() + "." + url;
		System.out.println("Executing " + url);
		
		Class<?> cl = Class.forName(url);
		return (TargetController) cl.newInstance();
	}

}
