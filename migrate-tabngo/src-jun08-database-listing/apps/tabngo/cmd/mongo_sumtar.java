package apps.tabngo.cmd;

import java.io.File;

import org.nebula.mongo.ChecksumUtils;
import org.nebula.util.TargetController;

import apps.tabngo.cmd.models.MongoChecksum;

public class mongo_sumtar extends TargetController
{
	@Override
	public void processRequest() 
	throws Exception 
	{
		System.out.println(this.getClass().getSimpleName());
		String f1 = super.printParam("input: ", this.mongoArgs.getDatabaseName(""));
		String h = super.printParam("host: ", this.mongoArgs.getHost() );
		int port = super.printParam("port: ", this.mongoArgs.getPort());
		File f2 = super.printParam("checksum: ", this.mongoArgs.getCmpFile());
		File f3 = super.printParam("output: ", this.mongoArgs.getOutputFile());
		
		
		ChecksumUtils.compareObjects(
				MongoChecksum.fromFolder(h, port, f1), 
				MongoChecksum.fromJson(f2), f3);
		
		super.showFileIf(f3);
	}


}
