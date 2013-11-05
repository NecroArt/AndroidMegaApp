package com.view;

import java.util.ArrayList;

import smsParsing.Parser;
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

		// check that programmer sure that tread run
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
					SMS sms = dbHelper.findLastSms(SmsReceiver.getContext());

					if (sms != null) {

						isSmsFound = true;

						ArrayList<SmsRecord> recordsArray = Parser.parse(sms);

						for (SmsRecord currentRecord : recordsArray) {

							// TODO delete this
							SmsReceiver.amount++;

							dbHelper.addRecord(currentRecord);

						}

					}

				}

				decNumberRunning();

			} catch (Exception ex) {

				decNumberRunning();
				System.out.println("exception on sleep of thread");
				ex.printStackTrace();

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