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
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import database.DbHelper;

public class MainActivity extends Activity {

	public final static Integer COLUMN_REQ_AMOUNT = 0;
	public final static String TELEPHONE_NUMBER = "000019";
	private static final int RESULT_SETTINGS = 1;
	public static String lastSmsDate = "Нет данных об sms";

	private static Ringtone r = null;

	public static String my_text = null;
	public static String text = null;
	
	//TODO выпилить?
	public static Locale locale = null;
	
	public static int mId;
	private static boolean firstLaunch = true;
	public static Context context = null;
	public static String keyPhrase = "Статус критичных процессов REP-COMM";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);

			TextView lastSmsDateTextView = (TextView) findViewById(R.id.last_sms_date_old);

			DbHelper dbHelper = new DbHelper(this, null, null,
					DbHelper.getDBVersion());

			Long millis = dbHelper.getLastSmsDate();
			locale = getResources().getConfiguration().locale;
			if (millis != 0L) {
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(millis);
				lastSmsDate = date.get(Calendar.DAY_OF_MONTH)
						+ " "
						+ new SimpleDateFormat("MMMM", MainActivity.locale)
								.format(date.getTime())
						+ " "
						+ String.format("%02d:%02d:%02d",
								date.get(Calendar.HOUR_OF_DAY),
								date.get(Calendar.MINUTE),
								date.get(Calendar.SECOND));
			}
			lastSmsDateTextView.setText(lastSmsDate);

			context = this;

			if (firstLaunch) {
				// makeNotification();
				firstLaunch = false;
				/*
				 * NotificationManager mNotificationManager =
				 * (NotificationManager)
				 * getSystemService(Context.NOTIFICATION_SERVICE);
				 * mNotificationManager.cancel(mId);
				 */

			}

			/*
			 * Uri alert = RingtoneManager
			 * .getDefaultUri(RingtoneManager.TYPE_ALARM); if (alert == null) {
			 * // alert is null, using backup alert = RingtoneManager
			 * .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); if (alert ==
			 * null) { // I can't see this ever being null (as // always // have
			 * a default notification) but just // incase // alert backup is
			 * null, using 2nd backup alert = RingtoneManager
			 * .getDefaultUri(RingtoneManager.TYPE_RINGTONE); } } r =
			 * RingtoneManager.getRingtone(getApplicationContext(), alert);
			 */

		} catch (Exception ex) {
			writeLog(ex);
		}

	}

	@Override
	public void onResume() {
		onCreate(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_menu, menu);
		getMenuInflater().inflate(R.menu.database_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i1 = new Intent(this, SettingsActivity.class);
			startActivityForResult(i1, RESULT_SETTINGS);
			break;
		case R.id.action_database_settings:
			Intent i2 = new Intent(this, SettingsDatabaseActivity.class);
			startActivity(i2);
			break;
		case R.id.action_about:
			Intent i3 = new Intent(this, AboutActivity.class);
			startActivity(i3);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
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

		// EditText editText = (EditText) findViewById(R.id.edit_message);
		Integer rowsAdded = 0;

		/*
		 * try { Integer smsNumber =
		 * Integer.valueOf(editText.getText().toString()); if (smsNumber > 0) {
		 * rowsAdded = dbHelper.addAll(this, smsNumber); } else { rowsAdded =
		 * dbHelper.addAll(this); } } catch (NumberFormatException ex) {
		 */
		rowsAdded = dbHelper.addAll(this);
		// }

		Toast.makeText(this, "added " + rowsAdded + " rows", Toast.LENGTH_LONG)
				.show();

	}

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

	public void showPanes(View view) {

		Intent intent = new Intent(this, DisplayPanesActivity.class);
		startActivity(intent);

	}

	/*
	 * public void showCodes(View view) {
	 * 
	 * Intent intent = new Intent(this, TestShowCodes.class);
	 * 
	 * startActivity(intent); }
	 */

	public static void writeLog(Exception ex) {

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
			osw.write(sw.toString());

			osw.write(sw.toString());
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
