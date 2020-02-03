package bo.boimpl;

import bo.EngineBO;
import dao.EngineDAO;
import model.Engine;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created on 19-11-2016 at 20:47.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */


public class EngineBOImpl implements EngineBO {

    @Autowired
    EngineDAO engineDAO;

    public EngineBOImpl() {
    }

    public void setEngineDAO(EngineDAO engineDAO) {
        this.engineDAO = engineDAO;
    }

    public void Save(Engine obj) {
        engineDAO.Save(obj);

    }

    public void Update(Engine obj) {
        engineDAO.Update(obj);
    }

    public void Delete(Engine obj) {
        engineDAO.Delete(obj);
    }

    public List<Engine> getAll() {
        return engineDAO.FindAll();
    }

    @Override
    public Engine finById(int id) {
        return engineDAO.FindById(id);
    }
}
