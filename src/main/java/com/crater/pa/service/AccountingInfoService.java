package com.crater.pa.service;

import com.crater.pa.bean.service.AccountingRegistrantResultDto;
import com.crater.pa.bean.service.CatalogResultDto;

import java.util.List;

public interface AccountingInfoService {
    List<AccountingRegistrantResultDto> getAllAccountingRegistrant();

    List<CatalogResultDto> getAllCatalog();
}
