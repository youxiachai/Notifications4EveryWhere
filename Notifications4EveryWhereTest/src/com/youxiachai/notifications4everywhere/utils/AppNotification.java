package com.youxiachai.notifications4everywhere.utils;

import com.android.support.v8.app.NotificationCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;

public class AppNotification {
	
	public static NotificationCompat.Builder newBaseNotify(Context ctx,int smallIcon,String ticker,Bitmap large,PendingIntent pi){
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx)
		.setContentTitle("setContentTitle")
		.setWhen(System.currentTimeMillis())
		.setContentText("setContentText")
		.setContentInfo("setContentInfo")
		.setSmallIcon(smallIcon)
		.setContentIntent(pi)
		.setLargeIcon(large)
		.setTicker(ticker);
		return mBuilder;
	}
	//set context view 
	public static Notification apiNie(){
		return null;
	}
	
	
	public static void cancel(){
		
	}
}
