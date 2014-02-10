package com.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import database.DbHelper;
import database.SmsRecordRepDbStatus;

public class DisplayTableActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make sure we're running on Honeycomb or higher to use ActionBar
		// APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		// TODO create method with parameter - amount required records
		ArrayList<SmsRecordRepDbStatus> recordsArray = dbHelper.getAllRepDbStatus();

		TableLayout SMSTable = new TableLayout(this);
		SMSTable.setStretchAllColumns(true);
		SMSTable.setShrinkAllColumns(true);

		// TODO change this to parameter
		int i = 0;

		Calendar lastSmsId = null;
		for (SmsRecordRepDbStatus currentSMS : recordsArray) {

			// add space between different sms
			if (!currentSMS.getDate().equals(lastSmsId)) {

				TableRow currentRow = new TableRow(this);
				currentRow.setGravity(Gravity.CENTER_HORIZONTAL);

				TextView SMSDateView = new TextView(this);
				SMSDateView.setText("");
				currentRow.addView(SMSDateView);

				TextView SMSParamName = new TextView(this);
				SMSParamName.setText("");
				currentRow.addView(SMSParamName);

				TextView SMSParamValue = new TextView(this);
				SMSParamValue.setText("");
				currentRow.addView(SMSParamValue);

				SMSTable.addView(currentRow);

			}

			TableRow currentRow = new TableRow(this);
			currentRow.setGravity(Gravity.CENTER_HORIZONTAL);

			TextView SMSSmsIdView = new TextView(this);
			SMSSmsIdView.setGravity(Gravity.LEFT);
			SMSSmsIdView.setText(String.valueOf(currentSMS.getDate().getTimeInMillis()));
			currentRow.addView(SMSSmsIdView);

			TextView Delimeter1 = new TextView(this);
			Delimeter1.setText("|");
			currentRow.addView(Delimeter1);

			TextView SMSIdView = new TextView(this);
			SMSIdView.setGravity(Gravity.CENTER);
			SMSIdView.setText(String.valueOf(currentSMS.getId()));
			currentRow.addView(SMSIdView);

			TextView Delimeter2 = new TextView(this);
			Delimeter2.setText("|");
			currentRow.addView(Delimeter2);

			TextView SMSDateView = new TextView(this);
			// TODO test
			Locale locale = getResources().getConfiguration().locale;
			SMSDateView.setText(currentSMS.getDate().get(Calendar.DAY_OF_MONTH)
					+ " "
					+ new SimpleDateFormat("MMMM", locale).format(currentSMS
							.getDate().getTime())
					+ " "
					+ String.format("%02d:%02d",
							currentSMS.getDate().get(Calendar.HOUR_OF_DAY),
							currentSMS.getDate().get(Calendar.MINUTE)));
			currentRow.addView(SMSDateView);

			TextView SMSParamName = new TextView(this);
			SMSParamName.setText(currentSMS.getParameterName());
			currentRow.addView(SMSParamName);

			TextView SMSParamValue = new TextView(this);
			SMSParamValue.setGravity(Gravity.RIGHT);
			SMSParamValue.setText(currentSMS.getParameterValue());
			currentRow.addView(SMSParamValue);

			SMSTable.addView(currentRow);

			lastSmsId = currentSMS.getDate();

			// TODO change this to parameter

			i++;
			if (i == 11 * 100) {
				break;
			}

		}

		ScrollView tableScrollView = new ScrollView(this);
		tableScrollView.setFillViewport(true);
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

}
