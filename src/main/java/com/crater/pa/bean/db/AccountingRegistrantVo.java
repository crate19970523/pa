package com.crater.pa.bean.db;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public record AccountingRegistrantVo(@Param("SEQNO") Long seqno, @Param("NAME") String name,
                                     @Param("LAST_UPDATE_TIME") LocalDateTime lastUpdateTime,
                                     @Param("LAST_UPDATE_USER_ID") String lastUpdateUserId) {
}
