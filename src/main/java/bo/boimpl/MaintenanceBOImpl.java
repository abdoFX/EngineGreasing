package bo.boimpl;

import bo.MaintenanceBO;
import dao.MaintenaceDAO;
import model.maintenance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created on 30-11-2016 at 22:14.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class MaintenanceBOImpl implements MaintenanceBO {
    @Autowired
    private MaintenaceDAO maintenaceDAO;

    @Override
    public void Save(maintenance obj) {
        maintenaceDAO.Save(obj);

    }

    @Override
    public void Update(maintenance obj) {
        maintenaceDAO.Update(obj);
    }

    @Override
    public void Delete(maintenance obj) {
        maintenaceDAO.Delete(obj);
    }

    @Override
    public List<maintenance> getAll() {
        return maintenaceDAO.FindAll();
    }

    @Override
    public maintenance finById(int id) {
        return maintenaceDAO.FindById(id);
    }
}
