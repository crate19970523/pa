package com.crater.pa.service.impl;

import com.crater.pa.bean.db.TransactionVo;
import com.crater.pa.bean.service.AddAccountingDto;
import com.crater.pa.dao.TransactionDao;
import com.crater.pa.exception.DbException;
import com.crater.pa.service.UpdateAccountingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateAccountingInfoServiceImpl implements UpdateAccountingInfoService {
    private TransactionDao transactionDao;
    @Override
    @Transactional
    public void addAccounting(AddAccountingDto addAccountingDto) {
        try {
            var transactionVo = generateTransactionVo(addAccountingDto);
            insertTransaction(transactionVo);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("add accounting have unknown error", e);
        }
    }

    private TransactionVo generateTransactionVo(AddAccountingDto addAccountingDto) {
        try {
            return new TransactionVo(null, addAccountingDto.accountingRegistrantId(), addAccountingDto.catalogId(),
                    addAccountingDto.itemName(), addAccountingDto.amount(), addAccountingDto.provFileName(), addAccountingDto.remark(),
                    LocalDateTime.now(), "system", null, null, addAccountingDto.buyDate());
        } catch (Exception e) {
            throw new RuntimeException("generate transaction vo fail", e);
        }
    }

    private void insertTransaction(TransactionVo transactionVo) {
        try {
            transactionDao.insertNewTransaction(transactionVo);
        } catch (Exception e) {
            throw new DbException("insert transaction fail", e);
        }
    }

    @Autowired
    public UpdateAccountingInfoServiceImpl setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
        return this;
    }
}
