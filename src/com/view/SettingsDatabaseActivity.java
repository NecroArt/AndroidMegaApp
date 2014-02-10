package com.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import database.DbHelper;
import database.SmsRecordRepDbStatus;
import android.annotation.TargetApi;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SettingsDatabaseActivity extends DisplayPanesActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_preferences);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		TextView lastSmsDateTextView = (TextView) findViewById(R.id.last_sms_date_on_database_panel);

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		Long millis = dbHelper.getLastSmsDateRepDbStatus();
		Locale locale = getResources().getConfiguration().locale;
		if (millis != 0L) {
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(millis);
			String lastSmsDate = date.get(Calendar.DAY_OF_MONTH)
					+ " "
					+ new SimpleDateFormat("MMMM", locale).format(date
							.getTime())
					+ " "
					+ String.format("%02d:%02d:%02d",
							date.get(Calendar.HOUR_OF_DAY),
							date.get(Calendar.MINUTE),
							date.get(Calendar.SECOND));
			lastSmsDateTextView.setText("Последняя смс в базе данных от "
					+ lastSmsDate);
		} else {
			lastSmsDateTextView.setText("База данных пуста");
		}
	}

	public void showTable(View view) {

		try {
			Intent intent = new Intent(this, DisplayTableActivity.class);
			startActivity(intent);
		} catch (Exception ex) {
			MainActivity.writeLog(ex);
		}
	}
	
	public void showIds(View view) {
		ArrayList<String> smsIds = new ArrayList<String>();
		final String SMS_URI_INBOX = "content://sms/inbox";
		Uri uri = Uri.parse(SMS_URI_INBOX);
		String[] projection = new String[] { "_id"};

		// interesting sms only from 000019
		String whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER
				+ "\"";

		// fetching sms with order by date

		ContextWrapper contextWrapper = new android.content.ContextWrapper(
				this);

		Cursor cur = contextWrapper.getContentResolver().query(
				uri,
				projection,
				whereClause,
				null,
				" date asc");
		if (cur.moveToFirst()) {

			int index_Date = cur.getColumnIndex("_id");
			do {
				String longDate = cur.getString(index_Date);

				
				smsIds.add(longDate);

			} while (cur.moveToNext());

			if (!cur.isClosed()) {
				cur.close();
				cur = null;
			}
		}
		
		
		TableLayout SMSTable = new TableLayout(this);
		SMSTable.setStretchAllColumns(true);
		SMSTable.setShrinkAllColumns(true);
		for (String str: smsIds) {
			TableRow currentRow = new TableRow(this);
			currentRow.setGravity(Gravity.CENTER_HORIZONTAL);
			
			TextView SMSDateView = new TextView(this);
			SMSDateView.setText(str);
			currentRow.addView(SMSDateView);
			
			SMSTable.addView(currentRow);
		}
		
		ScrollView tableScrollView = new ScrollView(this);
		tableScrollView.setFillViewport(true);
		tableScrollView.addView(SMSTable);

		setContentView(tableScrollView);
	}

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

}
