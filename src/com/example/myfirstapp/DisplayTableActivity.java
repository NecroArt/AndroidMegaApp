package com.example.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



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
        
        TableLayout table = new TableLayout(this);
        table.setStretchAllColumns(true);  
        table.setShrinkAllColumns(true);
        TableRow rowTitle = new TableRow(this);  
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);  
        TableRow rowDayLabels = new TableRow(this);  
        TableRow rowHighs = new TableRow(this);  
        TableRow rowLows = new TableRow(this);  
        TableRow rowConditions = new TableRow(this);  
        rowConditions.setGravity(Gravity.CENTER);  
        TextView empty = new TextView(this);
        
        TextView title = new TextView(this);  
        title.setText("Java Weather Table");  
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);  
        title.setGravity(Gravity.CENTER);  
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);  
        TableRow.LayoutParams params = new TableRow.LayoutParams();  
        params.span = 6;  
        rowTitle.addView(title, params);  
        
        // labels column  
        TextView highsLabel = new TextView(this);  
        highsLabel.setText("Day High");  
        highsLabel.setTypeface(Typeface.DEFAULT_BOLD);  
        TextView lowsLabel = new TextView(this);  
        lowsLabel.setText("Day Low");  
        lowsLabel.setTypeface(Typeface.DEFAULT_BOLD);  
        TextView conditionsLabel = new TextView(this);  
        conditionsLabel.setText("Conditions");  
        conditionsLabel.setTypeface(Typeface.DEFAULT_BOLD);  
        rowDayLabels.addView(empty);  
        rowHighs.addView(highsLabel);  
        rowLows.addView(lowsLabel);  
        rowConditions.addView(conditionsLabel);  
        // day 1 column  
        TextView day1Label = new TextView(this);  
        day1Label.setText("Feb 7");  
        day1Label.setTypeface(Typeface.SERIF, Typeface.BOLD);  
        TextView day1High = new TextView(this);  
        day1High.setText("28°F");  
        day1High.setGravity(Gravity.CENTER_HORIZONTAL);  
        TextView day1Low = new TextView(this);  
        day1Low.setText("15°F");  
        day1Low.setGravity(Gravity.CENTER_HORIZONTAL);  
        rowDayLabels.addView(day1Label);  
        rowHighs.addView(day1High);  
        rowLows.addView(day1Low);  
        
        // day2 column  
        TextView day2Label = new TextView(this);  
        day2Label.setText("Feb 8");  
        day2Label.setTypeface(Typeface.SERIF, Typeface.BOLD);  
        TextView day2High = new TextView(this);  
        day2High.setText("26°F");  
        day2High.setGravity(Gravity.CENTER_HORIZONTAL);  
        TextView day2Low = new TextView(this);  
        day2Low.setText("14°F");  
        day2Low.setGravity(Gravity.CENTER_HORIZONTAL);  
        
        rowDayLabels.addView(day2Label);  
        rowHighs.addView(day2High);  
        rowLows.addView(day2Low);  
        
        // day3 column  
        TextView day3Label = new TextView(this);  
        day3Label.setText("Feb 9");  
        day3Label.setTypeface(Typeface.SERIF, Typeface.BOLD);  
        TextView day3High = new TextView(this);  
        day3High.setText("23°F");  
        day3High.setGravity(Gravity.CENTER_HORIZONTAL);  
        TextView day3Low = new TextView(this);  
        day3Low.setText("3°F");  
        day3Low.setGravity(Gravity.CENTER_HORIZONTAL);  
        
        rowDayLabels.addView(day3Label);  
        rowHighs.addView(day3High);  
        rowLows.addView(day3Low);  
        
        // day4 column  
        TextView day4Label = new TextView(this);  
        day4Label.setText("Feb 10");  
        day4Label.setTypeface(Typeface.SERIF, Typeface.BOLD);  
        TextView day4High = new TextView(this);  
        day4High.setText("17°F");  
        day4High.setGravity(Gravity.CENTER_HORIZONTAL);  
        TextView day4Low = new TextView(this);  
        day4Low.setText("5°F");  
        day4Low.setGravity(Gravity.CENTER_HORIZONTAL);  
        
        rowDayLabels.addView(day4Label);  
        rowHighs.addView(day4High);  
        rowLows.addView(day4Low);  
        
        // day5 column  
        TextView day5Label = new TextView(this);  
        day5Label.setText("Feb 11");  
        day5Label.setTypeface(Typeface.SERIF, Typeface.BOLD);  
        TextView day5High = new TextView(this);  
        day5High.setText("19°F");  
        day5High.setGravity(Gravity.CENTER_HORIZONTAL);  
        TextView day5Low = new TextView(this);  
        day5Low.setText("6°F");  
        day5Low.setGravity(Gravity.CENTER_HORIZONTAL);  
        
        rowDayLabels.addView(day5Label);  
        rowHighs.addView(day5High);  
        rowLows.addView(day5Low);  
        
        
        table.addView(rowTitle);  
        table.addView(rowDayLabels);  
        table.addView(rowHighs);  
        table.addView(rowLows);  
        table.addView(rowConditions);  
        setContentView(table);
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
