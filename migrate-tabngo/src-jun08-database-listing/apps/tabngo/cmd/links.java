package apps.tabngo.cmd;

import java.awt.Desktop;
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
		int cnt = 0;
		for(String tk: db.listCollectionNames())
		{
			MongoCollection<Document> table = db.getCollection(tk);
			
			for(Document rj: table.find())
			{
				Map<String, String> row = new LinkedHashMap<String, String>();
				
				row.put("_id", rj.get("_id").toString());
				row.put("_table", tk);
				
				for(String fj: rj.keySet())
				{
					Object vj1 = rj.get(fj);
					String vj = (vj1==null ? "" : vj1.toString());
					
					if(vj.startsWith("http://") 
							|| vj.startsWith("https://")) row.put(fj, vj);
				} //for each field
				
				if(row.keySet().size() > 2) { cnt++; out.println(row); }
			} //for each row
		} //for each table
		
		out.close();
		mongo.close();
		
		showFinal(mongoBase, cnt, conf);
	}

	private static void showFinal(String mongoBase, int cnt, ParamParser conf)
	throws Exception
	{
		System.out.println("Links in database " + mongoBase + ": " + cnt);
	
		if( conf.showResult()) 
			Desktop.getDesktop().open(conf.getOutputFile());
	}
	

}
