/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Taxes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author embehr
 */
public class TaxesDAOImpl implements TaxesDAO {
    private List<Taxes> taxes;
    private final String file = "Taxes.txt";
    public static final String DELIMITER = ",";

    @Override
    public List<Taxes> displayAllTaxes() throws FlooringMasteryDAOException {
        loadTaxes();
        
        return taxes;
    }

    @Override
    public Taxes displayTaxByState(String abbreviation) throws FlooringMasteryDAOException {
        loadTaxes();
        Taxes stateTax = null;
        
        for(Taxes tax: taxes) {
            if(abbreviation.equals(tax.getStateName())) {
                stateTax = tax;
                break;
            }
        }
        
        return stateTax;
    }
    
    private Taxes unmarchallTaxes(String taxesAsText) {
        String[] taxTokens = taxesAsText.split(DELIMITER);
        Taxes taxesFromFile = new Taxes();
        
        taxesFromFile.setStateAbbreviation(taxTokens[0]);
        taxesFromFile.setStateName(taxTokens[1]);
        taxesFromFile.setTaxRate(new BigDecimal(taxTokens[2]));
        
        return taxesFromFile;
    }
    
    private void loadTaxes() throws FlooringMasteryDAOException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(
                    new FileReader(file)));
        } catch(FileNotFoundException e) {
            throw new FlooringMasteryDAOException("-_- Could not load tax data into memory.", e);
        }
        
        String currentLine;
        Taxes currentTax;
        taxes = new ArrayList<>();
        scanner.nextLine();
        
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarchallTaxes(currentLine);
            
            taxes.add(currentTax);
        }
        
        scanner.close();
    }
}
