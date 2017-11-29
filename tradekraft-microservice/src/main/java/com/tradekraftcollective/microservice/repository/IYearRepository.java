package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IYearRepository extends JpaRepository<Year, Long> {
    Year findByYear(@Param("year") String year);
}
