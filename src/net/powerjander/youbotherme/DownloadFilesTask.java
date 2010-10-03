package net.powerjander.youbotherme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
	protected Long doInBackground(URL... urls) {
		int count = urls.length;
		long totalSize = 0;
		for (int i = 0; i < count; i++) {
			// totalSize += Downloader.downloadFile(urls[i]);
			publishProgress((int) ((i / (float) count) * 100));
		}
		return totalSize;
	}

	protected void onPreExecute() {

	}

	protected void onProgressUpdate(Integer... progress) {
		// setProgressPercent(progress[0]);
	}

	protected void onPostExecute(Long result) {
		// showDialog("Downloaded " + result + " bytes");
	}
}

class Downloader {

	public Downloader() {
		// TODO Auto-generated constructor stub
	}

	public static JSONObject downloadFile(String user, String longitude,
			String latitude) {

		JSONObject object = null;
		BufferedReader reader;

		StringBuffer builder = null;

		try {
			 URLConnection connection = new URL(
			 "http://193.145.51.125/bcnmusichackday/alarmclock/webapp/android.php?user_id=0")
			 .openConnection();

//			URLConnection connection = new URL(
//					"http://visualizr.sweetmonster.net/json_pueba2.txt")
//					.openConnection();
			reader = new BufferedReader(new InputStreamReader(connection
					.getInputStream()), 1024 * 16);
			builder = new StringBuffer();
			String line;

			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}


			object = new JSONObject(builder.toString());



			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("qq", "sigue sin entrar");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;

	}

}