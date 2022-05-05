package validators;

import Database.Database;
import model.OrderItem;

import java.util.ArrayList;

public class ItemPresenceValidation implements ValidationHandler{
    @Override
    public String validate(ArrayList<OrderItem> items) {
        Database database = Database.getInstance();
        for(OrderItem orderItem: items){
            if(!database.getStocksInventoryMap().containsKey(orderItem.getName())){
                return String.format("Requested item %s does not exist in the stocks", orderItem.getName());
            }
        }
        return "";
    }
}
