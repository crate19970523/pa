package com.crater.pa.controller;

import com.crater.pa.service.AccountingInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountingInfoControllerTest {
    private AccountingInfoController testTarget;
    private AccountingInfoService accountingInfoService;

    @BeforeEach
    public void setUp() {
        accountingInfoService = mock(AccountingInfoService.class);
        testTarget = new AccountingInfoController();
        testTarget.setAccountingInfoService(accountingInfoService);
    }

    @Test
    public void getAccountingRegistrant_serverThrowRunTimeException_fail() {
        when(accountingInfoService.getAllAccountingRegistrant()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> testTarget.getAccountingRegistrant());
    }
}
