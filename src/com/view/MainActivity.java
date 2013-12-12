package com.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import database.DbHelper;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static Integer COLUMN_REQ_AMOUNT = 0;
	public final static String TELEPHONE_NUMBER = "000019";
	private static final int RESULT_SETTINGS = 1;
	//public final static String TELEPHONE_NUMBER = "15555215556";
	public static String lastSmsDate = "��� ������ �� sms";
	
	public static String my_text = null;
	public static String text = null;
	public static Locale locale = null;
	private static int mId;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView lastSmsDateTextView = (TextView) findViewById(R.id.last_sms_date);
		
		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());
		//dbHelper.getNewSms(this);
		Long millis = dbHelper.getLastSmsDate();
		locale = getResources().getConfiguration().locale;
		if (millis != 0L) {
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(millis);
			lastSmsDate = date.get(Calendar.DAY_OF_MONTH)
					+ " "
					+ new SimpleDateFormat("MMMM", MainActivity.locale).format(date.getTime())
					+ " "
					+ String.format("%02d:%02d:%02d",
							date.get(Calendar.HOUR_OF_DAY),
							date.get(Calendar.MINUTE), date.get(Calendar.SECOND));
		}
		lastSmsDateTextView.setText(lastSmsDate);
		
		
		//TODO make normal notification
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this).setSmallIcon(R.drawable.notif).setContentTitle("My notification").setContentText("Hello World!");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(mId, mBuilder.build());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onRestart () {
		
		onCreate(null);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	    switch (item.getItemId()){
	        case R.id.action_settings:
	        	Intent i = new Intent(this, SettingsActivity.class);
	        	startActivityForResult(i, RESULT_SETTINGS);
	        	break;

	    }
	    //return super.onOptionsItemSelected(item);
	    return true;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        switch (requestCode) {
        case RESULT_SETTINGS:
            
            break;
 
        }
 
    }
	
	public void showMessages(View view) {

		// Intent intent = new Intent(this, DisplayMessageActivity.class);
		Intent intent = new Intent(this, Test.class);
		/*
		 * EditText editText = (EditText) findViewById(R.id.edit_message);
		 * Integer rowNumReq = 0; try { rowNumReq =
		 * Integer.parseInt(editText.getText().toString()); } catch
		 * (NumberFormatException ex) {
		 * 
		 * } Test testClass = new Test(); String message =
		 * testClass.getSMS(this, rowNumReq); intent.putExtra(EXTRA_MESSAGE,
		 * message);
		 */
		startActivity(intent);
		// Toast.makeText(this, String.valueOf(SmsReceiver.sms) + " = " +
		// String.valueOf(SmsReceiver.db), Toast.LENGTH_LONG).show();

	}

	public void showTable(View view) {

		Intent intent = new Intent(this, DisplayTableActivity.class);
		startActivity(intent);
	}

	public void deleteAll(View view) {

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());
		Integer deletedRows = dbHelper.deleteAll();
		Toast.makeText(
				this,
				deletedRows > 0 ? "deleted " + deletedRows.toString() + " rows"
						: "table already empty", Toast.LENGTH_LONG).show();

	}

	public void addAll(View view) {

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		//EditText editText = (EditText) findViewById(R.id.edit_message);
		Integer rowsAdded = 0;

		/*try {
			Integer smsNumber = Integer.valueOf(editText.getText().toString());
			if (smsNumber > 0) {
				rowsAdded = dbHelper.addAll(this, smsNumber);
			} else {
				rowsAdded = dbHelper.addAll(this);
			}
		} catch (NumberFormatException ex) {*/
			rowsAdded = dbHelper.addAll(this);
		//}

		Toast.makeText(this, "added " + rowsAdded + " rows", Toast.LENGTH_LONG)
				.show();

	}

	/*public void deleteSms(View view) {

		EditText editText = (EditText) findViewById(R.id.edit_message);
		Integer reqId = 0;
		try {
			reqId = Integer.parseInt(editText.getText().toString());
		} catch (NumberFormatException ex) {

		}

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		Integer rowsDeleted = 0;

		if (reqId > 0) {
			rowsDeleted = dbHelper.deleteBySmsId(reqId);
		}

		Toast.makeText(this, "deleted " + rowsDeleted + " rows",
				Toast.LENGTH_LONG).show();

	}

	public void deleteRecord(View view) {

		EditText editText = (EditText) findViewById(R.id.edit_message);
		Integer reqId = 0;
		try {
			reqId = Integer.parseInt(editText.getText().toString());
		} catch (NumberFormatException ex) {

		}

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		Integer rowsDeleted = 0;

		if (reqId > 0) {
			rowsDeleted = dbHelper.deleteById(reqId);
		}

		Toast.makeText(this, "deleted " + rowsDeleted + " rows",
				Toast.LENGTH_LONG).show();

	}*/

	public void dropDatabase(View view) {

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());
		try {

			dbHelper.recreateDatabase();
			Toast.makeText(this, "database successfully recreated",
					Toast.LENGTH_LONG).show();

		} catch (Exception ex) {

			Toast.makeText(this,
					"some exception was occured on recreate database",
					Toast.LENGTH_LONG).show();

		}

	}

	public void findByName(View view) {

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

	}

	public void showPanes(View view) {

		Intent intent = new Intent(this, DisplayPanesActivity.class);
		startActivity(intent);

	}

	public void showCodes(View view) {

		Intent intent = new Intent(this, TestShowCodes.class);

		startActivity(intent);
	}
	
	public static void writeLog (Exception ex) {
		
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath() + "/myLogcat");
		dir.mkdirs();
		File file = new File(dir, "logcat.txt");
		try {
			// to write logcat in text file
			FileOutputStream fOut = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);

			// Write the string to the file
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			sw.toString();
			osw.write(sw.toString());
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showPreferences(View view) {

		Intent intent = new Intent(this, SettingsActivity.class);

		startActivity(intent);
	}

}
