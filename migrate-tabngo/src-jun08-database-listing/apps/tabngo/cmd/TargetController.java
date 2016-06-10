package apps.tabngo.cmd;

import org.nebula.util.DatabaseParams;

public abstract class TargetController 
{
	public String mongoHost;
	public int mongoPort;
	public String mongoBase;
	public DatabaseParams mongoArgs;
	
	public abstract void processRequest() throws Exception;
	
}
