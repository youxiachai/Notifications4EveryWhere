package com.youxiachai.notifications4everywhere;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.support.v8.app.NotificationCompat.Builder;
import com.youxiachai.notifications4everywhere.utils.AppNotification;

public class MainActivity extends Activity {
	Builder mBuilder;
	NotificationManager mNotifyManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent resultIntent = new Intent(this, Second.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		 stackBuilder.addParentStack(Second.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);

//	final	PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1,
//			PendingIntent.FLAG_UPDATE_CURRENT);
	
	final PendingIntent resultPendingIntent =
	        PendingIntent.getActivity(
	        this,
	        0,
	        resultIntent,
	        PendingIntent.FLAG_UPDATE_CURRENT);
	
		mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		final Bitmap large = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 mBuilder = AppNotification.newBaseNotify(MainActivity.this, R.drawable.ic_launcher, "ok", large, resultPendingIntent);
				
					
				// mNotifyManager.notify(0, mBuilder.build());
				downloadTest();
				
			}
		});
	}


	public void downloadTest(){
		// Start a lengthy operation in a background thread
		new Thread(
		    new Runnable() {
		        @Override
		        public void run() {
		            int incr;
		            // Do the "lengthy" operation 20 times
		            for (incr = 0; incr <= 100; incr+=5) {
		                    // Sets the progress indicator to a max value, the
		                    // current completion percentage, and "determinate"
		                    // state
		                    mBuilder.setProgress(100, incr, false).setContentInfo(incr + "%");
		                    // Displays the progress bar for the first time.
		                    mNotifyManager.notify(0, mBuilder.build());
		                        // Sleeps the thread, simulating an operation
		                        // that takes time
		                        try {
		                            // Sleep for 5 seconds
		                            Thread.sleep(1*1000);
		                        } catch (InterruptedException e) {
		                            Log.d("notify", "sleep failure");
		                        }
		            }
		            // When the loop is finished, updates the notification
		            mBuilder.setContentText("Download complete")
		            // Removes the progress bar
		                    .setProgress(0,0,false);
		            mNotifyManager.notify(0, mBuilder.build());
		        }
		    }
		// Starts the thread by calling the run() method in its Runnable
		).start();
	}

}
