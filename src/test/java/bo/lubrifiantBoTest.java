package bo;

import model.Engine;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 28-11-2016 at 18:55.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class lubrifiantBoTest {

/*
    private ApplicationContext context = null;

    @Before
    public void setup() {
        context = new ClassPathXmlApplicationContext("/spring/config/beanLocations.xml");
    }

    @Test
    public void insertLubrifiantTestWithoutEngines() {
        LubrifiantBO lubrifiantBo = (LubrifiantBO) context.getBean("lubrifiantBo");
        lubrifiant lubrifiant = new lubrifiant();
        lubrifiant.setDesignation("Graisse Naftal");

        boolean thrown = false;

        try {
            lubrifiantBo.Save(lubrifiant);

        } catch (Exception e) {
            e.printStackTrace();
            thrown = true;
        }

        Assert.assertFalse(thrown);
    }


    @Test
    public void insertLubrifiantTestWithEngines() {
        LubrifiantBO lubrifiantBo = (LubrifiantBO) context.getBean("lubrifiantBo");
        lubrifiant lubrifiant = new lubrifiant();
        lubrifiant.setDesignation("Graisse Naftal");

        EnginePart engine = null;
        Set<EnginePart> list = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            engine = new EnginePart()
            engine.setDesignation("width Lub" + i);
            engine.setType("type width Lub" + i);
            engine.setFrequincy(i);
            engine.setLubrifiant(lubrifiant);
            list.add(engine);
        }


      //  lubrifiant.setEngines(list);

        boolean thrown = false;

        try {
            lubrifiantBo.Save(lubrifiant);

        } catch (Exception e) {
            e.printStackTrace();
            thrown = true;
        }

        Assert.assertFalse(thrown);
    }


    @Test
    public void updateLubrifiantTestWithoutEngines() {
        LubrifiantBO lubrifiantBo = (LubrifiantBO) context.getBean("lubrifiantBo");
        lubrifiant lubrifiant = lubrifiantBo.finById(6);
        lubrifiant.setDesignation("Graisse Naftal tested");


        boolean thrown = false;

        try {

            lubrifiantBo.Update(lubrifiant);
        } catch (Exception e) {
            e.printStackTrace();
            thrown = true;
        }

        Assert.assertFalse(thrown);
    }
*/
}
