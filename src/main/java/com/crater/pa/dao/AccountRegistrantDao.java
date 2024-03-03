package com.crater.pa.dao;

import com.crater.pa.bean.db.AccountingRegistrantVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface AccountRegistrantDao {
    List<AccountingRegistrantVo> queryAll();
    void insert(AccountingRegistrantVo accountingRegistrantVo);
}
