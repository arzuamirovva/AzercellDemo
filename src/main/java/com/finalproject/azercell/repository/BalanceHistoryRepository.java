package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.BalanceHistoryEntity;
import com.finalproject.azercell.entity.NumberEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BalanceHistoryRepository extends CrudRepository<BalanceHistoryEntity, Integer> {

    List findByUserId(Integer userId);
    List findAll();
//    @Query(value = "SELECT * FROM balance_history WHERE number = :number ORDER BY transaction_date DESC LIMIT 1", nativeQuery = true)
//    Optional<BalanceHistoryEntity> findLastTransactionByNumber(String number);
    @Query("SELECT MAX(b.transactionDate) FROM BalanceHistoryEntity b WHERE b.number = :numberEntity")
    Optional<LocalDateTime> findLastTransactionDateByNumber(NumberEntity numberEntity);
}
