package database;

import java.util.ArrayList;
import java.util.Calendar;

import com.view.MainActivity;
import com.view.SMS;

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


public class DbHelper extends SQLiteOpenHelper {

	public static abstract class TableEntry implements BaseColumns {
		public static final String TABLE_NAME = "SMS_CONTENT";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_SMS_ID = "sms_id";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_PARAMETER = "parameter";
		public static final String COLUMN_NAME_VALUE = "value";
		public static final String INTEGER_TYPE = "INTEGER";
	}

	public static final String COLUMN_NAME_SMS_ID = "sms_id";
	public static final String COLUMN_NAME_DATE = "date";
	public static final String COLUMN_NAME_PARAMETER = "parameter";
	public static final String COLUMN_NAME_VALUE = "value";
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "productDB.db";

	public static final String INTEGER_TYPE = "INTEGER";
	final String SMS_URI_INBOX = "content://sms/inbox";

	public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ TableEntry.TABLE_NAME + " (" + TableEntry.COLUMN_NAME_ID + " "
			+ TableEntry.INTEGER_TYPE + " PRIMARY KEY, "
			+ TableEntry.COLUMN_NAME_SMS_ID + " " + TableEntry.INTEGER_TYPE
			+ ", " + TableEntry.COLUMN_NAME_DATE + " "
			+ TableEntry.INTEGER_TYPE + " , "
			+ TableEntry.COLUMN_NAME_PARAMETER + " varchar (100), "
			+ TableEntry.COLUMN_NAME_VALUE + " varchar (100)" + ");";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ TableEntry.TABLE_NAME;

	public static final String TABLE_NAME = "sms_content";
	public static final String VARCHARTYPE = "VARCHAR (100)";

	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	/**
	 * Execute delete statement.
	 * @param db - Database object linked on application database.
	 */
	public void onDelete(SQLiteDatabase db) {

		db.execSQL(SQL_DELETE_ENTRIES);
	}

	/**
	 * Allow get database object for work with it.
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
			db.execSQL(SQL_DELETE_ENTRIES);
		} catch (SQLException ex) {
			throw ex;
		}
		onCreate(db);
		db.close();
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL(SQL_DELETE_ENTRIES);
		} catch (SQLException ex) {
			throw ex;
		}
		onCreate(db);
		db.close();
	}

	/**
	 * Return database version. This is a number hardcoded in DbHelper class as static int.
	 * @return Version of database in DbHelper class.
	 */
	public static int getDBVersion() {
	
		return DATABASE_VERSION;
	
	}

	/**
	 * Fetch all sms from phone number, parse it and insert result in database.
	 * @param context - Context where function use.
	 * @return Number of inserted rows.
	 */
	public int addAll(Context context) {
	
		ArrayList<SMS> SMSArray = new ArrayList<SMS>();
		SMSArray = getSMSArrayList(context, 0);
	
		int rowsAddedAmount = 0;
	
		ArrayList<String> ids = new ArrayList<String>();
		ids = getSmsIds();
		
		for (SMS currentSms : SMSArray) {
	
			// check, that current sms is not already in database
			if (!ids.contains(currentSms.getId())) {
	
				ArrayList<SmsRecord> recordsArray = Parser.parse(currentSms);
				
				for (SmsRecord currentRecord : recordsArray) {
	
					addRecord(currentRecord);
					rowsAddedAmount++;
	
				}
	
			}
	
			// TODO delete this
			if (rowsAddedAmount == 100) {
	
				break;
	
			}
	
		}
	
		return rowsAddedAmount;
	}

	/**
	 * Add new record in database.
	 * 
	 * @param date
	 *            - SMS date
	 * @param parameter
	 *            - parameter name
	 * @param value
	 *            - parameter value
	 */
	public boolean addRecord(SmsRecord addingRecord) {

		boolean added = false;

		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_SMS_ID, addingRecord.getSmsId());
		values.put(COLUMN_NAME_DATE, addingRecord.getDate().getTimeInMillis());
		values.put(COLUMN_NAME_PARAMETER, addingRecord.getParameterName());
		values.put(COLUMN_NAME_VALUE, addingRecord.getParameterValue());

		SQLiteDatabase db = this.getWritableDatabase();

		added = db.insert(TABLE_NAME, null, values) == -1L ? false : true;
		db.close();

		return added;

	}

	/**
	 * Delete SmsRecord by id parameter.
	 * @param id - Id of SmsRecord which must be delete.
	 * @return The number of rows affected.
	 */
	public int deleteById(Integer id) {
	
		int rowsDeleted = 0;
	
		SQLiteDatabase db = this.getWritableDatabase();
		String [] args = new String [] {String.valueOf(id)};
		rowsDeleted = db.delete(TABLE_NAME, TableEntry.COLUMN_NAME_ID + " = ?", args);
	
		return rowsDeleted;
	
	}
	
	/**
	 * Delete SmsRecord by id parameter.
	 * @param id - Id of Sms which contained in SmsRecord that must be delete.
	 * @return The number of rows affected.
	 */
	public int deleteBySmsId(Integer id) {
		
		int rowsDeleted = 0;
		
		SQLiteDatabase db = this.getWritableDatabase();
		String [] args = new String [] {String.valueOf(id)};
		rowsDeleted = db.delete(TABLE_NAME, TableEntry.COLUMN_NAME_SMS_ID + " = ?", args);
	
		return rowsDeleted;
	
	}

	/**
	 * Delete all data from table DbHelper.TABLE_NAME
	 * 
	 * @return
	 */
	public int deleteAll() {
	
		SQLiteDatabase db = this.getWritableDatabase();
	
		int amountRows = db.delete(TABLE_NAME, null, null);
		
		db.close();
		
		return amountRows;
	
	}

	public SmsRecord findByDate(Calendar date) {

		//TODO work out
		SmsRecord rec = null;
		return rec;

	}

	/**
	 * Fetch SmsRecords from application database, which parameter name equal "name" 
	 * @param name - Name of interesting parameter.
	 * @return Array list of SmsRecord.
	 */
	public ArrayList<SmsRecord> findByParameterName(String name) {

		//TODO test
		String query = "Select * FROM " + TABLE_NAME + " WHERE "
				+ COLUMN_NAME_PARAMETER + " = \"" + name + "\"";

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		ArrayList<SmsRecord> rec = new ArrayList<SmsRecord>();

		if (cursor.moveToFirst() == true) {
			do {
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(Long.parseLong(cursor.getString(2)));

				SmsRecord newRecord = new SmsRecord(cursor.getString(1), date,
						cursor.getString(2), cursor.getString(3));
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

	public SmsRecord findByParameterValue(String value) {
		
		//TODO work out
		SmsRecord rec = null;
		return rec;

	}

	/**
	 * Return set number records from database, group by id. If amount is not
	 * defined or 0, then return all records. If table empty - returns null.
	 */
	public ArrayList<SmsRecord> getAll() {
	
		ArrayList<SmsRecord> recordArray = new ArrayList<SmsRecord>();
	
		String query = "Select * FROM " + TABLE_NAME + " order by date desc";
		SQLiteDatabase db = this.getReadableDatabase();
	
		Cursor cursor = db.rawQuery(query, null);
	
		if (cursor.moveToFirst()) {
			do {
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(Long.parseLong(cursor.getString(2)));
	
				SmsRecord newRecord = new SmsRecord(cursor.getInt(0), cursor.getString(1), date,
						cursor.getString(3), cursor.getString(4));
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
	 * Fetch sms from phone memory.
	 * @param context - Context where function use.
	 * @param rowNumReq
	 * @return
	 */
	public static ArrayList<SMS> getSMSArrayList(Context context, Integer rowNumReq) {
	
		ArrayList<SMS> array = new ArrayList<SMS>();
	
		final String SMS_URI_INBOX = "content://sms/inbox";
	
		Uri uri = Uri.parse(SMS_URI_INBOX);
		String[] projection = new String[] { "_id", "address", "person",
				"body", "date", "type" };
	
		String whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER +"\"";
	
		ContextWrapper contextWrapper = new android.content.ContextWrapper(context);
		
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
	
				SMS newSMS = new SMS(strId, strbody, longDate);
	
				array.add(newSMS);
				
			} while (cur.moveToNext());
	
			if (!cur.isClosed()) {
				
				cur.close();
				cur = null;
				
			}
		}
	
		return array;
	}

	/**
	 * Fetch sms id of all records from table DbHelper.TableEntry.TABLE_NAME
	 * 
	 * @return Set of ids.
	 */
	public ArrayList<String> getSmsIds() {
	
		ArrayList<String> ids = new ArrayList<String>();
	
		String query = "Select distinct " + TableEntry.COLUMN_NAME_SMS_ID
				+ " FROM " + TABLE_NAME;
	
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
	
		if (cursor.moveToFirst()) {
	
			do {
	
				ids.add(cursor.getString(0));
	
			} while (cursor.moveToNext());
	
		}
		if (!cursor.isClosed()) {
			cursor.close();
			cursor = null;
		}
		
		db.close();
		
		return ids;
	
	}

	/**
	 * Fetch sms from phone memory using ContentProvider. Look up sms that only received from number 000019.
	 * @param context -  Context where function use.
	 * @param rowNumReq - number of required sms amount. 
	 * @return ArrayList of SmsRecord.
	 */
	public ArrayList<SmsRecord> getSMSRecordArrayList(Context context, Integer rowNumReq) {
		ArrayList<SmsRecord> array = new ArrayList<SmsRecord>();
		final String SMS_URI_INBOX = "content://sms/inbox";
		try {
			Uri uri = Uri.parse(SMS_URI_INBOX);
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
	
			// interesting sms only from 000019
			String whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER +"\"";
	
			// fetching sms with order by date
			
			ContextWrapper contextWrapper = new android.content.ContextWrapper(context);
			
			Cursor cur = contextWrapper.getContentResolver().query(
					uri,
					projection,
					whereClause,
					null,
					" date asc"
							+ (rowNumReq > 0 ? " limit 0, "
									+ rowNumReq.toString() : ""));
			if (cur.moveToFirst()) {
				
				int index_Date = cur.getColumnIndex("date");
				do {
					long longDate = cur.getLong(index_Date);
					
					Calendar currentCalendarDate = Calendar.getInstance();
					currentCalendarDate.setTimeInMillis(longDate);
					SmsRecord newSMS = new SmsRecord("1", currentCalendarDate,
							"name", "value");
	
					array.add(newSMS);
					
				} while (cur.moveToNext());
	
				if (!cur.isClosed()) {
					cur.close();
					cur = null;
				}
			}
		} catch (SQLiteException ex) {
			System.out.println("sql-exception occured");
		} catch (NullPointerException ex) {
			System.out.println("null pointer exception occured");
		}
	
		return array;
	}

	/**
	 * Fetch sms from phone memory, which have ids have not contained in application database. 
	 * @param context - Context where function use.
	 * @return Array list of sms, which _id fields not in application database.
	 */
	public ArrayList<SMS> getNewSms(Context context) {

		ArrayList<SMS> messages = new ArrayList<SMS>();

		ArrayList<String> smsIds = getSmsIds();

		try {
			Uri uri = Uri.parse(SMS_URI_INBOX);
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			String smsIdsList = null;
			
			for (String id : smsIds) {
				if (smsIdsList == null) {
					
					smsIdsList = id;
				} else {
					
					smsIdsList += ", " + id;
					
				}
				
			}
			String whereClause = null;
			if (smsIdsList != null) {
				
			whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER +"\"" + " and _id not in ("
					+ smsIdsList + ")";
			
			} else {
				
				whereClause = "address=\"" + MainActivity.TELEPHONE_NUMBER +"\"";
				
			}
			
			ContextWrapper contextWrapper = new android.content.ContextWrapper(context);
			
			//TODO delete limit
			Cursor cursor = contextWrapper.getContentResolver().query(uri, projection,
					whereClause, null, " date desc");

			if (cursor.moveToFirst()) {

				int index_id = cursor.getColumnIndex("_id");
				int index_Body = cursor.getColumnIndex("body");
				int index_Date = cursor.getColumnIndex("date");
				
				do {

					SMS newSms = new SMS(cursor.getString(index_id),
							cursor.getString(index_Body),
							cursor.getString(index_Date));

					messages.add(newSms);

				} while (cursor.moveToNext());

			}
			if (!cursor.isClosed()) {
				cursor.close();
				cursor = null;
			}
		} catch (SQLiteException ex) {
			System.out.println("sql-exception occured");
		}
		return messages;

	}
	
	
}
