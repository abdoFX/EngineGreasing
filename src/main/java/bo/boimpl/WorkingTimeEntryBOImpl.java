package bo.boimpl;

import bo.WorkingTimeEntryBO;
import dao.WorkingTimeEntryDAO;
import model.WorkingTimeEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created on 30-11-2016 at 20:51.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class WorkingTimeEntryBOImpl implements WorkingTimeEntryBO {

    @Autowired
    private WorkingTimeEntryDAO workingTimeEntryDAO;

    @Override
    public void Save(WorkingTimeEntry obj) {
        workingTimeEntryDAO.Save(obj);
    }

    @Override
    public void Update(WorkingTimeEntry obj) {
        workingTimeEntryDAO.Update(obj);
    }

    @Override
    public void Delete(WorkingTimeEntry obj) {
        workingTimeEntryDAO.Delete(obj);
    }

    @Override
    public List<WorkingTimeEntry> getAll() {
        return workingTimeEntryDAO.FindAll();
    }

    @Override
    public WorkingTimeEntry finById(int id) {
        return workingTimeEntryDAO.FindById(id);
    }
}
