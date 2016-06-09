package tasks.list_rows;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class MongoAccess 
{
	public static Set<String> listMongoFields(MongoCollection<Document> table) 
	{
		Set<String> fields = new LinkedHashSet<String>();
		
		for(Document rk: table.find())
				fields.addAll(rk.keySet());

		return fields;
	}

	public static File getDesktopFile(String f) 
	{
		return new File(System.getProperty("user.home") + "/Desktop/" + f);
	}	
}
