package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.persistence.entity.Year;
import com.tradekraftcollective.microservice.repository.IYearRepository;
import com.tradekraftcollective.microservice.service.IYearManagementService;
import com.tradekraftcollective.microservice.validator.YearValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class YearManagementService implements IYearManagementService {

    private static final String DESCENDING = "desc";

    @Autowired
    IYearRepository yearRepository;

    @Autowired
    YearValidator yearValidator;

    @Override
    public List<Year> getyears(String sortOrder) {
        log.info("Fetching all years.");

        Sort order = new Sort(Sort.Direction.ASC, "year");
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = new Sort(Sort.Direction.DESC, "year");
        }

        List<Year> years = yearRepository.findAll(order);

        return years;
    }

    @Override
    public Year createYear(Year year) {
        log.info("Creating year [{}]", year.getYear());

        yearValidator.validateYear(year);

        Year returnYear = yearRepository.save(year);

        log.info("***** SUCCESSFULLY CREATED YEAR = {} *****", returnYear.getYear());

        return returnYear;
    }

    @Override
    public List<Year> getExistingYears(List<Year> years) {
        List<Year> formattedYearsList = new ArrayList<>();

        for(int yearIndex = 0; yearIndex < years.size(); yearIndex++) {
            Year foundYear = yearRepository.findByYear(years.get(yearIndex).getYear().toString());

            if(foundYear != null) {
                formattedYearsList.add(foundYear);
            } else {
                formattedYearsList.add(years.get(yearIndex));
            }
        }

        return formattedYearsList;
    }
}
