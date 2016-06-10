package apps.tabngo.cmd;

import java.awt.Desktop;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;
import org.nebula.mongo.MongoAccess;
import org.nebula.util.DatabaseParams;
import org.nebula.util.TargetController;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class links  extends TargetController
{
	@Override
	public void processRequest() throws Exception 
	{
		checkLinks(mongoHost, mongoPort, mongoBase, mongoArgs);
		showFinal(mongoBase, mongoArgs);
	}

	private static void checkLinks(String mongoHost, int mongoPort, String mongoBase, DatabaseParams conf)
	throws Exception
	{
		
		MongoClient mongo = new MongoClient(mongoHost, mongoPort);
		MongoDatabase db = mongo.getDatabase(mongoBase);

		PrintWriter out = new PrintWriter(conf.getOutputFile());
		
		for(String tk: db.listCollectionNames())
		{
			MongoAccess.tableCounter++;
			
			MongoCollection<Document> table = db.getCollection(tk);
			
			for(Document rj: table.find())
			{
				MongoAccess.objectCounter++;
				
				Map<String, String> row = new LinkedHashMap<String, String>();
				
				row.put("_id", rj.get("_id").toString());
				row.put("_table", tk);
				
				for(String fj: rj.keySet())
				{
					Object vj1 = rj.get(fj);
					String vj = (vj1==null ? "" : vj1.toString());
					
					if(vj.startsWith("http://") 
							|| vj.startsWith("https://")) 
					{
						row.put(fj, vj);
					}
				} //for each field
				
				if(row.keySet().size() > 2 ) 
				{
					MongoAccess.selectedCounter++; 
					out.println( MongoAccess.writeJson(row) ); 
				}
			} //for each row
		} //for each table
		
		MongoAccess.printCounters(out, mongoBase);
		
		out.close();
		mongo.close();		
	}

	private static void showFinal(String mongoBase, DatabaseParams conf)
	throws Exception
	{
		MongoAccess.printCounters(System.out, mongoBase);
	
		if( conf.showResult()) 
			Desktop.getDesktop().open(conf.getOutputFile());
		
		System.out.println("See result at: " 
				+ conf.getOutputFile().getAbsolutePath() );		
	}



}
