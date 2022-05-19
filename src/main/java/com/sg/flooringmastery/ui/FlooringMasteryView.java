/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Taxes;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author embehr
 */
public class FlooringMasteryView {
    private final UserIO io;
    
    public FlooringMasteryView(UserIO io){
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        //print menu
        io.print(" === Flooring Program === ");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export Data");
        io.print("6. Quit");

        //return selection as integer.
        return io.readInt("Please select from the above choices.", 1, 6);
    }
    
    public Order createNewOrder(Taxes taxInfo, Product productInfo){
        
        Order newOrder = new Order();
        //An Order NEEDS: 
        
        
        //order number
        int orderNum = 0; //getOrderNumInput();
        newOrder.setOrderNumber(orderNum);
        
        //Customer Name
        String custName = getCustomerName();
        newOrder.setCustomerName(custName);
        
        //DATE
        String date = getOrderDateInput();
        newOrder.setOrderDate(date);
        
        //Cust State
        String custState = taxInfo.getStateName();
        
        newOrder.setState(custState);
        
        //Tax Rate
        BigDecimal taxRate = taxInfo.getOrderTaxRate();
        newOrder.setTaxRate(taxRate);
        
        
        //Product Type
        
        String productType = productInfo.getProductType();
        newOrder.setProductType(productType);
        
        //Area
        
        BigDecimal orderArea = getArea();
        
        newOrder.setArea(orderArea);
        
        //Cost Per SQR FT
        BigDecimal costPerSqrFt = productInfo.getCostPerSquareFoot();
        newOrder.setCostPerSquareFoot(costPerSqrFt);
        
        
        //Labor Cost per SQR FT
        BigDecimal laborCostPerSqrFt = productInfo.getLaborCostPerSquareFoot();
        newOrder.setLaborCostPerSquareFoot(laborCostPerSqrFt);
        
        //Labor Cost
        BigDecimal LaborCost = laborCostPerSqrFt.multiply(orderArea);
        newOrder.setLaborCost(LaborCost);
        
        //Material Cost
        BigDecimal matsCost = getMaterialCost(orderArea, costPerSqrFt);
        newOrder.setMaterialCost(matsCost);
        
        //tax
        BigDecimal orderTaxes = getTaxes( matsCost,  laborCostPerSqrFt,  taxRate);
        newOrder.setTax(orderTaxes);
        
        //total cost.
        BigDecimal orderTotal = getTotalCost( matsCost,  laborCostPerSqrFt,  orderTaxes);
        newOrder.setTotal(orderTotal);
        
        
        //fill all fields in then return order.
        return newOrder;
    }   
    
    //print bad command input string
    public void displayBadCommandBanner(){
        io.print("== Bad Command Input, please retry ==");
    }
    
    //take in message to print, prepend ERROR: to it and display
    public void displayErrorMessage(String msg){
        io.print("ERROR: " + msg);
    }
    
    public void successfulAddBanner(){
        io.print("== Order Added Successfully ==");
    }
    
    public void successfulEditBanner(){
        io.print("== Order Edited Successfully ==");
    }
    
    public void successfulRemoveBanner(){
        io.print("== Order Removed Successfully ==");
    }
    
    public void exitBanner(){
        io.print("== EXITING FLOORING PROGRAM, GOODBYE ==");
    }
    
    
    //gets order date as string, may modify to be dateandtime compliant
    public String getOrderDateInput(){
        

        //NOTE: Return to this and format for good DateTime api usage
        String date =  io.readString("Input Date of Order in MMDDYYYY Format");
        
        int dateInt = Integer.valueOf(date);
        
        
        return date;
    
    }
    
        public String getConfirmation(){
        

        //NOTE: Return to this and format for good DateTime api usage
        return io.readString("Would you like to place this order? : Y / N ");
    
    }
    
    //asks user for order number, returns int order num value
    public int getOrderNumInput(){
        
        return io.readInt("Please Input order number");

    }
    
    public String getCustomerName(){
        return io.readString("Please input Customer Name");
    }
    
    public String getProductType(){
        
        return  io.readString("Please input product type");
    
    }
    
    public String getDeleteConfirm(){
        
        return  io.readString("Are you sure you want to delete this order? : Y / N");
    
    }
    
    public BigDecimal getArea(){
        double areaBD =  io.readDouble("Please input Area (In Sqr FT.) of at least 100 Sqr Ft.");
               
        while (areaBD < 100 ){
            io.print("Area Must Be Over 100, please try again: ");
            areaBD =  io.readDouble("Please input Area (In Sqr FT.) of at least 100 Sqr Ft.");
        }

        
        BigDecimal doubleToBD = new BigDecimal(areaBD);
 
        
        
        return doubleToBD;
    }
    
    public BigDecimal getCostPerSquareFoot(){
        return io.readBigDecimal("Please input cost per sqr ft.");
        
    }
    
    public BigDecimal getLaborCostPerSquareFoot(){
        return io.readBigDecimal("Please input labor cost per sqr ft.");
        
    }
    
    public String getCustomerState(){
        return io.readString("Please input State of residence");
    }
   
    public BigDecimal getMaterialCost(BigDecimal Area, BigDecimal CostPerSquareFoot){
        return Area.multiply(CostPerSquareFoot);
    }
    
    public BigDecimal getLaborCost(BigDecimal Area, BigDecimal LaborPerSqrFt){
        return Area.multiply(LaborPerSqrFt);
    }
    
    //divide tax rate by 100, then multiply it by the sum of costs
    public BigDecimal getTaxes(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate){
        //BigDecimal taxes = new BigDecimal(taxRate/100);
        //sum costs
        BigDecimal hundred = new BigDecimal(100);
        BigDecimal taxConver = taxRate.divide(hundred);
        BigDecimal summedCosts = materialCost.add(laborCost);
        //multiply by tax rate
        return summedCosts.multiply(taxConver);
    }
    
    //sum up all costs given
    public BigDecimal getTotalCost(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxes){
        return materialCost.add(taxes).add(laborCost);
    }
    
    
    public void displayOrderList(List<Order> orderList) {
        io.print("==== ALL ORDERS ====");
        for (Order currentItem : orderList) {
            //FORMAT: ID: name price QTY
            String orderItem = String.format("#%s : %s %s %s %s %s %s %s %s %s %s",
                  currentItem.getOrderNumber(),
                  currentItem.getCustomerName(),
                  currentItem.getState(),
                  currentItem.getProductType(),
                  currentItem.getArea(), 
                  currentItem.getCostPerSquareFoot(), 
                  currentItem.getLaborCostPerSquareFoot(),
                  currentItem.getMaterialCost(),
                  currentItem.getLaborCost(),
                  currentItem.getTax(),
                  currentItem.getTotal());


            io.print(orderItem);
            
        }    
       
    }
    
    public void displayOrder(Order order) {
        io.print("==== YOUR ORDER ====");
        
        
            //FORMAT: ID: name price QTY
            String orderItem = String.format("Your Order: %s %s %s %s %s %s %s %s %s %s",
                  //order.getOrderNumber(),
                  order.getCustomerName(),
                  order.getState(),
                  order.getProductType(),
                  order.getArea(), 
                  order.getCostPerSquareFoot(), 
                  order.getLaborCostPerSquareFoot(),
                  order.getMaterialCost(),
                  order.getLaborCost(),
                  order.getTax(),
                  order.getTotal());


            io.print(orderItem);
            
    }    
    
    //check if stringified input is nonexistent.
    public boolean isEmpty(String input){
        
        return input.length() ==0;
    }
    
    
    public void displayProductList(List<Product> productList){
                for (Product currentItem : productList) {
            //FORMAT: ID: name price QTY
            String productItem = String.format("#%s : %s %s",
                  currentItem.getProductType(),
                  currentItem.getCostPerSquareFoot(),
                  currentItem.getLaborCostPerSquareFoot());

            io.print(productItem);
            
        }
    
    }
    
    
    
    public String editCustName(String custName){
        String newCustName =  io.readString("Replace name: " + custName + " or leave blank to keep." );
        
        if(isEmpty(newCustName)){
            return custName;
        }
        return newCustName;
    }
    
    public String editCustState(String custState){
        String newCustState =  io.readString("Replace State: " + custState + " or leave blank to keep." );
        
        if(isEmpty(newCustState)){
            io.print("OLD: " + custState);
            return custState;
        }
        io.print("NEW STATE : "+ newCustState);
        return newCustState;
    }
        
    public String editProductType(String prodType){
        String newProd =  io.readString("Replace Product Type: " + prodType + " or leave blank to keep." );
        if(isEmpty(newProd)){
            return prodType;
        }
        return newProd;
    }
    
    public BigDecimal editArea(BigDecimal oldArea){
        String newAreaSTR =  io.readString("Replace Area: " + oldArea + " or leave blank to keep");
        if(isEmpty(newAreaSTR)){
            return oldArea;
        }
        Double newAreaAsDB = Double.valueOf(newAreaSTR);
        while (newAreaAsDB < 100 ){
            io.print("Area Must Be Over 100, please try again: ");
            newAreaAsDB =  io.readDouble("Please input Area (In Sqr FT.) of at least 100 Sqr Ft.");
        
        }
        BigDecimal newArea = new BigDecimal(newAreaAsDB);


        String areaString = String.valueOf(newArea);
        
        if(isEmpty(areaString)){
            return oldArea;
        }
        
        return newArea;
        
    }
}
