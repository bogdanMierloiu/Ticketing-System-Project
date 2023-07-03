package ro.sci.requestservice.service;

import org.springframework.stereotype.Service;

@Service
public class NumberGeneratorSCI {

    private Long lastNumber = 0L;

    public Long getNextNumber() {
        synchronized (this) {
            lastNumber++;
            return lastNumber;
        }
    }
}
