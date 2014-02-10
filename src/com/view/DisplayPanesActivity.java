package com.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import database.DbHelper;
import database.SmsRecordRepDbStatus;

public class DisplayPanesActivity extends Activity {

	private static int daysAmount = 5;
	private static final int RESULT_SETTINGS = 1;
	public static String keyPhraseRepDBStatus = "Статус критичных процессов REP-COMM";
	public static String keyPhraseAbonDynamic = "Динамика АБ за ";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		try {
			MainActivity.locale = getResources().getConfiguration().locale;

			super.onCreate(savedInstanceState);
			setContentView(R.layout.panel);

			PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

			// установка значения даты последней полученной смс
			TextView lastSmsDateTextView = (TextView) findViewById(R.id.last_sms_date);

			DbHelper dbHelper = new DbHelper(this, null, null,
					DbHelper.getDBVersion());
			
			//TEST
			/*Calendar cal = Calendar.getInstance();
			
			SmsRecordRepDbStatus rec = new SmsRecordRepDbStatus(cal, "GoldenGate", "OK");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ОТРАБОТАЛО", "123");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "НОЧЬЮ_УПАЛО", "2");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ABONTODAY", "вчера");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ВЫРУЧКА", "вчера");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ОПЕРАТИВНЫЙ", "вчера");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "SEND_IMSI", "OK");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ВЧЕРА_УПАЛО", "2");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ПАДАЛО_7_ДНЕЙ", "2");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "СВОБОДНО", "234");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "FTP_UPL", "OK");
			dbHelper.addRecordRepDBStatus(rec);
			
			cal.add(Calendar.HOUR_OF_DAY, 2);
			rec = new SmsRecordRepDbStatus(cal, "GoldenGate", "OK");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ОТРАБОТАЛО", "123");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "НОЧЬЮ_УПАЛО", "2");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ABONTODAY", "вчера");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ВЫРУЧКА", "вчера");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ОПЕРАТИВНЫЙ", "вчера");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "SEND_IMSI", "OK");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ВЧЕРА_УПАЛО", "2");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ПАДАЛО_7_ДНЕЙ", "2");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "СВОБОДНО", "234");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "FTP_UPL", "OK");
			dbHelper.addRecordRepDBStatus(rec);
			
			cal.add(Calendar.DAY_OF_MONTH, -1);
			rec = new SmsRecordRepDbStatus(cal, "GoldenGate", "WARN");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ОТРАБОТАЛО", "124");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "НОЧЬЮ_УПАЛО", "5");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ABONTODAY", "позавчера");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ВЫРУЧКА", "позавчера");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ОПЕРАТИВНЫЙ", "позавчера");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "SEND_IMSI", "WARN");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ВЧЕРА_УПАЛО", "6");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "ПАДАЛО_7_ДНЕЙ", "5");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "СВОБОДНО", "879");
			dbHelper.addRecordRepDBStatus(rec);
			rec = new SmsRecordRepDbStatus(cal, "FTP_UPL", "ERR");
			dbHelper.addRecordRepDBStatus(rec);*/

			Long millis = dbHelper.getLastSmsDateRepDbStatus();
			Locale locale = getResources().getConfiguration().locale;
			if (millis != 0L) {
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(millis);
				String lastSmsDate = date.get(Calendar.DAY_OF_MONTH)
						+ " "
						+ new SimpleDateFormat("MMMM", locale).format(date
								.getTime())
						+ " "
						+ String.format("%02d:%02d:%02d",
								date.get(Calendar.HOUR_OF_DAY),
								date.get(Calendar.MINUTE),
								date.get(Calendar.SECOND));
				lastSmsDateTextView.setText("Обновлено " + lastSmsDate);
			} else {
				lastSmsDateTextView.setText("Нет данных об sms");
			}

			TextView version = (TextView) findViewById(R.id.text_view_version);
			version.setText(this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
			
			ArrayList<String> parameters = new ArrayList<String>();

			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());

			if (prefs.getBoolean("show_golden_gate_status", true)) { // TODO
																		// может
																		// лучше
																		// наоборот,
																		// сделать
																		// visible,
																		// а при
																		// проверке
																		// ставить
																		// gone

				parameters.add("GoldenGate");

				LinearLayout linearLayout = (LinearLayout) findViewById(R.id.goldenGateLinearLayout);
				linearLayout.setVisibility(View.VISIBLE);

			} else { // TODO а это надо?

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

			ArrayList<SmsRecordRepDbStatus> recordsArray = dbHelper
					.getLastRecordsRepDbStatus(5, parameters);

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
			MainActivity.writeLog(ex);
		}

	}

	@Override
	public void onRestart() {
		onCreate(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_menu, menu);
		getMenuInflater().inflate(R.menu.database_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i1 = new Intent(this, SettingsActivity.class);
			startActivityForResult(i1, RESULT_SETTINGS);
			break;
		case R.id.action_database_settings:
			Intent i2 = new Intent(this, SettingsDatabaseActivity.class);
			startActivity(i2);
			break;
		case R.id.action_about:
			Intent i3 = new Intent(this, AboutActivity.class);
			startActivity(i3);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:

			break;

		}

	}
	
	public void addAll(View view) {

		try {
			DbHelper dbHelper = new DbHelper(this, null, null,
					DbHelper.getDBVersion());

			// EditText editText = (EditText) findViewById(R.id.edit_message);
			Integer rowsAdded = 0;

			/*
			 * try { Integer smsNumber =
			 * Integer.valueOf(editText.getText().toString()); if (smsNumber >
			 * 0) { rowsAdded = dbHelper.addAll(this, smsNumber); } else {
			 * rowsAdded = dbHelper.addAll(this); } } catch
			 * (NumberFormatException ex) {
			 */
			rowsAdded = dbHelper.addAll(this);
			// }

			Toast.makeText(this, "Добавлено " + rowsAdded + " записей",
					Toast.LENGTH_LONG).show();
		} catch (Exception ex) {
			MainActivity.writeLog(ex);
		}

	}

	public void deleteAll(View view) {

		try {
			DbHelper dbHelper = new DbHelper(this, null, null,
					DbHelper.getDBVersion());
			Integer deletedRows = dbHelper.deleteAllRepDbStatus();
			Toast.makeText(
					this,
					deletedRows > 0 ? "Удалено " + deletedRows.toString()
							+ " записей" : "БД пуста", Toast.LENGTH_LONG)
					.show();
		} catch (Exception ex) {
			MainActivity.writeLog(ex);
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
	private void showGoldenGate(ArrayList<SmsRecordRepDbStatus> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.goldenGateLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.goldenGateTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.goldenGateTableRowValues);
		int added = 0;

		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("GoldenGate")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				ImageView imageViewValue = (ImageView) tableRowValues
						.getChildAt(added);

				if (recordsArray.get(i).getParameterValue().matches("OK")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue().equals("WARN")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showOtrabotalo(ArrayList<SmsRecordRepDbStatus> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.otrabotaloLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.otrabotaloTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.otrabotaloTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ОТРАБОТАЛО")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				TextView textViewValue = (TextView) tableRowValues
						.getChildAt(added);
				textViewValue.setText(recordsArray.get(i).getParameterValue());

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showNochyuUpalo(ArrayList<SmsRecordRepDbStatus> recordsArray) {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.nochyuUpaloLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.nochyuUpaloTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.nochyuUpaloTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("НОЧЬЮ_УПАЛО")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				TextView textViewValue = (TextView) tableRowValues
						.getChildAt(added);
				textViewValue.setText(recordsArray.get(i).getParameterValue());

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showAbontoday(ArrayList<SmsRecordRepDbStatus> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.abontodayLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.abontodayTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.abontodayTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ABONTODAY")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				ImageView imageViewValue = (ImageView) tableRowValues
						.getChildAt(added);

				if (recordsArray.get(i).getParameterValue().matches("вчера")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue()
							.equals("позавчера")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showViruchka(ArrayList<SmsRecordRepDbStatus> recordsArray) {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.viruchkaLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.viruchkaTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.viruchkaTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ВЫРУЧКА")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				ImageView imageViewValue = (ImageView) tableRowValues
						.getChildAt(added);

				if (recordsArray.get(i).getParameterValue().matches("вчера")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue()
							.equals("позавчера")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showOperativniy(ArrayList<SmsRecordRepDbStatus> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.operativniyLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.operativniyTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.operativniyTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ОПЕРАТИВНЫЙ")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				ImageView imageViewValue = (ImageView) tableRowValues
						.getChildAt(added);

				if (recordsArray.get(i).getParameterValue().matches("вчера")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue()
							.equals("позавчера")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showSendImsi(ArrayList<SmsRecordRepDbStatus> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sendImsiLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.sendImsiTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.sendImsiTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("SEND_IMSI")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				ImageView imageViewValue = (ImageView) tableRowValues
						.getChildAt(added);

				if (recordsArray.get(i).getParameterValue().matches("OK")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					if (recordsArray.get(i).getParameterValue().equals("WARN")) {
						imageViewValue.setImageResource(R.drawable.warning);
					} else {
						imageViewValue.setImageResource(R.drawable.error);
					}
				}

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showFtpUpl(ArrayList<SmsRecordRepDbStatus> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ftpUplLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.ftpUplTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.ftpUplTableRowValues);
		int childId = 0;
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("FTP_UPL")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				ImageView imageViewValue = (ImageView) tableRowValues
						.getChildAt(added);

				if (recordsArray.get(i).getParameterValue().matches("OK")) {
					imageViewValue.setImageResource(R.drawable.success);
				} else {
					imageViewValue.setImageResource(R.drawable.error);
				}

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showVcheraUpalo(ArrayList<SmsRecordRepDbStatus> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.vcheraUpaloLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.vcheraUpaloTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.vcheraUpaloTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ВЧЕРА_УПАЛО")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				TextView textViewValue = (TextView) tableRowValues
						.getChildAt(added);
				textViewValue.setText(recordsArray.get(i).getParameterValue());

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showPadaloSemDney(ArrayList<SmsRecordRepDbStatus> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.padaloSemDneyLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.padaloSemDneyTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.padaloSemDneyTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("ПАДАЛО_7_ДНЕЙ")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				TextView textViewValue = (TextView) tableRowValues
						.getChildAt(added);
				textViewValue.setText(recordsArray.get(i).getParameterValue());

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
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
	private void showSvobodno(ArrayList<SmsRecordRepDbStatus> recordsArray) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.svobodnoLinearLayout);
		TableRow tableRowDays = (TableRow) findViewById(R.id.svobodnoTableRowDays);
		TableRow tableRowValues = (TableRow) findViewById(R.id.svobodnoTableRowValues);
		int added = 0;
		// TODO make days amount parameter
		for (int i = 0; added < daysAmount && i < recordsArray.size(); i++) {

			if (recordsArray.get(i).getParameterName().equals("СВОБОДНО")) {

				Calendar date = recordsArray.get(i).getDate();

				TextView textViewDay = (TextView) tableRowDays
						.getChildAt(added);
				textViewDay.setText(String.valueOf(date
						.get(Calendar.DAY_OF_MONTH)));

				TextView textViewValue = (TextView) tableRowValues
						.getChildAt(added);
				textViewValue.setText(recordsArray.get(i).getParameterValue());

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

		} else {
			if (added < daysAmount) {
				for (int i = added; i < daysAmount; i++) {
					tableRowDays.getChildAt(i).setVisibility(View.GONE);
					tableRowValues.getChildAt(i).setVisibility(View.GONE);
				}
			}
		}
	}

	public static int getDaysAmount() {
		return daysAmount;
	}

	public static void setDaysAmount(int newDaysAmount) {
		daysAmount = newDaysAmount;
	}

}
