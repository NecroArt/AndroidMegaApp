package com.view;

import java.util.ArrayList;
import java.util.Calendar;

import database.DbHelper;
import database.SmsRecord;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DisplayPanesActivity extends Activity {

	final Integer daysAmount = 5;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.panel);

		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		ArrayList<Integer> proceedsStatus = new ArrayList<Integer>();
		ArrayList<Integer> operationalStatus = new ArrayList<Integer>();
		ArrayList<Integer> dayNumber = new ArrayList<Integer>();

		ArrayList<SmsRecord> recordsArray = dbHelper.getSMSRecordArrayList(
				this, daysAmount);

		String lastSmsId = "";
		for (SmsRecord currentRecord : recordsArray) {
			if (currentRecord.getParameterName().equals("ВЫРУЧКА")) {
				if (currentRecord.getParameterValue().equals("вчера")) {
					proceedsStatus.add(0);
				} else {
					if (currentRecord.getParameterValue().equals("позавчера")) {
						proceedsStatus.add(1);
					} else {
						proceedsStatus.add(2);
					}
				}
				dayNumber.add(currentRecord.getDate().get(Calendar.DAY_OF_MONTH));
			} else {
				if (currentRecord.getParameterName().equals("ОПЕРАТИВНЫЙ")) {
					if (currentRecord.getParameterValue().equals("вчера")) {
						operationalStatus.add(0);
					} else {
						if (currentRecord.getParameterValue().equals(
								"позавчера")) {
							operationalStatus.add(1);
						} else {
							operationalStatus.add(2);
						}
					}
				}
			}
		}

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		// proceeds
		RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.setBackgroundColor(Color.GRAY);
		relativeLayout.setId(1);

		int childId = 0;

		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		RelativeLayout.LayoutParams textViewParameters = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		TextView textView = new TextView(this);
		textView.setId(++childId);
		textView.setText("test text");
		// textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setLayoutParams(textViewParameters);
		relativeLayout.addView(textView);

		ImageView imageView1 = new ImageView(this);
		imageView1.setId(++childId);
		imageView1.setImageResource(R.drawable.ic_launcher);

		RelativeLayout.LayoutParams imageViewParameters = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imageViewParameters.addRule(RelativeLayout.BELOW, textView.getId());
		relativeLayout.addView(imageView1, imageViewParameters);

		ImageView imageView2 = new ImageView(this);
		imageView2.setImageResource(R.drawable.pailoid);
		imageView2.setId(++childId);
		RelativeLayout.LayoutParams imageViewParameters2 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imageViewParameters2.addRule(RelativeLayout.BELOW, textView.getId());
		imageViewParameters2.addRule(RelativeLayout.RIGHT_OF,
				imageView1.getId());
		imageView2.setLayoutParams(imageViewParameters2);
		relativeLayout.addView(imageView2);

		linearLayout.addView(relativeLayout, rlp);

		// operational
		relativeLayout = new RelativeLayout(this);
		relativeLayout.setBackgroundColor(Color.BLUE);

		relativeLayout.setId(2);

		childId = 0;

		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		// rlp.addRule(RelativeLayout.BELOW, 1);

		textViewParameters = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		textView = new TextView(this);
		textView.setId(++childId);
		textView.setText("another test text");
		// textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setLayoutParams(textViewParameters);
		relativeLayout.addView(textView);

		imageView1 = new ImageView(this);
		imageView1.setId(++childId);
		imageView1.setImageResource(R.drawable.ic_launcher);

		imageViewParameters = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imageViewParameters.addRule(RelativeLayout.BELOW, textView.getId());
		relativeLayout.addView(imageView1, imageViewParameters);

		imageView2 = new ImageView(this);
		imageView2.setImageResource(R.drawable.pailoid);
		imageView2.setId(++childId);
		imageViewParameters2 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imageViewParameters2.addRule(RelativeLayout.BELOW, textView.getId());
		imageViewParameters2.addRule(RelativeLayout.RIGHT_OF,
				imageView1.getId());
		imageView2.setLayoutParams(imageViewParameters2);
		relativeLayout.addView(imageView2);

		linearLayout.addView(relativeLayout, rlp);
		// ///////

		ScrollView scrollView = new ScrollView(this);
		scrollView.setFillViewport(true);
		scrollView.addView(linearLayout);

		setContentView(scrollView);
	}

}
