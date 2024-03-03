package com.crater.pa.service;

import com.crater.pa.bean.request.lineChat.Event;
import com.crater.pa.bean.service.LineChatDto;

import java.util.List;

public interface LineChatService {
    void catchLineWebhook(List<LineChatDto> lineChatDtos);
}
