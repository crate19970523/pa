package com.crater.pa.dao;

import com.crater.pa.bean.db.TransactionVo;

public interface TransactionDao {
    void insertNewTransaction(TransactionVo transactionVo);
}
