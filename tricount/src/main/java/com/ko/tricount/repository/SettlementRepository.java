package com.ko.tricount.repository;

import com.ko.tricount.entity.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
