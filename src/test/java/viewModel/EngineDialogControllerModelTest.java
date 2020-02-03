package viewModel;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created on 22-11-2016 at 20:42.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class EngineDialogControllerModelTest {

    EngineDialogViewModel enginePartDialogViewModel = new EngineDialogViewModel();

    @Test
    public void ButtonOkTest() {

        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/config/beanLocations.xml");
//        Assert.assertFalse(engineDialogViewModel.savePossibleProperty().get());

        enginePartDialogViewModel.setType("Abdo");

//        Assert.assertFalse(engineDialogViewModel.savePossibleProperty().get());

        enginePartDialogViewModel.setDesignation("Abdo");

        Assert.assertTrue(enginePartDialogViewModel.formChangeProperty().get());

    }

}
