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
import android.widget.TextView;

public class Test extends Activity {

	private static int daysAmount = 5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panel);

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		// �����������
		RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.setBackgroundColor(Color.parseColor("#883399FF"));
		relativeLayout.setId(1);
		relativeLayout.setGravity(Gravity.CENTER);

		int childId = 0;

		TableLayout.LayoutParams rlp = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT, 1f);

		RelativeLayout.LayoutParams textViewParameters = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		TextView textView = new TextView(this);
		textView.setId(++childId);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		textView.setText("�����������");
		textView.setLayoutParams(textViewParameters);
		relativeLayout.addView(textView);

		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		
		for (int i = 1; i <= getDaysAmount(); i++) {
			
			ImageView imageView = new ImageView(this);
			imageView.setId(++childId);
			imageView.setImageResource(R.drawable.green_disk);
			
			innerLinearLayout.addView(imageView);
			
		}

		RelativeLayout.LayoutParams imageViewParameters = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imageViewParameters.addRule(RelativeLayout.BELOW, textView.getId());
		relativeLayout.addView(innerLinearLayout, imageViewParameters);
		
		ImageView imageView1 = new ImageView(this);
//		imageView1.setId(++childId);
//		imageView1.setImageResource(R.drawable.green_disk);
//
//		RelativeLayout.LayoutParams imageViewParameters = new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT);
//		imageViewParameters.addRule(RelativeLayout.BELOW, textView.getId());
//		relativeLayout.addView(imageView1, imageViewParameters);
//
		ImageView imageView2 = new ImageView(this);
//		imageView2.setImageResource(R.drawable.green_disk_red_and_white_rings);
//		imageView2.setId(++childId);
		RelativeLayout.LayoutParams imageViewParameters2 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
//		imageViewParameters2.addRule(RelativeLayout.BELOW, textView.getId());
//		imageViewParameters2.addRule(RelativeLayout.RIGHT_OF,
//				imageView1.getId());
//		imageView2.setLayoutParams(imageViewParameters2);
//		relativeLayout.addView(imageView2);

		linearLayout.addView(relativeLayout, rlp);

		// �������
		relativeLayout = new RelativeLayout(this);
		relativeLayout.setGravity(Gravity.CENTER);
		// relativeLayout.setBackgroundColor(Color.BLUE);
		relativeLayout.setBackgroundColor(Color.parseColor("#CCFFCC"));

		relativeLayout.setId(2);

		childId = 0;

		rlp = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT, 1f);

		textViewParameters = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		textView = new TextView(this);
		textView.setId(++childId);
		textView.setText("�������");
		// textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setLayoutParams(textViewParameters);
		relativeLayout.addView(textView);

		imageView1 = new ImageView(this);
		imageView1.setId(++childId);
		imageView1.setImageResource(R.drawable.red_ring_and_exclamation_mark);

		imageViewParameters = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imageViewParameters.addRule(RelativeLayout.BELOW, textView.getId());
		relativeLayout.addView(imageView1, imageViewParameters);

		imageView2 = new ImageView(this);
		imageView2.setImageResource(R.drawable.green_disk);
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

	public static int getDaysAmount() {
		return daysAmount;
	}

	public static void setDaysAmount(int newDaysAmount) {
		daysAmount = newDaysAmount;
	}

}
