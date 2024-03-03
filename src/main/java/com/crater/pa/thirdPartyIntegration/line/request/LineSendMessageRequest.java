package com.crater.pa.thirdPartyIntegration.line.request;

import java.util.List;

public record LineSendMessageRequest(String to, List<Message> messages) {
}
