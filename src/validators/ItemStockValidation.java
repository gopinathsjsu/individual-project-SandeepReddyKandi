package validators;

import Database.Database;
import model.OrderItem;

import java.util.ArrayList;

public class ItemStockValidation implements ValidationHandler{
    @Override
    public String validate(ArrayList<OrderItem> items) {
        Database database = Database.getInstance();
        for(OrderItem orderItem: items){
            if(database.getStocksInventoryMap().get(orderItem.getName()).getQuantity()<orderItem.getQuantity()){
                return String.format("Requested quantity %d for %s is not present in the stocks, stocks contains only %d items", orderItem.getQuantity(), orderItem.getName(), database.getStocksInventoryMap().get(orderItem.getName()).getQuantity());
            }
        }
        return "";
    }
}
