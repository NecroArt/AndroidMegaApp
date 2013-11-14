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
	public static String str1 = "";
	public static String str2 = "�����";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ScrollView scrollView = new ScrollView(this);
		scrollView.setFillViewport(true);

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		/*DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());
		ArrayList<SmsRecord> recordsArray = dbHelper.getAll();

		for (int i = 0; i < 20; i++) {
			if (recordsArray.get(i).getParameterName().equals("�����������")) {
				SmsRecord curRec = recordsArray.get(i);
				for (char c : curRec.getParameterValue().toCharArray()) {
					TextView curText = new TextView(this);
					curText.setText(c + " = " + String.valueOf((int) c));
					linearLayout.addView(curText);
				}
			}

		}*/
		for (char c: str1.toCharArray()) {
			TextView curText = new TextView(this);
			curText.setText(c + " = " + String.valueOf((int) c));
			linearLayout.addView(curText);
		}
		TextView empty = new TextView(this);
		linearLayout.addView(empty);
		for (char c: str2.toCharArray()) {
			TextView curText = new TextView(this);
			curText.setText(c + " = " + String.valueOf((int) c));
			linearLayout.addView(curText);
		}
		scrollView.addView(linearLayout);
		setContentView(scrollView);
	}
}
