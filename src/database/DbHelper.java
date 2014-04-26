package database;

import java.util.ArrayList;
import java.util.Calendar;

import smsParsing.Parser;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.view.MainActivity;
import com.view.SMSInstance;

public class DbHelper extends SQLiteOpenHelper {

	public static abstract class TableEntryRepDBStatus implements BaseColumns {
		public static final String TABLE_NAME = "SMS_CONTENT";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_PARAMETER = "parameter";
		public static final String COLUMN_NAME_VALUE = "value";
	}

	/**
	 * Представляет таблицу притока-оттока абонентов. Для каждой смс хранится особая строка с датой актуальности смс. Такая строка идентифицируется
	 * словом "DATE" в колонке COLUMN_NAME_REGION; в этом случае дата хранится в формате дд.мм.гггг в колонке COLUMN_NAME_DELIVERY_SUBS, а остальные
	 * содержат null-значения.
	 * @author artem.voytsekhovsky
	 *
	 */
	public static abstract class TableEntryAbonDynamic implements BaseColumns {
		public static final String TABLE_NAME = "ADON_DYNAMIC_CONTENT";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_REGION = "region";
		public static final String COLUMN_NAME_DELIVERY_SUBS = "delivery_subs";
		public static final String COLUMN_NAME_CHURN = "churn";
		public static final String COLUMN_NAME_TREND = "trend";
	}

	public static final String COLUMN_NAME_SMS_ID = "sms_id";
	public static final String COLUMN_NAME_DATE = "date";
	public static final String COLUMN_NAME_PARAMETER = "parameter";
	public static final String COLUMN_NAME_VALUE = "value";
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "productDB.db";

	public static final String INTEGER_TYPE = "INTEGER";
	public static final String VARCHAR_TYPE = "VARCHAR (100)";

	final String SMS_URI_INBOX = "content://sms/inbox";

	public static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
			+ TableEntryRepDBStatus.TABLE_NAME + " ("
			+ TableEntryRepDBStatus.COLUMN_NAME_ID + " " + INTEGER_TYPE
			+ " PRIMARY KEY, " + TableEntryRepDBStatus.COLUMN_NAME_DATE
			+ " " + INTEGER_TYPE + ", "
			+ TableEntryRepDBStatus.COLUMN_NAME_PARAMETER + " varchar (100), "
			+ TableEntryRepDBStatus.COLUMN_NAME_VALUE + " varchar (100)"
			+ ");| create table IF NOT EXISTS " + TableEntryAbonDynamic.TABLE_NAME + " ("
			+ TableEntryAbonDynamic.COLUMN_NAME_ID + " " + INTEGER_TYPE
			+ " PRIMARY KEY, " + TableEntryAbonDynamic.COLUMN_NAME_DATE
			+ " " + INTEGER_TYPE + ", "
			+ TableEntryAbonDynamic.COLUMN_NAME_REGION + " " + VARCHAR_TYPE
			+ ", " + TableEntryAbonDynamic.COLUMN_NAME_DELIVERY_SUBS + " "
			+ INTEGER_TYPE + ", " + TableEntryAbonDynamic.COLUMN_NAME_CHURN
			+ " " + INTEGER_TYPE + ", "
			+ TableEntryAbonDynamic.COLUMN_NAME_TREND + " " + INTEGER_TYPE
			+ ");";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ TableEntryRepDBStatus.TABLE_NAME + ";| DROP TABLE IF EXISTS "
			+ TableEntryAbonDynamic.TABLE_NAME;

	private static final String SQL_UPGRADE_FROM_1_to_2_ENTRIES = "create table "
			+ TableEntryAbonDynamic.TABLE_NAME
			+ " ("
			+ TableEntryAbonDynamic.COLUMN_NAME_ID
			+ " "
			+ INTEGER_TYPE
			+ " PRIMARY KEY, "
			+ COLUMN_NAME_SMS_ID
			+ " "
			+ INTEGER_TYPE
			+ ", "
			+ TableEntryAbonDynamic.COLUMN_NAME_DATE
			+ " "
			+ INTEGER_TYPE
			+ ", "
			+ TableEntryAbonDynamic.COLUMN_NAME_REGION
			+ " "
			+ VARCHAR_TYPE
			+ ", "
			+ TableEntryAbonDynamic.COLUMN_NAME_DELIVERY_SUBS
			+ " "
			+ INTEGER_TYPE
			+ ", "
			+ TableEntryAbonDynamic.COLUMN_NAME_CHURN
			+ " "
			+ INTEGER_TYPE
			+ ", "
			+ TableEntryAbonDynamic.COLUMN_NAME_TREND
			+ " "
			+ INTEGER_TYPE
			+ ");";
	
	private static final String SQL_UPGRADE_FROM_2_to_3_ENTRIES = "alter table " + TableEntryRepDBStatus.TABLE_NAME + " rename to " +
			TableEntryRepDBStatus.TABLE_NAME + "_old " + ";|" + "alter table " + TableEntryAbonDynamic.TABLE_NAME + " rename to " +
			TableEntryAbonDynamic.TABLE_NAME + "_old " + ";|" + SQL_CREATE_ENTRIES + "|" + "insert into " + TableEntryRepDBStatus.TABLE_NAME + "(" + 
			TableEntryRepDBStatus.COLUMN_NAME_ID + ", " + TableEntryRepDBStatus.COLUMN_NAME_DATE + ", " + TableEntryRepDBStatus.COLUMN_NAME_PARAMETER
			+ ", " + TableEntryRepDBStatus.COLUMN_NAME_VALUE + ")" + " select " + TableEntryRepDBStatus.COLUMN_NAME_ID
			+ ", " + TableEntryRepDBStatus.COLUMN_NAME_DATE + ", " + TableEntryRepDBStatus.COLUMN_NAME_PARAMETER
			+ ", " + TableEntryRepDBStatus.COLUMN_NAME_VALUE + " from " + TableEntryRepDBStatus.TABLE_NAME + "_old ;|"
			
			+ "insert into " + TableEntryAbonDynamic.TABLE_NAME + "(" + 
			TableEntryAbonDynamic.COLUMN_NAME_ID + ", " + TableEntryAbonDynamic.COLUMN_NAME_DATE + ", " + TableEntryAbonDynamic.COLUMN_NAME_CHURN +
			", " + TableEntryAbonDynamic.COLUMN_NAME_DELIVERY_SUBS + ", " + TableEntryAbonDynamic.COLUMN_NAME_REGION + ", " + 
			TableEntryAbonDynamic.COLUMN_NAME_TREND + ")" + " select " + TableEntryAbonDynamic.COLUMN_NAME_ID + ", " + 
			TableEntryAbonDynamic.COLUMN_NAME_DATE + ", " + TableEntryAbonDynamic.COLUMN_NAME_CHURN +
			", " + TableEntryAbonDynamic.COLUMN_NAME_DELIVERY_SUBS + ", " + TableEntryAbonDynamic.COLUMN_NAME_REGION + ", " + 
			TableEntryAbonDynamic.COLUMN_NAME_TREND + " from " + TableEntryAbonDynamic.TABLE_NAME + "_old ;| drop table if exists " +
			TableEntryRepDBStatus.TABLE_NAME + "_old ;| drop table if exists " + TableEntryAbonDynamic.TABLE_NAME + "_old;";

	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL(SQL_CREATE_ENTRIES);
		String[] arrayPhrases = SQL_CREATE_ENTRIES.split("\\|");
		for (String curEntry: arrayPhrases) {
			db.execSQL(curEntry);
		}
	}

	/**
	 * Execute delete statement.
	 * 
	 * @param db
	 *            - Database object linked on application database.
	 */
	public void onDelete(SQLiteDatabase db) {

		db.execSQL(SQL_DELETE_ENTRIES);
	}

	/**
	 * Allow get database object for work with it.
	 * 
	 * @return Current SQLite Database object.
	 */
	public SQLiteDatabase getDatabase() {

		return this.getWritableDatabase();

	}

	/**
	 * Execute delete and create statements.
	 */
	public void recreateDatabase() {

		SQLiteDatabase db = this.getWritableDatabase();
		onDelete(db);
		onCreate(db);
		db.close();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			//db.execSQL(SQL_UPGRADE_FROM_1_to_2_ENTRIES);
			String[] arrayPhrases = SQL_CREATE_ENTRIES.split("\\|");
			for (String curEntry: arrayPhrases) {
				db.execSQL(curEntry);
			}
			arrayPhrases = SQL_UPGRADE_FROM_2_to_3_ENTRIES.split("\\|");
			for (String curEntry: arrayPhrases) {
				db.execSQL(curEntry);
			}
		} catch (SQLException ex) {
			MainActivity.writeLog(ex);
		}
		db.close();
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL(SQL_DELETE_ENTRIES);
		} catch (SQLException ex) {
			MainActivity.writeLog(ex);
		}
		onCreate(db);
		db.close();
	}

	/**
	 * Return database version. This is a number hardcoded in DbHelper class as
	 * static int.
	 * 
	 * @return Version of database in DbHelper class.
	 */
	public static int getDBVersion() {

		return DATABASE_VERSION;

	}

	/**
	 * Fetch all sms from phone number, parse it and insert result in database.
	 * 
	 * @param context
	 *            - Context where function use.
	 * @return Number of inserted rows.
	 */
	public int addAll(Context context) {

		DbHelper dbHelper = new DbHelper(context, null, null,
				DbHelper.getDBVersion());
		ArrayList<SMSInstance> smsArrayList = dbHelper.getNewSms(context);

		int rowsAddedAmount = 0;

		for (SMSInstance currentSms : smsArrayList) {

			// check, that current sms is not already in database

			ArrayList<Object> recordsArray = Parser.parse(currentSms);

			for (Object currentRecord : recordsArray) {

				addRecordRepDBStatus((SmsRecordRepDbStatus) currentRecord);
				rowsAddedAmount++;

			}

		}

		return rowsAddedAmount;
	}

	/**
	 * Fetch all sms from phone number, parse it and insert result in database.
	 * 
	 * @param context
	 *            - Context where function use.
	 * @param addingSmsNumber
	 *            - Количество смс, которое нужно обработать. Если значение
	 *            меньше 1, то будут добавлены все соотвествующие смс.
	 * @return Number of inserted rows.
	 */
	public int addAll(Context context, Integer addingSmsNumber) {

		DbHelper dbHelper = new DbHelper(context, null, null,
				DbHelper.getDBVersion());
		ArrayList<SMSInstance> smsArrayList = dbHelper.getNewSms(context,
				addingSmsNumber);

		int rowsAddedAmount = 0;

		for (SMSInstance currentSms : smsArrayList) {

			// check, that current sms is not already in database

			ArrayList<Object> recordsArray = Parser.parse(currentSms);

			for (Object currentRecord : recordsArray) {

				addRecordRepDBStatus((SmsRecordRepDbStatus) currentRecord);
				rowsAddedAmount++;

			}

		}

		return rowsAddedAmount;
	}

	/**
	 * Добавляет новую запись в таблицу статуса REP-DB {@link TableEntryRepDBStatus}.
	 * 
	 * @param addingRecord - Запись статуса REP-DB, которую нужно добавить в таблицу {@link TableEntryRepDBStatus}.
	 * @return true - если запись была добавлена, иначе false.
	 */
	public boolean addRecordRepDBStatus(SmsRecordRepDbStatus addingRecord) {

		boolean added = false;

		ContentValues values = new ContentValues();
		
		values.put(TableEntryRepDBStatus.COLUMN_NAME_DATE, addingRecord
				.getDate().getTimeInMillis());
		values.put(TableEntryRepDBStatus.COLUMN_NAME_PARAMETER,
				addingRecord.getParameterName());
		values.put(TableEntryRepDBStatus.COLUMN_NAME_VALUE,
				addingRecord.getParameterValue());

		SQLiteDatabase db = this.getWritableDatabase();

		added = db.insert(TableEntryRepDBStatus.TABLE_NAME, null, values) == -1L ? false
				: true;
		db.close();

		return added;

	}
	
	/**
	 * Добавляет новую запись в таблицу движений абонентов {@link TableEntryAbonDynamic}.
	 * @param addingRecord  - Запись статуса REP-DB, которую нужно добавить в таблицу {@link TableEntryAbonDynamic}.
	 * @return true - если запись была добавлена, иначе false.
	 */
	public boolean addRecordAbonDynamic (SmsRecordAbonDynamic addingRecord) {
		
		boolean added = false;

		ContentValues values = new ContentValues();
		
		values.put(TableEntryAbonDynamic.COLUMN_NAME_REGION, addingRecord.getRegion());
		values.put(TableEntryAbonDynamic.COLUMN_NAME_DELIVERY_SUBS, addingRecord.getSubs());
		values.put(TableEntryAbonDynamic.COLUMN_NAME_CHURN, addingRecord.getChurn());
		values.put(TableEntryAbonDynamic.COLUMN_NAME_TREND, addingRecord.getTrend());
		
		SQLiteDatabase db = this.getWritableDatabase();

		added = db.insert(TableEntryAbonDynamic.TABLE_NAME, null, values) == -1L ? false
				: true;
		
		db.close();
		
		return added;
	}

	/**
	 * Delete SmsRecord by id parameter.
	 * 
	 * @param id
	 *            - Id of SmsRecord which must be delete.
	 * @return The number of rows affected.
	 */
	public int deleteByIdRepDbStatus(Integer id) {

		int rowsDeleted = 0;

		SQLiteDatabase db = this.getWritableDatabase();
		String[] args = new String[] { String.valueOf(id) };
		rowsDeleted = db.delete(TableEntryRepDBStatus.TABLE_NAME,
				TableEntryRepDBStatus.COLUMN_NAME_ID + " = ?", args);

		db.close();
		return rowsDeleted;

	}

	

	/**
	 * Delete SmsRecord by id parameter.
	 * 
	 * @param id
	 *            - Id of Sms which contained in SmsRecord that must be delete.
	 * @return The number of rows affected.
	 */
	/*public int deleteBySmsIdRepDbStatus(Integer id) {

		int rowsDeleted = 0;

		SQLiteDatabase db = this.getWritableDatabase();
		String[] args = new String[] { String.valueOf(id) };
		rowsDeleted = db.delete(TableEntryRepDBStatus.TABLE_NAME,
				TableEntryRepDBStatus.COLUMN_NAME_SMS_ID + " = ?", args);

		db.close();
		return rowsDeleted;

	}*/

	/**
	 * Delete all data from table DbHelper.TABLE_NAME
	 * 
	 * @return
	 */
	public int deleteAllRepDbStatus() {

		SQLiteDatabase db = this.getWritableDatabase();

		int amountRows = db
				.delete(TableEntryRepDBStatus.TABLE_NAME, null, null);
		
		amountRows += db
				.delete(TableEntryAbonDynamic.TABLE_NAME, null, null);

		db.close();

		return amountRows;

	}

	/**
	 * Извлекает из базы данных массив записей с указанной датой.
	 * 
	 * @param date
	 *            - Дата получения сообщения.
	 * @return Массив найденных записей.
	 */
	public ArrayList<SmsRecordRepDbStatus> findRecordByDate(Long date) {

		// TODO work out
		ArrayList<SmsRecordRepDbStatus> rec = null;
		return rec;

	}

	public SMSInstance findLastSmsRepDbStatus(Context context) {

		SMSInstance sMSInstance = null;

		try {

			Uri uri = Uri.parse(SMS_URI_INBOX);

			ContextWrapper contextWrapper = new android.content.ContextWrapper(
					context);
			String startWith = "Статус критичных процессов REP_COMM%";
			// String startWith = "GoldenGate%";
			String whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER
					+ "\" and body like " + "\"" + startWith + "\"";
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			Cursor cursor = contextWrapper.getContentResolver().query(uri,
					projection, whereClause, null, "date desc");

			if (cursor.moveToFirst()) {

				int index_id = cursor.getColumnIndex("_id");
				int index_Body = cursor.getColumnIndex("body");
				int index_Date = cursor.getColumnIndex("date");

				sMSInstance = new SMSInstance(cursor.getString(index_id),
						cursor.getString(index_Body),
						cursor.getString(index_Date));

			}

		} catch (Exception ex) {

			MainActivity.writeLog(ex);

		}
		
		return sMSInstance;
	}
	
	/**
	 * Fetch SmsRecords from application database, which parameter name equal
	 * "name"
	 * 
	 * @param name
	 *            - Name of interesting parameter.
	 * @return Array list of SmsRecord.
	 */
	public ArrayList<SmsRecordRepDbStatus> findByParameterNameRepDbStatus(String name) {

		// TODO test
		String query = "Select * FROM " + TableEntryRepDBStatus.TABLE_NAME
				+ " WHERE " + COLUMN_NAME_PARAMETER + " = \"" + name + "\"";

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		ArrayList<SmsRecordRepDbStatus> rec = new ArrayList<SmsRecordRepDbStatus>();

		if (cursor.moveToFirst() == true) {
			do {
				SmsRecordRepDbStatus newRecord = new SmsRecordRepDbStatus(
						cursor.getInt(0), cursor.getString(1), cursor.getString(2),
						cursor.getString(3));
				rec.add(newRecord);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
			cursor = null;
		}

		db.close();

		return rec;

	}

	/**
	 * Return set number records from database, group by id. If amount is not
	 * defined or 0, then return all records. If table empty - returns null.
	 */
	public ArrayList<SmsRecordRepDbStatus> getAllRepDbStatus() {

		ArrayList<SmsRecordRepDbStatus> recordArray = new ArrayList<SmsRecordRepDbStatus>();

		String query = "Select * FROM " + TableEntryRepDBStatus.TABLE_NAME
				+ " order by date desc";
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				SmsRecordRepDbStatus newRecord = new SmsRecordRepDbStatus(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3));
				recordArray.add(newRecord);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
			cursor = null;
		}

		db.close();

		return recordArray;

	}

	/**
	 * Возвращает дату последней смс из базы данных.
	 * 
	 * @return Миллисекунды, которые можно перевести в календарь.
	 */
	public Long getLastSmsDateRepDbStatus() {
	
		String query = "Select MAX(" + TableEntryRepDBStatus.COLUMN_NAME_DATE
				+ ") FROM " + TableEntryRepDBStatus.TABLE_NAME;
	
		SQLiteDatabase db = this.getWritableDatabase();
	
		Cursor cursor = db.rawQuery(query, null);
	
		if (cursor.moveToFirst() == true) {
			return cursor.getLong(0);
		}
	
		db.close();
		return 0L;
	
	}

	/**
	 * Получает из базы данных приложения строки, названия параметров которых
	 * содержится в parameterNames.
	 * 
	 * @param rowNumReq
	 *            - количество строк для каждого параметра, которое нужно
	 *            получить.
	 * @param parameterNames
	 *            - названия параметров, которые движок будет искать в базе.
	 * @return Массив соответствующих записей в количестве, не более чем
	 *         количество_строк * количество_имён_параметров.
	 */
	public ArrayList<SmsRecordRepDbStatus> getLastRecordsRepDbStatus(Integer rowNumReq,
			ArrayList<String> parameterNames) {

		ArrayList<SmsRecordRepDbStatus> records = new ArrayList<SmsRecordRepDbStatus>();

		SQLiteDatabase db = this.getReadableDatabase();

		String lastSmsDateQuery = "select max(date) from "
				+ TableEntryRepDBStatus.TABLE_NAME;

		Cursor cursor = db.rawQuery(lastSmsDateQuery, null);

		Long maxDate = null;
		if (cursor.moveToFirst()) {
			maxDate = cursor.getLong(0);
		}
		if (!cursor.isClosed()) {
			cursor.close();
			cursor = null;
		}

		if (maxDate != null) {

			// TODO cast rowNumReq time intervals below last sms time
			Calendar lastCal = Calendar.getInstance();
			lastCal.setTimeInMillis(maxDate);

			String query = "";

			for (int i = 0; i < rowNumReq; i++) {
				Calendar startCalendar = Calendar.getInstance();
				startCalendar.setTimeInMillis(maxDate);
				startCalendar.set(Calendar.HOUR_OF_DAY, 0);
				startCalendar.set(Calendar.MINUTE, 0);
				startCalendar.set(Calendar.SECOND, 0);
				startCalendar.set(Calendar.MILLISECOND, 0);
				startCalendar.add(Calendar.DAY_OF_MONTH, -i);

				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTimeInMillis(maxDate);
				endCalendar.set(Calendar.HOUR_OF_DAY, 23);
				endCalendar.set(Calendar.MINUTE, 59);
				endCalendar.set(Calendar.SECOND, 59);
				endCalendar.set(Calendar.MILLISECOND, 99);
				endCalendar.add(Calendar.DAY_OF_MONTH, -i);

				query += " select max("
						+ TableEntryRepDBStatus.COLUMN_NAME_DATE + ") from "
						+ TableEntryRepDBStatus.TABLE_NAME
						+ " where date between "
						+ String.valueOf(startCalendar.getTimeInMillis())
						+ " and "
						+ String.valueOf(endCalendar.getTimeInMillis());
				if (i < rowNumReq - 1) {
					query += " union ";
				}
			}

			ArrayList<Long> arrayCal = new ArrayList<Long>();

			if (!query.equals("")) {
				cursor = db.rawQuery(query, null);
				if (cursor.moveToFirst()) {

					do {
						arrayCal.add(cursor.getLong(0));
					} while (cursor.moveToNext());

				}
				if (!cursor.isClosed()) {
					cursor.close();
					cursor = null;
				}
			}
			String dates = "";
			for (int i = 0; i < arrayCal.size(); i++) {
				if (i < arrayCal.size() - 1) {
					dates += arrayCal.get(i) + ", ";
				} else {
					dates += arrayCal.get(i);
				}
			}

			String parameters = "";
			for (int i = 0; i < parameterNames.size(); i++) {
				if (i < parameterNames.size() - 1) {
					parameters += "\'" + parameterNames.get(i) + "\', ";
				} else {
					parameters += "\'" + parameterNames.get(i) + "\'";
				}
			}

			query = "select * from (select "
					+ TableEntryRepDBStatus.COLUMN_NAME_DATE + ", "
					+ TableEntryRepDBStatus.COLUMN_NAME_PARAMETER + ", "
					+ TableEntryRepDBStatus.COLUMN_NAME_VALUE + " from "
					+ TableEntryRepDBStatus.TABLE_NAME + " where "
					+ TableEntryRepDBStatus.COLUMN_NAME_DATE + " in (" + dates
					+ ") and " + TableEntryRepDBStatus.COLUMN_NAME_PARAMETER
					+ " in (" + parameters + ") order by "
					+ TableEntryRepDBStatus.COLUMN_NAME_DATE + " asc) q limit "
					+ String.valueOf(rowNumReq * parameterNames.size());
			cursor = db.rawQuery(query, null);
			if (cursor.moveToFirst()) {

				do {
					SmsRecordRepDbStatus newSmsRecrd = new SmsRecordRepDbStatus(cursor.getString(0),
							cursor.getString(1),
							cursor.getString(2));
					records.add(newSmsRecrd);
				} while (cursor.moveToNext());

			}
			if (!cursor.isClosed()) {
				cursor.close();
				cursor = null;
			}

		}

		db.close();

		return records;
	}

	/**
	 * Получает из базы данных приложения строки, названия параметров которых
	 * содержится в parameterNames.
	 * 
	 * @param rowNumReq
	 *            - количество строк для каждого параметра, которое нужно
	 *            получить.
	 * @param parameterNames
	 *            - названия параметров, которые движок будет искать в базе.
	 * @return Массив соответствующих записей в количестве, не более чем
	 *         количество_строк * количество_имён_параметров.
	 */
	public ArrayList<SmsRecordAbonDynamic> getLastRecordsAbonDynamic() {
		ArrayList<SmsRecordAbonDynamic> records = new ArrayList<SmsRecordAbonDynamic>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		String query = 
				"select " +
						TableEntryAbonDynamic.COLUMN_NAME_DATE + ", " +
						TableEntryAbonDynamic.COLUMN_NAME_REGION + ", " +
						TableEntryAbonDynamic.COLUMN_NAME_DELIVERY_SUBS + ", " +
						TableEntryAbonDynamic.COLUMN_NAME_CHURN + ", " +
						TableEntryAbonDynamic.COLUMN_NAME_TREND +
		" from " + TableEntryAbonDynamic.TABLE_NAME +
				" where " + TableEntryAbonDynamic.COLUMN_NAME_DATE + " in (select max(" + TableEntryAbonDynamic.COLUMN_NAME_DATE + ") from "
				+ TableEntryAbonDynamic.TABLE_NAME + ")";
		
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {

			do {
				SmsRecordAbonDynamic newSmsRecord = new SmsRecordAbonDynamic(cursor.getString(0),
						cursor.getString(1),
						cursor.getString(2),
						cursor.getString(3),
						cursor.getString(4));
				records.add(newSmsRecord);
			} while (cursor.moveToNext());

		}
		if (!cursor.isClosed()) {
			cursor.close();
			cursor = null;
		}
		
		db.close();

		return records;
	}
	/**
	 * Fetch sms from phone memory, which have ids have not contained in
	 * application database.
	 * 
	 * @param context
	 *            - Context where function use.
	 * @return Array list of sms, which _id fields not in application database.
	 */
	public ArrayList<SMSInstance> getNewSms(Context context) {

		ArrayList<SMSInstance> messages = new ArrayList<SMSInstance>();

		Long lastSmsDate = getLastDateRepDbStatus();

		try {

			Uri uri = Uri.parse(SMS_URI_INBOX);

			// TODO clear this
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			
			String whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER + "\"";
			if (lastSmsDate != null && lastSmsDate != 0) {

				whereClause += " and date > " + String.valueOf(lastSmsDate);

			}
			
			ContextWrapper contextWrapper = new android.content.ContextWrapper(
					context);

			Cursor cursor = contextWrapper.getContentResolver().query(uri,
					projection, whereClause, null, " date desc");

			if (cursor.moveToFirst()) {

				int index_id = cursor.getColumnIndex("_id");
				int index_Body = cursor.getColumnIndex("body");
				int index_Date = cursor.getColumnIndex("date");

				do {

					SMSInstance newSms = new SMSInstance(
							cursor.getString(index_id),
							cursor.getString(index_Body),
							cursor.getString(index_Date));

					messages.add(newSms);

				} while (cursor.moveToNext());

			}

			if (!cursor.isClosed()) {

				cursor.close();
				cursor = null;

			}

		} catch (Exception ex) {

			MainActivity.writeLog(ex);

		}

		return messages;

	}

	/**
	 * Возвращает максимальную дату из записей в {@link TableEntryRepDBStatus}. Если возникло исключение, то возвращает null. Если данные не найдены, то возвращает 0. 
	 * @return Дата в миллисекундах.
	 */
	private Long getLastDateRepDbStatus() {

		Long maxDate = null;
		
		try {
		SQLiteDatabase db = this.getReadableDatabase();

		String lastSmsDateQuery = "select max(date) from "
				+ TableEntryRepDBStatus.TABLE_NAME;

		Cursor cursor = db.rawQuery(lastSmsDateQuery, null);
		
		maxDate = 0L;
		if (cursor.moveToFirst()) {
			maxDate = cursor.getLong(0);
		}
		if (!cursor.isClosed()) {
			cursor.close();
			cursor = null;
		}
		}
		catch (Exception ex) {
			MainActivity.writeLog(ex);
		}
				
		return maxDate;
	}

	/**
	 * Fetch sms from phone memory, which have ids have not contained in
	 * application database.
	 * 
	 * @param context
	 *            - Context where function use.
	 * @param smsNumber
	 *            - Количество смс, которое нужно получить. Если меньше 1, то
	 *            произведёт попытку поиска 1 смс.
	 * @return Array list of sms, which _id fields not in application database.
	 */
	// TODO test
	private ArrayList<SMSInstance> getNewSms(Context context, Integer smsNumber) {

		if (smsNumber < 1) {
			smsNumber = 1;
		}

		ArrayList<SMSInstance> messages = new ArrayList<SMSInstance>();

		Long lastSmsDate = getLastDateRepDbStatus();

		try {

			Uri uri = Uri.parse(SMS_URI_INBOX);

			// TODO clear this
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			String whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER + "\"";
			if (lastSmsDate != null && lastSmsDate != 0) {

				whereClause += " and date > " + String.valueOf(lastSmsDate);

			}

			ContextWrapper contextWrapper = new android.content.ContextWrapper(
					context);

			Cursor cursor = contextWrapper.getContentResolver().query(uri,
					projection, whereClause, null,
					" date desc limit " + String.valueOf(smsNumber));

			if (cursor.moveToFirst()) {

				int index_id = cursor.getColumnIndex("_id");
				int index_Body = cursor.getColumnIndex("body");
				int index_Date = cursor.getColumnIndex("date");

				do {

					SMSInstance newSms = new SMSInstance(
							cursor.getString(index_id),
							cursor.getString(index_Body),
							cursor.getString(index_Date));

					messages.add(newSms);

				} while (cursor.moveToNext());

			}

			if (!cursor.isClosed()) {

				cursor.close();
				cursor = null;

			}

		} catch (Exception ex) {

			MainActivity.writeLog(ex);

		}

		return messages;
	}

	/**
	 * Fetch sms from phone memory.
	 * 
	 * @param context
	 *            - Context where function use.
	 * @param rowNumReq
	 * @return
	 */
	public static ArrayList<SMSInstance> getSMSArrayList(Context context,
			Integer rowNumReq) {

		ArrayList<SMSInstance> array = new ArrayList<SMSInstance>();

		final String SMS_URI_INBOX = "content://sms/inbox";

		Uri uri = Uri.parse(SMS_URI_INBOX);
		String[] projection = new String[] { "_id", "address", "person",
				"body", "date", "type" };

		String whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER
				+ "\"";

		ContextWrapper contextWrapper = new android.content.ContextWrapper(
				context);

		// fetching sms with order by date
		Cursor cur = contextWrapper.getContentResolver().query(
				uri,
				projection,
				whereClause,
				null,
				" date desc"
						+ (rowNumReq > 0 ? " limit 0, " + rowNumReq.toString()
								: ""));
		if (cur.moveToFirst()) {

			int index_id = cur.getColumnIndex("_id");
			int index_Body = cur.getColumnIndex("body");
			int index_Date = cur.getColumnIndex("date");

			do {

				String strId = cur.getString(index_id);

				String strbody = cur.getString(index_Body);
				String longDate = cur.getString(index_Date);

				SMSInstance newSMS = new SMSInstance(strId, strbody, longDate);

				array.add(newSMS);

			} while (cur.moveToNext());

			if (!cur.isClosed()) {

				cur.close();
				cur = null;

			}
		}

		return array;
	}

}
