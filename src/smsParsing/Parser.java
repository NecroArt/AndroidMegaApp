package smsParsing;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Calendar;

import com.view.DisplayPanesActivity;
import com.view.MainActivity;
import com.view.SMSInstance;

import database.SmsRecordAbonDynamic;
import database.SmsRecordRepDbStatus;

public class Parser {

	public static ArrayList<Object> parse(SMSInstance smsInstance) {

		String allMessages = smsInstance.getContent();

		ArrayList<Object> recordsArray = new ArrayList<Object>();
		try {

			// check that sms contain what will
			// TODO remove this?
			if (allMessages
					.startsWith(DisplayPanesActivity.keyPhraseRepDBStatus)) {

				String[] arrayPhrases = allMessages.split((char) 10 + "|"
						+ (char) 13);

				String[] parameterNames = { "GoldenGate", "ÎÒĞÀÁÎÒÀËÎ",
						"ÍÎ×ÜŞ_ÓÏÀËÎ", "ABONTODAY", "ÂÛĞÓ×ÊÀ", "ÎÏÅĞÀÒÈÂÍÛÉ",
						"SEND_IMSI", "Â×ÅĞÀ_ÓÏÀËÎ", "ÏÀÄÀËÎ_7_ÄÍÅÉ",
						"ÑÂÎÁÎÄÍÎ", "FTP_UPL" };

				ArrayList<SimpleEntry<String, String>> keyValueArray = getKeyAndValue(
						arrayPhrases, parameterNames);
				// Calendar calendar = Calendar.getInstance();
				for (SimpleEntry<String, String> currentRecord : keyValueArray) {

					SmsRecordRepDbStatus newRecord = new SmsRecordRepDbStatus(
							smsInstance.getDate(), currentRecord.getKey(),
							currentRecord.getValue());
					recordsArray.add(newRecord);

				}

			}
		} catch (Exception ex) {
			MainActivity.writeLog(ex, allMessages);
		}
		return recordsArray;

	}

	private static ArrayList<SimpleEntry<String, String>> getKeyAndValue(
			String[] arrayPhrases, String[] parameters) {
		ArrayList<SimpleEntry<String, String>> arrayList = null;
		try {
			arrayList = new ArrayList<SimpleEntry<String, String>>();

			for (String currentString : arrayPhrases) {

				boolean added = false;

				for (int j = 0; j < parameters.length && !added; j++) {

					if (currentString.indexOf(":") != -1
							&& currentString.substring(0,
									currentString.indexOf(":")).equals(
									parameters[j])) {

						String parameterValue = currentString.substring(
								currentString.indexOf(":") + 2,
								currentString.length());
						SimpleEntry<String, String> newEntry = new SimpleEntry<String, String>(
								parameters[j], parameterValue);
						arrayList.add(newEntry);
						added = true;

					}
				}

			}

		} catch (Exception ex) {
			MainActivity.writeLog(ex);
		}
		return arrayList;
	}

}
