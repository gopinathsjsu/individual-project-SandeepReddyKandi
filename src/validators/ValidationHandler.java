package validators;

import model.OrderItem;

import java.util.ArrayList;

public interface ValidationHandler {
    String validate(ArrayList<OrderItem> items);
}
