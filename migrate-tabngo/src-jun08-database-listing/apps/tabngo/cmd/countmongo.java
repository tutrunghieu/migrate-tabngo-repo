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

public class countmongo 
{
	public static void main(String[] args) throws Exception
	{
		ParamParser conf = new ParamParser(args);
		String mongoHost = conf.getHost();
		int mongoPort = conf.getPort();
		String mongoBase = conf.getDatabaseName("data-egg");
		
		int nof_tab = 0, nof_rows = 0;
		Map<String, Integer> counters = new LinkedHashMap<String, Integer>(); 
		
		{
			MongoClient mongo = new MongoClient(mongoHost, mongoPort);
			MongoDatabase db = mongo.getDatabase(mongoBase);

			for(String tk: db.listCollectionNames())
			{
				MongoCollection<Document> table = db.getCollection(tk);
				int nk = (int)table.count();
				
				nof_tab++;
				nof_rows += nk;
				counters.put(tk, nk);
			}
			
			mongo.close();			
		}
		
		{
			PrintWriter out = new PrintWriter(conf.getOutputFile());
			
			out.println("database: " + mongoBase);
			out.println("#tables: " + nof_tab);
			
			for(String tk: counters.keySet())
				out.println("#objects in '"+tk+"': " + counters.get(tk));
			
			out.println("#objects in total: " + nof_rows);
			
			out.close();
			
			System.out.println("See result at: " + conf.getOutputFile().getAbsolutePath() );
			
			if( conf.showResult())
				Desktop.getDesktop().open(conf.getOutputFile());
		}
		
		return;
	}

}
