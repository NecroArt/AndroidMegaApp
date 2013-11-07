package com.view;

import java.util.ArrayList;

import database.DbHelper;
import database.SmsRecord;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TestShowCodes extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ScrollView scrollView = new ScrollView(this);
		scrollView.setFillViewport(true);

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());
		ArrayList<SmsRecord> recordsArray = dbHelper.getAll();

		for (int i = 0; i < 20; i++) {
			if (recordsArray.get(i).getParameterName().equals("ÎÏÅÐÀÒÈÂÍÛÉ")) {
				SmsRecord curRec = recordsArray.get(i);
				for (char c : curRec.getParameterValue().toCharArray()) {
					TextView curText = new TextView(this);
					curText.setText(c + " = " + String.valueOf((int) c));
					linearLayout.addView(curText);
				}
			}

		}
		scrollView.addView(linearLayout);
		setContentView(scrollView);
	}
}
