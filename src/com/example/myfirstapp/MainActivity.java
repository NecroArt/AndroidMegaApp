package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
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
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	//String message = editText.getText().toString();
    	String message = readTestSms();
    	//getSMS();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    public String readTestSms() {
    	
    	//------------------
    	StringBuilder smsBuilder = new StringBuilder();
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_ALL = "content://sms/";
         try {  
             Uri uri = Uri.parse(SMS_URI_INBOX);  
             String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };  
             Cursor cur = getContentResolver().query(uri, projection, null/*where_clause*/, null, "date desc");
              if (cur.moveToFirst()) {  
                 int index_Address = cur.getColumnIndex("address");  
                 int index_Person = cur.getColumnIndex("person");  
                 int index_Body = cur.getColumnIndex("body");  
                 int index_Date = cur.getColumnIndex("date");  
                 int index_Type = cur.getColumnIndex("type");         
                 //do {  
                     String strAddress = cur.getString(index_Address);  
                     int intPerson = cur.getInt(index_Person);  
                     String strbody = cur.getString(index_Body);  
                     long longDate = cur.getLong(index_Date);  
                     int int_Type = cur.getInt(index_Type);  

                     smsBuilder.append("[ ");  
                     smsBuilder.append(strAddress + ", ");  
                     smsBuilder.append(intPerson + ", ");  
                     smsBuilder.append(strbody + ", ");  
                     smsBuilder.append(longDate + ", ");  
                     smsBuilder.append(int_Type);  
                     smsBuilder.append(" ]\n\n");  
                 //} while (cur.moveToNext());  

                 //if (!cur.isClosed()) {  
                     cur.close();  
                     cur = null;  
                 //}  
             } else {  
                 smsBuilder.append("no result!");
             } // end if
         }
         catch (SQLiteException ex) {  
             System.out.println("SQLiteException" + ex.getMessage());
         }  
    	//------------------
        /*Cursor cursor = getContentResolver().query(Uri.parse("content://sms/draft"), null, null, null, null);
    	cursor.moveToFirst();
    	
    	String msgData = "";
    	
    	do{
    		for(int idx=0;idx<cursor.getColumnCount();idx++)
    		{
    			//msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
    			if (cursor.getColumnName(idx).compareTo("body") == 0) {
    				msgData = cursor.getString(idx);
    			}
    		}
    	}while(cursor.moveToNext());

    	return msgData;*/
	return smsBuilder.toString();
    }
    
    public List<String> getSMS(){
        List<String> sms = new ArrayList<String>();
           Uri uriSMSURI = Uri.parse("content://sms/inbox");
           Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);

           while (cur.moveToNext()) {
                  String address = cur.getString(cur.getColumnIndex("address"));
                  String body = cur.getString(cur.getColumnIndexOrThrow("body"));
                 sms.add("Number: " + address + " .Message: " + body);  

             }
           return sms;
    }
}
