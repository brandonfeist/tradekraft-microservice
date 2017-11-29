package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.persistence.entity.Year;
import com.tradekraftcollective.microservice.repository.IYearRepository;
import com.tradekraftcollective.microservice.service.IYearManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class YearManagementService implements IYearManagementService {

    private static final String DESCENDING = "desc";

    @Autowired
    IYearRepository yearRepository;

    @Override
    public List<Year> getyears( String sortOrder) {
        log.info("Fetching all years.");

        Sort order = new Sort(Sort.Direction.ASC, "year");
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = new Sort(Sort.Direction.DESC, "year");
        }

        List<Year> years = yearRepository.findAll(order);

        return years;
    }
}
