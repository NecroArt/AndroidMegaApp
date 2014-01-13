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
import android.view.View;
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
	private static int paddingTop = 5;
	private static int paddingBottom = 5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panel);

		// TextView textView = (TextView)
		// findViewById(R.id.textViewGoldenGateDayOne);
		// textView.setVisibility(View.GONE);
		// textView.setText("");

		/*
		 * LinearLayout linearLayout = new LinearLayout(this);
		 * linearLayout.setOrientation(LinearLayout.VERTICAL);
		 */

		// TODO make it as user options

		// LinearLayout linearLayoutGoldenGate = (LinearLayout)
		// findViewById(R.id.golden_gate_include);

		ArrayList<String> parameters = new ArrayList<String>();

		DbHelper dbHelper = new DbHelper(this, null, null,
				DbHelper.getDBVersion());

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		if (prefs.getBoolean("show_golden_gate_status", true)) {

			parameters.add("GoldenGate");

			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.goldenGateLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);

		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.goldenGateLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.goldenGateTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_otrabotalo_status", true)) {
			parameters.add("ОТРАБОТАЛО");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.otrabotaloLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.otrabotaloLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.otrabotaloTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_nochyu_upalo_status", true)) {
			parameters.add("НОЧЬЮ_УПАЛО");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.nochyuUpaloLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.nochyuUpaloLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.nochyuUpaloTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_abontoday_status", true)) {
			parameters.add("ABONTODAY");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.abontodayLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.abontodayLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.abontodayTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_viruchka_status", true)) {
			parameters.add("ВЫРУЧКА");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.viruchkaLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.viruchkaLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.viruchkaTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_operativniy_status", true)) {
			parameters.add("ОПЕРАТИВНЫЙ");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.operativniyLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.operativniyLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.operativniyTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_send_imsi_status", true)) {
			parameters.add("SEND_IMSI");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sendImsiLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sendImsiLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.sendImsiTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_ftp_upl_status", true)) {
			parameters.add("FTP_UPL");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ftpUplLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ftpUplLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.ftpUplTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_vchera_upalo_status", true)) {
			parameters.add("ВЧЕРА_УПАЛО");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.vcheraUpaloLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.vcheraUpaloLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.vcheraUpaloTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_padalo_7_dney_status", true)) {
			parameters.add("ПАДАЛО_7_ДНЕЙ");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.padaloSemDneyLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.padaloSemDneyLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.padaloSemDneyTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		if (prefs.getBoolean("show_svobodno_status", true)) {
			parameters.add("СВОБОДНО");
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.svobodnoLinearLayout);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.svobodnoLinearLayout);
			TableLayout tableLayout = (TableLayout) findViewById(R.id.svobodnoTableLayout);
			linearLayout.removeView(tableLayout);
			
			linearLayout.setVisibility(View.GONE);
			
		}

		ArrayList<SmsRecord> recordsArray = dbHelper.getLastRecords(5,
				parameters);

		try {

			// GoldenGate
			if (prefs.getBoolean("show_golden_gate_status", true)) {
				showGoldenGate(recordsArray);
			}

			// ОТРАБОТАЛО
			if (prefs.getBoolean("show_otrabotalo_status", true)) {
				showOtrabotalo(recordsArray);
			}

			// НОЧЬЮ_УПАЛО
			if (prefs.getBoolean("show_nochyu_upalo_status", true)) {
				showNochyuUpalo(recordsArray);
			}

			// ABONTODAY
			if (prefs.getBoolean("show_abontoday_status", true)) {
				showAbontoday(recordsArray);
			}

			// ВЫРУЧКА
			if (prefs.getBoolean("show_viruchka_status", true)) {
				showViruchka(recordsArray);
			}

			// ОПЕРАТИВНЫЙ
			if (prefs.getBoolean("show_operativniy_status", true)) {
				showOperativniy(recordsArray);
			}

			// SEND_IMSI
			if (prefs.getBoolean("show_send_imsi_status", true)) {
				showSendImsi(recordsArray);
			}

			// FTP_UPL
			if (prefs.getBoolean("show_ftp_upl_status", true)) {
				showFtpUpl(recordsArray);
			}

			// ВЧЕРА_УПАЛО
			if (prefs.getBoolean("show_vchera_upalo_status", true)) {
				showVcheraUpalo(recordsArray);
			}

			// ПАДАЛО_7_ДНЕЙ
			if (prefs.getBoolean("show_padalo_7_dney_status", true)) {
				showPadaloSemDney(recordsArray);
			}

			// СВОБОДНО
			if (prefs.getBoolean("show_svobodno_status", true)) {
				showSvobodno(recordsArray);
			}

		} catch (Exception ex) {
			// TODO handle exceptions correctly way
			/*
			 * StackTraceElement[] s = ex.getStackTrace(); String str = ""; for
			 * (int i = 0; i < s.length; i++) { str += s[i].toString() +
			 * " --- "; } TextView text = new TextView(this);
			 * text.setText(ex.getMessage() + " --- " + str);
			 * linearLayout.addView(text); MainActivity.writeLog(ex);
			 */
		}

	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра
	 * "GoldenGate"
	 * 
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showGoldenGate(ArrayList<SmsRecord> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.goldenGateLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.goldenGateTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.goldenGateTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("GoldenGate")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				textViewDay.setGravity(Gravity.CENTER);
				tableRowDays.addView(textViewDay);

				ImageView imageViewValue = new ImageView(this);
				imageViewValue.setId(++childId);
				imageViewValue.setPadding(0, 0, 0, 0);
				

				if (recordsArray.get(i).getParameterValue().matches("OK")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue().equals("WARN")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}
				tableRowValues.addView(imageViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.goldenGateTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}

	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра
	 * "ОТРАБОТАЛО"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showOtrabotalo(ArrayList<SmsRecord> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.otrabotaloLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.otrabotaloTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.otrabotaloTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ОТРАБОТАЛО")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setGravity(Gravity.CENTER);
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				tableRowDays.addView(textViewDay);

				TextView textViewValue = new TextView(this);
				textViewValue.setPadding(0, paddingTop, 0, paddingBottom);
				textViewValue.setGravity(Gravity.CENTER);

				textViewValue.setText(recordsArray.get(i).getParameterValue());
				tableRowValues.addView(textViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.otrabotaloTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}

	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра
	 * "НОЧЬЮ_УПАЛО"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showNochyuUpalo(ArrayList<SmsRecord> recordsArray) {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.nochyuUpaloLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.nochyuUpaloTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.nochyuUpaloTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("НОЧЬЮ_УПАЛО")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setGravity(Gravity.CENTER);
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				tableRowDays.addView(textViewDay);

				TextView textViewValue = new TextView(this);
				textViewValue.setPadding(0, paddingTop, 0, paddingBottom);
				textViewValue.setGravity(Gravity.CENTER);

				textViewValue.setText(recordsArray.get(i).getParameterValue());
				tableRowValues.addView(textViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.nochyuUpaloTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}

	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра
	 * "ABONTODAY"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showAbontoday(ArrayList<SmsRecord> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.abontodayLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.abontodayTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.abontodayTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ABONTODAY")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				textViewDay.setGravity(Gravity.CENTER);
				tableRowDays.addView(textViewDay);

				ImageView imageViewValue = new ImageView(this);
				imageViewValue.setId(++childId);
				imageViewValue.setPadding(0, 0, 0, 0);

				if (recordsArray.get(i).getParameterValue().matches("вчера")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue().equals("позавчера")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}
				tableRowValues.addView(imageViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.abontodayTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}

	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра "Выручка"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showViruchka(ArrayList<SmsRecord> recordsArray) {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.viruchkaLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.viruchkaTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.viruchkaTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ВЫРУЧКА")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				textViewDay.setGravity(Gravity.CENTER);
				tableRowDays.addView(textViewDay);

				ImageView imageViewValue = new ImageView(this);
				imageViewValue.setId(++childId);
				imageViewValue.setPadding(0, 0, 0, 0);

				if (recordsArray.get(i).getParameterValue().matches("вчера")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue().equals("позавчера")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}
				tableRowValues.addView(imageViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.viruchkaTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}

	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра
	 * "Оперативный"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showOperativniy(ArrayList<SmsRecord> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.operativniyLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.operativniyTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.operativniyTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ОПЕРАТИВНЫЙ")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				textViewDay.setGravity(Gravity.CENTER);
				tableRowDays.addView(textViewDay);

				ImageView imageViewValue = new ImageView(this);
				imageViewValue.setId(++childId);
				imageViewValue.setPadding(0, 0, 0, 0);

				if (recordsArray.get(i).getParameterValue().matches("вчера")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue().equals("позавчера")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}
				tableRowValues.addView(imageViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.operativniyTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}
	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра
	 * "SEND_IMSI"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showSendImsi(ArrayList<SmsRecord> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sendImsiLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.sendImsiTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.sendImsiTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("SEND_IMSI")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				textViewDay.setGravity(Gravity.CENTER);
				tableRowDays.addView(textViewDay);

				ImageView imageViewValue = new ImageView(this);
				imageViewValue.setId(++childId);
				imageViewValue.setPadding(0, 0, 0, 0);

				if (recordsArray.get(i).getParameterValue().matches("OK")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue().equals("WARN")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}
				tableRowValues.addView(imageViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.sendImsiTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}
	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра "FTP_UPL"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showFtpUpl(ArrayList<SmsRecord> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ftpUplLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.ftpUplTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.ftpUplTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("FTP_UPL")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				textViewDay.setGravity(Gravity.CENTER);
				tableRowDays.addView(textViewDay);

				ImageView imageViewValue = new ImageView(this);
				imageViewValue.setId(++childId);
				imageViewValue.setPadding(0, 0, 0, 0);

				if (recordsArray.get(i).getParameterValue().matches("OK")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					imageViewValue.setImageResource(R.drawable.error);
				}
				tableRowValues.addView(imageViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.ftpUplTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}
	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра
	 * "ВЧЕРА_УПАЛО"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showVcheraUpalo(ArrayList<SmsRecord> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.vcheraUpaloLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.vcheraUpaloTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.vcheraUpaloTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ВЧЕРА_УПАЛО")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setGravity(Gravity.CENTER);
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				tableRowDays.addView(textViewDay);

				TextView textViewValue = new TextView(this);
				textViewValue.setPadding(0, paddingTop, 0, paddingBottom);
				textViewValue.setGravity(Gravity.CENTER);

				textViewValue.setText(recordsArray.get(i).getParameterValue());
				tableRowValues.addView(textViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.vcheraUpaloTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}
	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра
	 * "ПАДАЛО_7_ДНЕЙ"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showPadaloSemDney(ArrayList<SmsRecord> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.padaloSemDneyLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.padaloSemDneyTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.padaloSemDneyTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ПАДАЛО_7_ДНЕЙ")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setGravity(Gravity.CENTER);
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				tableRowDays.addView(textViewDay);

				TextView textViewValue = new TextView(this);
				textViewValue.setPadding(0, paddingTop, 0, paddingBottom);
				textViewValue.setGravity(Gravity.CENTER);

				textViewValue.setText(recordsArray.get(i).getParameterValue());
				tableRowValues.addView(textViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.padaloSemDneyTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}
	}

	/**
	 * Добавляет в полученный linearLayout блок с состоянием параметра
	 * "СВОБОДНО"
	 * 
	 * @param displayPanesActivity
	 *            - ссылка на Activity, в которой производится отрисовка.
	 * @param linearLayout
	 *            - макет в displayPanesActivity, в который будет добавлен
	 *            контент.
	 * @param recordsArray
	 *            - данные, на основе которых будет формироваться отображаемый в
	 *            linearLayout контент.
	 */
	private void showSvobodno(ArrayList<SmsRecord> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.svobodnoLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.svobodnoTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.svobodnoTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("СВОБОДНО")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = new TextView(this);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));
				textViewDay.setGravity(Gravity.CENTER);
				textViewDay.setPadding(0, paddingTop, 0, paddingBottom);
				tableRowDays.addView(textViewDay);

				TextView textViewValue = new TextView(this);
				textViewValue.setPadding(0, paddingTop, 0, paddingBottom);
				textViewValue.setGravity(Gravity.CENTER);

				textViewValue.setText(recordsArray.get(i).getParameterValue());
				tableRowValues.addView(textViewValue);

				added++;

			}
		}

		if (added == 0) {

			TableLayout tableLayout = (TableLayout) findViewById(R.id.svobodnoTableLayout);
			linearLayout.removeView(tableLayout);

			TextView dataNotFound = new TextView(this);
			dataNotFound.setGravity(Gravity.CENTER);
			dataNotFound.setText("Данные не найдены");
			linearLayout.addView(dataNotFound);

		}
	}

	public static int getDaysAmount() {
		return daysAmount;
	}

	public static void setDaysAmount(int newDaysAmount) {
		daysAmount = newDaysAmount;
	}

}
