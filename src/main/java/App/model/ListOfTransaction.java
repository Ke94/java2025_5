package App.model;

import java.util.List;
import java.util.ArrayList;

public class ListOfTransaction {
    List<Transaction> list = new ArrayList<>();
    int transactionCount = 0;
    public void add(Transaction transaction){
        list.add(transaction);
        transactionCount++;
    }
}
