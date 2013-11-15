package com.view;

import java.util.ArrayList;
import java.util.Calendar;

import database.DbHelper;
import database.SmsRecord;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Test extends Activity {

	private static int daysAmount = 5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panel);

		int childId = 0;
		
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		try{
			
		//
		//
		//
		// оперативный
		//

		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);

		// label
		TextView textView = new TextView(this);
		textView.setText("Оперативный");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#43C6DB"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);

		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		// images
		TableRow currentRow = new TableRow(this);
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		//TODO make it as user options
		String [] parameters = {"ОПЕРАТИВНЫЙ", "ABONTODAY"};
		ArrayList<SmsRecord> recordsArray = dbHelper.getLastRecords(5, parameters);

		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<Integer> status = new ArrayList<Integer>();

		currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;

		// get status for last 5 days
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ОПЕРАТИВНЫЙ")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);

				//TODO return to days of month
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
				
				if (recordsArray.get(i).getParameterValue().matches("вчера")) {
					status.add(0);
				} else {
					if (recordsArray.get(i).getParameterValue()
							.equals("позавчера")) {
						status.add(1);
					} else {
						status.add(2);
					}
				}

				added++;

			}
		}

		if (added == 0) {
			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			dataNotFound.setBackgroundColor(Color.parseColor("#43C6DB"));
			innerLinearLayout.addView(dataNotFound);
		}

		if (status.size() == daysNumber.size()) {
			for (int i = 0; i < status.size(); i++) {

				TextView curDay = new TextView(this);
				curDay.setGravity(Gravity.CENTER);
				curDay.setText(String.valueOf(daysNumber.get(i)));
				days.addView(curDay);

				ImageView imageView = new ImageView(this);
				switch (status.get(i)) {
				case 0:
					imageView.setImageResource(R.drawable.test);
					break;
				case 1:
					imageView
							.setImageResource(R.drawable.test_green_disk_red_and_white_rings);
					break;
				default:
					imageView.setImageResource(R.drawable.red_disc);
					break;
				}
				currentRow.addView(imageView);
			}
		}

		// add images
		if (added != 0) {
			table.addView(days);
			table.addView(currentRow);
			table.setBackgroundColor(Color.parseColor("#43C6DB"));
			innerLinearLayout.addView(table, relativeLayoutParameters);
		}

		linearLayout.addView(innerLinearLayout);

		//
		//
		//
		// выручка
		//

		innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

		params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);

		// label
		textView = new TextView(this);
		textView.setText("ABONTODAY");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);

		table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		// images
		currentRow = new TableRow(this);
		days = new TableRow(this);
		added = 0;
		
		relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
		
		daysNumber.clear();
		status.clear();
		
		// TODO make days amount parameter
				for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

					if (recordsArray.get(i).getParameterName().equals("ABONTODAY")) {
						ImageView imageView = new ImageView(this);
						imageView.setId(++childId);
						imageView.setPadding(0, 0, 0, 0);

						//TODO return to days of month
						daysNumber.add(recordsArray.get(i).getDate()
								.get(Calendar.DAY_OF_MONTH));
						
						if (recordsArray.get(i).getParameterValue().matches("вчера")) {
							status.add(0);
						} else {
							if (recordsArray.get(i).getParameterValue()
									.equals("позавчера")) {
								status.add(1);
							} else {
								status.add(2);
							}
						}

						added++;

					}
				}

				if (added == 0) {
					TextView dataNotFound = new TextView(this);
					dataNotFound.setGravity(Gravity.CENTER);
					dataNotFound.setText("Данные не найдены");
					dataNotFound.setBackgroundColor(Color.parseColor("#8800CCCC"));
					innerLinearLayout.addView(dataNotFound);
				}

				if (status.size() == daysNumber.size()) {
					for (int i = 0; i < status.size(); i++) {

						TextView curDay = new TextView(this);
						curDay.setGravity(Gravity.CENTER);
						curDay.setText(String.valueOf(daysNumber.get(i)));
						days.addView(curDay);

						ImageView imageView = new ImageView(this);
						switch (status.get(i)) {
						case 0:
							imageView.setImageResource(R.drawable.test);
							break;
						case 1:
							imageView
									.setImageResource(R.drawable.test_green_disk_red_and_white_rings);
							break;
						default:
							imageView.setImageResource(R.drawable.red_disc);
							break;
						}
						currentRow.addView(imageView);
					}
				}

				// add images
				if (added != 0) {
					table.addView(days);
					table.addView(currentRow);
					table.setBackgroundColor(Color.parseColor("#8800CCCC"));
					innerLinearLayout.addView(table, relativeLayoutParameters);
				}

				linearLayout.addView(innerLinearLayout);
		
		

		//
		//add all in layout
		}
		catch (Exception ex) {
			StackTraceElement[] s = ex.getStackTrace();
			String str = "";
			for (int i = 0; i < s.length; i++) {
				str += s[i].toString() + " --- ";				
			}
			TextView text = new TextView(this);
			text.setText(ex.getMessage() + " --- " + str);
			linearLayout.addView(text);
		}
		ScrollView scrollView = new ScrollView(this);
		scrollView.setFillViewport(true);
		scrollView.addView(linearLayout);

		setContentView(scrollView);
		
	}

	public static int getDaysAmount() {
		return daysAmount;
	}

	public static void setDaysAmount(int newDaysAmount) {
		daysAmount = newDaysAmount;
	}

}
