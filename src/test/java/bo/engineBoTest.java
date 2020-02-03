package bo;

/**
 * Created on 20-11-2016 at 18:25.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */

public class engineBoTest {
/*
    @Test
    public void ListEngineTest() {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/config/beanLocations.xml");

        EngineBO engineBO = (EngineBO) context.getBean("engineBo");
        List<Engine> engines = null;

        Boolean Thrown = false;
        try {
            engines = engineBO.getAll();


        } catch (Exception e) {
            e.printStackTrace();
            Thrown = true;
        }

        Assert.assertFalse(Thrown);


        Assert.assertNotNull(engines);


    }


    @Test
    public void insertEngine() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/config/beanLocations.xml");

        EngineBO engineBO = (EngineBO) context.getBean("engineBo");

        lubrifiant lubrifiant = new lubrifiant();
        lubrifiant.setDesignation("Lubrifiant 1");

        Boolean Thrown = false;
        try {
            Engine engine = new Engine();
            engine.setId(10);
            engine.setType("Abdood");
            engine.setDesignation("321231231");
            engine.setFrequincy(1234);

            engine.setLubrifiant(lubrifiant);
            engineBO.Save(engine);


        } catch (Exception e) {
            e.printStackTrace();
            Thrown = true;
        }

        Assert.assertFalse(Thrown);


    }

    @Test
    public void updateEngineInformation() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/config/beanLocations.xml");

        EngineBO engineBO = (EngineBO) context.getBean("engineBo");
        Engine engine = engineBO.finById(1);

        Assert.assertNotNull(engine);

        engine.setDesignation("Update by abdo designation");
        engine.setType("Update by abdo type");

        lubrifiant lubrifiant = new lubrifiant();
        lubrifiant.setDesignation("New Lubriant");


        engine.setLubrifiant(lubrifiant);
        boolean thrown = false;

        try {

            engineBO.Update(engine);
        } catch (Exception e) {
            thrown = true;
            e.printStackTrace();
        }

        Assert.assertFalse(thrown);
    }
    */
}
