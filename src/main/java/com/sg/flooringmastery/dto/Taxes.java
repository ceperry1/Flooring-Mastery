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
public class Taxes {
    private String StateAbbreviation;
    private String StateName;
    private BigDecimal TaxRate;
    
    public BigDecimal getOrderTaxRate(){
        return this.TaxRate;
    }
    
    public void setTaxRate(BigDecimal taxRate){
        this.TaxRate = taxRate;
    }
    
    public String getStateAbbreviation(){
        return this.StateAbbreviation;
    }
    
    public void setStateAbbreviation(String stateAbbr){
        this.StateAbbreviation = stateAbbr;
    }
    
    public String getStateName(){
        return this.StateName;
    }
    
    public void setStateName(String stateName){
        this.StateName = stateName;
    }
    
}
