package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
    Country findByIso(String iso);
}
