package com.crater.pa.controller;

import com.crater.pa.bean.response.*;
import com.crater.pa.bean.response.Error;
import com.crater.pa.bean.service.AccountingRegistrantResultDto;
import com.crater.pa.bean.service.CatalogResultDto;
import com.crater.pa.service.AccountingInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController

@Tag(name = "取得記帳相關資訊")
public class AccountingInfoController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AccountingInfoService accountingInfoService;

    @Operation(description = "取得記帳人員的下拉選單")
    @GetMapping("/accountingInfo/accountingRegistrant")
    public GetAccountingRegistrantResponse getAccountingRegistrant() {
        try {
            var accountingRegistrants = accountingInfoService.getAllAccountingRegistrant();
            return generateGetAccountingRegistrantResponse(accountingRegistrants);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return new GetAccountingRegistrantResponse(
                    new Error(false, "get accounting regiestant error", e.getMessage()), null);
        }
    }

    private GetAccountingRegistrantResponse generateGetAccountingRegistrantResponse(
            List<AccountingRegistrantResultDto> accountingRegistrantResultDtos) {
        try {
            return new GetAccountingRegistrantResponse(new Error(true, null, null),
                    accountingRegistrantResultDtos.stream().map(a ->
                            new AccountRegistrantInfo(a.name(), a.accountingRegistrantSeqno())).toList());
        } catch (Exception e) {
            throw new RuntimeException("generate response fail", e);
        }
    }

    @Operation(description = "取得消費類別清單，已棄用")
    @GetMapping("/accountingInfo/catalog")
    public GetCatalogResponse getCatalog() {
        try {
            var catalogs = accountingInfoService.getAllCatalog();
            return generateCatalogResponse(catalogs);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return new GetCatalogResponse(new Error(false, "getCatalog fail", e.getMessage()), null);
        }
    }

    private GetCatalogResponse generateCatalogResponse(List<CatalogResultDto> catalogResultDtos) {
        try {
            return new GetCatalogResponse(new Error(true, null, null),
                    catalogResultDtos.stream().map(c -> new CatalogInfo(c.displayName(), c.seqno())).toList());
        } catch (Exception e) {
            throw new RuntimeException("generate catalog response fail", e);
        }
    }

    @Autowired
    public void setAccountingInfoService(AccountingInfoService accountingInfoService) {
        this.accountingInfoService = accountingInfoService;
    }
}
