package controller;
import Database.Database;
import helper.FileHelper;
import model.Items;

import java.io.IOException;

public class StockInventoryController {
    private final Database database = Database.getInstance();
    public StockInventoryController(String path) throws IOException {
        FileHelper fileHelper = new FileHelper(path);
        try{
            fileHelper.fileReader(true);
        }catch (Exception e){
            System.out.println("The Dataset file path was not found. Please enter valid file path");
            System.exit(0);
        }
        for (String s : fileHelper.getContentFile()) {
            String[] splitItem = s.split(",");
            database.getStocksInventoryMap().put(splitItem[1], new Items(splitItem[0], splitItem[1], Integer.parseInt(splitItem[2]), Double.parseDouble(splitItem[3])));
        }
    }
}
