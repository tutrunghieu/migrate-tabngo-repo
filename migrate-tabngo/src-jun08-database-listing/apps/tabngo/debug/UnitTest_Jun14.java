package apps.tabngo.debug;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class UnitTest_Jun14 
{
	
	public static void main(String[] args) 
	{
		for(int i=0;i<1000;i++)
		{
			System.out.println("time="+i);
			sendPost();
			break;
		}
		
	}

	private static void sendPost() 
	{
		//String url = "http://192.168.100.135:7070/api/order/save";
		String url = "http://103.200.20.202:7070/api/order/save";
		
		JSONObject json= new JSONObject();
		
		json.put("order_name", "Đơn hàng 1");
		json.put("desc", "2h chiều lấy");
		json.put("list_product", "[{\"_id\":\"1074592f-a273-4b3e-8255-d27928bb3b2f\"}]");
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("data_order", json.toString()));
		
		urlParameters.add(new BasicNameValuePair("id_store", "8b20941a-9194-407b-80a3-2f53320e3fc4"));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));

			httpPost.setHeader("username", "silkroadpacific");
			httpPost.setHeader("password","123456");
			
			httpPost.setHeader("token","97eb2238eead6883392c105362bedbfd");

			HttpResponse httpResponse = httpclient.execute(httpPost);
			print(httpResponse);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		//return requestResult;
	}

	private static void print(HttpResponse res) throws Exception {
		InputStream st = res.getEntity().getContent();
		BufferedReader rd = new BufferedReader(new InputStreamReader(st));

		String line = rd.readLine();
		System.out.println("=======return from line");
		System.out.println(line);
		rd.close();

	}

//	private static String convertInputStreamToString(InputStream inputStream) throws IOException {
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//		String line = "";
//		String result = "";
//		while ((line = bufferedReader.readLine()) != null)
//			result += line;
//
//		inputStream.close();
//		return result;
//
//	}
}
