package bo;

import java.util.List;

/**
 * Created on 19-11-2016 at 20:41.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public interface BO<T> {
    void Save(T obj);

    void Update(T obj);

    void Delete(T obj);

    List<T> getAll();

    T finById(int id);

}
