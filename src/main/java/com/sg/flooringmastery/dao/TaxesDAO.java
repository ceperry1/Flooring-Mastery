/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Taxes;
import java.util.List;

/**
 *
 * @author embehr
 */
public interface TaxesDAO {
    List<Taxes> displayAllTaxes() throws FlooringMasteryDAOException;
    
    Taxes displayTaxByState(String name) throws FlooringMasteryDAOException;
}
