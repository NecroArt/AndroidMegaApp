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
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	private static Context context = null;
	
	// TODO delete this
	public static Long sms = 0L;
	public static Long db = 0L;

	@Override
	public void onReceive(Context context, Intent intent) {
		// intent.setFlags(32);

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

				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(context);

				// TODO стоит проверять только первую строку
				if (isNumber000019
						&& (allMessages
								.startsWith(DisplayPanesActivity.keyPhraseRepDBStatus) || allMessages
								.startsWith(DisplayPanesActivity.keyPhraseAbonDynamic))) {
					HandleIncommingSms.setContext(context);
					HandleIncommingSms.setSms(msgs);
					HandleIncommingSms.incNumberRunning();
					(new HandleIncommingSms()).start();

					// TODO delete this?
					isNumber000019 = false;

					if (prefs.getBoolean("abort_broadcast", false)) {

						abortBroadcast();
					}

				}

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(msgs[0].getTimestampMillis());
				sms = msgs[0].getTimestampMillis();

				if (prefs.getBoolean("show_sms_content_on_receive", false)) {

					Toast.makeText(
							context,
							(allMessages.length() == 0 ? "no text"
									: allMessages + "\n" + msg_from),
							Toast.LENGTH_LONG).show();

				}

			} catch (Exception e) {

				Toast.makeText(context, "exception on sms catch",
						Toast.LENGTH_LONG).show();

			}
		}

	}

	public static Context getContext() {

		return context;

	}

	//TODO реализовать или удалить
	@SuppressWarnings("unused")
	private String markMessageRead(Context context, String body) {

		String result = "";
		Uri uri = Uri.parse("content://sms/inbox");
		String whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER
				+ "\"";

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
