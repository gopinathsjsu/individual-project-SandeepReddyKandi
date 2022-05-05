package validators;

import Database.Database;
import model.OrderItem;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemCategoryCapValidation implements ValidationHandler{
    private final int essentialCategoryMaxLimit = 40;
    private final int luxuryCategoryMaxLimit = 30;
    private final int miscCategoryMaxLimit = 60;
    @Override
    public String validate(ArrayList<OrderItem> items) {
        HashMap<String,Integer> map = new HashMap<>();
        Database database = Database.getInstance();
        for(OrderItem orderItem: items){
            map.put(database.getStocksInventoryMap().get(orderItem.getName()).getCategory(),map.getOrDefault(database.getStocksInventoryMap().get(orderItem.getName()).getCategory(),0)+orderItem.getQuantity());
        }
        if(map.getOrDefault("Luxury",0)>luxuryCategoryMaxLimit){
            return String.format("Requested quantity %d for %s category exceeds the limit of %d items only.", map.getOrDefault("Luxury",0), "Luxury", luxuryCategoryMaxLimit);
        }else if(map.getOrDefault("Essential",0)> essentialCategoryMaxLimit){
            return String.format("Requested quantity %d for %s category exceeds the limit of %d items only.", map.getOrDefault("Essential",0), "Essential",  essentialCategoryMaxLimit);
        }else if(map.getOrDefault("Misc",0)>miscCategoryMaxLimit){
            return String.format("Requested quantity %d for %s category exceeds the limit of %d items only.", map.getOrDefault("Misc",0), "Misc", miscCategoryMaxLimit);
        }
        return "";
    }
}
