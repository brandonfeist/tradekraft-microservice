package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.persistence.entity.Year;
import com.tradekraftcollective.microservice.service.IYearManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/years")
public class YearManagementController {

    private static final String SORT_ORDER_DESC = "desc";

    @Autowired
    IYearManagementService yearManagementService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getYears(
            @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER_DESC, required = false) String sortOrder,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getYears [{}]", xRequestId);

        List<Year> years = yearManagementService.getyears(sortOrder);

        return new ResponseEntity<>(years, HttpStatus.OK);
    }
}
