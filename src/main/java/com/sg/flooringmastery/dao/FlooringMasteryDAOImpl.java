/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author embehr
 */
public class FlooringMasteryDAOImpl implements FlooringMasteryDAO {
    private List<Order> orders = new ArrayList<>();
    private String file;
    public static final String DELIMITER = ",";

    @Override
    public List<Order> displayOrders(String date) throws FlooringMasteryDAOException {
        String[] redate = date.split("/");
        String reformatedDate = "";
        
        for(String partialDate: redate) {
            reformatedDate += partialDate;
        }
        
        file = "Orders_" + reformatedDate + ".txt";
        
        loadOrders();
        
        return orders;
    }

    @Override
    public Order addOrder(Order order, String date) throws FlooringMasteryDAOException {
        String[] redate = date.split("/");
        String reformatedDate = "";
        
        for(String partialDate: redate) {
            reformatedDate += partialDate;
        }
        
        file = "Orders_" + reformatedDate + ".txt";
        
        try {
            loadOrders();
            
            order.setOrderNumber(orders.get(orders.size() - 1).getOrderNumber() + 1);
        } catch (FlooringMasteryDAOException ex) {
            order.setOrderNumber(1);
        }
        
        orders.add(order);
        
        writeOrders();
        
        return order;
    }
    
    @Override
    public Order getOrder(int orderNumber, String date) throws FlooringMasteryDAOException {
        String[] redate = date.split("/");
        String reformatedDate = "";
        
        for(String partialDate: redate) {
            reformatedDate += partialDate;
        }
        
        file = "Orders_" + reformatedDate + ".txt";
        
        loadOrders();
        
        return orders.get(orderNumber - 1);
    }

    @Override
    public Order editOrder(int orderNumber, String date, Order editedOrder) throws FlooringMasteryDAOException {
        String[] redate = date.split("/");
        String reformatedDate = "";
        
        for(String partialDate: redate) {
            reformatedDate += partialDate;
        }
        
        file = "Orders_" + reformatedDate + ".txt";
        
        loadOrders();
        orders.set(orderNumber - 1, editedOrder);
        writeOrders();
        
        return editedOrder;
    }

    @Override
    public Order removeOrder(int orderNumber, String date) throws FlooringMasteryDAOException {
        String[] redate = date.split("/");
        String reformatedDate = "";
        
        for(String partialDate: redate) {
            reformatedDate += partialDate;
        }
        
        file = "Orders_" + reformatedDate + ".txt";
        Order removedOrder;
        
        loadOrders();
        
        removedOrder = orders.remove(orderNumber - 1);
        
        writeOrders();
        
        return removedOrder;
    }

    @Override
    public void exportData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Order unmarchallOrder(String orderAsText) {
        String[] orderTokens = orderAsText.split(DELIMITER);
        Order orderFromFile = new Order();
        
        orderFromFile.setOrderNumber(Integer.parseInt(orderTokens[0]));
        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setState(orderTokens[2]);
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
        orderFromFile.setProductType(orderTokens[4]);
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
        orderFromFile.setTax(new BigDecimal(orderTokens[10]));
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));
        
        return orderFromFile;
    }
    
    private void loadOrders() throws FlooringMasteryDAOException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(
                    new FileReader(file)));
        } catch(FileNotFoundException e) {
            throw new FlooringMasteryDAOException("-_- Could not load file data into memory.", e);
        }
        
        String currentLine;
        Order currentOrder;
        orders = new ArrayList<>();
        
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarchallOrder(currentLine);
            
            orders.add(currentOrder);
        }
        
        scanner.close();
    }
    
    private String marchallOrder(Order order) {
        String orderAsText = order.getOrderNumber() + DELIMITER + 
                order.getCustomerName() + DELIMITER + order.getState() + 
                DELIMITER + order.getTaxRate() + DELIMITER + 
                order.getProductType() + DELIMITER + order.getArea() + DELIMITER
                + order.getCostPerSquareFoot() + DELIMITER + 
                order.getLaborCostPerSquareFoot() + DELIMITER + 
                order.getMaterialCost() + DELIMITER + order.getLaborCost() + 
                DELIMITER + order.getTax() + DELIMITER + order.getTotal();
        
        return orderAsText;
    }
    
    private void writeOrders() throws FlooringMasteryDAOException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(file));
        } catch(IOException e) {
            throw new FlooringMasteryDAOException("Could not save order data.", 
                    e);
        }
        
        String orderAsText;
        
        for(Order currentOrder : orders) {
            orderAsText = marchallOrder(currentOrder);
            
            out.println(orderAsText);
            out.flush();
        }
        
        out.close();
    }
}
