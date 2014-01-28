package com.view;

import java.util.ArrayList;

import smsParsing.Parser;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import database.DbHelper;
import database.SmsRecord;

public class HandleIncommingSms extends Thread {

	private static int runningThreadAmount = 0;
	private static Context context = null;
	private static SmsMessage[] msgs = null;

	/**
	 * Сколько миллисекунд должен ждать поток, прежде чем пытаться найти смс в
	 * базе данных.
	 */
	private static int millisToSleep = 5000;

	/**
	 * Default constructor.
	 */
	@SuppressWarnings("deprecation")
	public void run() {

		// проверка того, что нет треда, ищущего смс
		if (runningThreadAmount == 1) {

			try {

				// TODO может быть уже использовать свой статичный контекст?
				DbHelper dbHelper = new DbHelper(SmsReceiver.getContext(),
						null, null, DbHelper.getDBVersion());
				dbHelper.close();
				ArrayList<String> smsIds = dbHelper.getSmsIds();

				String allMessages = "";

				String msg_from = null;
				boolean isNumber000019 = false;
				// if full message body will be contained in several
				// sms-messages, than msgs.length will > 1
				for (int i = 0; i < msgs.length; i++) {

					msg_from = msgs[i].getOriginatingAddress();
					//TODO test

					String msgBody = msgs[i].getMessageBody();

					if (msg_from.equals(MainActivity.TELEPHONE_NUMBER)
							&& !isNumber000019) {

						isNumber000019 = true;

					}

					allMessages += msgBody;

				}

				ArrayList<SmsRecord> recordsArray = Parser.parse(allMessages, msgs[0].getIndexOnIcc());

				for (SmsRecord currentRecord : recordsArray) {

					// TODO delete this
					SmsReceiver.amount++;

					dbHelper.addRecord(currentRecord);

				}

				// создание нотификации
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(context);

				/*if (prefs.getBoolean("add_sms", false)) {

					ContentValues values = new ContentValues();
					values.put("address", msg_from);
					values.put("body", allMessages);
					// TODO может быть уже использовать свой статичный контекст?
					SmsReceiver.getContext().getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
				}*/

				if (recordsArray.size() > 0
						&& prefs.getBoolean("notify_on_sms_receive_enabled",
								false)) {

					// TODO make notification if not exist
					NotificationManager mNotificationManager = (NotificationManager) context
							.getSystemService(Context.NOTIFICATION_SERVICE);

					Notification notif = new Notification(
							R.drawable.notification,
							"Обновлён статус процессов REP-COMM",
							System.currentTimeMillis());
					notif.flags |= Notification.FLAG_AUTO_CANCEL;

					Intent notificationIntent = new Intent(context,
							DisplayPanesActivity.class);
					PendingIntent contentIntent = PendingIntent.getActivity(
							context, 0, notificationIntent, 0);

					notif.setLatestEventInfo(context, "Sms-Report Parser",
							"Обновление статуса критических процессов",
							contentIntent);
					mNotificationManager.notify(MainActivity.mId, notif);

				}

				decNumberRunning();

			} catch (Exception ex) {

				decNumberRunning();
				MainActivity.writeLog(ex);

			}
		}
	}

	public static void incNumberRunning() {

		runningThreadAmount++;

	}

	public static void decNumberRunning() {

		runningThreadAmount--;

	}

	public static int getNumberRunning() {

		return runningThreadAmount;

	}

	/**
	 * @return the context
	 */
	public static Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public static void setContext(Context context) {
		HandleIncommingSms.context = context;
	}

	public static void setSms(SmsMessage[] msgs) {
		HandleIncommingSms.msgs = msgs.clone();

	}

}