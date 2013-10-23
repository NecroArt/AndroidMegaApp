package com.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import database.SmsRecord;



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
        
        DbHelper dbHelper = new DbHelper(this, null, null, DbHelper.getDBVersion());
        
        ArrayList<SmsRecord> recordsArray = dbHelper.getAll();
        
        TableLayout SMSTable = new TableLayout(this);
        SMSTable.setStretchAllColumns(true);  
        SMSTable.setShrinkAllColumns(true);

    	//TODO change this to parameter
        int i = 0;
        
		String lastSmsId = "";
		for (SmsRecord currentSMS : recordsArray) {

			// add space between different sms
			if (!currentSMS.getSmsId().equals(lastSmsId)) {

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
			SMSSmsIdView.setText(currentSMS.getSmsId());
			currentRow.addView(SMSSmsIdView);
			
			TextView SMSIdView = new TextView(this);
			SMSIdView.setText(String.valueOf(currentSMS.getId()));
			currentRow.addView(SMSIdView);
			
			TextView SMSDateView = new TextView(this);
			SMSDateView.setText(currentSMS.getDate().get(Calendar.DAY_OF_MONTH)
					+ " "
					+ new SimpleDateFormat("MMMM").format(currentSMS.getDate()
							.getTime())
					+ " "
					+ String.format("%02d:%02d",
							currentSMS.getDate().get(Calendar.HOUR_OF_DAY),
							currentSMS.getDate().get(Calendar.MINUTE)));
			currentRow.addView(SMSDateView);

			TextView SMSParamName = new TextView(this);
			SMSParamName.setText(currentSMS.getParameterName());
			currentRow.addView(SMSParamName);

			TextView SMSParamValue = new TextView(this);
			SMSParamValue.setText(currentSMS.getParameterValue());
			currentRow.addView(SMSParamValue);

			SMSTable.addView(currentRow);

			lastSmsId = currentSMS.getSmsId();

			// TODO change this to parameter
			i++;
			if (i == 10 * 10 /*=> rownum * 10*/) {
				break;
			}
		}
		
		/*for (SmsRecord currentSMS : recordsArray) {

			TableRow currentRow = new TableRow(this);
			currentRow.setGravity(Gravity.CENTER_HORIZONTAL);

			TextView SMSDateView = new TextView(this);
			SMSDateView.setText(currentSMS.getDate().get(Calendar.DAY_OF_MONTH)
					+ " "
					+ new SimpleDateFormat("MMMM").format(currentSMS.getDate()
							.getTime())
					+ " "
					+ String.format("%02d:%02d",
							currentSMS.getDate().get(Calendar.HOUR_OF_DAY),
							currentSMS.getDate().get(Calendar.MINUTE)));
			currentRow.addView(SMSDateView);

			TextView SMSParamName = new TextView(this);
			SMSParamName.setText(currentSMS.getParameterName());
			currentRow.addView(SMSParamName);

			TextView SMSParamValue = new TextView(this);
			SMSParamValue.setText(currentSMS.getParameterValue());
			currentRow.addView(SMSParamValue);

			SMSTable.addView(currentRow);

			
		}*/
        
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
	
}
