package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

@RequiredArgsConstructor
class RequestServiceTest {

    @Test
    void isValidCNP() {

        // Test CNPs
        String validCNP = "6031231521019";
        // Assert CNPs
        Assertions.assertTrue(isValidCNP(validCNP));

    }

    public boolean isValidCNP(String cnp) {
        // Verificăm dimensiunea CNP-ului
        if (cnp.length() != 13) {
            return false;
        }

        // Verificăm prima cifră
        char firstDigit = cnp.charAt(0);
        boolean isMale = firstDigit == '1' || firstDigit == '5';
        boolean isFemale = firstDigit == '2' || firstDigit == '6';
        if (!isMale && !isFemale) {
            return false;
        }

        // Verificăm celelalte cifre
        try {
            int year = Integer.parseInt(cnp.substring(1, 3));
            year += firstDigit == '5' || firstDigit == '6' ? 2000 : 1900;
            int month = Integer.parseInt(cnp.substring(3, 5));
            int day = Integer.parseInt(cnp.substring(5, 7));
            int countyCode = Integer.parseInt(cnp.substring(7, 9));
            int orderNumber = Integer.parseInt(cnp.substring(9, 12));

            if (year < 0 || year > 9999 || month < 1 || month > 12 ||
                    day < 1 || day > 31 || countyCode < 1 || countyCode > 52 ||
                    orderNumber < 1 || orderNumber > 999) {
                return false;
            }

            // Verificăm anul de naștere în funcție de prima cifră
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            boolean isBornBefore2000 = firstDigit == '1' || firstDigit == '2' ;
            boolean isBornAfter2000 = firstDigit == '5' || firstDigit == '6';
            if ((isBornBefore2000 && (year < 1900 || year > currentYear - 18)) || (isBornAfter2000 && (year < 2000 || year > currentYear - 18))) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // CNP-ul a trecut toate verificările
        return true;
    }


}