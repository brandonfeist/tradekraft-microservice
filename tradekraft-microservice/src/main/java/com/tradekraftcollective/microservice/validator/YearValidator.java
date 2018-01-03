package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Year;
import com.tradekraftcollective.microservice.repository.IYearRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

@Slf4j
@Component
public class YearValidator {

    @Autowired
    IYearRepository yearRepository;

    private final int STARTING_YEAR = 2015;

    public void validateYear(Year year) {
        validateYearNumber(year);
        validateIfYearIsDuplicate(year);
    }

    private void validateIfYearIsDuplicate(Year year) {
        Year yearCheck = yearRepository.findByYear(year.getYear().toString());

        if(yearCheck != null) {
            log.error("Cannot create year [{}] as it already exists.", year.getYear());
            throw new ServiceException(ErrorCode.INVALID_YEAR, "Cannot create year [" + year.getYear() + "] as it already exists.");
        }
    }

    private void validateYearNumber(Year year) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        int currentUtcYear = calendar.get(Calendar.YEAR);

        if(year.getYear() < STARTING_YEAR || year.getYear() > currentUtcYear) {
            log.error("Cannot create year that is less than [{}] or more than the current year [{}]", STARTING_YEAR, currentUtcYear);
            throw new ServiceException(ErrorCode.INVALID_YEAR, "Cannot create year that is less than [ " + STARTING_YEAR + " ] or more than the current year [" + currentUtcYear + "]");
        }
    }
}
