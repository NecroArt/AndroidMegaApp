package smsParsing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.view.DisplayPanesActivity;
import com.view.MainActivity;
import com.view.SMS;

import database.SmsRecord;

public class Parser {

	public static ArrayList<SmsRecord> parse(SMS sms) {

		String smsText = sms.getContent();
		ArrayList<SmsRecord> recordsArray = new ArrayList<SmsRecord>();

		// check that sms contain what will
		// TODO remove this?
		if (smsText.startsWith(DisplayPanesActivity.keyPhraseRepDBStatus)) {

			String[] arrayPhrases = smsText.split((char) 10 + "|" + (char) 13);

			String[] parameterNames = { "GoldenGate", "нрпюанрюкн",
					"мнвэч_союкн", "ABONTODAY", "бшпсвйю", "бшпсвйю",
					"ноепюрхбмши", "SEND_IMSI", "бвепю_союкн", "оюдюкн_7_дмеи",
					"ябнандмн", "FTP_UPL" };

			for (String currentString : arrayPhrases) {

				boolean added = false;

				for (int j = 0; j < parameterNames.length && !added; j++) {

					if (currentString.indexOf(":") != -1
							&& currentString.substring(0,
									currentString.indexOf(":")).equals(
									parameterNames[j])) {

						SmsRecord newRecord = new SmsRecord(sms.getId(),
								sms.getDate(), parameterNames[j],
								currentString.substring(
										currentString.indexOf(":") + 2,
										currentString.length()));
						recordsArray.add(newRecord);
						added = true;

					}
				}

			}

		}

		return recordsArray;

	}

	public static ArrayList<SmsRecord> parse(String allMessages) {
		
		ArrayList<SmsRecord> recordsArray = new ArrayList<SmsRecord>();

		// check that sms contain what will
		// TODO remove this?
		if (allMessages.startsWith(MainActivity.keyPhrase)) {

			String[] arrayPhrases = allMessages.split((char) 10 + "|" + (char) 13);

			String[] parameterNames = { "GoldenGate", "нрпюанрюкн",
					"мнвэч_союкн", "ABONTODAY", "бшпсвйю", "бшпсвйю",
					"ноепюрхбмши", "SEND_IMSI", "бвепю_союкн", "оюдюкн_7_дмеи",
					"ябнандмн", "FTP_UPL" };

			Calendar calendar = Calendar.getInstance();
			for (String currentString : arrayPhrases) {

				boolean added = false;

				for (int j = 0; j < parameterNames.length && !added; j++) {

					if (currentString.indexOf(":") != -1
							&& currentString.substring(0,
									currentString.indexOf(":")).equals(
									parameterNames[j])) {

						SmsRecord newRecord = new SmsRecord(calendar,
								parameterNames[j], currentString.substring(
										currentString.indexOf(":") + 2,
										currentString.length()));
						recordsArray.add(newRecord);
						added = true;

					}
				}

			}

		}

		return recordsArray;
	}
}
