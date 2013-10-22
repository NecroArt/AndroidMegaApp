package smsParsing;

import java.util.ArrayList;

import com.view.SMS;


import database.SmsRecord;

public class Parser {

	/*
	 * private static Set<String> keyWords = new Set<String>();
	 * 
	 * public static boolean addKeyWord (String word) { boolean wordAdded =
	 * false;
	 * 
	 * keyWords.add(word); return wordAdded; }
	 */

	public static ArrayList<SmsRecord> parse(SMS sms) {

		ArrayList<SmsRecord> recordsArray = new ArrayList<SmsRecord>();

		String smsText = sms.getContent();

		String goldenGate = "GoldenGate";
		String otrabotalo = "нрпюанрюкн";
		String nochyuUpalo = "мнвэч_союкн";
		String AbonToday = "ABONTODAY";
		String viruchka = "бшпсвйю";
		String operativniy = "ноепюрхбмши";
		String SEND_IMSI = "SEND_IMSI";
		String vcheraUpalo = "бвепю_союкн";
		String padalo = "оюдюкн_7_дмеи";
		String svobodno = "ябнандмн";

		ArrayList<String> keyWords = new ArrayList<String>();
		keyWords.add(goldenGate);
		keyWords.add(otrabotalo);
		keyWords.add(nochyuUpalo);
		keyWords.add(AbonToday);
		keyWords.add(viruchka);
		keyWords.add(operativniy);
		keyWords.add(SEND_IMSI);
		keyWords.add(vcheraUpalo);
		keyWords.add(padalo);
		keyWords.add(svobodno);

		String[] arrayPhrases = smsText.split("\n");

		for (int i = 0; i < arrayPhrases.length; i++) {
			boolean added = false;
			// parameter name
			// arrayPhrases[i].substring(0, arrayPhrases[i].indexOf(":"));

			// value
			// arrayPhrases[i].substring(arrayPhrases[i].indexOf(":"),
			// arrayPhrases[i].length());

			for (int j = 0; j < keyWords.size() && !added; j++) {

				if (arrayPhrases[i].indexOf(":") != -1
						&& arrayPhrases[i].substring(0,
								arrayPhrases[i].indexOf(":")).equals(
								keyWords.get(j))) {

					SmsRecord newRecord = new SmsRecord(sms.getId(),
							sms.getDate(), keyWords.get(j),
							arrayPhrases[i].substring(
									arrayPhrases[i].indexOf(":") + 2,
									arrayPhrases[i].length()));
					recordsArray.add(newRecord);
					added = true;

				}

			}

		}

		return recordsArray;

	}
}
