package smsParsing;

import java.util.AbstractMap.SimpleEntry;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
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

				String[] parameterNames = { "GoldenGate", "ОТРАБОТАЛО",
						"НОЧЬЮ_УПАЛО", "ABONTODAY", "ВЫРУЧКА", "ОПЕРАТИВНЫЙ",
						"SEND_IMSI", "ВЧЕРА_УПАЛО", "ПАДАЛО_7_ДНЕЙ",
						"СВОБОДНО", "FTP_UPL" };

				ArrayList<SimpleEntry<String, String>> keyValueArray = getKeyAndValue(
						arrayPhrases, parameterNames);

				for (SimpleEntry<String, String> currentRecord : keyValueArray) {

					SmsRecordRepDbStatus newRecord = new SmsRecordRepDbStatus(
							smsInstance.getDate(), currentRecord.getKey(),
							currentRecord.getValue());
					recordsArray.add(newRecord);

				}

			} else {
				if (allMessages
						.startsWith(DisplayPanesActivity.keyPhraseAbonDynamic)) {
					String[] arrayPhrases = allMessages.split((char) 10 + "|"
							+ (char) 13);

					// работает так: из первой строки берётся дата, вторая
					// игнорится, из остальных строк, кроме той, что начинается
					// с "-" берутся данные

					// извлечение даты
					String firstString = arrayPhrases[0];
					String date = null;
					Pattern p = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
					Matcher m = p.matcher(firstString);
					if (m.find()) {
						date = m.group();
						
						SmsRecordAbonDynamic smsRecordAbonDynamic = new SmsRecordAbonDynamic(
								smsInstance.getDate(), "DATE", date);
						
						recordsArray.add(smsRecordAbonDynamic);
					}

					p = Pattern
							.compile("([A-Za-z]*): (\\d*)/(\\d*) \\((\\d*)\\%\\)");
					for (int i = 2; i < arrayPhrases.length; i++) {

						m = p.matcher(arrayPhrases[i]);

						if (m.find()) {

							String region = m.group(1);
							String subs = m.group(2);
							String churn = m.group(3);
							String trend = m.group(4);

							SmsRecordAbonDynamic smsRecordAbonDynamic = new SmsRecordAbonDynamic(
									smsInstance.getDate(), region, subs, churn,
									trend);
							
							recordsArray.add(smsRecordAbonDynamic);
						}

					}

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
