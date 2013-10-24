package com.view;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	private SharedPreferences preferences;
	private static Context context = null;
	public static int amount = 0;
	
	//TODO delete this
	public static Long sms = 0L;
	public static Long db = 0L;

	@Override
	public void onReceive(Context context, Intent intent) {

		SmsReceiver.context = context;
		Bundle bundle = intent.getExtras(); // ---get the SMS message passed
											// in---
		SmsMessage[] msgs = null;
		String msg_from;
		if (bundle != null) {
			// ---retrieve the SMS message received---
			try {
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];

				String allMessages = "";

				// if full message body will be contained in several
				// sms-messages, than msgs.length will > 1
				for (int i = 0; i < msgs.length; i++) {
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					msg_from = msgs[i].getOriginatingAddress();

					String msgBody = msgs[i].getMessageBody();

					if (HandleIncommingSms.getNumberRunning() == 0
							&& msg_from.equals(MainActivity.TELEPHONE_NUMBER)) {

						HandleIncommingSms.incNumberRunning();
						(new HandleIncommingSms(msgs[0].getTimestampMillis())).start();

					}

					allMessages += msgBody;

				}

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(msgs[0].getTimestampMillis());
				sms = msgs[0].getTimestampMillis();

				Toast.makeText(
						context,
						(allMessages.length() == 0 ? "no text" : allMessages + "\n"
								+ String.valueOf(cal.getTime())),
						Toast.LENGTH_LONG).show();

			} catch (Exception e) {

				Toast.makeText(context, "exception on sms catch",
						Toast.LENGTH_LONG).show();

			}
		}

	}

	public static Context getContext() {

		return context;

	}

	private String markMessageRead(Context context, String number, String body) {

		String result = "";
		Uri uri = Uri.parse("content://sms/inbox");
		String whereClause = "address=\"" + number + "\"";
		
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
