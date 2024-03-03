package com.crater.pa.bean.request.lineChat;

public record Message(String type, String id, String quoteToken, String text) {
}
