package apps.tabngo.cmd.models;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;
import org.nebula.mongo.MongoAccess;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoChecksum 
{
	public String dbname;
	public int tableCounter;
	public int objectCounter; 
	public Map<String, Integer> counters = new LinkedHashMap<String, Integer>();

	public static MongoChecksum fromJson(File f2) 
			throws Exception
	{
		return MongoAccess.readJson(f2, 
				MongoChecksum.class, new MongoChecksum());
	}
	
	
	public static MongoChecksum fromFolder(String h, int p, String d) 
	throws Exception
	{
		MongoChecksum res = new MongoChecksum();
		
		res.dbname = d;
		
		MongoClient mongo = new MongoClient(h, p);
		MongoDatabase db = mongo.getDatabase(d);

		for(String tk: db.listCollectionNames())
		{
			res.tableCounter++;
			MongoCollection<Document> table = db.getCollection(tk);
			
			int t = (int)table.count();
			res.objectCounter += t;
			res.counters.put(tk, t);
		}
			
		mongo.close();			
		
		return res;
	}

}
