package com.crater.pa.bean.db;

import java.time.LocalDateTime;

public record AccountingRegistrantVo(Long seqno, String name, LocalDateTime lastUpdateTime, String lastUpdateUserId) {
}
