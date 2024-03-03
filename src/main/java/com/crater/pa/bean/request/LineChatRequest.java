package com.crater.pa.bean.request;

import com.crater.pa.bean.request.lineChat.Event;

import java.util.List;

public record LineChatRequest(String destination, List<Event> events) {
}
