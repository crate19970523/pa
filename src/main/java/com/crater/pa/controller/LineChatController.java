//package com.crater.pa.controller;
//
//import com.crater.pa.bean.request.LineChatRequest;
//import com.crater.pa.bean.request.lineChat.Event;
//import com.crater.pa.bean.service.LineChatDto;
//import com.crater.pa.service.LineChatService;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.TimeZone;
//
//@RestController
//public class LineChatController {
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    private LineChatService lineChatService;
//
//    @PostMapping("/line/test")
//    public void test(@RequestBody String request) {
//        try { //{"destination":"Ufdec6a872dc8b08ad7adf9652fb6facc","events":[]}
//            System.out.println(request);
//            System.out.println("finish");
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    @PostMapping("/linea/webhook")
//    public void catchLineChatWebHook(@RequestBody LineChatRequest lineChatRequest) {
//        try {
//            var dtos = lineChatRequest.events().stream().map(this::generateLineChatDto).toList();
//            lineChatService.catchLineWebhook(dtos);
//            log.debug("success");
//        } catch (Exception e) {
//            log.error(ExceptionUtils.getStackTrace(e));
//        }
//    }
//
//    public LineChatDto generateLineChatDto(Event event) {
//        try {
//            var lineUserId = event.source().userId();
//            var chatContent = "message".equals(event.type()) ? event.message().text() : null;
//            var time = LocalDateTime.ofInstant(Instant.ofEpochMilli(event.timestamp()),
//                    TimeZone.getTimeZone("Asia/Taipei").toZoneId());
//            return new LineChatDto(time, lineUserId, event.type(), chatContent);
//        } catch (Exception e) {
//            throw new RuntimeException("generate line chat dto for service fail", e);
//        }
//    }
//
//    @Autowired
//    public void setLineChatService(LineChatService lineChatService) {
//        this.lineChatService = lineChatService;
//    }
//}
