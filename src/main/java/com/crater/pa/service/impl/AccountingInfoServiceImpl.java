package com.crater.pa.service.impl;

import com.crater.pa.bean.db.AccountingRegistrantVo;
import com.crater.pa.bean.db.CatalogVO;
import com.crater.pa.bean.service.AccountingRegistrantResultDto;
import com.crater.pa.bean.service.CatalogResultDto;
import com.crater.pa.dao.AccountRegistrantDao;
import com.crater.pa.dao.CatalogDao;
import com.crater.pa.exception.DbException;
import com.crater.pa.service.AccountingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountingInfoServiceImpl implements AccountingInfoService {
    private AccountRegistrantDao accountRegistrantDao;
    private CatalogDao catalogDao;

    @Override
    public List<AccountingRegistrantResultDto> getAllAccountingRegistrant() {
        try {
            var accountingRegistrants = queryAccountingRegistrant();
            return generateAccountingRegistrantResultDto(accountingRegistrants);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("getAllAccountingRegistrant have unknown error", e);
        }
    }

    private List<AccountingRegistrantVo> queryAccountingRegistrant() {
        try {
            return accountRegistrantDao.queryAll();
        } catch (Exception e) {
            throw new DbException("query AccountingRegistrant fail", e);
        }
    }

    private List<AccountingRegistrantResultDto> generateAccountingRegistrantResultDto(
            List<AccountingRegistrantVo> accountingRegistrantVos) {
        try {
            return accountingRegistrantVos.stream()
                    .map(a -> new AccountingRegistrantResultDto(a.seqno(), a.name())).toList();
        } catch (Exception e) {
            throw new RuntimeException("generate AccountingRegistrantResultDto fail", e);
        }
    }

    @Override
    public List<CatalogResultDto> getAllCatalog() {
        try {
            var catalogs = queryAllCatalog();
            return generateCatalogResultDto(catalogs);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("get all catalog have unknown error", e);
        }
    }

    private List<CatalogVO> queryAllCatalog() {
        try {
            return catalogDao.queryAll();
        } catch (Exception e) {
            throw new DbException("query all catalog fail", e);
        }
    }

    private List<CatalogResultDto> generateCatalogResultDto(List<CatalogVO> catalogVOS) {
        try {
            return catalogVOS.stream().map(c -> new CatalogResultDto(c.seqno(), c.displayName())).toList();
        } catch (Exception e) {
            throw new RuntimeException("generate CatalogResultDto fail", e);
        }
    }

    @Autowired
    public AccountingInfoServiceImpl setAccountRegistrantDao(AccountRegistrantDao accountRegistrantDao) {
        this.accountRegistrantDao = accountRegistrantDao;
        return this;
    }

    @Autowired
    public AccountingInfoServiceImpl setCatalogDao(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
        return this;
    }

}
