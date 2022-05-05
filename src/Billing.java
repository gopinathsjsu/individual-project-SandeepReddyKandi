import controller.CardController;
import controller.StockInventoryController;
import controller.InputController;

import java.io.IOException;

public class Billing {
    public static void main(String[] args) throws IOException {
        if(args.length==0){
            System.out.println("Pass the file path to Stock Inventory as the first argument.");
            System.exit(0);
        }
        if(args.length==1){
            System.out.println("Pass the file path to Cards Data as the second argument.");
            System.exit(0);
        }
        if(args.length==2){
            System.out.println("Pass the file path to Order input file as the third argument.");
            System.exit(0);
        }
        StockInventoryController stockInventoryController = new StockInventoryController(args[0]);
        CardController cardController = new CardController(args[1]);
        processOrderFile(args[2]);
    }

    private static void processOrderFile(String path){
        InputController inputController = new InputController(path);
        if(inputController.processOrder()){
            if(inputController.checkOrder()){
                inputController.calculateTotal();
                if(inputController.getTotal()>0){
                    inputController.checkoutOrder();
                    System.out.println("The total amount of the order is $" + inputController.getTotal());
                }
            }else {
                System.out.println("Error log created.");
                inputController.generateOutputFile();
            }
        }else {
            System.out.println("Order file not found");
        }
    }
}
