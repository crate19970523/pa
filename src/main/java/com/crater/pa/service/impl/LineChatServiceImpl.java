//package com.crater.pa.service.impl;
//
//import com.crater.pa.bean.db.AccountingRegistrantVo;
//import com.crater.pa.bean.db.TransactionVo;
//import com.crater.pa.bean.db.dto.TransactionDto;
//import com.crater.pa.bean.service.LineChatDto;
//import com.crater.pa.dao.AccountRegistrantDao;
//import com.crater.pa.dao.TransactionDao;
//import com.crater.pa.exception.PaException;
//import com.crater.pa.service.LineChatService;
//import com.crater.pa.thirdPartyIntegration.line.LineApi;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.TemporalAdjusters;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//@Service
//public class LineChatServiceImpl implements LineChatService {
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    private TransactionDao transactionDao;
//    private AccountRegistrantDao accountRegistrantDao;
//    private LineApi lineApi;
//    private String targetGroupId;
//
//    @Override
//    @Transactional
//    public void catchLineWebhook(List<LineChatDto> lineChatDtos) {
//        try {
//            for (LineChatDto lineChatDto : lineChatDtos) {
//                if (isUpdateAccountingRegistrant(lineChatDto.chatContent())) {
//                    doUpdateAccountingRegistrant(lineChatDto);
//                } else if (isQueryTransaction(lineChatDto.chatContent())) {
//                    doQueryTransaction();
//                } else if (isAddTransaction(lineChatDto.chatContent())) {
//                    doAddTransaction(lineChatDto);
//                } else {
//                    handleUnableToClassifyEvent(lineChatDto);
//                }
//            }
//        } catch (Exception e) {
//            lineApi.sendMessage(targetGroupId, "執行失敗: \n" + ExceptionUtils.getStackTrace(e));
//            throw new PaException("catchLineWebhook have error", e);
//        }
//    }
//
//    private boolean isAddTransaction(String chatContent) {
//        try {
//            for (String s : chatContent.split("\n")) {
//                if (s.startsWith("備註")) break;
//                if (!s.replaceAll("元$", "").matches(".*[0-9]$")) return false;
//            }
//            return true;
//        } catch (Exception e) {
//            throw new PaException("check is add transaction have unknown error", e);
//        }
//    }
//
//    private void doAddTransaction(LineChatDto lineChatDto) {
//        try {
//            var accountingUserInfo = Optional.ofNullable(accountRegistrantDao.queryByLineUserid(lineChatDto.lineUserid()))
//                    .orElseGet(() -> {
//                        var newAccountingUserInfo = generateAccountingRegistrantVo(lineChatDto);
//                        accountRegistrantDao.insert(newAccountingUserInfo);
//                        return accountRegistrantDao.queryByLineUserid(lineChatDto.lineUserid());
//                    });
//            var itemAndAmounts = parseStringToItemAmount(lineChatDto.chatContent());
//            var remark = findRemark(lineChatDto.chatContent());
//            for (ItemAndAmount itemAndAmount : itemAndAmounts) {
//                var newTransactionVo = generateTransactionVo(itemAndAmount, lineChatDto.time().toLocalDate(),
//                        accountingUserInfo.seqno(),
//                        remark);
//                transactionDao.insertNewTransaction(newTransactionVo);
//            }
//        } catch (PaException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new PaException("do add transaction have unknown error", e);
//        }
//    }
//
//    public AccountingRegistrantVo generateAccountingRegistrantVo(LineChatDto lineChatDto) {
//        try {
//            return new AccountingRegistrantVo(null, null, lineChatDto.lineUserid(), LocalDateTime.now(),
//                    "systemFromLine");
//        } catch (Exception e) {
//            throw new PaException("generateAccountingRegistrantVo fail", e);
//        }
//    }
//
//    private ArrayList<ItemAndAmount> parseStringToItemAmount(String transactionString) {
//        var itemAndAmounts = new ArrayList<ItemAndAmount>();
//        var parseFailMessage = new ArrayList<String>();
//        for (String s : transactionString.split("\n")) {
//            try {
//                s = s.replaceAll("元$", "");
//                if (s.startsWith("備註")) break;
//                var itemAmount = s.split("(?<=\\D)(?=\\d)");
//                itemAndAmounts.add(new ItemAndAmount(s.replace(itemAmount[itemAmount.length - 1], ""),
//                        new BigDecimal(itemAmount[itemAmount.length - 1])));
//            } catch (Exception e) {
//                log.error(ExceptionUtils.getStackTrace(e)); //Since it will not be thrown out, so log is print.
//                parseFailMessage.add("parse transaction string fail, content: " + s);
//            }
//        }
//        if (!parseFailMessage.isEmpty()) {
//            throw new PaException(String.join("\n", parseFailMessage));
//        } else {
//            return itemAndAmounts;
//        }
//    }
//
//    private String findRemark(String chatContent) {
//        try {
//            var remark = new StringBuilder();
//            var isRemarkStart = false;
//            for (String s : chatContent.split("\n")) {
//                if (s.startsWith("備註")) isRemarkStart = true;
//                if (isRemarkStart) remark.append(s);
//            }
//            return !remark.isEmpty() ? remark.toString() : null;
//        } catch (Exception e) {
//            throw new PaException("find remark fail", e);
//        }
//    }
//
//    private TransactionVo generateTransactionVo(ItemAndAmount itemAndAmount, LocalDate buyTime,
//                                                Long accountingRegistrantSeqno, String remark) {
//        try {
//            return new TransactionVo(null, accountingRegistrantSeqno, null, itemAndAmount.item,
//                    itemAndAmount.amount, null, remark, LocalDateTime.now(), "systemFromLine",
//                    LocalDateTime.now(), "systemFromLine", buyTime);
//        } catch (Exception e) {
//            throw new PaException("generate accounting registrant vo fail", e);
//        }
//    }
//
//    private boolean isQueryTransaction(String chatContent) {
//        try {
//            return chatContent.startsWith("結算");
//        } catch (Exception e) {
//            throw new PaException("check is query transaction fail", e);
//        }
//    }
//
//    private void doQueryTransaction() {
//        try {
//            var returnMessage = new StringBuilder();
//            var accountRegistrants = accountRegistrantDao.queryAll();
//            var transactionDatas =
//                    transactionDao.queryTransactionByDate(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
//                            LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
//            returnMessage.append("本月消費: ").append(calculateMonthlyTotal(transactionDatas)).append("\n");
//            returnMessage.append("個人消費總計: \n");
//            accountRegistrants.forEach(a ->
//                    returnMessage.append(Optional.ofNullable(a.name()).orElse(a.seqno().toString()))
//                            .append(": ").append(calculatePersonalTotal(a.seqno(), transactionDatas)).append("\n"));
//            returnMessage.append("下面是總明細: \n").append(generateTransactionDetails(transactionDatas));
//            sendTransactionAccountingMessageToLine(returnMessage.toString());
//        } catch (Exception e) {
//            throw new PaException("queryTransaction fail", e);
//        }
//    }
//
//    private String calculateMonthlyTotal(List<TransactionDto> transactionDtos) {
//        try {
//            var result = new BigDecimal("0");
//            for (TransactionDto transactionDto : transactionDtos) {
//                result = result.add(transactionDto.amount());
//            }
//            return new DecimalFormat("#,###.000").format(result);
//        } catch (Exception e) {
//            throw new PaException("calculatePersonalTotal fail", e);
//        }
//    }
//
//    private String calculatePersonalTotal(Long accountRegistrantSeqno, List<TransactionDto> transactionDtos) {
//        try {
//            var result = new BigDecimal("0");
//            for (TransactionDto transactionDto : transactionDtos.stream().filter(t -> Objects.equals(accountRegistrantSeqno, t.accountingRegistrantSeqno())).toList()) {
//                result = result.add(transactionDto.amount());
//            }
//            return new DecimalFormat("#,###.000").format(result);
//        } catch (Exception e) {
//            throw new PaException("calculate personal total", e);
//        }
//    }
//
//    private String generateTransactionDetails(List<TransactionDto> transactionDtos) {
//        try {
//            var returnMessage = new StringBuilder();
//            transactionDtos.forEach(t ->
//                    returnMessage.append(t.buyDate().format(DateTimeFormatter.ISO_LOCAL_DATE)).append(" ")
//                            .append(Optional.ofNullable(t.name()).orElse(t.accountingRegistrantSeqno().toString()))
//                            .append(" ").append(t.itemName()).append(" ")
//                            .append(new DecimalFormat("#,###.000").format(t.amount())).append("\n"));
//            return returnMessage.toString();
//        } catch (Exception e) {
//            throw new PaException("generate transaction detail string fail", e);
//        }
//    }
//
//    private void sendTransactionAccountingMessageToLine(String sendMessage) {
//        try {
//            lineApi.sendMessage(targetGroupId, sendMessage);
//        } catch (Exception e) {
//            throw new PaException("send line messages fail", e);
//        }
//    }
//
//    private boolean isUpdateAccountingRegistrant(String chatContent) {
//        return false;
//    }
//
//    private void doUpdateAccountingRegistrant(LineChatDto lineChatDto) {
//
//    }
//
//    private void handleUnableToClassifyEvent(LineChatDto lineChatDto) {
//
//    }
//
//    @Autowired
//    public void setTransactionDao(TransactionDao transactionDao) {
//        this.transactionDao = transactionDao;
//    }
//
//    @Autowired
//    public void setAccountRegistrantDao(AccountRegistrantDao accountRegistrantDao) {
//        this.accountRegistrantDao = accountRegistrantDao;
//    }
//
//    @Autowired
//    public void setLineApi(LineApi lineApi) {
//        this.lineApi = lineApi;
//    }
//
//    @Value("${line.groupId}")
//    public void setTargetGroupId(String targetGroupId) {
//        this.targetGroupId = targetGroupId;
//    }
//
//    private record ItemAndAmount(String item, BigDecimal amount) {
//
//    }
//}
