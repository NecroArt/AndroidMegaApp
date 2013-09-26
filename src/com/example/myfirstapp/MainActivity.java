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
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/draft"), null, null, null, null);
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

    	return msgData;
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
