package ro.sci.requestservice.service;

import org.springframework.stereotype.Service;

@Service
public class NumberGeneratorSCI {

    private static Long lastNumber = 0L;

    public static synchronized Long getNextNumber() {
        lastNumber++;
        return lastNumber;
    }


}
