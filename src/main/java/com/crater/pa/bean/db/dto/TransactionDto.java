package com.crater.pa.bean.db.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDto(Long seqno, LocalDate buyDate, Long accountingRegistrantSeqno, String name,
                             String catalog, String itemName, BigDecimal amount) {
}
