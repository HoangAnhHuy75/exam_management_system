package edu.poly.exammanagement.service;

import edu.poly.exammanagement.dto.request.UserRequest;
import edu.poly.exammanagement.dto.response.UserResponse;
import edu.poly.exammanagement.entity.User;
import edu.poly.exammanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public UserResponse insertUser(UserRequest request){
        User user = User.builder()
                .cccd(request.getCccd())
                .sbd(request.getSbd())
                .name(request.getName())
                .birthday(request.getBirthday())
                .phone(request.getPhone())
                .password(request.getPassword())
                .gioiTinh(request.getGioiTinh())
                .email(request.getEmail())
                .khu_vuc(request.getKhu_vuc())
                .doi_tuong(request.getDoi_tuong())
                .build();
        return mapToUserResponse(userRepository.save(user));
    }
    public UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .maTS(user.getMaTS())
                .cccd(user.getCccd())
                .sbd(user.getSbd())
                .name(user.getName())
                .birthday(user.getBirthday())
                .phone(user.getPhone())
                .password(user.getPassword())
                .gioiTinh(user.getGioiTinh())
                .email(user.getEmail())
                .khu_vuc(user.getKhu_vuc())
                .doi_tuong(user.getDoi_tuong())
                .update_at(user.getUpdate_at())
                .build();
    }
}
