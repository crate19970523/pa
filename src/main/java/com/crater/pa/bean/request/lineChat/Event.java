package com.crater.pa.bean.request.lineChat;

public record Event(String type, String webhookEventId, Long timestamp, Message message, Source source, String mode) {
}
