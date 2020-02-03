package dao;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created on 19-11-2016 at 20:25.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */


public interface DAO<T> {
    void Save(T obj);

    void Update(T obj);

    void Delete(T obj);

    abstract T FindById(int id);

    abstract List<T> FindAll();

}
