package bo.boimpl;

import bo.LubrifiantBO;
import dao.LubrifiantDAO;
import model.Lubrifiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Created on 28-11-2016 at 19:02.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */


public class LubrifiantBOImpl implements LubrifiantBO {

    @Autowired
    @Qualifier("lubrifiantDAO")
    LubrifiantDAO lubrifiantDAO;


    public LubrifiantBOImpl() {
    }

    @Override
    public void Save(Lubrifiant obj) {
        lubrifiantDAO.Save(obj);
    }

    @Override
    public void Update(Lubrifiant obj) {
        lubrifiantDAO.Update(obj);
    }

    @Override
    public void Delete(Lubrifiant obj) {
        lubrifiantDAO.Delete(obj);
    }

    @Override
    public List<Lubrifiant> getAll() {
        return lubrifiantDAO.FindAll();
    }

    @Override
    public Lubrifiant finById(int id) {
        return lubrifiantDAO.FindById(id);
    }
}
