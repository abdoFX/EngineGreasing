package dao.daoimpl;

import dao.LubrifiantDAO;
import model.Lubrifiant;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 28-11-2016 at 19:01.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
@Transactional
@Repository
public class LubrifiantDAOImpl implements LubrifiantDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void Save(Lubrifiant obj) {
        sessionFactory.getCurrentSession().save(obj);
    }

    @Override
    public void Update(Lubrifiant obj) {
        sessionFactory.getCurrentSession().update(obj);
    }

    @Override
    public void Delete(Lubrifiant obj) {
        sessionFactory.getCurrentSession().delete(obj);
    }

    @Override
    public Lubrifiant FindById(int id) {
        return sessionFactory.getCurrentSession().get(Lubrifiant.class, id);
    }

    @Override
    public List<Lubrifiant> FindAll() {
        return (List<Lubrifiant>) sessionFactory.getCurrentSession().createQuery("From Lubrifiant").list();
    }
}
