package com.atm.system.repository;

import com.atm.system.model.Denomination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DenominationRepository extends JpaRepository<Denomination, Long> {
}
