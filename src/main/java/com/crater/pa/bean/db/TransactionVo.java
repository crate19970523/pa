package com.crater.pa.bean.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionVo(Long seqno, Long accountingRegistrantSeqno, Long catalogSeqno, String itemName,
                            BigDecimal amount, String provFileName, String remark, LocalDateTime createTime,
                            String createUserid, LocalDateTime updateTime, String updateUserid, LocalDate buyDate) {
}
