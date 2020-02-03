package bo.boimpl;

import bo.ZoneBO;
import dao.ZoneDAO;
import model.Zone;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created on 29-11-2016 at 22:40.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class ZoneBOImpl implements ZoneBO {

    @Autowired
    private ZoneDAO zoneDAO;

    @Override
    public void Save(Zone obj) {
        zoneDAO.Save(obj);
    }

    @Override
    public void Update(Zone obj) {
        zoneDAO.Update(obj);
    }

    @Override
    public void Delete(Zone obj) {
        zoneDAO.Delete(obj);
    }

    @Override
    public List<Zone> getAll() {
        return zoneDAO.FindAll();
    }

    @Override
    public Zone finById(int id) {
        return zoneDAO.FindById(id);
    }
}
