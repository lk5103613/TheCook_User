package com.like.network;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.like.entity.Order;
import com.like.util.CONSTANTS;

public class CommitOrderDao {

	public static boolean saveOrder(Order po) {
		String url = APIS.BASE_URL + "/index.php/appOrder/saveOrder";
		String uid = po.uid;
		String dining_time = po.dining_time;
		String servicer_usercnt = po.servicer_usercnt;
		String kitchen_size = po.kitchen_size;
		HttpClient client = new DefaultHttpClient();
		HttpPost request;
		try {
			request = new HttpPost(new URI(url));
			List params = new ArrayList();
			params.add(new BasicNameValuePair("uid", uid));
			params.add(new BasicNameValuePair("dining_time", dining_time));
			params.add(new BasicNameValuePair("servicer_usercnt",
					servicer_usercnt));
			params.add(new BasicNameValuePair("kitchen_size", kitchen_size));
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String out = EntityUtils.toString(entity, "UTF-8");
					System.out.println("out    " + out);
					JSONObject joA = new JSONObject(out);
					int code = joA.getInt("code");
					if (code == 1) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("exception");
			e.printStackTrace();
		}

		return false;
	}

}
