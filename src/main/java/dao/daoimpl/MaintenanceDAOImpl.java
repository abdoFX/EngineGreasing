package dao.daoimpl;

import dao.MaintenaceDAO;
import model.maintenance;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 30-11-2016 at 22:12.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
@Transactional
@Repository
public class MaintenanceDAOImpl implements MaintenaceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void Save(maintenance obj) {
        sessionFactory.getCurrentSession().save(obj);
    }

    @Override
    public void Update(maintenance obj) {
        sessionFactory.getCurrentSession().update(obj);
    }

    @Override
    public void Delete(maintenance obj) {
        sessionFactory.getCurrentSession().delete(obj);
    }

    @Override
    public maintenance FindById(int id) {
        return sessionFactory.getCurrentSession().find(maintenance.class, id);
    }

    @Override
    public List<maintenance> FindAll() {
        return (List<maintenance>) sessionFactory.getCurrentSession().createQuery("From maintenance").list();

    }
}
