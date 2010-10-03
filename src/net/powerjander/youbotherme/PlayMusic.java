package net.powerjander.youbotherme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class PlayMusic extends BroadcastReceiver { 
	
	MediaPlayer player;

	
	@Override
	 public void onReceive(Context context, Intent intent) {
	   try {
	     Bundle bundle = intent.getExtras();
	     String link = bundle.getString("url"); 
	     Log.d("YBM", "playing "+link);  
	     
	     Toast.makeText(context, "qq", 500).show(); 
			player = MediaPlayer.create(context, Uri.parse((link))); 
			player.start();
	    } catch (Exception e) {
	     Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
	     e.printStackTrace();

	    }
	 } 
	
	public void onDestroy() {
		Log.d("qq", "onDestroy");
		if (player != null) {
			player.stop(); 
			player.release(); 
		}
	}


}
