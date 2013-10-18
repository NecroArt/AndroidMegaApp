package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

import smsParsing.Parser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
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
    	Test testClass = new Test();
    	String message = testClass.getSMS(rowNumReq);
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    public void showTable(View view) {
    	
    	Intent intent = new Intent(this, DisplayTableActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	intent.putExtra(EXTRA_MESSAGE, editText.getText().toString());
    	startActivity(intent);
    }
    
    public void syncData (View view) {
    	DbHelper dbHandler = new DbHelper(this, null, null, DbHelper.getDBVersion());
    	
    	//TODO get sms
    	
    	//TODO insert statement
    	ArrayList<SmsRecord> array = getSMSRecordArrayList(0);
    	for (SmsRecord currentSms: array) {
    		//TODO add to database
    		//dbHandler.addRecord("0", currentSms.getDate().toString(), currentSms.getParameterName(), currentSms.getParameterValue());
    	}
    }
    
    /**
     * Fetch sms from phone memory using ContentProvider. Look up sms that only received from number 000019. 
     * @param rowNumReq - number of required sms amount. 
     * @return ArrayList of SmsRecord.
     */
	ArrayList<SmsRecord> getSMSRecordArrayList(Integer rowNumReq) {
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
					SmsRecord newSMS = new SmsRecord("1", currentCalendarDate,
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
	
	public void deleteAll(View view) {
		
		DbHelper dbHelper = new DbHelper(this, null, null, DbHelper.getDBVersion());
		Integer deletedRows = dbHelper.deleteAll();
		Toast.makeText(this, deletedRows > 0 ? "deleted" + deletedRows.toString() + "rows" : "table already empty" , Toast.LENGTH_LONG).show();
		
	}
	
	public void addAll(View view) {
		
		DbHelper dbHelper = new DbHelper(this, null, null, DbHelper.getDBVersion());
		
		ArrayList<SMS> SMSArray = new ArrayList<SMS>(); 
        SMSArray = getSMSArrayList(0);
        
        int rowsAdded = 0;
        
        Set<String> ids = new TreeSet<String>() ;
        ids = dbHelper.getSmsIds();
        ArrayList<SmsRecord> globalArray = new ArrayList<SmsRecord>();
        for (SMS currentSms: SMSArray) {
        	
        	//check, that current sms is not already in database
        	if (!ids.contains(currentSms.getId())) {
	        	
        		ArrayList<SmsRecord> recordsArray = Parser.parse(currentSms);
	        	for (SmsRecord cur: recordsArray) {
	        		globalArray.add(cur);
	        	}
	        	
	        	for (SmsRecord currentRecord: recordsArray) {
	        		
	        		dbHelper.addRecord(currentRecord);
	        		rowsAdded++;
	        		
	        	}
	        	
        	}
        }
        
        Toast.makeText(this, "added " + rowsAdded + " rows", Toast.LENGTH_LONG).show();
        
	}
	
	public ArrayList<SMS> getSMSArrayList (Integer rowNumReq) {
		
		ArrayList<SMS> array = new ArrayList<SMS>();

		final String SMS_URI_INBOX = "content://sms/inbox";
		try {
             Uri uri = Uri.parse(SMS_URI_INBOX);  
             String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
             
             //interesting sms only from 000019
             //TODO clear this
             //String whereClause = "address=\"000019\"";
             String whereClause = "address=\"15555215554\"";
             
             //fetching sms with order by date
             Cursor cur = getContentResolver().query(uri, projection, whereClause, null, " date desc" + (rowNumReq > 0 ? " limit 0, " + rowNumReq.toString(): ""));
             if (cur.moveToFirst()) {
            	 int index_id = cur.getColumnIndex("_id");
            	 int index_Body = cur.getColumnIndex("body");
                 int index_Date = cur.getColumnIndex("date");
                 do {
                	 String strId = cur.getString(index_id);
                	 
            	     String strbody = cur.getString(index_Body);
                     long longDate = cur.getLong(index_Date);
                     
                     Calendar currentCalendarDate = Calendar.getInstance();
                     currentCalendarDate.setTimeInMillis(longDate);
                     
                     //TODO clear this
                     /*String [] stringArray = strbody.split("\n");
                     SMS newSMS = new SMS(stringArray[2], currentCalendarDate);*/
                     
                     SMS newSMS = new SMS(strId, strbody, currentCalendarDate);
                     
                     array.add(newSMS);
                     //TODO clear this
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
        	 
        	//TODO delete this
        	 Context context = getApplicationContext();
             Toast.makeText(context, "sql-exception occured", Toast.LENGTH_LONG).show();
         	
         }
        
        return array;
	}
}
