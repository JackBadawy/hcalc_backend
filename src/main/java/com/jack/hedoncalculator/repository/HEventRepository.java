package com.jack.hedoncalculator.repository;

import com.jack.hedoncalculator.model.HEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HEventRepository extends JpaRepository<HEvent, Long> {
}
