package config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateConfig {
    private EntityManagerFactory factory;

    public HibernateConfig() {
        this.factory = Persistence.createEntityManagerFactory("user_role");
    }
    
    // Getter (viết tay)
    public EntityManagerFactory getFactory() {
        return factory;
    }
    
    // Setter (viết tay)
    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }
}