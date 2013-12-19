package com.view;

import java.util.ArrayList;
import java.util.Calendar;

import database.DbHelper;
import database.SmsRecord;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class DisplayPanesActivity extends Activity {

	private static int daysAmount = 5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panel);

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		// TODO make it as user options
		// String[] parameters = { "�����������", "ABONTODAY" };
		ArrayList<String> parameters = new ArrayList<String>();

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		if (prefs.getBoolean("show_golden_gate_status", true)) {
			parameters.add("GoldenGate");
		}

		if (prefs.getBoolean("show_otrabotalo_status", true)) {
			parameters.add("����������");
		}

		if (prefs.getBoolean("show_nochyu_upalo_status", true)) {
			parameters.add("�����_�����");
		}

		if (prefs.getBoolean("show_abontoday_status", true)) {
			parameters.add("ABONTODAY");
		}

		if (prefs.getBoolean("show_viruchka_status", true)) {
			parameters.add("�������");
		}

		if (prefs.getBoolean("show_operativniy_status", true)) {
			parameters.add("�����������");
		}

		if (prefs.getBoolean("show_send_imsi_status", true)) {
			parameters.add("SEND_IMSI");
		}

		if (prefs.getBoolean("show_ftp_upl_status", true)) {
			parameters.add("FTP_UPL");
		}

		if (prefs.getBoolean("show_vchera_upalo_status", true)) {
			parameters.add("�����_�����");
		}

		if (prefs.getBoolean("show_padalo_7_dney_status", true)) {
			parameters.add("������_7_����");
		}

		if (prefs.getBoolean("show_svobodno_status", true)) {
			parameters.add("��������");
		}

		ArrayList<SmsRecord> recordsArray = dbHelper.getLastRecords(5,
				parameters);

		try {

			// GoldenGate
			if (prefs.getBoolean("show_golden_gate_status", true)) {
				showGoldenGate(this, linearLayout, recordsArray);
			}

			// ����������
			if (prefs.getBoolean("show_otrabotalo_status", true)) {
				showOtrabotalo(this, linearLayout, recordsArray);
			}

			// �����_�����
			if (prefs.getBoolean("show_nochyu_upalo_status", true)) {
				showNochyuUpalo(this, linearLayout, recordsArray);
			}

			// ABONTODAY
			if (prefs.getBoolean("show_abontoday_status", true)) {
				showAbontoday(this, linearLayout, recordsArray);
			}

			// �������
			if (prefs.getBoolean("show_viruchka_status", true)) {
				showViruchka(this, linearLayout, recordsArray);
			}

			// �����������
			if (prefs.getBoolean("show_operativniy_status", true)) {
				showOperativniy(this, linearLayout, recordsArray);
			}

			// SEND_IMSI
			if (prefs.getBoolean("show_send_imsi_status", true)) {
				showSendImsi(this, linearLayout, recordsArray);
			}

			// FTP_UPL
			if (prefs.getBoolean("show_ftp_upl_status", true)) {
				showFtpUpl(this, linearLayout, recordsArray);
			}

			// �����_�����
			if (prefs.getBoolean("show_vchera_upalo_status", true)) {
				showVcheraUpalo(this, linearLayout, recordsArray);
			}

			// ������_7_����
			if (prefs.getBoolean("show_padalo_7_dney_status", true)) {
				showPadaloSemDney(this, linearLayout, recordsArray);
			}

			// ��������
			if (prefs.getBoolean("show_svobodno_status", true)) {
				showSvobodno(this, linearLayout, recordsArray);
			}

		} catch (Exception ex) {
			// TODO handle exceptions correctly way
			StackTraceElement[] s = ex.getStackTrace();
			String str = "";
			for (int i = 0; i < s.length; i++) {
				str += s[i].toString() + " --- ";
			}
			TextView text = new TextView(this);
			text.setText(ex.getMessage() + " --- " + str);
			linearLayout.addView(text);
			MainActivity.writeLog(ex);
		}
		ScrollView scrollView = new ScrollView(this);
		scrollView.setFillViewport(true);
		scrollView.addView(linearLayout);

		setContentView(scrollView);

	}

	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ��������� "GoldenGate"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showGoldenGate(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("GoldenGate");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);
	
		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
	
		// images
		TableRow currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<Integer> status = new ArrayList<Integer>();
	
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("GoldenGate")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				if (recordsArray.get(i).getParameterValue().matches("OK")) {
					status.add(0);
				} else {
					if (recordsArray.get(i).getParameterValue()
							.equals("WARN")) {
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
			dataNotFound.setText("������ �� �������");
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
					imageView.setImageResource(R.drawable.success);
					break;
				case 1:
					imageView.setImageResource(R.drawable.warning);
					break;
				default:
					imageView.setImageResource(R.drawable.error);
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
	
	}

	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ��������� "����������"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showOtrabotalo(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("����������");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);
	
		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
	
		// images
		TableRow currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<String> status = new ArrayList<String>();
	
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("����������")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				
				status.add(recordsArray.get(i).getParameterValue());
				
	
				added++;
	
			}
		}
	
		if (added == 0) {
			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("������ �� �������");
			dataNotFound.setBackgroundColor(Color.parseColor("#8800CCCC"));
			innerLinearLayout.addView(dataNotFound);
		}
	
		if (status.size() == daysNumber.size()) {
			for (int i = 0; i < status.size(); i++) {
	
				TextView curDay = new TextView(this);
				curDay.setGravity(Gravity.CENTER);
				curDay.setText(String.valueOf(daysNumber.get(i)));
				days.addView(curDay);
	
				TextView textViewValue = new TextView(this);
				textViewValue.setGravity(Gravity.CENTER);
				textViewValue.setText(status.get(i));
				
				currentRow.addView(textViewValue);
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
		
	}

	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ���������
	 * "�����_�����"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showNochyuUpalo(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("�����_�����");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);
	
		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
	
		// images
		TableRow currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<String> status = new ArrayList<String>();
	
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("�����_�����")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				
				status.add(recordsArray.get(i).getParameterValue());
				
	
				added++;
	
			}
		}
	
		if (added == 0) {
			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("������ �� �������");
			dataNotFound.setBackgroundColor(Color.parseColor("#8800CCCC"));
			innerLinearLayout.addView(dataNotFound);
		}
	
		if (status.size() == daysNumber.size()) {
			for (int i = 0; i < status.size(); i++) {
	
				TextView curDay = new TextView(this);
				curDay.setGravity(Gravity.CENTER);
				curDay.setText(String.valueOf(daysNumber.get(i)));
				days.addView(curDay);
	
				TextView textViewValue = new TextView(this);
				textViewValue.setGravity(Gravity.CENTER);
				textViewValue.setText(status.get(i));
				
				currentRow.addView(textViewValue);
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
	}

	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ��������� "ABONTODAY"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showAbontoday(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("ABONTODAY");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);
	
		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
	
		// images
		TableRow currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<Integer> status = new ArrayList<Integer>();
	
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("ABONTODAY")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				if (recordsArray.get(i).getParameterValue().matches("�����")) {
					status.add(0);
				} else {
					if (recordsArray.get(i).getParameterValue()
							.equals("���������")) {
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
			dataNotFound.setText("������ �� �������");
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
					imageView.setImageResource(R.drawable.success);
					break;
				case 1:
					imageView.setImageResource(R.drawable.warning);
					break;
				default:
					imageView.setImageResource(R.drawable.error);
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
	
	}

	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ��������� "�������"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showViruchka(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("�������");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);
	
		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
	
		// images
		TableRow currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<Integer> status = new ArrayList<Integer>();
	
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("�������")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				if (recordsArray.get(i).getParameterValue().matches("�����")) {
					status.add(0);
				} else {
					if (recordsArray.get(i).getParameterValue()
							.equals("���������")) {
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
			dataNotFound.setText("������ �� �������");
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
					imageView.setImageResource(R.drawable.success);
					break;
				case 1:
					imageView.setImageResource(R.drawable.warning);
					break;
				default:
					imageView.setImageResource(R.drawable.error);
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
	
	}

	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ���������
	 * "�����������"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showOperativniy(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("�����������");
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
	
		currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<Integer> status = new ArrayList<Integer>();
		// get status for last 5 days
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("�����������")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				// TODO return to days of month
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				if (recordsArray.get(i).getParameterValue().matches("�����")) {
					status.add(0);
				} else {
					if (recordsArray.get(i).getParameterValue()
							.equals("���������")) {
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
			dataNotFound.setText("������ �� �������");
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
					imageView.setImageResource(R.drawable.success);
					break;
				case 1:
					imageView.setImageResource(R.drawable.warning);
					break;
				default:
					imageView.setImageResource(R.drawable.error);
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
	
	}

	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ���������
	 * "SEND_IMSI"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showSendImsi(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("SEND_IMSI");
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
	
		currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<Integer> status = new ArrayList<Integer>();
		// get status for last 5 days
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("SEND_IMSI")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				// TODO return to days of month
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				if (recordsArray.get(i).getParameterValue().matches("OK")) {
					status.add(0);
				} else {
					if (recordsArray.get(i).getParameterValue()
							.equals("WARN")) {
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
			dataNotFound.setText("������ �� �������");
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
					imageView.setImageResource(R.drawable.success);
					break;
				case 1:
					imageView.setImageResource(R.drawable.warning);
					break;
				default:
					imageView.setImageResource(R.drawable.error);
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
	
	}

	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ��������� "FTP_UPL"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showFtpUpl(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("FTP_UPL");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);
	
		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
	
		// images
		TableRow currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<Integer> status = new ArrayList<Integer>();
	
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("FTP_UPL")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				if (recordsArray.get(i).getParameterValue().matches("OK")) {
					status.add(0);
				} else {
						status.add(1);
				}
	
				added++;
	
			}
		}
	
		if (added == 0) {
			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("������ �� �������");
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
					imageView.setImageResource(R.drawable.success);
					break;
				case 1:
					imageView.setImageResource(R.drawable.warning);
					break;
				default:
					imageView.setImageResource(R.drawable.error);
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
		
	}

	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ���������
	 * "�����_�����"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showVcheraUpalo(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("�����_�����");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);
	
		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
	
		// images
		TableRow currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<String> status = new ArrayList<String>();
	
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("�����_�����")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				
				status.add(recordsArray.get(i).getParameterValue());
				
	
				added++;
	
			}
		}
	
		if (added == 0) {
			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("������ �� �������");
			dataNotFound.setBackgroundColor(Color.parseColor("#8800CCCC"));
			innerLinearLayout.addView(dataNotFound);
		}
	
		if (status.size() == daysNumber.size()) {
			for (int i = 0; i < status.size(); i++) {
	
				TextView curDay = new TextView(this);
				curDay.setGravity(Gravity.CENTER);
				curDay.setText(String.valueOf(daysNumber.get(i)));
				days.addView(curDay);
	
				TextView textViewValue = new TextView(this);
				textViewValue.setGravity(Gravity.CENTER);
				textViewValue.setText(status.get(i));
				
				currentRow.addView(textViewValue);
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
	}
	
	
	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ���������
	 * "������_7_����"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showPadaloSemDney(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("������_7_����");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);
	
		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
	
		// images
		TableRow currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<String> status = new ArrayList<String>();
	
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("������_7_����")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				
				status.add(recordsArray.get(i).getParameterValue());
				
	
				added++;
	
			}
		}
	
		if (added == 0) {
			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("������ �� �������");
			dataNotFound.setBackgroundColor(Color.parseColor("#8800CCCC"));
			innerLinearLayout.addView(dataNotFound);
		}
	
		if (status.size() == daysNumber.size()) {
			for (int i = 0; i < status.size(); i++) {
	
				TextView curDay = new TextView(this);
				curDay.setGravity(Gravity.CENTER);
				curDay.setText(String.valueOf(daysNumber.get(i)));
				days.addView(curDay);
	
				TextView textViewValue = new TextView(this);
				textViewValue.setGravity(Gravity.CENTER);
				textViewValue.setText(status.get(i));
				
				currentRow.addView(textViewValue);
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
	}
	
	/**
	 * ��������� � ���������� linearLayout ���� � ���������� ���������
	 * "��������"
	 * 
	 * @param displayPanesActivity
	 *            - ������ �� Activity, � ������� ������������ ���������.
	 * @param linearLayout
	 *            - ����� � displayPanesActivity, � ������� ����� ��������
	 *            �������.
	 * @param recordsArray
	 *            - ������, �� ������ ������� ����� ������������� ������������ �
	 *            linearLayout �������.
	 */
	private void showSvobodno(DisplayPanesActivity displayPanesActivity,
			LinearLayout linearLayout, ArrayList<SmsRecord> recordsArray) {
		LinearLayout innerLinearLayout = new LinearLayout(this);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
	
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 50, 10, 50);
		innerLinearLayout.setLayoutParams(params);
	
		// label
		int childId = 0;
	
		TextView textView = new TextView(this);
		textView.setText("��������");
		textView.setId(++childId);
		textView.setBackgroundColor(Color.parseColor("#8800CCCC"));
		textView.setGravity(Gravity.CENTER);
		innerLinearLayout.addView(textView);
	
		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
	
		// images
		TableRow currentRow = new TableRow(this);
		TableRow days = new TableRow(this);
		int added = 0;
	
		TableRow.LayoutParams relativeLayoutParameters = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
	
		ArrayList<Integer> daysNumber = new ArrayList<Integer>();
		ArrayList<String> status = new ArrayList<String>();
	
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {
	
			if (recordsArray.get(i).getParameterName().equals("��������")) {
				ImageView imageView = new ImageView(this);
				imageView.setId(++childId);
				imageView.setPadding(0, 0, 0, 0);
	
				daysNumber.add(recordsArray.get(i).getDate()
						.get(Calendar.DAY_OF_MONTH));
	
				
				status.add(recordsArray.get(i).getParameterValue());
				
	
				added++;
	
			}
		}
	
		if (added == 0) {
			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("������ �� �������");
			dataNotFound.setBackgroundColor(Color.parseColor("#8800CCCC"));
			innerLinearLayout.addView(dataNotFound);
		}
	
		if (status.size() == daysNumber.size()) {
			for (int i = 0; i < status.size(); i++) {
	
				TextView curDay = new TextView(this);
				curDay.setGravity(Gravity.CENTER);
				curDay.setText(String.valueOf(daysNumber.get(i)));
				days.addView(curDay);
	
				TextView textViewValue = new TextView(this);
				textViewValue.setGravity(Gravity.CENTER);
				textViewValue.setText(status.get(i));
				
				currentRow.addView(textViewValue);
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
	}
	
	public static int getDaysAmount() {
		return daysAmount;
	}

	public static void setDaysAmount(int newDaysAmount) {
		daysAmount = newDaysAmount;
	}

}
