package dao.daoimpl;

import dao.ZoneDAO;
import model.Zone;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 29-11-2016 at 22:41.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */

@Transactional
@Repository
public class ZoneDAOImpl implements ZoneDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void Save(Zone obj) {
        sessionFactory.getCurrentSession().save(obj);
    }

    @Override
    public void Update(Zone obj) {
        sessionFactory.getCurrentSession().update(obj);
    }

    @Override
    public void Delete(Zone obj) {
        sessionFactory.getCurrentSession().delete(obj);
    }

    @Override
    public Zone FindById(int id) {
        return sessionFactory.getCurrentSession().get(Zone.class, id);
    }

    @Override
    public List<Zone> FindAll() {
        return (List<Zone>) sessionFactory.getCurrentSession().createQuery("From Zone").list();
    }
}
