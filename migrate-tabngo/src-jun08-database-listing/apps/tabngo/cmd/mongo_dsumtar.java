package apps.tabngo.cmd;

import java.io.File;

import org.nebula.mongo.MongoAccess;
import org.nebula.util.TargetController;

public class mongo_dsumtar extends TargetController
{
	@Override
	public void processRequest() throws Exception 
	{
		File ref = super.printParam("checksum: ", this.mongoArgs.getCmpFile());
		String dbname = super.printParam("database: ", this.mongoArgs.getDatabaseName(""));
		String host = super.printParam("host: ", this.mongoArgs.getHost() );
		int port = super.printParam("port: ", this.mongoPort);
		File output = super.printParam("output: ", this.mongoArgs.getOutputFile());
				
		super.showFileIf(output);
	}

}
