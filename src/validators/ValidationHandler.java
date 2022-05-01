package validators;

import model.OrderItem;

import java.util.ArrayList;

public interface ValidationHandler {
    boolean validate(ArrayList<OrderItem> items);
}
