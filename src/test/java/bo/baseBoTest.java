package bo;

import bo.boimpl.BaseBOImpl;
import model.Base;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created on 29-11-2016 at 19:01.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class baseBoTest {
    private ApplicationContext context = null;

    @Before
    public void setup() {
        context = new ClassPathXmlApplicationContext("/spring/config/beanLocations.xml");
    }

    @Test
    public void insertBaseTestWidthoutZones(){
        Base base = new Base();
        BaseBo baseBo = context.getBean("baseBo",BaseBo.class);

        boolean thrown = false ;

        base.setName("Name");

        try {
            baseBo.Save(base);
        }catch (Exception e){
            thrown = true;
            e.printStackTrace();
        }

        Assert.assertFalse(thrown);

    }
    @Test
    public void findByIdBaseTestWidthoutZones(){
        Base base = null;
        BaseBo baseBo = context.getBean("baseBo",BaseBo.class);

        boolean thrown = false ;



        try {
            base = baseBo.finById(1);
        }catch (Exception e){
            thrown = true;
            e.printStackTrace();
        }

        Assert.assertFalse(thrown);
        Assert.assertNotNull(base);

    }

    @Test
    public void UpdateBaseTestWidthoutZones(){
        Base base = null;
        BaseBo baseBo = context.getBean("baseBo",BaseBo.class);

        boolean thrown = false ;



        try {
            base = baseBo.finById(1);
        }catch (Exception e){
            thrown = true;
            e.printStackTrace();
        }

        Assert.assertFalse(thrown);

        base.setName("Changed");
        Base updated = new Base();
        try {
               baseBo.Update(base);
            updated = baseBo.finById(1);
        }catch (Exception e){
            thrown = true;
            e.printStackTrace();
        }
        Assert.assertTrue(updated.getName().equals("Changed"));

    }


    @Test
    public void DeleteBaseTestWidthoutZones(){
        Base base = null;
        BaseBo baseBo = context.getBean("baseBo",BaseBo.class);

        boolean thrown = false ;



        try {
            base = baseBo.finById(1);
            baseBo.Delete(base);
        }catch (Exception e){
            thrown = true;
            e.printStackTrace();
        }

        Assert.assertFalse(thrown);

        Base updated = new Base();
        try {
            base = baseBo.finById(1);
        }catch (Exception e){
            thrown = true;
            e.printStackTrace();
        }
        Assert.assertNull(base);

    }
}
