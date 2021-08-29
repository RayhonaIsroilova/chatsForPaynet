package uz.pdp.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.chat.entity.Attachment;
import uz.pdp.chat.entity.AttachmentContent;
import uz.pdp.chat.payload.ApiResponse;
import uz.pdp.chat.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;


    public Attachment uploadFile(@RequestBody MultipartFile file){
     return attachmentService.upload(file);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> downloadFile( HttpServletResponse response, @PathVariable UUID id){
        ApiResponse download = attachmentService.download(response, id);
        return ResponseEntity.status(download.isSuccess()?200:409).body(download);
    }

}
