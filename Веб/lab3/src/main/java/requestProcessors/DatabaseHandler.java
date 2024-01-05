package requestProcessors;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import beans.ResultBean;

import java.io.Serializable;
import java.util.List;

@Stateless
public class DatabaseHandler implements Serializable {
    @PersistenceContext
    private static EntityManager db;

    public static ResultBean create(ResultBean shot) {
		ResultBean newShot = new ResultBean(shot);
        db.persist(newShot);
        return newShot;
    }

    public List<ResultBean> findAll() {
        return db.createQuery("from ResultBean", ResultBean.class).getResultList();
    }
}
