/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.PermissionDAO;
import DTO.PermissionDTO;
import DTO.RoleDTO;
import java.util.ArrayList;
import java.util.List;

public class PermissionBUS {

    private PermissionDAO permissionDAO =
            new PermissionDAO();

    // =========================
    // Lấy tất cả role
    // =========================
    public ArrayList<RoleDTO> getAllRole() {

        return permissionDAO.getAllRole();
    }

    // =========================
    // Lấy tất cả permission
    // =========================
    public ArrayList<PermissionDTO> getAllPermission() {

        return permissionDAO.getAllPermission();
    }

    // =========================
    // Lấy role theo id
    // =========================
    public RoleDTO getRoleById(int id) {

        return permissionDAO.getRoleById(id);
    }

    // =========================
    // Phân quyền cho role
    // =========================
    public boolean assignPermissionToRole(
            int roleId,
            List<Integer> permissionIds
    ) {

        return permissionDAO
                .assignPermissionToRole(
                        roleId,
                        permissionIds
                ) > 0;
    }

    // =========================
    // Thêm role
    // =========================
    public boolean insertRole(String roleName) {

        if (roleName == null
                || roleName.trim().isEmpty()) {

            return false;
        }

        RoleDTO role = new RoleDTO();

        role.setRoleName(roleName.trim());

        return permissionDAO.insertRole(role) > 0;
    }

    // =========================
    // Xóa role
    // =========================
    public String deleteRole(int roleId) {

    boolean isUsed =
            permissionDAO.isRoleUsed(roleId);

    if (isUsed) {

        return "Role đang được gán cho user, không thể xóa!";
    }

    boolean success =
            permissionDAO.deleteRole(roleId) > 0;

    if (success) {
        return "OK";
    }

    return "Xóa role thất bại!";
}
    public ArrayList<PermissionDTO> getPermissionByRole(int roleId) {
    return permissionDAO.getPermissionByRole(roleId);
}
}