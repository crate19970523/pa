package com.crater.pa.bean.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record AddAccountingRequest(@Schema(description = "記帳人員ID") Long accountingRegistrantId,
                                   @Schema(description = "消費類別ID") Long catalogId,
                                   @Schema(description = "消費產品") String item,
                                   @Schema(description = "消費金額") BigDecimal amount,
                                   @Schema(description = "消費證明圖片檔名(即將移除)") String provFileName,
                                   @Schema(description = "購買日期") String buyDate) {
}
