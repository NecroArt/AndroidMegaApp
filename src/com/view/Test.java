package com.view;

import java.util.ArrayList;

import database.DbHelper;
import database.SmsRecord;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.MarginLayoutParams;
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

		//
		// оперативный
		//

		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);

		// label
		TextView textView = new TextView(this);
		textView.setText("Оперативный");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#2F00CCFF"));
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
		ArrayList<SmsRecord> recordsArray = dbHelper.getAll();

		int i = 0;
		currentRow = new TableRow(this);
		for (int added = 0; added < 5 && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ОПЕРАТИВНЫЙ")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);

				if (recordsArray.get(i).getParameterValue().equals("вчера")) {
					imageView.setImageResource(R.drawable.test);
				} else {
					imageView
							.setImageResource(R.drawable.test_green_disk_red_and_white_rings);
				}

				added++;
				
				currentRow.addView(imageView);
			}
		}
		// add images
		table.addView(currentRow);

		table.setBackgroundColor(Color.parseColor("#2F00CCFF"));

		innerLinearLayout.addView(table, relativeLayoutParameters);
		linearLayout.addView(innerLinearLayout);

		//
		// выручка
		//

		innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

		params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);

		// label
		textView = new TextView(this);
		textView.setText("Выручка");
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);

		table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		// images
		currentRow = new TableRow(this);
		relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
		currentRow = new TableRow(this);
		for (i = 1; i <= getDaysAmount(); i++) {

			ImageView imageView = new ImageView(this);
			imageView.setId(++childId);
			imageView.setPadding(0, 0, 0, 0);
			imageView.setImageResource(R.drawable.test);
			currentRow.addView(imageView);

		}
		// add images
		table.addView(currentRow);

		table.setBackgroundColor(Color.parseColor("#8800CCCC"));

		innerLinearLayout.addView(table, relativeLayoutParameters);
		linearLayout.addView(innerLinearLayout);

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
