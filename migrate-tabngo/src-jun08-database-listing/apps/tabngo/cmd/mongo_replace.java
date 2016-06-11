package apps.tabngo.cmd;

import org.nebula.mongo.MongoAccess;
import org.nebula.util.TargetController;

public class mongo_replace extends TargetController
{

	@Override
	public void processRequest() throws Exception 
	{
		System.out.println(this.getClass().getSimpleName());
		String dbname = super.printParam("input: ", this.mongoArgs.getDatabaseName(""));
		String host = super.printParam("host: ", this.mongoArgs.getHost() );
		int port = super.printParam("port: ", this.mongoArgs.getPort());
		String strA = super.printParam("rfrom: ", this.mongoArgs.getReplaceFrom() );
		String strB = super.printParam("rto: ", this.mongoArgs.getReplaceTo() );
		
		
//		File d = super.printParam("output: ", this.mongoArgs.getOutputFile());
		
		MongoAccess.replaceValues(host, port, dbname, strA, strB);
	}
	
}
