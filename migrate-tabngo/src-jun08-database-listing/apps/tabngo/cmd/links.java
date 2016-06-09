package apps.tabngo.cmd;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;
import org.nebula.util.ParamParser;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class links 
{
	public static void main(String[] args) throws Exception
	{
		ParamParser conf = new ParamParser(args);
		String mongoHost = conf.getHost();
		int mongoPort = conf.getPort();
		String mongoBase = conf.getDatabaseName("data-egg");
		
		MongoClient mongo = new MongoClient(mongoHost, mongoPort);
		MongoDatabase db = mongo.getDatabase(mongoBase);

		PrintWriter out = new PrintWriter(conf.getOutputFile());
		
		out.println("- database: " + mongoBase);		
		for(String tk: db.listCollectionNames())
		{
			MongoCollection<Document> table = db.getCollection(tk);
			
			for(Document rj: table.find())
			{
				Map<String, String> row = new LinkedHashMap<String, String>();
				
				row.put("_id", rj.get("_id").toString());
				
				for(String fj: rj.keySet())
				{
					Object vj1 = rj.get(fj);
					String vj = (vj1==null ? "" : vj1.toString());
					
					if(vj.startsWith("http://") 
							|| vj.startsWith("https://")) row.put(fj, vj);
				} //for each field
				
				if(row.keySet().size() > 1) 
				{
					row.put("_table", tk);
					out.println(row);
				}
			} //for each row
		} //for each table
		
		out.close();
		
		mongo.close();
			
//		
//		MongoCollection<Document> t = db.getCollection(table);
//		
//		HtmlWriter out = new HtmlWriter(file);
//		
//		out.h1(table + " (" + t.count() + ")");
//		out.table();
	}

}
