package controller;

import Database.Database;
import helper.FileHelper;

import java.io.IOException;
import java.util.HashSet;

public class CardController {
    private final Database dbInstance = Database.getInstance();

    public CardController(String path) throws IOException {
        FileHelper fileHelper = new FileHelper(path);
        HashSet<String> cardsData = dbInstance.getCardsMap();
        try {
            fileHelper.fileReader(true);
        } catch (Exception e){
            System.out.println("Could not read cards data, please make sure to pass the file path for cards data as second argument.");
            System.exit(0);
        }
        cardsData.addAll(fileHelper.getContentFile());
    }
}
