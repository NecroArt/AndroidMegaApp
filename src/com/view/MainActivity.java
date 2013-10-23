package com.view;

import com.view.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import database.DbHelper;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static Integer COLUMN_REQ_AMOUNT = 0;
	public final static String TELEPHONE_NUMBER = "000019";
	//public final static String TELEPHONE_NUMBER = "15555215556";
	
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
    	/*Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	Integer rowNumReq = 0;
    	try {
    		rowNumReq = Integer.parseInt(editText.getText().toString());
    	}
    	catch (NumberFormatException ex) {
    		
    	}
    	Test testClass = new Test();
    	String message = testClass.getSMS(this, rowNumReq);
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);*/
    	Toast.makeText(this, String.valueOf(SmsReceiver.amount), Toast.LENGTH_LONG).show();
    }
    
    public void showTable(View view) {
    	
    	Intent intent = new Intent(this, DisplayTableActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	intent.putExtra(EXTRA_MESSAGE, editText.getText().toString());
    	startActivity(intent);
    }
    
	public void deleteAll(View view) {
		
		DbHelper dbHelper = new DbHelper(this, null, null, DbHelper.getDBVersion());
		Integer deletedRows = dbHelper.deleteAll();
		Toast.makeText(this, deletedRows > 0 ? "deleted" + deletedRows.toString() + "rows" : "table already empty" , Toast.LENGTH_LONG).show();
		
	}
	
	public void addAll(View view) {
		
		DbHelper dbHelper = new DbHelper(this, null, null, DbHelper.getDBVersion());
		
		Integer rowsAdded = dbHelper.addAll(this);
		
        Toast.makeText(this, "added " + rowsAdded + " rows", Toast.LENGTH_LONG).show();
        
	}
	
	
	
	public void dropDatabase(View view) {
		
		DbHelper dbHelper = new DbHelper(this, null, null, DbHelper.getDBVersion());
		try {
			
			dbHelper.recreateDatabase();
			Toast.makeText(this, "database successfully recreated", Toast.LENGTH_LONG).show();
			
		} catch (Exception ex) {
			
			Toast.makeText(this, "some exception was occured on recreate database", Toast.LENGTH_LONG).show();
			
		}
		
	}
	
}
