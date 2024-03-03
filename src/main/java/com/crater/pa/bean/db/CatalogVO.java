package com.crater.pa.bean.db;

import java.time.LocalDateTime;

public record CatalogVO(Long seqno, String displayName, LocalDateTime lastUpdateTime, String lastUpdateUser) {
}
