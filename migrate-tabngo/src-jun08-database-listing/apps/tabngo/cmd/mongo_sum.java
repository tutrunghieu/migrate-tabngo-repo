package apps.tabngo.cmd;

import java.io.File;

import org.nebula.mongo.MongoAccess;
import org.nebula.util.TargetController;

import apps.tabngo.cmd.models.MongoChecksum;

public class mongo_sum extends TargetController
{
	@Override
	public void processRequest() throws Exception 
	{
		System.out.println(this.getClass().getSimpleName());
		String f1 = super.printParam("database: ", this.mongoArgs.getDatabaseName(""));
		String h = super.printParam("host: ", this.mongoArgs.getHost() );
		int port = super.printParam("port: ", this.mongoArgs.getPort());
		File f2 = super.printParam("output: ", this.mongoArgs.getOutputFile());
		
		
		MongoAccess.writeJson(MongoChecksum.fromFolder(h, port, f1), f2);
		
		super.showFileIf(f2);
	}

}
