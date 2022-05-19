/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;
import com.sg.flooringmastery.dao.FlooringMasteryDAOException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Taxes;
import java.util.List;
import com.sg.flooringmastery.dto.Product;

/**
 *
 * @author embehr
 */
public interface FlooringMasteryServiceLayer {
    //TODO: return and fix when order object is made.
    void addOrder(Order order, String date) throws FlooringMasteryDAOException;
    
    //TODO: fix to return an order object when Order is implemented.
    Order removeOrder(String orderDate, int orderNum) throws FlooringMasteryDAOException;
    
    void editOrder(String orderDate, int orderNum) throws FlooringMasteryDAOException;
    
    Order getOrder(int orderNum, String orderDate) throws FlooringMasteryDAOException;

    List<Order> getOrderList(String orderDate) throws FlooringMasteryDAOException;
    
    Taxes getTaxInfo(String stateName) throws FlooringMasteryDAOException;
    
    Product getProductInfo(String productType) throws FlooringMasteryDAOException;
    
    List<Product> getAllProducts() throws FlooringMasteryDAOException;

}
