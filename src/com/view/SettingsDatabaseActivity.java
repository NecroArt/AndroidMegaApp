package com.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import database.DbHelper;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.TextView;

public class SettingsDatabaseActivity extends MainActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_preferences);
		
		TextView lastSmsDateTextView = (TextView) findViewById(R.id.last_sms_date_on_database_panel);

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		Long millis = dbHelper.getLastSmsDate();
		
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
		lastSmsDateTextView.setText("Последняя смс в базе данных от " + lastSmsDate);
	}

}
