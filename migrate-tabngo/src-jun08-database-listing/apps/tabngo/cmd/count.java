package apps.tabngo.cmd;

import java.awt.Desktop;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;
import org.nebula.mongo.MongoAccess;
import org.nebula.util.DatabaseParams;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class count extends TargetController 
{

	@Override
	public void processRequest() throws Exception
	{
//		DatabaseParams conf = MongoAccess.execArgs = new DatabaseParams(args);
//		String mongoHost = conf.getHost();
//		int mongoPort = conf.getPort();
//		String mongoBase = conf.getDatabaseName("data-egg");
		
		countObjects(mongoHost, mongoPort, mongoBase, mongoArgs);
		showFinal(mongoBase, mongoArgs);
	}

	private static void countObjects(String mongoHost, int mongoPort, String mongoBase, DatabaseParams conf)
	throws Exception
	{
		Map<String, Integer> counters = new LinkedHashMap<String, Integer>(); 
		
		{
			MongoClient mongo = new MongoClient(mongoHost, mongoPort);
			MongoDatabase db = mongo.getDatabase(mongoBase);

			for(String tk: db.listCollectionNames())
			{
				MongoAccess.tableCounter++;
				MongoCollection<Document> table = db.getCollection(tk);
				
				int t = (int)table.count();
				MongoAccess.objectCounter += t;
				counters.put(tk, t);
			}
			
			mongo.close();			
		}
		
		{
			PrintWriter out = new PrintWriter(conf.getOutputFile());
			
			out.println("database: " + mongoBase);
			out.println("#tables: " + MongoAccess.tableCounter);
			out.println("#objects in total: " + MongoAccess.objectCounter);
			
			for(String tk: counters.keySet())
				out.println("#objects in '"+tk+"': " + counters.get(tk));
			
			out.close();
		}		
	}

	private static void showFinal(String mongoBase, DatabaseParams conf)
	throws Exception
	{
		if( conf.showResult())
			Desktop.getDesktop().open(conf.getOutputFile());
		
		System.out.println("See result at: " 
				+ conf.getOutputFile().getAbsolutePath() );		
	}


}
