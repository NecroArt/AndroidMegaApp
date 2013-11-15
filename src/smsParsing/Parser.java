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
		if (smsText.startsWith("Ñòàòóñ êğèòè÷íûõ ïğîöåññîâ REP-COMM")) {
			//TODO delete this
				//true){
			
			// TODO delete this
			 //if (smsText.startsWith("G")) {

			//TODO move this strings in static array
			String goldenGate = "GoldenGate";
			String otrabotalo = "ÎÒĞÀÁÎÒÀËÎ";
			String nochyuUpalo = "ÍÎ×ÜŞ_ÓÏÀËÎ";
			String AbonToday = "ABONTODAY";
			String viruchka = "ÂÛĞÓ×ÊÀ";
			String operativniy = "ÎÏÅĞÀÒÈÂÍÛÉ";
			String SEND_IMSI = "SEND_IMSI";
			String vcheraUpalo = "Â×ÅĞÀ_ÓÏÀËÎ";
			String padalo = "ÏÀÄÀËÎ_7_ÄÍÅÉ";
			String svobodno = "ÑÂÎÁÎÄÍÎ";
			String FTP_Upl = "FTP_UPL";

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
			keyWords.add(FTP_Upl);

			String[] arrayPhrases = smsText.split((char) 10 + "|" + (char) 13);
			
			//TODO delete this
			MainActivity.text = arrayPhrases[0];
			MainActivity.my_text = "Ñòàòóñ êğèòè÷íûõ ïğîöåññîâ REP_COMM";

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
