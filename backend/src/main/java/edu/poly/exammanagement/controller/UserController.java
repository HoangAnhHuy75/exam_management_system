package edu.poly.exammanagement.controller;

import edu.poly.exammanagement.dto.request.UserRequest;
import edu.poly.exammanagement.dto.response.ApiResponse;
import edu.poly.exammanagement.dto.response.UserResponse;
import edu.poly.exammanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request){
        UserResponse userRespone = userService.insertUser(request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(1)
                .message("Thêm thành công")
                .result(userRespone)
                .build());
    }

}
