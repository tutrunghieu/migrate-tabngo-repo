package apps.tabngo.migrate;

import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MongoFrame extends JFrame 
{
	public JTextField textHost;
	public JTextField textPort;
	public JTextField textBase;
	
	public MongoFrame()
	{
		textHost = new JTextField();
		textPort = new JTextField();
		textBase = new JTextField();
	}
	
	public void setFormValues(String host, int port, String base) 
	{
		// TODO Auto-generated method stub
		
	}

}
