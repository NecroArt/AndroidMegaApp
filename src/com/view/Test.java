package com.view;

import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

public class Test extends Activity {
	public String getSMS(Integer rowNumReq) {

		StringBuilder smsBuilder = new StringBuilder();
		final String SMS_URI_INBOX = "content://sms/inbox";
		try {
			Uri uri = Uri.parse(SMS_URI_INBOX);
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };

			// interesting sms only from 000019
			String whereClause = "address=\"000019\"";

			// fetching sms with order by date
			Cursor cur = getContentResolver().query(
					uri,
					projection,
					whereClause,
					null,
					" date"
							+ (rowNumReq > 0 ? " limit 0, "
									+ rowNumReq.toString() : ""));
			if (cur.moveToFirst()) {
				int index_Address = cur.getColumnIndex("address");
				int index_Person = cur.getColumnIndex("person");
				int index_Body = cur.getColumnIndex("body");
				int index_Date = cur.getColumnIndex("date");
				int index_Type = cur.getColumnIndex("type");
				do {
					String strAddress = cur.getString(index_Address);
					int intPerson = cur.getInt(index_Person);
					String strbody = cur.getString(index_Body);
					long longDate = cur.getLong(index_Date);
					int int_Type = cur.getInt(index_Type);

					smsBuilder.append("[ ");
					smsBuilder.append(strAddress + ", ");
					smsBuilder.append(intPerson + ", ");
					smsBuilder.append(strbody + ", ");
					smsBuilder.append(new Date(longDate) + ", ");
					smsBuilder.append(int_Type);
					smsBuilder.append(" ]\n\n");
				} while (cur.moveToNext());

				if (!cur.isClosed()) {
					cur.close();
					cur = null;
				}
			} else {
				smsBuilder.append("no result!");
			}
		} catch (SQLiteException ex) {
			smsBuilder.append("SQLiteException" + ex.getMessage());
		}

		return smsBuilder.toString();
	}
}
