package com.progressoft.fxclusteredwarehouse.repository;

import com.progressoft.fxclusteredwarehouse.models.entitis.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DealRepository extends JpaRepository<Deal, String > {
}
