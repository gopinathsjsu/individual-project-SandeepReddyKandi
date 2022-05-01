package validators;

import Database.Database;
import model.OrderItem;

import java.util.ArrayList;

public class ItemPresenceValidation implements ValidationHandler{
    @Override
    public boolean validate(ArrayList<OrderItem> items) {
        Database database = Database.getInstance();
        for(OrderItem orderItem: items){
            if(!database.getStocksInventoryMap().containsKey(orderItem.getName())){
                return false;
            }
        }
        return true;
    }
}
