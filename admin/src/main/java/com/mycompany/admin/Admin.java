/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.admin;

import config.HibernateConfig;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kiman
 */
public class Admin {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // Khởi tạo config
        HibernateConfig config = new HibernateConfig();
        EntityManagerFactory factory = config.getFactory();

        try {
            // Tạo EntityManager để kết nối
            EntityManager em = factory.createEntityManager();

            // Test kết nối bằng câu lệnh đơn giản
            Object result = em.createNativeQuery("SELECT 1").getSingleResult();

            System.out.println("✅ Kết nối database THÀNH CÔNG!");
            System.out.println("✅ Kết quả test: " + result);

            // Đóng kết nối
            em.close();

        } catch (Exception e) {
            System.err.println("❌ Kết nối database THẤT BẠI!");
            System.err.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
