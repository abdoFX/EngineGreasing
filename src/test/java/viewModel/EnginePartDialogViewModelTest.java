package viewModel;

import model.Engine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created on 27-11-2016 at 14:50.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class EnginePartDialogViewModelTest {
/*

    private ApplicationContext context;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("/spring/config/beanLocations.xml");

    }

    @Test
    public void testFormChanges() {
        EngineDialogViewModel engineDialogViewModel = context.getBean("engineDialogViewModel", EngineDialogViewModel.class);
        Assert.assertFalse(engineDialogViewModel.formChangeProperty().get());

        engineDialogViewModel.setType("Abdo");
        engineDialogViewModel.setDesignation("myserialNumber");
        engineDialogViewModel.setId(1);

        Assert.assertTrue(engineDialogViewModel.getFormChange());
    }


    @Test
    public void testNewUpdatesChanges() {
        EngineDialogViewModel engineDialogViewModel = context.getBean("engineDialogViewModel", EngineDialogViewModel.class);

        Assert.assertTrue(engineDialogViewModel.getNewOrUpdateState().equals("NEW"));

        engineDialogViewModel.initializeFields(new Engine());

        Assert.assertFalse(engineDialogViewModel.getFormChange());

        Assert.assertTrue(engineDialogViewModel.getNewOrUpdateState().equals("UPDATE"));


    }

    @Test
    public void okButtonDisabled() {
        EngineDialogViewModel engineDialogViewModel = context.getBean("engineDialogViewModel", EngineDialogViewModel.class);

        Assert.assertFalse(engineDialogViewModel.getEngineDialogValidator().getValidationStatus().isValid());

        engineDialogViewModel.setType("Name");

        Assert.assertFalse(engineDialogViewModel.getEngineDialogValidator().getValidationStatus().isValid());

        engineDialogViewModel.setDesignation("SErial number");

        Assert.assertTrue(engineDialogViewModel.getEngineDialogValidator().getValidationStatus().isValid());


    }

    */
}
