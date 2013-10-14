package com.example.myfirstapp;

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

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		// if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
		Bundle bundle = intent.getExtras(); // ---get the SMS message passed
											// in---
		SmsMessage[] msgs = null;
		String msg_from;
		if (bundle != null) {
			// ---retrieve the SMS message received---
			try {
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];
				String allMEssages = "";
				for (int i = 0; i < msgs.length; i++) {
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					msg_from = msgs[i].getOriginatingAddress();
					String msgBody = msgs[i].getMessageBody();
					
					//test
					String MarkResult = markMessageRead(context, msg_from, msgBody);
					
					allMEssages += MarkResult + " - " + msgBody;
				}
				Toast.makeText(context,
						(allMEssages.length() == 0 ? "no text" : allMEssages),
						Toast.LENGTH_SHORT).show();

			} catch (Exception e) {
				// Log.d("Exception caught",e.getMessage());
			}
		}
		// }
		// FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
	}

	private String markMessageRead(Context context, String number, String body) {

		String result = "";
		Uri uri = Uri.parse("content://sms/inbox");
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, null);
		try {

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				result = "faild when try sleep; ";
			}
			while (cursor.moveToNext()) {
				if ((cursor.getString(cursor.getColumnIndex("address"))
						.equals(number))
						&& (cursor.getInt(cursor.getColumnIndex("read")) == 0)) {
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
		finally {
			return result;
		}
	}
}
