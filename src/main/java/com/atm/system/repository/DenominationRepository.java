package com.atm.system.repository;

import com.atm.system.model.Denomination;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DenominationRepository
        extends JpaRepository<Denomination, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM Denomination d ORDER BY d.value DESC")
    List<Denomination> findDenominationsForUpdate();

}
