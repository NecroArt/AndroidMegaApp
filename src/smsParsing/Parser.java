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
			
			String[] arrayPhrases = smsText.split((char) 10 + "|" + (char) 13);

			String[] parameterNames = { "GoldenGate", "ÎÒĞÀÁÎÒÀËÎ",
					"ÍÎ×ÜŞ_ÓÏÀËÎ", "ABONTODAY", "ÂÛĞÓ×ÊÀ", "ÂÛĞÓ×ÊÀ",
					"ÎÏÅĞÀÒÈÂÍÛÉ", "SEND_IMSI", "Â×ÅĞÀ_ÓÏÀËÎ", "ÏÀÄÀËÎ_7_ÄÍÅÉ",
					"ÑÂÎÁÎÄÍÎ", "FTP_UPL"};

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
}
