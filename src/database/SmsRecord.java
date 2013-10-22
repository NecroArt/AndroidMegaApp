package database;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class SmsRecord implements Parcelable {

	public class SmsRecordCreator implements Parcelable.Creator<SmsRecord> {

		@Override
		public SmsRecord createFromParcel(Parcel source) {
			return new SmsRecord(source);
		}

		@Override
		public SmsRecord[] newArray(int size) {
			return new SmsRecord[size];
		}

	}

	private int id;
	private String smsId;
	private Calendar date;
	private String parameterName;
	private String parameterValue;

	public SmsRecord(int id, String smsId, Calendar date, String parameterName,
			String parameterValue) {

		this.id = id;
		this.smsId = smsId;
		this.date = date;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}

	public SmsRecord(String smsId, String date, String parameterName,
			String parameterValue) {

		this.smsId = smsId;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(date));
		this.date = calendar;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}

	public SmsRecord(String smsId, Calendar date, String parameterName,
			String parameterValue) {

		this.id = 0;
		this.smsId = smsId;
		this.date = date;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}

	/**
	 * Deserializer.
	 * 
	 * @param source
	 */
	public SmsRecord(Parcel source) {
		id = source.readInt();
		smsId = source.readString();
		Calendar buf = Calendar.getInstance();
		buf.setTimeInMillis(source.readLong());
		date = buf;
		parameterValue = source.readString();
	}

	public String getSmsId() {
		return this.smsId;
	}

	public Calendar getDate() {
		return this.date;
	}

	public String getParameterName() {
		return this.parameterName;
	}

	public String getParameterValue() {
		return this.parameterValue;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setParameterName(String name) {
		this.parameterName = name;
	}

	public void setParameterValue(String value) {
		this.parameterValue = value;
	}

	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeLong(date.getTimeInMillis());
		dest.writeString(parameterName);
		dest.writeString(parameterValue);
	}

	public class MyCreator implements Parcelable.Creator<SmsRecord> {

		public SmsRecord createFromParcel(Parcel source) {
			return new SmsRecord(source);
		}

		@Override
		public SmsRecord[] newArray(int size) {
			return new SmsRecord[size];
		}
	}
}
