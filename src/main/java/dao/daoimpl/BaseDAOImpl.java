package dao.daoimpl;

import dao.BaseDAO;
import model.Base;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 29-11-2016 at 19:08.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */

@Transactional
@Repository
public class BaseDAOImpl implements BaseDAO {
    @Autowired

    private SessionFactory sessionFactory;

    public void Save(Base obj) {
        sessionFactory.getCurrentSession().save(obj);
    }

    public void Update(Base obj) {
        sessionFactory.getCurrentSession().update(obj);
    }

    public void Delete(Base obj) {
        sessionFactory.getCurrentSession().delete(obj);
    }

    public Base FindById(int id) {
        return sessionFactory.getCurrentSession().get(Base.class, id);
    }

    public List<Base> FindAll() {
        return (List<Base>) sessionFactory.getCurrentSession().createQuery("From Base").list();
    }

}
