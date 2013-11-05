package smsParsing;

import java.util.ArrayList;

import com.view.MainActivity;
import com.view.SMS;

import database.SmsRecord;

public class Parser {

	public static ArrayList<SmsRecord> parse(SMS sms) {

		String smsText = sms.getContent();
		ArrayList<SmsRecord> recordsArray = new ArrayList<SmsRecord>();

		// check that sms contain what will
		if (smsText.startsWith("Статус критичных процессов REP-COMM")) {
			// TODO delete this
			 //if (smsText.startsWith("G")) {

			String goldenGate = "GoldenGate";
			String otrabotalo = "ОТРАБОТАЛО";
			String nochyuUpalo = "НОЧЬЮ_УПАЛО";
			String AbonToday = "ABONTODAY";
			String viruchka = "ВЫРУЧКА";
			String operativniy = "ОПЕРАТИВНЫЙ";
			String SEND_IMSI = "SEND_IMSI";
			String vcheraUpalo = "ВЧЕРА_УПАЛО";
			String padalo = "ПАДАЛО_7_ДНЕЙ";
			String svobodno = "СВОБОДНО";

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
			
			//TODO delete this
			MainActivity.text = arrayPhrases[0];
			MainActivity.my_text = "Статус критичных процессов REP_COMM";

			for (String currentString: arrayPhrases) {

				boolean added = false;

				for (int j = 0; j < keyWords.size() && !added; j++) {

					if (currentString.indexOf(":") != -1
							&& currentString.substring(0,
									currentString.indexOf(":")).equals(
									keyWords.get(j))) {

						SmsRecord newRecord = new SmsRecord(sms.getId(),
								sms.getDate(), keyWords.get(j),
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
}
