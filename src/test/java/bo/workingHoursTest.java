package bo;

import junit.framework.Assert;
import model.WorkingTimeEntry;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;

/**
 * Created on 30-11-2016 at 20:44.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class workingHoursTest {

    private ApplicationContext context = null;

    @Before
    public void setup() {
        context = new ClassPathXmlApplicationContext("/spring/config/beanLocations.xml");
    }


    @Test
    public void insertWorkingHoursWithoutEngine() {
        WorkingTimeEntryBO workingTimeEntryBO = context.getBean("workingTimeEntryBo", WorkingTimeEntryBO.class);

        WorkingTimeEntry workingTimeEntry = new WorkingTimeEntry();
        workingTimeEntry.setDateTime(LocalDateTime.now());
        workingTimeEntry.setWtHours(10);
        workingTimeEntry.setWtMinutes(0);
        workingTimeEntry.setWtSeconds(0);


        boolean thrown = false;

        try {
            workingTimeEntryBO.Save(workingTimeEntry);
        }catch (Exception e){
            e.printStackTrace();
            thrown = true ;
        }


        Assert.assertFalse(thrown);


    }
}
