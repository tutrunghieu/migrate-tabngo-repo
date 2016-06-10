package apps.tabngo.cmd;

import java.awt.Desktop;
import java.io.File;
import java.io.PrintWriter;

import org.nebula.mongo.MongoAccess;
import org.nebula.util.TargetController;

import apps.tabngo.cmd.models.ChecksumUtils;
import apps.tabngo.cmd.models.WarChecksum;

public class war_sumtar extends TargetController
{
	@Override
	public void processRequest() 
	throws Exception 
	{
		File f1 = this.mongoArgs.getWarFile();
		File f2 = this.mongoArgs.getCmpFile();
		File f3 = this.mongoArgs.getOutputFile();
		
		System.out.println("Comparing WAR to checksum");
		System.out.println("check war file: " + f1);
		System.out.println("against file: " + f2);
		System.out.println("output to: " + f3);
		
		ChecksumUtils.compare(fromWar(f1), fromJson(f2), f3);
		
		if(this.mongoArgs.showResult()) Desktop.getDesktop().open(f3);
	}

	private WarChecksum fromJson(File f2) 
	{
		return MongoAccess.readJson(f2, 
				WarChecksum.class, new WarChecksum());
	}

	private WarChecksum fromWar(File f1) 
	{
		WarChecksum res = new WarChecksum();
		res.fileName = f1.getName() + "";
		res.fileSize = f1.length() + "";
		return res;
	}
	

}
