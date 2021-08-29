package uz.pdp.chat.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.chat.entity.Attachment;
import uz.pdp.chat.entity.AttachmentContent;
import uz.pdp.chat.payload.ApiResponse;
import uz.pdp.chat.repository.AttachmentContentRepo;
import uz.pdp.chat.repository.AttachmentRepo;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepo attachmentRepository;
    @Autowired
    AttachmentContentRepo attachmentContentRepository;

    @SneakyThrows
    public Attachment upload(MultipartFile file){
        if(file!=null){
            Attachment attachment=new Attachment();
            attachment.setName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());
            Attachment savedAttachment = attachmentRepository.save(attachment);
            AttachmentContent attachmentContent=new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            attachmentContentRepository.save(attachmentContent);
            return savedAttachment;
        }
        return null;
    }

    @SneakyThrows
    public ApiResponse download(HttpServletResponse response, UUID id) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if(optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
            if(optionalAttachmentContent.isPresent()){
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getName() + "\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
            }
            return new ApiResponse("Successfully download file",true);
        }
        return new ApiResponse("File does not download!",false);
    }


}
