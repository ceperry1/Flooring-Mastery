/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author Travis
 */
public class Product {
    public String productType;
    public BigDecimal CostPerSquareFoot;
    public BigDecimal LaborCostPerSquareFoot;
    
    public String getProductType(){
        return this.productType;
    }
    
    public BigDecimal getCostPerSquareFoot(){
        return this.CostPerSquareFoot;
    }
    
    public BigDecimal getLaborCostPerSquareFoot(){
        return this.LaborCostPerSquareFoot;
    }
    
    
    public void setProductType(String productType){
        this.productType = productType;
    }
    
    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot){
        this.CostPerSquareFoot = costPerSquareFoot;
    }
   
    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot){
        this.LaborCostPerSquareFoot = laborCostPerSquareFoot;
    }
}  
