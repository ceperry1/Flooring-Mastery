package com.sg.flooringmastery.controller;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.dao.FlooringMasteryDAOException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Taxes;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import java.math.BigDecimal;
import java.util.List;
        
public class FlooringMasteryController {
    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer serviceLayer;
    
    public FlooringMasteryController(FlooringMasteryServiceLayer serviceLayer, FlooringMasteryView view){
        this.serviceLayer = serviceLayer;
        this.view = view;
    }
    public void run(){
        boolean keepGoing = true;
        int menuSelection = 0;
        while(keepGoing){
            try{
                menuSelection = printMenuGetSelection();
                
                switch(menuSelection){
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                    case 6:
                        keepGoing = false;
                        break;
                default:
                    unknownCommand();
                }
            }catch(FlooringMasteryDAOException e){
                view.displayErrorMessage(e.getMessage());
            }    
        }
        exitCommand();
    }
    
    private int printMenuGetSelection(){
        return view.printMenuAndGetSelection();
    }
    
    private void displayOrders() throws FlooringMasteryDAOException {
        String orderDate = view.getOrderDateInput();
        List<Order> orderList = serviceLayer.getOrderList(orderDate);
        view.displayOrderList(orderList);
    }
    
    private void addOrder() throws FlooringMasteryDAOException{
        boolean isStateCorrect = false;
        boolean isProductCorrect = false;
        Taxes taxInfo = null;
        Product productInfo = null;
        
        
        while(isStateCorrect == false){
            String state = view.getCustomerState();
            taxInfo = serviceLayer.getTaxInfo(state);
            if(taxInfo != null){
                isStateCorrect = true;
            }
            if(taxInfo == null){
                view.displayBadCommandBanner();
            }
        }  
        while(isProductCorrect == false){
            String productType = view.getProductType();
            productInfo = serviceLayer.getProductInfo(productType);
            if(productInfo != null){
                isProductCorrect = true;
            }
            if(productInfo == null){
                view.displayBadCommandBanner();
            }
        }
        
        Order newOrder = view.createNewOrder(taxInfo, productInfo);
        //ADD PRINT ORDER 

        view.displayOrder(newOrder);

        String wantToBuy = view.getConfirmation();
        //IF USER SAYS YES TO PROMPT ADD

        if(wantToBuy.equals("Y")){
            serviceLayer.addOrder(newOrder, newOrder.getOrderDate());
        }                  
    }    //ELSE BACK TO MENU.
    
    private void removeOrder() throws FlooringMasteryDAOException{
        String orderDate = view.getOrderDateInput();
        int orderNumber = view.getOrderNumInput();
        Order toBeRemoved = serviceLayer.getOrder(orderNumber, orderDate);
        
        view.displayOrder(toBeRemoved);
        
        String wantToDelete = view.getDeleteConfirm();
        //if Y response to asking about delete, delete it.
        if(wantToDelete.equals("Y")){
            
            serviceLayer.removeOrder(orderDate, orderNumber);
        }
    }
    
    private void editOrder() throws FlooringMasteryDAOException{
        //Import order given search params, then store each value from it locally
        String orderDate = view.getOrderDateInput();
        int orderNumber = view.getOrderNumInput();
        Order toBeEdited = serviceLayer.getOrder(orderNumber, orderDate);
        view.displayOrder(toBeEdited);
        //set all immutable features on new order
        String stateTest = toBeEdited.getState();
        String prodType = toBeEdited.getProductType();
        //if user changes something that could impact calculations, redo calc using view methods. 
        String newName = view.editCustName(toBeEdited.getCustomerName());
        String newState = view.editCustState(toBeEdited.getState());
        String newProductType = view.editProductType(prodType);
        BigDecimal newArea = view.editArea(toBeEdited.getArea());
        
        Taxes taxInfo = serviceLayer.getTaxInfo(newState);

        
        BigDecimal newTaxRate = taxInfo.getOrderTaxRate();
        
        Product newProduct = serviceLayer.getProductInfo(newProductType);
        BigDecimal newLaborPerSqrFt = newProduct.getLaborCostPerSquareFoot();
        
        //order date
        Order editedOrder = new Order();
        editedOrder.setOrderDate(orderDate);
        //order number
        editedOrder.setOrderNumber(toBeEdited.getOrderNumber());
        //customer name
        editedOrder.setCustomerName(newName);
        //state
        editedOrder.setState(newState);
        //tax rate
        editedOrder.setTaxRate(newTaxRate);
        //product type
        editedOrder.setProductType(newProductType);
        //area
        editedOrder.setArea(newArea);
        //cost per square foot
        editedOrder.setCostPerSquareFoot(newProduct.getCostPerSquareFoot());
        //labor cost per square foot
        BigDecimal laborCostPerSquareFoot = newProduct.getLaborCostPerSquareFoot();
        editedOrder.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        //material cost
        BigDecimal materialCost = editedOrder.getArea().multiply(editedOrder.getCostPerSquareFoot());
        editedOrder.setMaterialCost(materialCost);
        //labor cost
        
        BigDecimal laborCost = newArea.multiply(laborCostPerSquareFoot);
        editedOrder.setLaborCost(laborCost);
        //tax
        BigDecimal taxCost = editedOrder.getMaterialCost().add(editedOrder.getLaborCost());
        BigDecimal oneHundred = new BigDecimal(100);
        BigDecimal taxConversion = editedOrder.getTaxRate().divide(oneHundred);
        BigDecimal tax = taxCost.multiply(taxConversion);
        editedOrder.setTax(tax);
        //total
        BigDecimal total = materialCost.add(laborCost);
        BigDecimal total2 = total.add(tax);
        editedOrder.setTotal(total2);
        
        serviceLayer.removeOrder(orderDate, orderNumber);
        serviceLayer.addOrder(editedOrder, orderDate);
    }
    
    private void unknownCommand(){
        view.displayBadCommandBanner();
    }
    
    private void exitCommand(){
        view.exitBanner();
    }
    
    private void exportData(){
        System.out.println("yeah nah we aint doing that");
    }
    
}