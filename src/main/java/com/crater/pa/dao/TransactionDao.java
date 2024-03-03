package com.crater.pa.dao;

import com.crater.pa.bean.db.TransactionVo;
import com.crater.pa.bean.db.dto.TransactionDto;

import java.time.LocalDate;
import java.util.List;

public interface TransactionDao {
    void insertNewTransaction(TransactionVo transactionVo);
    List<TransactionDto> queryTransactionByDate(LocalDate startDate, LocalDate endDate);
}
