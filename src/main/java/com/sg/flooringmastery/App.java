/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.service.FlooringMasteryServiceImpl;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author embehr
 */
public class App {
    public static void main(String[] args) {
        /*
        UserIO myIO = new UserIOConsoleImpl();
        FlooringMasteryView myView = new FlooringMasteryView(myIO);
        FlooringMasteryServiceLayer serviceLayer = new FlooringMasteryServiceImpl();
        FlooringMasteryController controller = new FlooringMasteryController(serviceLayer, myView);
        controller.run();
        */
        ApplicationContext CTX = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = CTX.getBean("controller", 
                FlooringMasteryController.class);
        controller.run();
    }
}
