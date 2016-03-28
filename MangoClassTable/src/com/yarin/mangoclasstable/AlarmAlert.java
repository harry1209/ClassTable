package com.yarin.mangoclasstable;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;

public class AlarmAlert extends Activity {

	private MediaPlayer mMediaPlayer=null;
	private static final String MUSIC_PATH=new String("/sdcard/");
	
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		mMediaPlayer=new MediaPlayer();
		playMusic(MUSIC_PATH+"music1.mp3");
		
		new AlertDialog.Builder(AlarmAlert.this)
        .setIcon(R.drawable.ic_launcher)
		.setTitle("闹钟响了")
		.setMessage("你妹的，要上课了")
		.setPositiveButton("关掉它", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				AlarmAlert.this.finish();
				if(mMediaPlayer.isPlaying()) mMediaPlayer.pause();
			}
		}).show();
		
	}

	private void playMusic(String string) {
		// TODO Auto-generated method stub
		try{
			String path=string;
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(string);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener(){

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					playMusic(MUSIC_PATH+"music1.mp3");
				}
				
			});
		}catch(IOException e){		}
	}
}
