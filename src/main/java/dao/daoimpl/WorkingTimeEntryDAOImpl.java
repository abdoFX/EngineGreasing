package dao.daoimpl;

import dao.WorkingTimeEntryDAO;
import model.WorkingTimeEntry;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 30-11-2016 at 20:48.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
@Transactional
@Repository
public class WorkingTimeEntryDAOImpl implements WorkingTimeEntryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void Save(WorkingTimeEntry obj) {
        sessionFactory.getCurrentSession().save(obj);
    }

    @Override
    public void Update(WorkingTimeEntry obj) {
        sessionFactory.getCurrentSession().update(obj);
    }

    @Override
    public void Delete(WorkingTimeEntry obj) {
        sessionFactory.getCurrentSession().delete(obj);
    }

    @Override
    public WorkingTimeEntry FindById(int id) {
        return sessionFactory.getCurrentSession().find(WorkingTimeEntry.class, id);
    }

    @Override
    public List<WorkingTimeEntry> FindAll() {
        return (List<WorkingTimeEntry>) sessionFactory.getCurrentSession().createQuery("From WorkingTimeEntry").list();

    }
}
