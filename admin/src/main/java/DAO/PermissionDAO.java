package DAO;

import DTO.PermissionDTO;
import DTO.RoleDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class PermissionDAO {

    // =========================
    // Lấy tất cả role
    // =========================
    public ArrayList<RoleDTO> getAllRole() {

        try (Session session =
                HibernateUtil
                        .getSessionFactory()
                        .openSession()) {

            CriteriaBuilder cb =
                    session.getCriteriaBuilder();

            CriteriaQuery<RoleDTO> cq =
                    cb.createQuery(RoleDTO.class);

            Root<RoleDTO> root =
                    cq.from(RoleDTO.class);

            cq.select(root);

            List<RoleDTO> list =
                    session.createQuery(cq).getResultList();

            return new ArrayList<>(list);

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    // =========================
    // Lấy tất cả permission
    // =========================
    public ArrayList<PermissionDTO> getAllPermission() {

        try (Session session =
                HibernateUtil
                        .getSessionFactory()
                        .openSession()) {

            CriteriaBuilder cb =
                    session.getCriteriaBuilder();

            CriteriaQuery<PermissionDTO> cq =
                    cb.createQuery(PermissionDTO.class);

            Root<PermissionDTO> root =
                    cq.from(PermissionDTO.class);

            cq.select(root);

            List<PermissionDTO> list =
                    session.createQuery(cq).getResultList();

            return new ArrayList<>(list);

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    // =========================
    // Lấy role theo id
    // =========================
    public RoleDTO getRoleById(int id) {

        try (Session session =
                HibernateUtil
                        .getSessionFactory()
                        .openSession()) {

            return session.get(RoleDTO.class, id);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // =========================
    // Phân quyền cho role
    // =========================
    public int assignPermissionToRole(
            int roleId,
            List<Integer> permissionIds
    ) {

        Transaction tx = null;

        try (Session session =
                HibernateUtil
                        .getSessionFactory()
                        .openSession()) {

            tx = session.beginTransaction();

            RoleDTO role =
                    session.get(RoleDTO.class, roleId);

            if (role == null) {
                return 0;
            }

            Set<PermissionDTO> permissions =
                    new HashSet<>();

            for (Integer permissionId : permissionIds) {

                PermissionDTO permission =
                        session.get(
                                PermissionDTO.class,
                                permissionId
                        );

                if (permission != null) {
                    permissions.add(permission);
                }
            }

            role.setPermissions(permissions);

            session.update(role);

            tx.commit();

            return 1;

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            e.printStackTrace();

            return 0;
        }
    }
    // =========================
// Thêm role
// =========================
public int insertRole(RoleDTO roleDTO) {

    Transaction tx = null;

    try (Session session =
            HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

        tx = session.beginTransaction();

        session.save(roleDTO);

        tx.commit();

        return 1;

    } catch (Exception e) {

        if (tx != null) {
            tx.rollback();
        }

        e.printStackTrace();

        return 0;
    }
}


// =========================
// Xóa role
// =========================
public int deleteRole(int roleId) {

    Transaction tx = null;

    try (Session session =
            HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

        tx = session.beginTransaction();

        RoleDTO role =
                session.get(RoleDTO.class, roleId);

        if (role == null) {
            return 0;
        }

        session.delete(role);

        tx.commit();

        return 1;

    } catch (Exception e) {

        if (tx != null) {
            tx.rollback();
        }

        e.printStackTrace();

        return 0;
    }
}
public ArrayList<PermissionDTO> getPermissionByRole(int roleId) {

    try (Session session =
            HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

        String hql =
                "SELECT p " +
                "FROM RoleDTO r " +
                "JOIN r.permissions p " +
                "WHERE r.id = :roleId";

        List<PermissionDTO> list =
                session.createQuery(hql, PermissionDTO.class)
                        .setParameter("roleId", roleId)
                        .getResultList();

        return new ArrayList<>(list);

    } catch (Exception e) {

        e.printStackTrace();

        return new ArrayList<>();
    }
}
public boolean isRoleUsed(int roleId) {

    Session session =
            HibernateUtil
                    .getSessionFactory()
                    .openSession();

    String hql =
            "SELECT COUNT(u.id) " +
            "FROM UserDTO u " +
            "JOIN u.roles r " +
            "WHERE r.id = :roleId";

    Long count =
            session.createQuery(hql, Long.class)
                    .setParameter("roleId", roleId)
                    .uniqueResult();

    session.close();

    return count != null && count > 0;
}
}