package apps.tabngo.cmd;

import java.awt.Desktop;
import java.io.File;

import org.nebula.mongo.MongoAccess;
import org.nebula.util.TargetController;

import apps.tabngo.cmd.models.WarChecksum;

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
		
		WarChecksum res = new WarChecksum();
		res.fileName = f1.getName() + "";
		res.fileSize = f1.length() + "";
		MongoAccess.writeJson(res, this.mongoArgs.getOutputFile());
		
		if(this.mongoArgs.showResult()) Desktop.getDesktop().open(f2);		
	}

}
