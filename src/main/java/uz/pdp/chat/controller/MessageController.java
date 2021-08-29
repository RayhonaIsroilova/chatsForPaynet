package uz.pdp.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.chat.payload.ApiResponse;
import uz.pdp.chat.payload.MessageDTO;
import uz.pdp.chat.service.MessageService;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/sendMessage")
    public HttpEntity<?> sendMessage(@ModelAttribute MessageDTO messageDTO){
        ApiResponse apiResponse = messageService.sendMessage(messageDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @GetMapping("/readmessage")
    public HttpEntity<?> readMessage(){
        return ResponseEntity.ok(messageService.readMessage());
    }
}
