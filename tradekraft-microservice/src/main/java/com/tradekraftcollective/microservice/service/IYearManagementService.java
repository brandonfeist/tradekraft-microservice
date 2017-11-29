package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Year;

import java.util.List;

public interface IYearManagementService {
    List<Year> getyears(String sortOrder);
}
