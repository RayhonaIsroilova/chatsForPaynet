package uz.pdp.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.chat.controller.AttachmentController;
import uz.pdp.chat.entity.AttachmentContent;
import uz.pdp.chat.entity.Message;
import uz.pdp.chat.entity.User;
import uz.pdp.chat.payload.ApiResponse;
import uz.pdp.chat.payload.MessageDTO;
import uz.pdp.chat.payload.SendMessage;
import uz.pdp.chat.payload.UserDTO;
import uz.pdp.chat.repository.MessageRepo;
import uz.pdp.chat.repository.UserRepo;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    AttachmentController attachmentController;

    @Autowired
    UserRepo userRepo;
    public ApiResponse sendMessage(MessageDTO messageDTO) {
        Optional<User> byEmail = userRepo.findByEmail(messageDTO.getReceiver());
        if (!byEmail.isPresent())
        return new ApiResponse("This email not found ! ", false);
        User receiver = byEmail.get();
        Message message = new Message();
        message.setText(messageDTO.getText());
        message.setTitle(messageDTO.getTitle());
        message.setAttachment(attachmentController.uploadFile(messageDTO.getAttachment()));
        User sender = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        message.setSender(sender);
        message.setReceiver(receiver);
        Message save = messageRepo.save(message);
        SendMessage sendMessage = new SendMessage(save.getTitle(),save.getText(),save.getReceiver().getEmail(),save.getSentTime());
        return new ApiResponse("Succesfully send your message!",true, sendMessage);
    }

    public List<SendMessage> readMessage(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Message> allByReceiver = messageRepo.findAllByReceiver(user);
        List<SendMessage> sendMessages = new ArrayList<>();
        for (Message message : allByReceiver) {
            SendMessage sendMessage = new SendMessage(message.getTitle(),message.getText(),message.getSender().getEmail(),message.getSentTime());
            sendMessages.add(sendMessage);
        }
        return sendMessages;
    }

}
