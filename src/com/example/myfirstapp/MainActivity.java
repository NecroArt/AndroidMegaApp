package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import database.DbHelper;
import database.SmsRecord;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static Integer COLUMN_REQ_AMOUNT = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the Send button */
    public void showMessages(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	Integer rowNumReq = 0;
    	try {
    		rowNumReq = Integer.parseInt(editText.getText().toString());
    	}
    	catch (NumberFormatException ex) {
    		/*rowNumReq = 0;*/
    		//action not required
    	}
    	String message = getSMS(rowNumReq);
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    public String getSMS(Integer rowNumReq) {
    	
    	StringBuilder smsBuilder = new StringBuilder();
        final String SMS_URI_INBOX = "content://sms/inbox";
        try {
             Uri uri = Uri.parse(SMS_URI_INBOX);  
             String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
             
             //interesting sms only from 000019
             String whereClause = "address=\"000019\"";
             
             //fetching sms with order by date
             Cursor cur = getContentResolver().query(uri, projection, whereClause, null, " date" + (rowNumReq > 0 ? " limit 0, " + rowNumReq.toString(): ""));
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

                     smsBuilder.append("[ ");
                     smsBuilder.append(strAddress + ", ");
                     smsBuilder.append(intPerson + ", ");
                     smsBuilder.append(strbody + ", ");
                     smsBuilder.append(new Date(longDate)+ ", ");
                     smsBuilder.append(int_Type);
                     smsBuilder.append(" ]\n\n");
                 } while (cur.moveToNext());

                 if (!cur.isClosed()) {
                     cur.close();
                     cur = null;
                 }
             } else {
                 smsBuilder.append("no result!");
             }
         }
         catch (SQLiteException ex) {
        	 smsBuilder.append("SQLiteException" + ex.getMessage());
         }
         
         return smsBuilder.toString();
    }
    
    public void showTable(View view) {
    	Intent intent = new Intent(this, DisplayTableActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	intent.putExtra(EXTRA_MESSAGE, editText.getText().toString());
    	startActivity(intent);
    }
    
    public void syncData (View view) {
    	DbHelper dbHandler = new DbHelper(this, null, null, 1);
    	
    	//TODO get sms
    	
    	//TODO insert statement
    	ArrayList<SmsRecord> array = getSMSArrayList(0);
    	for (SmsRecord currentSms: array) {
    		//TODO add to database
    		dbHandler.addRecord(currentSms.getDate().toString(), currentSms.getParameterName(), currentSms.getParameterValue());
    	}
    }
    
    /**
     * Fetch sms from phone memory using ContentProvider. Look up sms that only received from number 000019. 
     * @param rowNumReq - number of required sms amount. 
     * @return ArrayList of SmsRecord.
     */
	ArrayList<SmsRecord> getSMSArrayList(Integer rowNumReq) {
		ArrayList<SmsRecord> array = new ArrayList<SmsRecord>();
		final String SMS_URI_INBOX = "content://sms/inbox";
		try {
			Uri uri = Uri.parse(SMS_URI_INBOX);
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };

			// interesting sms only from 000019
			String whereClause = "address=\"000019\"";

			// fetching sms with order by date
			Cursor cur = getContentResolver().query(
					uri,
					projection,
					whereClause,
					null,
					" date asc"
							+ (rowNumReq > 0 ? " limit 0, "
									+ rowNumReq.toString() : ""));
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
					SmsRecord newSMS = new SmsRecord(1, currentCalendarDate,
							"name", "value");

					array.add(newSMS);
				} while (cur.moveToNext());

				if (!cur.isClosed()) {
					cur.close();
					cur = null;
				}
			}
		} catch (SQLiteException ex) {
			System.out.println("sql-exception occured");
		} catch (NullPointerException ex) {
			System.out.println("null pointer exception occured");
		}

		return array;
	}
	
	public void showAddedSms(View view) {
		//TODO show sms from database
		ArrayList<SmsRecord> array = DbHelper.getAll();
		Intent intent = new Intent(this, DisplayTableActivity.class);
		
    	intent.putParcelableArrayListExtra("SmsRecordsArray", array);
	}
}
