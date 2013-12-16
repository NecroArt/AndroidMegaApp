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

	private Long timeInMillis = 0L;

	/**
	 * Default constructor.
	 */
	/*
	 * public HandleIncommingSms () {
	 * 
	 * }
	 */
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
								.getDefaultSharedPreferences(MainActivity.context);
						prefs.getBoolean(
								"notify_on_sms_receive_endabled", false);
						if (recordsArray.size() > 0
								&& prefs.getBoolean(
										"notify_on_sms_receive_endabled", false)) {

							// TODO make notification if not exist
							NotificationManager mNotificationManager = (NotificationManager) MainActivity.context
									.getSystemService(Context.NOTIFICATION_SERVICE);

							@SuppressWarnings("deprecation")
							Notification notif = new Notification(
									R.drawable.notification,
									"Обновлён статус процессов REP-COMM",
									System.currentTimeMillis());
							notif.flags |= Notification.FLAG_AUTO_CANCEL;
							Intent notificationIntent = new Intent(
									MainActivity.context,
									DisplayPanesActivity.class);
							PendingIntent contentIntent = PendingIntent
									.getActivity(MainActivity.context, 0,
											notificationIntent, 0);
							notif.setLatestEventInfo(MainActivity.context,
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

}