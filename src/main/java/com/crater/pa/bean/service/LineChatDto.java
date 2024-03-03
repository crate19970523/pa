package com.crater.pa.bean.service;

import java.time.LocalDateTime;

public record LineChatDto(LocalDateTime time, String lineUserid, String LineEventType, String chatContent) {

}
