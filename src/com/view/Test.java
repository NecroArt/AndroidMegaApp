package com.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

		// label
		TextView textView = new TextView(this);
		textView.setText("Оперативный");
		textView.setBackgroundColor(Color.parseColor("#8800CCFF"));
		textView.setGravity(Gravity.CENTER);
		linearLayout.addView(textView);

		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		// images
		TableRow currentRow = new TableRow(this);
		TableLayout.LayoutParams relativeLayoutParameters = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT, 1f);
		currentRow = new TableRow(this);
		for (int i = 1; i <= getDaysAmount(); i++) {

			ImageView imageView = new ImageView(this);
			imageView.setId(++childId);
			imageView.setPadding(0, 0, 0, 0);
			imageView.setImageResource(R.drawable.test);
			currentRow.addView(imageView);

		}
		// add images
		table.addView(currentRow);

		table.setBackgroundColor(Color.parseColor("#8800CCFF"));

		linearLayout.addView(table, relativeLayoutParameters);

		//
		// выручка
		//

		// label
		textView = new TextView(this);
		textView.setText("Выручка");
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		linearLayout.addView(textView);

		table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		// images
		currentRow = new TableRow(this);
		relativeLayoutParameters = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT, 1f);
		currentRow = new TableRow(this);
		for (int i = 1; i <= getDaysAmount(); i++) {

			ImageView imageView = new ImageView(this);
			imageView.setId(++childId);
			imageView.setPadding(0, 0, 0, 0);
			imageView.setImageResource(R.drawable.test);
			currentRow.addView(imageView);

		}
		// add images
		table.addView(currentRow);

		table.setBackgroundColor(Color.parseColor("#8800CCCC"));

		linearLayout.addView(table, relativeLayoutParameters);
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
