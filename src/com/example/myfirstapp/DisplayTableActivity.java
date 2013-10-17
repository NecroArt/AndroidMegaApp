package com.example.myfirstapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import smsParsing.Parser;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import database.DbHelper;
import database.SmsRecord;



public class DisplayTableActivity extends Activity {
	
	@SuppressLint("NewApi") 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Context context = getApplicationContext();
		System.out.println("start");
        Toast.makeText(context, "start read", Toast.LENGTH_LONG).show();
        System.out.println("start read");
        /*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.out.println("faild when try sleep");
		}*/
        
        Intent intent = getIntent();
        Integer rowNumReq = 0;
    	try {
    		rowNumReq = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
    	}
    	catch (NumberFormatException ex)
    	{
    		/*rowNumReq = 0;*/
    		//action not required
    	}
    	
    	ArrayList<SMS> SMSArray = new ArrayList<SMS>(); 
        SMSArray = getSMSArrayList(rowNumReq);
        
        Toast.makeText(context, "successfully read", Toast.LENGTH_LONG).show();
        System.out.println("successfully read");
        /*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.out.println("faild when try sleep");
		}*/
        
        //TODO delete this
        ArrayList<SmsRecord> recordsArray = new ArrayList<SmsRecord>();
        for (SMS currentSms: SMSArray) {
        	
        	//TODO uncomment this
        	//ArrayList<SmsRecord> recordsArray = Parser.parse(currentSms);
        	//TODO delete this
        	Toast.makeText(context, "start parse", Toast.LENGTH_LONG).show();
        	System.out.println("start parse");
        	/*try {
    			Thread.sleep(10000);
    		} catch (InterruptedException e) {
    			System.out.println("faild when try sleep");
    		}*/
        	recordsArray = Parser.parse(currentSms);
        	Toast.makeText(context, "successfully parsed", Toast.LENGTH_LONG).show();
        	System.out.println("successfully parsed");
        	/*try {
    			Thread.sleep(10000);
    		} catch (InterruptedException e) {
    			System.out.println("faild when try sleep");
    		}*/
        	
        	/*Toast.makeText(context, "start add", Toast.LENGTH_LONG).show();
        	for (SmsRecord currentRecord: recordsArray) {
        		
        		DbHelper dbHelper = new DbHelper(this, null, null, 1);
        		dbHelper.addRecord(currentRecord);
        		
        	}
        	Toast.makeText(context, "successfully add", Toast.LENGTH_LONG).show();*/
        }
        
        TableLayout SMSTable = new TableLayout(this);
        SMSTable.setStretchAllColumns(true);  
        SMSTable.setShrinkAllColumns(true);
        
        for (SmsRecord currentSMS: recordsArray) {
        	TableRow currentRow = new TableRow(this);  
        	currentRow.setGravity(Gravity.CENTER_HORIZONTAL);
        	
        	/*TextView SMSDateView = new TextView(this);
        	SMSDateView.setText(currentSMS.getDate().get(Calendar.DAY_OF_MONTH) + " " + new SimpleDateFormat("MMMM").format(currentSMS.getDate().getTime()) + 
    				" " + String.format("%02d:%02d", 
    						currentSMS.getDate().get(Calendar.HOUR_OF_DAY), 
    						currentSMS.getDate().get(Calendar.MINUTE)));
        	currentRow.addView(SMSDateView);

        	TextView SMSTextView = new TextView(this);
        	SMSTextView.setText(currentSMS.getContent());
        	currentRow.addView(SMSTextView);*/
        	
        	TextView SMSDateView = new TextView(this);
        	SMSDateView.setText(currentSMS.getDate().get(Calendar.DAY_OF_MONTH) + " " + new SimpleDateFormat("MMMM").format(currentSMS.getDate().getTime()) + 
    				" " + String.format("%02d:%02d", 
    						currentSMS.getDate().get(Calendar.HOUR_OF_DAY), 
    						currentSMS.getDate().get(Calendar.MINUTE)));
        	currentRow.addView(SMSDateView);
        	
        	TextView SMSParamName = new TextView(this);
        	SMSParamName.setText(currentSMS.getParameterName());
        	currentRow.addView(SMSParamName);
        	
        	TextView SMSParamValue = new TextView(this);
        	SMSParamValue.setText(currentSMS.getParameterValue());
        	currentRow.addView(SMSParamValue);
        	
        	SMSTable.addView(currentRow);
        }
        
        ScrollView tableScrollView = new ScrollView(this);
        tableScrollView.addView(SMSTable);
        
        setContentView(tableScrollView);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public ArrayList<SMS> getSMSArrayList (Integer rowNumReq) {
		ArrayList<SMS> array = new ArrayList<SMS>();
		final String SMS_URI_INBOX = "content://sms/inbox";
		try {
             Uri uri = Uri.parse(SMS_URI_INBOX);  
             String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
             
             //interesting sms only from 000019
             String whereClause = "address=\"000019\"";
             //String whereClause = "address=\"15555215554\"";
             
             //fetching sms with order by date
             Cursor cur = getContentResolver().query(uri, projection, whereClause, null, " date asc" + (rowNumReq > 0 ? " limit 0, " + rowNumReq.toString(): ""));
             if (cur.moveToFirst()) {
            	 int index_id = cur.getColumnIndex("_id");
            	 int index_Address = cur.getColumnIndex("address");
                 int index_Person = cur.getColumnIndex("person");
                 int index_Body = cur.getColumnIndex("body");
                 int index_Date = cur.getColumnIndex("date");
                 int index_Type = cur.getColumnIndex("type");
                 do {
                	 String strId = cur.getString(index_id);
                	 
            	     String strAddress = cur.getString(index_Address);
                     int intPerson = cur.getInt(index_Person);
                     String strbody = cur.getString(index_Body);
                     long longDate = cur.getLong(index_Date);
                     int int_Type = cur.getInt(index_Type);

                     Calendar currentCalendarDate = Calendar.getInstance();
                     currentCalendarDate.setTimeInMillis(longDate);
                     
                     /*String [] stringArray = strbody.split("\n");
                     SMS newSMS = new SMS(stringArray[2], currentCalendarDate);*/
                     
                     SMS newSMS = new SMS(strId, strbody, currentCalendarDate);
                     
                     array.add(newSMS);
                     /*smsBuilder.append("[ ");
                     smsBuilder.append(strAddress + ", ");
                     smsBuilder.append(intPerson + ", ");
                     smsBuilder.append(strbody + ", ");
                     smsBuilder.append(new Date(longDate)+ ", ");
                     smsBuilder.append(int_Type);
                     smsBuilder.append(" ]\n\n");*/
                 } while (cur.moveToNext());

                 if (!cur.isClosed()) {
                     cur.close();
                     cur = null;
                 }
             }
         }
         catch (SQLiteException ex) {
        	 System.out.println("sql-exception occured");
         }
        
        return array;
	}
	
}
