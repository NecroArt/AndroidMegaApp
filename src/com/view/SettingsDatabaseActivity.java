package com.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsDatabaseActivity extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.database_preferences);
		setContentView(R.layout.database_preferences);
	}

}
