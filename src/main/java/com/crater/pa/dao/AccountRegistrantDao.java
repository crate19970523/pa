package com.crater.pa.dao;

import com.crater.pa.bean.db.AccountingRegistrantVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface AccountRegistrantDao {
    List<AccountingRegistrantVo> queryAll();
    AccountingRegistrantVo queryBySeqno();
    void updateBySeqno(Long seqno, AccountingRegistrantVo accountingRegistrantVo);
    void deleteBySeqno(Long seqno);
}
