package edu.poly.exammanagement.dto.request;

import edu.poly.exammanagement.enums.GioiTinh;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequest {

    @NotBlank
    @Pattern(regexp = "\\d{12}", message = "CCCD phải có 12 số")
    private String cccd;

    @NotBlank(message = "Không đc bỏ trống số báo danh")
    private String sbd;

    @NotBlank(message = "Tên không được bỏ trống")
    private String name;

    @NotNull(message = "Không được bỏ trống ngày tháng năm sinh")
    private LocalDate birthday;

    @Pattern(regexp = "0\\d{9}", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Size(min = 6, message = "Mật khẩu phải ít nhất 6 ký tự")
    private String password;

    @NotNull
    private GioiTinh gioiTinh;

    @Email(message = "Email không hợp lệ")
    private String email;

    private String khu_vuc;

    private String doi_tuong;
}