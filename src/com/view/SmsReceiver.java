package com.view;

import java.util.ArrayList;
import java.util.Set;

import smsParsing.Parser;

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
import database.DbHelper;
import database.SmsRecord;

public class SmsReceiver extends BroadcastReceiver {
	private SharedPreferences preferences;
	private static Context context = null;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		
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
				for (int i = 0; i < msgs.length; i++) {
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					msg_from = msgs[i].getOriginatingAddress();
					String msgBody = msgs[i].getMessageBody();
					
					if (msg_from.equals("000019")) {
						
						(new Thread(new HandleIncommingSms())).start();
						
					}

					allMessages += msgBody;
				}

				// TODO wait some time while sms insret into database, get and
				// invoke parser of this sms
				/*try {
					System.out.println("go to sleep");
					Thread.sleep(30 * 1000); // in milliseconds
					System.out.println("waiked up");
					//wake up and look up new sms in database

					DbHelper dbHelper = new DbHelper(context, null, null, DbHelper.getDBVersion());
					//TODO get new sms and parse it
					
				} catch (Exception ex) {
					Toast.makeText(context,
							"exception on try sleep when sms was received",
							Toast.LENGTH_LONG).show();
				}*/
				Toast.makeText(context,
						(allMessages.length() == 0 ? "no text" : allMessages),
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(context,
						"exception on sms catch",
						Toast.LENGTH_LONG).show();
			}
		}
		// }
		// FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
	}

	public static Context getContext() {
		
		return context;
		
	}
	private String markMessageRead(Context context, String number, String body) {

		String result = "";
		Uri uri = Uri.parse("content://sms/inbox");
		String whereClause = "address=\"" + number + "\"";
		/*
		 * try { Thread.sleep(10000); } catch (InterruptedException e) { result
		 * = "faild when try sleep; "; }
		 */

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
	
	public class HandleIncommingSms implements Runnable {

	    public void run() {
	    	
	    	int iterationsOccured = 0;
	    	boolean smsFind = false;
	    	
	    	try {
	    		
	    		while(!smsFind && iterationsOccured < 10) {
	    			
	    			iterationsOccured++;
	    			
		    		//wait while sms will store in database
		    		Thread.sleep(30000);
		    		
		    		//TODO get new sms
		    		DbHelper dbHelper = new DbHelper(SmsReceiver.getContext(), null, null, DbHelper.getDBVersion());
		    		ArrayList<SMS> smsArrayList = dbHelper.getNewSms(getContext());
		    		
		    		if (smsArrayList.size() > 0) {
		    			
		    			for (SMS currentSms: smsArrayList) {
		    				
		    				ArrayList<SmsRecord> recordsArray = Parser.parse(currentSms);
		    				
		    				for (SmsRecord currentRecord : recordsArray) {
		    					
		    					dbHelper.addRecord(currentRecord);
		    					
		    				}
		    				
		    			}
		    			
		    		}
		    		
		    		//TODO parse sms and store to database
		    		
	    		}
	    	}
	    	catch (Exception ex) {
	    		System.out.println("exception on sleep of thread");
	    	}
	        
	    }

	}
}
