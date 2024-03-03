package com.crater.pa.controller;

import com.crater.pa.bean.request.AddAccountingRequest;
import com.crater.pa.bean.response.AddAccountingResponse;
import com.crater.pa.bean.response.Error;
import com.crater.pa.bean.service.AddAccountingDto;
import com.crater.pa.service.UpdateAccountingInfoService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class UpdateAccountingInfoController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private UpdateAccountingInfoService updateAccountingInfoService;

    @PostMapping("updateAccounting/add")
    public AddAccountingResponse addAccounting(@RequestBody AddAccountingRequest addAccountingRequest) {
        try {
            var addAccountingDto = generateAddAccountingDto(addAccountingRequest);
            updateAccountingInfoService.addAccounting(addAccountingDto);
            return new AddAccountingResponse(new Error(true, null, null));
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return new AddAccountingResponse(new Error(false, "add accounting fail", e.getMessage()));
        }
    }

    private AddAccountingDto generateAddAccountingDto(AddAccountingRequest addAccountingRequest) {
        try {
            return new AddAccountingDto(addAccountingRequest.accountingRegistrantId(), addAccountingRequest.catalogId(),
                    addAccountingRequest.item(), addAccountingRequest.amount(), addAccountingRequest.provFileName(),
                    null, LocalDate.parse(addAccountingRequest.buyDate(), DateTimeFormatter.ISO_DATE));
        } catch (Exception e) {
            throw new RuntimeException("generate AddAccountingDto fail", e);
        }
    }

    @Autowired
    public UpdateAccountingInfoController setUpdateAccountingInfoService(UpdateAccountingInfoService updateAccountingInfoService) {
        this.updateAccountingInfoService = updateAccountingInfoService;
        return this;
    }
}
