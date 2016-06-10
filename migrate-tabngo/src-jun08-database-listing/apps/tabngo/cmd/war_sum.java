package apps.tabngo.cmd;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.nebula.mongo.MongoAccess;
import org.nebula.util.TargetController;

public class war_sum extends TargetController
{
	@Override
	public void processRequest() throws Exception 
	{
		System.out.println("Creating checksum file for WAR file");
		
		File f1 = this.mongoArgs.getWarFile();
		System.out.println("input: " + f1.getAbsolutePath());
		
		File f2 = this.mongoArgs.getOutputFile();
		System.out.println("output: " + f2);
		
		Map<String, Object> res = new LinkedHashMap<String, Object>();
		res.put("file-name", f1.getName());
		res.put("file-size", f1.length());
		
		MongoAccess.writeJson(res, this.mongoArgs.getOutputFile());
	}

}
