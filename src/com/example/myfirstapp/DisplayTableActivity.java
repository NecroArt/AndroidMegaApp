package com.example.myfirstapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
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
    	
    	intent.getParcelableArrayListExtra("SmsRecordsArray");
    	
        ArrayList<SMS> SMSArray = new ArrayList<SMS>(); 
        SMSArray = getSMSArrayList(rowNumReq);
        
        TableLayout SMSTable = new TableLayout(this);
        SMSTable.setStretchAllColumns(true);  
        SMSTable.setShrinkAllColumns(true);
        
        for (SMS currentSMS: SMSArray) {
        	TableRow currentRow = new TableRow(this);  
        	currentRow.setGravity(Gravity.CENTER_HORIZONTAL);
        	
        	TextView SMSDateView = new TextView(this);
        	SMSDateView.setText(currentSMS.date.get(Calendar.DAY_OF_MONTH) + " " + new SimpleDateFormat("MMMM").format(currentSMS.date.getTime()) + 
    				" " + String.format("%02d:%02d", 
    						currentSMS.date.get(Calendar.HOUR_OF_DAY), 
    						currentSMS.date.get(Calendar.MINUTE)));
        	currentRow.addView(SMSDateView);

        	TextView SMSTextView = new TextView(this);
        	SMSTextView.setText(currentSMS.content);
        	currentRow.addView(SMSTextView);
        	
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
             
             //fetching sms with order by date
             Cursor cur = getContentResolver().query(uri, projection, whereClause, null, " date asc" + (rowNumReq > 0 ? " limit 0, " + rowNumReq.toString(): ""));
             if (cur.moveToFirst()) {
            	 int index_Address = cur.getColumnIndex("address");
                 int index_Person = cur.getColumnIndex("person");
                 int index_Body = cur.getColumnIndex("body");
                 int index_Date = cur.getColumnIndex("date");
                 int index_Type = cur.getColumnIndex("type");
                 do {
            	     String strAddress = cur.getString(index_Address);
                     int intPerson = cur.getInt(index_Person);
                     String strbody = cur.getString(index_Body);
                     long longDate = cur.getLong(index_Date);
                     int int_Type = cur.getInt(index_Type);

                     Calendar currentCalendarDate = Calendar.getInstance();
                     currentCalendarDate.setTimeInMillis(longDate);
                     SMS newSMS = new SMS(strbody, currentCalendarDate);
                     
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

	public class SMS {
		String content;
		Calendar date;
		
		public SMS (String content, Calendar date){
			this.content = content;
			this.date = date;
		}
		
		public String getContent () {
			return this.content;
		}
		
		public Calendar getDate () {
			return this.date;
		}
	}
}
