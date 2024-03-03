package com.crater.pa.bean.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddAccountingRequest(Long accountingRegistrantId, Long catalogId, String item, BigDecimal amount,
                                   String provFileName, String buyDate) {
}
