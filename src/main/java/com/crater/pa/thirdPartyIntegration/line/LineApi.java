//package com.crater.pa.thirdPartyIntegration.line;
//
//import com.crater.pa.thirdPartyIntegration.line.request.LineSendMessageRequest;
//import com.crater.pa.thirdPartyIntegration.line.request.Message;
//import com.crater.pa.utils.HttpClientUtil;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class LineApi {
//    private String lineSendMessageUrl;
//    private String authorization;
//    public void sendMessage(String groupId, String messageContent) {
//        Map<String, String> header = new HashMap<>();
//        header.put("Authorization", "Bearer " + authorization);
//        List<Message> messages = new ArrayList<>();
//        messages.add(new Message("text", messageContent));
//        var request = new LineSendMessageRequest(groupId, messages);
//        String response = HttpClientUtil.sendPostHttpByJson(lineSendMessageUrl, header, request, String.class);
//        System.out.println(response);
//    }
//
//    @Value("${line.sendMessageUrl}")
//    public void setLineSendMessageUrl(String lineSendMessageUrl) {
//        this.lineSendMessageUrl = lineSendMessageUrl;
//    }
//
//    @Value("${line.authorization}")
//    public void setAuthorization(String authorization) {
//        this.authorization = authorization;
//    }
//}
