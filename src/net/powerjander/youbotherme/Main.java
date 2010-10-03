package net.powerjander.youbotherme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {

	/** Called when the activity is first created. */
	// @Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setVolumeControlStream(AudioManager.STREAM_MUSIC); 

		// startService(new Intent(this, MyService.class));
		setContentView(R.layout.main); 

		Button buttonStart = (Button) findViewById(R.id.buttonStart);
		Button buttonStop = (Button) findViewById(R.id.buttonStop);

		final Intent q = new Intent(this, MyService.class);

		
		//getData("0", "1", "1"); 
		
		//Downloader q2 = new Downloader(); 
		//Downloader.downloadFile("q","q","q"); 

		buttonStart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// do something when the button is clicked

				startService(q);
			}
		});

		buttonStop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// do something when the button is clicked
				stopService(q);
			}
		});

		//		
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		// BroadcastReceiver mReceiver = new BroadcastReceiver() {
		//			
		// @Override
		// public void onReceive(Context context, Intent intent) {
		// // TODO Auto-generated method stub
		// Log.d("qq", "que tal");
		//				
		//				
		// }
		// };
		// this.registerReceiver(mReceiver, filter);

	}

	
//	public void getData(String user, String longitude, String latitude) {
//
//		URLConnection connection = new URL(
//				"http://193.145.51.125/bcnmusichackday/alarmclock/webapp/android.php?user_id=0")
//				.openConnection();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(
//				connection.getInputStream()), 1024 * 16);
//		StringBuffer builder = new StringBuffer();
//		String line;
//
//		while ((line = reader.readLine()) != null) {
//			builder.append(line).append("\n");
//		}
//
//		JSONObject object = new JSONObject(builder.toString()); 
//		Log.d("qq", object.getString("id")); 
//
//	} 

	public void postData(String user, String longitude, String latitude) {
		// Create a new HttpClient and Post Header
		// http://193.145.51.125/bcnmusichackday/alarmclock/webapp/android.php?user_id=0
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost;

		Log.d("YBM", "trying to update alarms");
		httppost = new HttpPost(
				"http://193.145.51.125/bcnmusichackday/alarmclock/webapp/android.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("user_id", user));
			// nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
			// nameValuePairs.add(new BasicNameValuePair("longitude",
			// longitude));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			// parse response
			// if (response != null) {

			// foreach alarm
			// set alarm

			// }

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//    
	//
	// @Override
	// protected void onResume() {
	//    	
	// super.onResume();
	// //lm.requestLocationUpdates(bestProvider, 20000, 1, this);
	// }
	//    
	// @Override
	// protected void onPause() {
	// super.onPause();
	// //lm.removeUpdates(this);
	// }
	//
	//   
}