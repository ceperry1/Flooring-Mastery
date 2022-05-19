/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Travis
 */
public class ProductDAOImpl implements ProductDAO{
    
    private List<Product> products;
    private final String file = "Products.txt";
    public static final String DELIMITER = ",";

    @Override
    public List<Product> displayAllProducts() throws FlooringMasteryDAOException {
                loadProducts();
                return products;
    }
    
    
       private void loadProducts() throws FlooringMasteryDAOException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(
                    new FileReader(file)));
        } catch(FileNotFoundException e) {
            throw new FlooringMasteryDAOException("-_- Could not load tax data into memory.", e);
        }
        
        String currentLine;
        Product currentTax;
        products = new ArrayList<>();
        scanner.nextLine();
        
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarchallProducts(currentLine);
            
            products.add(currentTax);
        }
        
        scanner.close();
    }

        //convert product data from file to objects.
        private Product unmarchallProducts(String productsAsText) {
            String[] taxTokens = productsAsText.split(DELIMITER);
            Product productFromFile = new Product();

            productFromFile.setProductType(taxTokens[0]);
            productFromFile.setCostPerSquareFoot(new BigDecimal(taxTokens[1]));
            productFromFile.setLaborCostPerSquareFoot(new BigDecimal(taxTokens[2]));

            return productFromFile;
        }
        
        
        @Override
        public Product displayProductByType(String productType) throws FlooringMasteryDAOException {
            
        loadProducts();
        Product productData = null;
        
        for(Product product: products) {
            
            if(productType.equals(product.getProductType())) {
                //if product type and product object product type match return product.
                productData = product;
                break;
            }
        }
        
        return productData;
    }
        
        
        
    
}
