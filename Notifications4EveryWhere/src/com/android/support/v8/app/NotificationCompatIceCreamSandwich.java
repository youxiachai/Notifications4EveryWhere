/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.support.v8.app;

import java.text.NumberFormat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RemoteViews;

import com.youxiachai.notifybar.R;

class NotificationCompatIceCreamSandwich {
	@SuppressLint("NewApi")
	static Notification add(Context context, Notification n,
			CharSequence contentTitle, CharSequence contentText,
			CharSequence contentInfo, RemoteViews tickerView, int number,
			PendingIntent contentIntent, PendingIntent fullScreenIntent,
			Bitmap largeIcon, int mProgressMax, int mProgress,
			boolean mProgressIndeterminate) {
		Notification.Builder b = new Notification.Builder(context)
				.setSound(n.sound, n.audioStreamType)
				.setVibrate(n.vibrate)
				.setSmallIcon(n.icon, n.iconLevel)
				.setTicker(n.tickerText, tickerView)
				.setLights(n.ledARGB, n.ledOnMS, n.ledOffMS)
				.setOngoing((n.flags & Notification.FLAG_ONGOING_EVENT) != 0)
				.setOnlyAlertOnce(
						(n.flags & Notification.FLAG_ONLY_ALERT_ONCE) != 0)
				.setAutoCancel((n.flags & Notification.FLAG_AUTO_CANCEL) != 0)
				.setDefaults(n.defaults)
				.setContentIntent(contentIntent)
				.setDeleteIntent(n.deleteIntent)
				.setFullScreenIntent(fullScreenIntent,
						(n.flags & Notification.FLAG_HIGH_PRIORITY) != 0);
		if (n.contentView != null) {
			b.setContent(n.contentView);
			return b.getNotification();
		} else {
			RemoteViews contentView = applyStandardTemplate(
					R.layout.notification_template_base, context, n,
					contentTitle, contentText, contentInfo, number, largeIcon,
					mProgressMax, mProgress, mProgressIndeterminate);
			// -------------

			Notification notification = b.getNotification();

			if (n.when != 0) {
				contentView.setViewVisibility(R.id.time, View.VISIBLE);
				contentView.setLong(R.id.time, "setTime", n.when);
			}

			notification.contentView = contentView;
			return notification;
		}
	}

	static RemoteViews applyStandardTemplate(int resId, Context context,
			Notification n, CharSequence contentTitle,
			CharSequence contentText, CharSequence contentInfo, int number,
			Bitmap largeIcon, int mProgressMax, int mProgress,
			boolean mProgressIndeterminate) {
		RemoteViews contentView = new RemoteViews(context.getPackageName(),
				R.layout.notification_template_base);
		boolean showLine3 = false;

		int smallIconImageViewId = R.id.icon;
		if (largeIcon != null) {
			contentView.setImageViewBitmap(R.id.icon, largeIcon);
			smallIconImageViewId = R.id.right_icon;
		}

		if (n.icon != 0) {
			contentView.setImageViewResource(smallIconImageViewId, n.icon);
			contentView.setViewVisibility(smallIconImageViewId, View.VISIBLE);
		} else {
			contentView.setViewVisibility(smallIconImageViewId, View.GONE);
		}
		if (contentTitle != null) {
			contentView.setTextViewText(R.id.title, contentTitle);
		}
		if (contentText != null) {
			contentView.setTextViewText(R.id.text, contentText);
			showLine3 = true;
		}

		if (contentInfo != null) {
			contentView.setTextViewText(R.id.info, contentInfo);
			contentView.setViewVisibility(R.id.info, View.VISIBLE);
			showLine3 = true;
		} else if (number > 0) {
			final int tooBig = Integer.MAX_VALUE;
			if (number > tooBig) {
				contentView.setTextViewText(R.id.info,
						String.valueOf(Integer.MAX_VALUE));
			} else {
				NumberFormat f = NumberFormat.getIntegerInstance();
				contentView.setTextViewText(R.id.info, f.format(number));
			}
			contentView.setViewVisibility(R.id.info, View.VISIBLE);
			showLine3 = true;
		} else {
			contentView.setViewVisibility(R.id.info, View.GONE);
		}

		// --

		if (mProgressMax != 0 || mProgressIndeterminate) {
			contentView.setProgressBar(android.R.id.progress, mProgressMax,
					mProgress, mProgressIndeterminate);
			contentView.setViewVisibility(android.R.id.progress, View.VISIBLE);
		} else {
			contentView.setViewVisibility(android.R.id.progress, View.GONE);
		}

		contentView.setViewVisibility(R.id.line3, showLine3 ? View.VISIBLE
				: View.GONE);
		return contentView;
	}

}
