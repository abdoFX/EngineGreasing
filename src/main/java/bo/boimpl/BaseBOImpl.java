package bo.boimpl;

import bo.BaseBo;
import dao.BaseDAO;
import model.Base;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created on 29-11-2016 at 19:06.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class BaseBOImpl implements BaseBo {


    @Autowired
    private BaseDAO baseDAO;

    @Override
    public void Save(Base obj) {
        baseDAO.Save(obj);
    }

    @Override
    public void Update(Base obj) {
        baseDAO.Update(obj);
    }

    @Override
    public void Delete(Base obj) {
        baseDAO.Delete(obj);
    }

    @Override
    public List<Base> getAll() {
        return baseDAO.FindAll();
    }

    @Override
    public Base finById(int id) {
        return baseDAO.FindById(id);
    }
}
