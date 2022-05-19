/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author embehr
 */
public interface FlooringMasteryDAO {
    List<Order> displayOrders(String date) throws FlooringMasteryDAOException;
    
    Order addOrder(Order order, String date) throws FlooringMasteryDAOException;
    
    Order editOrder(int orderNumber, String date, Order editedOrder) throws FlooringMasteryDAOException;
    
    Order removeOrder(int orderNumber, String date) throws FlooringMasteryDAOException;
    
    void exportData();
    
    Order getOrder(int orderNumber, String date) throws FlooringMasteryDAOException;
}
