package util;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // 1. Tạo registry
                StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml") // đường dẫn file config
                        .build();

                // 2. Tạo Metadata và SessionFactory
                sessionFactory = new MetadataSources(registry)
                        .addAnnotatedClass(DTO.NganhDTO.class)
                        .addAnnotatedClass(DTO.ToHopDTO.class)           // ✅ THÊM DÒNG NÀY
                        .addAnnotatedClass(DTO.ToHopNganhDTO.class)   // thêm entity
                        .addAnnotatedClass(DTO.UserDTO.class)
                        .buildMetadata()
                        .buildSessionFactory();

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khởi tạo Hibernate: " + e.getMessage());
            }
        }
        return sessionFactory;
    }
}
