package net.powerjander.youbotherme;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	private static final String TAG = "MyService";

	Context c;
	LocationManager locMgr;
	LocationListener locListener;
	private IBinder mBinder;
	private NotificationManager nm;
	Notification notification;
	String previousLink;
	static int PERIOD = 100; 
	boolean gps = false; 
	
	private double latitude;
	private double longitude; 

	AlarmManager alarmmMgr;

	@Override
	public IBinder onBind(Intent intent) { 
		return mBinder;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		Log.d("YBM", "onCreate");

		c = getApplicationContext();

		// player = MediaPlayer.create(this, R.raw.braincandy);
		// player = MediaPlayer.create(this,
		// Uri.parse("http://api.canoris.com/files/583eb468152940539efabb0bb6fee802/serve/?api_key=29fc87d7c85d47ec968419c8b4587a2a"));
		// player.setLooping(false); // Set looping

		// Uri.

	
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.d("YBM", "onDestroy");
//		if (player != null) {
//			player.stop(); 
//			player.release(); 
//		} 
		if (gps == true) { 
			locMgr.removeUpdates(locListener); 
		} 
		// notification.
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		String ns = Context.NOTIFICATION_SERVICE;
		// nm = (NotificationManager) getSystemService(ns);
		//		
		//		
		// int icon = R.drawable.stat_sample; // icon from resources
		// CharSequence tickerText = "Hello"; // ticker-text
		// long when = System.currentTimeMillis(); // notification time
		// Context context = getApplicationContext(); // application Context
		// CharSequence contentTitle = "My notification"; // expanded message
		// title
		// CharSequence contentText = "Hello World!"; // expanded message text
		//
		// Intent notificationIntent = new Intent(this, RemoteAlarmClock.class);
		// PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
		// notificationIntent, 0);
		//
		// // the next two lines initialize the Notification, using the
		// configurations above
		// Notification notification = new Notification(icon, tickerText, when);
		// notification.setLatestEventInfo(context, contentTitle, contentText,
		// contentIntent);

		Log.d(TAG, "onStart"); 
		
		//TODO uncomment 
		//runGPS(); 
		download(); 
		
		
		// player.start();
	}

	public void runGPS() {

		locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locListener = new LocationListener() { 

			public void onLocationChanged(Location location) {
				if (location != null) {
					Toast.makeText(
							getBaseContext(),
							"New location latitude [" + location.getLatitude()
									+ "] longitude [" + location.getLongitude()
									+ "]", Toast.LENGTH_SHORT).show();

					Log.d("YBM", "New location " + location.getLatitude() + " "
							+ location.getLongitude());


				}
			}

			
			public void onProviderDisabled(String provider) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}
		};
		locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, // minTime
				// in ms
				0, // minDistance in meters
				locListener);

	}  
	
	public void download() { 
		

		JSONObject object = Downloader.downloadFile("qq", Double
				.toString(latitude), Double
				.toString(longitude));

		// estructura del json
		try {
			String id = object.getString("id"); 
			String userid = object.getString("userid");
			String time = object.getString("time");
			String hour = object.getString("hour");
			String min = object.getString("min");
			//String time = object.getString("0");
			String active = object.getString("active");
			String link = object.getString("link");
			String created = object.getString("created");
			// String texto = object.getString("texo");

			Log.d("YBM", "id:" + id + " userid" + userid
					+ " hour:" + hour + " time" + time
					+ " link:" + link); 

			Log.d("YBM", "" + "equal to previous link "+link.equals(previousLink));
			if (link.equals(previousLink) != true) { 
				
				alarmmMgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
				Intent i = new Intent(c, PlayMusic.class);
				i.putExtra("url", link);
				PendingIntent pi = PendingIntent.getBroadcast(c, 0, i, 0);

				// the time now
				Calendar calendar = Calendar.getInstance();

				// noon tomorrow
				//calendar.add(Calendar.DAY_OF_YEAR, 1);
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
				calendar.set(Calendar.MINUTE, Integer.parseInt(min));
				calendar.set(Calendar.SECOND, 0); 
				
				
				// alarmmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				// SystemClock.elapsedRealtime(), PERIOD, pi);
				alarmmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
				
				// player.stop(); 
				//TODO 
				previousLink = link; 

			} else {

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	} 

}
