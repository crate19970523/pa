package com.crater.pa.bean.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddAccountingDto(Long accountingRegistrantId, Long catalogId, String itemName, BigDecimal amount,
                               String provFileName, String remark, LocalDate buyDate) {
}
