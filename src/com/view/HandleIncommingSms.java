package com.view;

import java.util.ArrayList;

import smsParsing.Parser;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import database.DbHelper;
import database.SmsRecord;

public class HandleIncommingSms extends Thread {

	private static int runningThreadAmount = 0;
	private static Context context = null;

	/**
	 * Default constructor.
	 */
	@SuppressWarnings("deprecation")
	public void run() {

		// проверка того, что нет треда, ищущего смс
		if (runningThreadAmount > 0) {

			int iterationsOccured = 0;
			boolean isSmsFound = false;

			try {

				while (!isSmsFound && iterationsOccured < 10) {

					iterationsOccured++;

					// wait while sms will store in database
					Thread.sleep(10000);

					// TODO может быть уже использовать свой статичный контекст?
					DbHelper dbHelper = new DbHelper(SmsReceiver.getContext(),
							null, null, DbHelper.getDBVersion());
					ArrayList<String> smsIds = dbHelper.getSmsIds();
					SMS sms = dbHelper.findLastSms(SmsReceiver.getContext());

					if (sms != null && !smsIds.contains(sms.getId())) {

						isSmsFound = true;

						ArrayList<SmsRecord> recordsArray = Parser.parse(sms);

						for (SmsRecord currentRecord : recordsArray) {

							// TODO delete this
							SmsReceiver.amount++;

							dbHelper.addRecord(currentRecord);

						}

						// создание нотификации
						SharedPreferences prefs = PreferenceManager
								.getDefaultSharedPreferences(context);
						if (recordsArray.size() > 0
								&& prefs.getBoolean(
										"notify_on_sms_receive_enabled", false)) {

							// TODO make notification if not exist
							NotificationManager mNotificationManager = (NotificationManager) context
									.getSystemService(Context.NOTIFICATION_SERVICE);

							Notification notif = new Notification(
									R.drawable.notification,
									"Обновлён статус процессов REP-COMM",
									System.currentTimeMillis());
							notif.flags |= Notification.FLAG_AUTO_CANCEL;
							
							Intent notificationIntent = new Intent(
									context,
									DisplayPanesActivity.class);
							PendingIntent contentIntent = PendingIntent
									.getActivity(context, 0,
											notificationIntent, 0);
							
							notif.setLatestEventInfo(context,
									"Sms-Report Parser",
									"Обновление статуса критических процессов",
									contentIntent);
							mNotificationManager
									.notify(MainActivity.mId, notif);

						}

					}

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
	 * @param context the context to set
	 */
	public static void setContext(Context context) {
		HandleIncommingSms.context = context;
	}

}