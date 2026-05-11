package BUS;

import DAO.UserDAO;
import DTO.UserDTO;

import java.util.ArrayList;

public class UserBUS {

    private UserDAO userDAO = new UserDAO();
    private ArrayList<UserDTO> userList = new ArrayList<>();

    // ===== Lấy tất cả =====
    public ArrayList<UserDTO> getAll() {
        userList = userDAO.getAll();
        return userList;
    }

    public String insert(UserDTO u) {

        // ===== VALIDATE =====
        String check = validateUser(u);
        if (check != null) {
            return check;
        }

        // ===== CHECK TRÙNG USERNAME =====
        if (checkDupUsername(u.getUsername())) {
            return "Username đã tồn tại!";
        }

        // ===== INSERT =====
        int result = userDAO.insert(u);

        if (result == 1) {
            return "OK";
        } else {
            return "Thêm người dùng thất bại!";
        }
    }

    // ===== Update =====
    public String update(UserDTO u) {
        String check = validateUser(u);
        if (check != null) {
            return check;
        }

        // ===== CHECK TRÙNG USERNAME =====
        UserDTO existing = userDAO.findByUsername(u.getUsername());
        if (existing != null && existing.getId() != u.getId()) {
            return "Username đã tồn tại!";
        }
        // ===== INSERT =====
        int result = userDAO.update(u);
        if (result == 1) {
            return "OK";
        } else {
            return "Sửa người dùng thất bại!";
        }
    }

    // ===== Ban user =====
    public int banUser(int userId) {
        return userDAO.banUser(userId);
    }

    // ===== Unban user =====
    public int unbanUser(int userId) {
        return userDAO.unbanUser(userId);
    }

    // ===== Lấy ID theo index (dùng cho table UI) =====
    public int getIDbyIndex(int index) {
        ArrayList<UserDTO> list = userDAO.getAll();
        if (index >= 0 && index < list.size()) {
            return list.get(index).getId();
        }
        return -1;
    }

    // ===== Tìm theo username =====
    public UserDTO findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        return userDAO.findByUsername(username.trim());
    }

    // ===== Lấy password theo username =====
    public String getPasswordByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        UserDTO u = userDAO.findByUsername(username.trim());

        if (u != null) {
            return u.getPassword();
        }

        return null;
    }

    // ===== Search =====
    public ArrayList<UserDTO> search(String keyword) {
        ArrayList<UserDTO> result = new ArrayList<>();

        if (keyword == null) {
            keyword = "";
        }
        String key = keyword.toLowerCase();

        for (UserDTO u : userDAO.getAll()) {
            boolean matchUsername = u.getUsername() != null && u.getUsername().toLowerCase().contains(key);

            if (matchUsername) {
                result.add(u);
            }
        }

        return result;
    }

    // ===== Check trùng username =====
    public boolean checkDupUsername(String username) {
        if (username == null) {
            return false;
        }
        return userDAO.findByUsername(username.trim()) != null;
    }

    // ===== Login =====
    public UserDTO login(
        String username,
        String password
) throws Exception {

    if (username == null
            || password == null
            || username.trim().isEmpty()
            || password.trim().isEmpty()) {

        throw new Exception("Vui lòng nhập đầy đủ dữ liệu");
    }

    UserDTO user =
            userDAO.findByUsername(
                    username.trim()
            );

    if (user == null) {
        throw new Exception("Tài khoản không tồn tại");
    }

    if (!user.getPassword().equals(password)) {
        throw new Exception("Sai mật khẩu");
    }

    if (Boolean.FALSE.equals(user.getEnabled())) {
        throw new Exception("Tài khoản đã bị khóa");
    }

    return user;
}

    // ===== Filter theo role + trạng thái =====
    public ArrayList<UserDTO> filter(String role, String status) {
        ArrayList<UserDTO> result = new ArrayList<>();

        for (UserDTO u : userDAO.getAll()) {
            boolean matchRole = role.equals("Tất cả");

            boolean matchStatus = status.equals("Tất cả")
                    || (status.equals("Active") && Boolean.TRUE.equals(u.getEnabled()));

            if (matchRole && matchStatus) {
                result.add(u);
            }
        }

        return result;
    }

    public UserDTO getById(int id) {
        return userDAO.getById(id);
    }

    public String validateUser(UserDTO u) {
        if (u.getUsername() == null || u.getUsername().trim().length() < 6) {
            return "Username phải >= 4 ký tự";
        }
        if (u.getUsername().contains(" ")) {
            return "Username không được chứa khoảng trắng";
        }
        if (u.getPassword() == null || u.getPassword().trim().length() < 6) {
            return "Password phải >= 6 ký tự";
        }


        return null;
    }

    public ArrayList<UserDTO> searchByUsername(String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        return userDAO.searchByUsername(keyword.trim());
    }

    public String delete(int id) {
        int result = userDAO.delete(id);

        if (result == 1) {
            return "Xóa user thành công!";
        } else {
            return "Xóa thất bại!";
        }
    }

}
