package database;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.provider.SyncStateContract.Columns;
import com.example.myfirstapp.DisplayTableActivity;
import com.example.myfirstapp.DisplayTableActivity.SMS;

public class DbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "productDB.db";
	
	public static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + TableEntry.TABLE_NAME + " (" + TableEntry.COLUMN_NAME_SMS_ID + " " + TableEntry.INTEGER_TYPE + " PRIMARY KEY, " +
					TableEntry.COLUMN_NAME_DATE + " timestamp, " + TableEntry.COLUMN_NAME_PARAMETER + " varchar (100));";
	
	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXIST " + TableEntry.TABLE_NAME;
	
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}
	
	public static final String TABLE_NAME = "sms_content";
	public static final String COLUMN_NAME_SMS_ID = "sms_id";
	public static final String COLUMN_NAME_DATE = "date";
	public static final String COLUMN_NAME_PARAMETER = "parameter";
	public static final String COLUMN_NAME_VALUE = "value";
	public static final String INTEGER_TYPE = "INTEGER";
	public static final String VARCHARTYPE = "VARCHAR (100)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	
	
	public static abstract class TableEntry implements BaseColumns {
		public static final String TABLE_NAME = "sms_content";
		public static final String COLUMN_NAME_SMS_ID = "sms_id";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_PARAMETER = "parameter";
		public static final String INTEGER_TYPE = "INTEGER";
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL(SQL_DELETE_ENTRIES);
		}
		catch (SQLException ex) {
			throw ex;
		}
		onCreate(db);
	}
	
	/**
	 * Add new record in database.
	 * @param date - SMS date 
	 * @param parameter - parameter name 
	 * @param value - parameter value
	 */
	public boolean addRecord (String date, String parameter, String value) {
		
		boolean added = false;
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_DATE, date);
		values.put(COLUMN_NAME_PARAMETER, parameter);
		values.put(COLUMN_NAME_DATE, date);
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		added = db.insert(TABLE_NAME, null, values) == -1L? false: true;
		db.close();
		//TODO set value "added" variable 
		
		return added;
		
	}
	
	public SmsRecord findByDate (Calendar date) {
		
		SmsRecord rec = null;
		return rec;
		
	}
	
	public ArrayList<SmsRecord> findByParameterName (String name) {
		
		String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_PARAMETER + " =  \"" + name + "\"";
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);
		
		ArrayList<SmsRecord> rec = new ArrayList<SmsRecord>();
		
		if (cursor.moveToFirst() == true)
		{
			do {
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(Long.parseLong(cursor.getString(1)));
				
				SmsRecord newRecord = new SmsRecord(
						Integer.parseInt(cursor.getString(0)),
						date,
						cursor.getString(2),
						cursor.getString(3)
						);
				rec.add(newRecord);
			}
			while (cursor.moveToNext());
		}
		
		return rec;
		
	}

	public SmsRecord findByParameterValue (String value) {

		SmsRecord rec = null;
		return rec;
		
	}
	
	public boolean delete (Integer id) {
		
		boolean deleted = false;
		
		//TODO delete
		
		//TODO set value "deleted" variable
		
		return deleted;
		
	}

	/** 
	 * syncing data of sms with database*/
	public void syncData () {
		
	}
	
	/**
	 * Return set number records from database, group by id. If amount is not defined or 0, then return all records. If table empty - returns null.
	 */
	public static ArrayList<SmsRecord> getAll () {
		ArrayList<SmsRecord> recordArray = new ArrayList<SmsRecord>();
		DisplayTableActivity classEntity = new DisplayTableActivity();
		ArrayList<SMS> smsArrayList = classEntity.getSMSArrayList (3);
		Integer i = 0;
		for (SMS currentSMS: smsArrayList) {
			//TODO right sms adding
			SmsRecord newSmsRecord = new SmsRecord(currentSMS.getDate(), "param " + i.toString(), "value " + i.toString());
			recordArray.add(newSmsRecord);
			i++;
		}
		 
		return recordArray;
		
	}
		
}
