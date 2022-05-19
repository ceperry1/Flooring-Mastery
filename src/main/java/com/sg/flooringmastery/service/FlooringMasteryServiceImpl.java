/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;
import com.sg.flooringmastery.dao.FlooringMasteryDAO;
import com.sg.flooringmastery.dao.FlooringMasteryDAOException;
import com.sg.flooringmastery.dao.FlooringMasteryDAOImpl;
import com.sg.flooringmastery.dao.ProductDAO;
import com.sg.flooringmastery.dao.ProductDAOImpl;
import com.sg.flooringmastery.dao.TaxesDAO;
import com.sg.flooringmastery.dao.TaxesDAOImpl;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Taxes;
import java.util.List;

/**
 *
 * @author embehr
 */
public class FlooringMasteryServiceImpl implements FlooringMasteryServiceLayer {
    
    //bring in dao
    //init, declare true values in constructors
        private FlooringMasteryDAO dao = new FlooringMasteryDAOImpl();
        private TaxesDAO taxDAO = new TaxesDAOImpl();
        private ProductDAO productDAO = new ProductDAOImpl();
    public FlooringMasteryServiceImpl(FlooringMasteryDAO dao, TaxesDAO taxDao, ProductDAO product){
         dao = new FlooringMasteryDAOImpl();
         taxDAO = new TaxesDAOImpl();
         productDAO = new ProductDAOImpl();
        
        
        
    }
    
    
    public FlooringMasteryServiceImpl(){
        
        
        
        
    }


    @Override
    public void addOrder(Order order, String date) throws FlooringMasteryDAOException{
        //Call DAO version of add order.
        dao.addOrder(order, date);
        
    }

    @Override
    public Order removeOrder(String orderDate, int orderNum) throws FlooringMasteryDAOException{
        Order toBeRemoved;
        
        //call remove item from dao, set equal to an order object.
        toBeRemoved = dao.removeOrder(orderNum, orderDate);
        //return said order object.
        return toBeRemoved;
    }

    @Override
    public void editOrder(String orderDate, int orderNum) throws FlooringMasteryDAOException{
        
        // get the order to be edited using dao.getOrder, save to Order object locally.
       Order editedOrder = dao.getOrder(orderNum, orderDate);
        
       //call dao method to edit order using info given and order obj
       dao.editOrder(orderNum, orderDate, editedOrder);
    }
    
    @Override
    public Order getOrder(int orderNum, String orderDate) throws FlooringMasteryDAOException{
        Order orderRetrieved = dao.getOrder(orderNum, orderDate);
        return orderRetrieved;
    }
    
    @Override
    public List<Order> getOrderList(String orderDate) throws FlooringMasteryDAOException{
        //call dao 
        return dao.displayOrders(orderDate);
        
    
    }
    
    @Override
    public Taxes getTaxInfo(String stateName) throws FlooringMasteryDAOException{
        Taxes stateTaxObject = taxDAO.displayTaxByState(stateName);
        return stateTaxObject;
    
    }

    @Override
    public Product getProductInfo(String productType) throws FlooringMasteryDAOException {
        Product productInfoObject = productDAO.displayProductByType(productType);
        return productInfoObject;
    }
    
    @Override
    public List<Product> getAllProducts() throws FlooringMasteryDAOException{
        return productDAO.displayAllProducts();
    };
    
}
