package controller;

import Database.Database;
import helper.FileHelper;
import model.Items;
import model.Order;
import model.OrderItem;
import validators.ItemCategoryCapValidation;
import validators.ItemPresenceValidation;
import validators.ItemStockValidation;
import validators.ValidationHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class InputController {
    private final Database database = Database.getInstance();
    private final Order currentOrder = new Order();
    private FileHelper fileHelper;
    private final ArrayList<String> output = new ArrayList<>();
    private final ArrayList<OrderItem> items = new ArrayList<>();
    private final HashSet<String> creditCards = database.getCardsMap();
    private double total = 0;

    public InputController() {}

    public InputController(String filePath){
        fileHelper = new FileHelper(filePath);
    }

    public void addToOutput(String str){
        output.add(str);
    }

    public boolean processOrder() {
        try{
            fileHelper.fileReader(true);
        }catch (Exception e){
            return false;
        }
        getItems(fileHelper.getContentFile());
        return true;
    }
    public boolean checkOrder() {
        checkItemStock();
        return output.size()==0;
    }

    public void calculateTotal() {
        for(OrderItem item: items){
            total += item.getQuantity()*database.getStocksInventoryMap().get(item.getName()).getPrice();
        }
        currentOrder.setTotalPrice(total);
    }

    public double getTotal() {
    return currentOrder.getTotalPrice();
    }

    public void checkoutOrder() {
        for(OrderItem orderItem: items){
            Items item = database.getStocksInventoryMap().get(orderItem.getName());
            item.setQuantity(item.getQuantity()-orderItem.getQuantity());
        }
        for(String credit:creditCards){
            if(!database.getCardsMap().contains(credit)){
                database.getCardsMap().add(credit);
            }
        }
        generateOutputFile();
    }

    public void printMessage() {
        for(String line: output){
            System.out.println(line);
        }
    }

    public void getItems(ArrayList<String> fileContent){
        for(String line: fileContent){
            String[] item = line.split(",");
            if(database.getStocksInventoryMap().containsKey(item[0])){
                items.add(new OrderItem(item[0],Integer.parseInt(item[1]),item[2].replaceAll("\\s+","")));
            }else{
                output.add("Item "+item[0]+" not found");
            }
        }
        if(!output.isEmpty()){
            items.clear();
        }
    }

    public boolean checkItemStock() {
        StringBuilder sb = new StringBuilder();
        database.getOrderItemsMap().add(currentOrder);
        ValidationHandler itemPresence = new ItemPresenceValidation();
        ValidationHandler itemStock = new ItemStockValidation();
        ValidationHandler itemCategory = new ItemCategoryCapValidation();;
        if(!itemPresence.validate(items)){
            output.add("One of the Item doesn't exist in the stock");
        }else if(!itemStock.validate(items)){
            output.add("Please correct quantities of one of the items");
        }else if(!itemCategory.validate(items)){
            output.add("Limit on one of the Categories has exceeded");
        }
        for(OrderItem orderItem: items){
            Items item = database.getStocksInventoryMap().get(orderItem.getName());
            if(item.getQuantity()<orderItem.getQuantity()){
                if(sb.length()>0)
                    sb.append(",");
                sb.append(orderItem.getName()+"("+item.getQuantity()+")");
            }else{
                if(!creditCards.contains(orderItem.getCardDetails()))
                    creditCards.add(orderItem.getCardDetails());
            }
        }
        if(sb.length()>0){
            output.add("Please correct quantities");
            output.add(sb.toString());
        }
        return sb.length()==0;
    }

    public void generateOutputFile(){
        //System.out.println("Zing Zing Amazing");
        if(output.size()==0){
            output.add("Amount Paid");
            output.add(Double.toString((currentOrder.getTotalPrice())));
            try{
                fileHelper.writeOutput(output,false);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                fileHelper.writeOutput(output,true);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
