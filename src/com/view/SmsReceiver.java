package com.view;

import java.util.Calendar;
import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SmsReceiver extends BroadcastReceiver {
	private static Context context = null;
	public static int amount = 0;

	// TODO delete this
	public static Long sms = 0L;
	public static Long db = 0L;

	@Override
	public void onReceive(Context context, Intent intent) {

		SmsReceiver.context = context;
		Bundle bundle = intent.getExtras(); // ---get the SMS message passed
											// in---
		SmsMessage[] msgs = null;
		String msg_from = null;
		if (bundle != null) {
			// ---retrieve the SMS message received---
			try {
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];

				String allMessages = "";

				boolean isNumber000019 = false;
				// if full message body will be contained in several
				// sms-messages, than msgs.length will > 1
				for (int i = 0; i < msgs.length; i++) {
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					msg_from = msgs[i].getOriginatingAddress();

					String msgBody = msgs[i].getMessageBody();

					if (HandleIncommingSms.getNumberRunning() == 0
							&& msg_from.equals(MainActivity.TELEPHONE_NUMBER)
							&& !isNumber000019) {

						isNumber000019 = true;
						
					}

					allMessages += msgBody;

				}

				if (isNumber000019) {
					HandleIncommingSms.incNumberRunning();
					(new HandleIncommingSms()).start();
					isNumber000019 = false;
					//abortBroadcast();
				}

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(msgs[0].getTimestampMillis());
				sms = msgs[0].getTimestampMillis();

				/*Toast.makeText(
						context,
						(allMessages.length() == 0 ? "no text" : allMessages
								+ "\n" + msg_from), Toast.LENGTH_LONG).show();*/
				
				/*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);
				if (prefs.getBoolean("notify_on_sms_receive_endabled", true)) {*/
				//PendingIntent pi = PendingIntent.getBroadcast(this.getContext(), 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				/*NotificationManager mNotificationManager = (NotificationManager) MainActivity.context.getSystemService(Context.NOTIFICATION_SERVICE);
				
				@SuppressWarnings("deprecation")
				Notification notif = new Notification(R.drawable.ic_launcher,"Critical process report", System.currentTimeMillis());
				notif.flags |= Notification.FLAG_AUTO_CANCEL;
				Intent notificationIntent = new Intent(context, MainActivity.class);
				PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
				notif.setLatestEventInfo(context, "Sms-Report Parser", "Обновление статуса критических процессов", contentIntent);
				mNotificationManager.notify(MainActivity.mId, notif);*/
				/*}*/
			} catch (Exception e) {

				Toast.makeText(context, "exception on sms catch",
						Toast.LENGTH_LONG).show();

			}
		}

	}

	public static Context getContext() {

		return context;

	}

	private String markMessageRead(Context context, String body) {

		String result = "";
		Uri uri = Uri.parse("content://sms/inbox");
		String whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER + "\"";

		Cursor cursor = context.getContentResolver().query(uri, null,
				whereClause, null, null);
		try {

			while (cursor.moveToNext()) {
				if (cursor.getInt(cursor.getColumnIndex("read")) == 0) {
					if (cursor.getString(cursor.getColumnIndex("body"))
							.startsWith(body)) {
						String SmsMessageId = cursor.getString(cursor
								.getColumnIndex("_id"));
						ContentValues values = new ContentValues();
						values.put("read", true);
						context.getContentResolver().update(
								Uri.parse("content://sms/inbox"), values,
								"_id=" + SmsMessageId, null);
						result += "succesfully marked";
					}
				}
			}
		} catch (Exception e) {
			Log.e("Mark Read", "Error in Read: " + e.toString());
			result += "exception during marking";
		}
		return result;
	}

}
