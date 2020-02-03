package dao.daoimpl;

import dao.EngineDAO;
import model.Engine;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 19-11-2016 at 21:09.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */


@Transactional
@Repository
public class EngineDaoImpl implements EngineDAO {

    @Autowired

    private SessionFactory sessionFactory;


    public void Save(Engine obj) {
        sessionFactory.getCurrentSession().save(obj);
    }

    public void Update(Engine obj) {
        sessionFactory.getCurrentSession().update(obj);
    }

    public void Delete(Engine obj) {
        sessionFactory.getCurrentSession().delete(obj);
    }

    public Engine FindById(int id) {
        return sessionFactory.getCurrentSession().get(Engine.class, id);
    }

    public List<Engine> FindAll() {
        return (List<Engine>) sessionFactory.getCurrentSession().createQuery("From Engine").list();
    }


}
