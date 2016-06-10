package apps.tabngo.cmd;

import java.io.File;

import org.nebula.util.TargetController;

import apps.tabngo.cmd.models.FolderChecksum;
import apps.tabngo.cmd.models.ChecksumUtils;
import apps.tabngo.cmd.models.MongoChecksum;

public class mongo_sumtar extends TargetController
{
	@Override
	public void processRequest() 
	throws Exception 
	{
		System.out.println("Comparing mongo");
		String f1 = super.printParam("input: ", this.mongoArgs.getDatabaseName(""));
		String h = super.printParam("host: ", this.mongoArgs.getHost() );
		int p = super.printParam("port: ", this.mongoPort);
		File f2 = super.printParam("checksum: ", this.mongoArgs.getCmpFile());
		File f3 = super.printParam("output: ", this.mongoArgs.getOutputFile());
		
		
		ChecksumUtils.compare(
				MongoChecksum.fromFolder(h, p, f1), 
				MongoChecksum.fromJson(f2), f3);
		
		super.showFileIf(f3);
	}


}
