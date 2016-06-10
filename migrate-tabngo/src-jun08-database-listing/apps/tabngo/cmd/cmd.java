package apps.tabngo.cmd;

import org.nebula.mongo.MongoAccess;
import org.nebula.util.DatabaseParams;
import org.nebula.util.TargetController;

public class cmd 
{
	public static void main(String[] args) throws Exception 
	{
		args = "war-sum   -war $/migrate-tabngo.jar    -out  $/out-check1.txt".split("\\s+"); 

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
		url = cmd.class.getPackage().getName() + "." + url.replace('-', '_');
		System.out.println("Executing " + url);
		
		Class<?> cl = Class.forName(url);
		return (TargetController) cl.newInstance();
	}

}
