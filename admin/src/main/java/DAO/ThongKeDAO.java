/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.NganhDTO;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class ThongKeDAO {
        public int getSoLuongThiSinh() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Long count = session.createQuery(
                    "SELECT COUNT(*) FROM ThiSinhDTO",
                    Long.class
            ).uniqueResult();

            return count.intValue();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
        public int getSoLuongNguyenVong() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        Long count = session.createQuery(
                "SELECT COUNT(*) FROM NguyenVongDTO",
                Long.class
        ).uniqueResult();

        return count.intValue();

    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
        public int getTongSoNganh() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        Long count = session.createQuery(
                "SELECT COUNT(*) FROM NganhDTO",
                Long.class
        ).uniqueResult();

        return count.intValue();

    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
        public int getSoLuongToHop() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        Long count = session.createQuery(
                "SELECT COUNT(*) FROM ToHopDTO",
                Long.class
        ).uniqueResult();

        return count.intValue();

    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
        public ArrayList<Object[]> getTop5NganhTyLeChoiCaoNhat() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        Query<Object[]> query = session.createQuery(
                "SELECT n.tenNganh, " +
                "n.nChiTieu, " +
                "COUNT(nv.idnv) " +
                "FROM NguyenVongDTO nv, NganhDTO n " +
                "WHERE nv.nvManganh = n.maNganh " +
                "GROUP BY n.tenNganh, n.nChiTieu " +
                "ORDER BY (COUNT(nv.idnv) * 1.0 / n.nChiTieu) DESC",
                Object[].class
        );

        query.setMaxResults(5);

        return new ArrayList<>(query.list());

    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    }
}
        public int getSoLuongThiSinhTrungTuyen() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        Long count = session.createQuery(
                "SELECT COUNT(cccd) " +
                "FROM XetTuyenDTO " +
                "WHERE ketQua = 'Đậu'",
                Long.class
        ).uniqueResult();

        return count.intValue();

    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
  
}
