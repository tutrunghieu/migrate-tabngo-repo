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

public class links 
{
	public static void main(String[] args) throws Exception
	{
		DatabaseParams conf = MongoAccess.execArgs = new DatabaseParams(args);
		String mongoHost = conf.getHost();
		int mongoPort = conf.getPort();
		String mongoBase = conf.getDatabaseName("data-egg");
		
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
		
		out.println("//database: " + mongoBase);
		out.println("//tables : " + MongoAccess.tableCounter);
		out.println("//objects: " + MongoAccess.objectCounter);
		out.println("//objects with links: " + MongoAccess.selectedCounter);
		
		out.close();
		mongo.close();
		
		showFinal(mongoBase, conf);
	}

	private static void showFinal(String mongoBase, DatabaseParams conf)
	throws Exception
	{
		System.out.println("database: " + mongoBase);
		System.out.println("tables : " + MongoAccess.tableCounter);
		System.out.println("objects: " + MongoAccess.objectCounter);
		System.out.println("links: " + MongoAccess.selectedCounter);
	
		if( conf.showResult()) 
			Desktop.getDesktop().open(conf.getOutputFile());
	}
	

}
