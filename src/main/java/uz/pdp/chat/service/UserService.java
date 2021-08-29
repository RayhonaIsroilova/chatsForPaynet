package uz.pdp.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.chat.entity.User;
import uz.pdp.chat.payload.UserDTO;
import uz.pdp.chat.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public List<UserDTO> getAll(){
        List<User> allByOnline = userRepo.findAllByOnline(true);
        List<UserDTO> listUserDto = new ArrayList<>();
        for (User user : allByOnline) {
             UserDTO userDTO = new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.isOnline());
             listUserDto.add(userDTO);
        }
        return listUserDto;
    }


}
