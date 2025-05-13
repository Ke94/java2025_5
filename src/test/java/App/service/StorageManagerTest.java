package App.service;

import App.model.KindOfTransaction;
import App.model.StorageManager;
import App.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class StorageManagerTest {
    public static void main(String[] args){
        Transaction transaction0 = new Transaction(KindOfTransaction.EXPENSES, "午餐", 90);
        Transaction transaction1 = new Transaction(KindOfTransaction.EXPENSES, "晚餐", 120);
        Transaction transaction2 = new Transaction(KindOfTransaction.INCOME, "發票", 200);
        Transaction transaction3 = new Transaction(KindOfTransaction.EXPENSES, "交通費", 80);
        List<Transaction> list = new ArrayList<>();
        list.add(transaction0);
        list.add(transaction1);
        list.add(transaction2);
        list.add(transaction3);

        StorageManager storageManager = new StorageManager();
        storageManager.saveTransactions(list);
        List<Transaction> list2 = storageManager.loadTransactions();
        for(Transaction transaction : list2){
            System.out.println(transaction.toString());
        }
    }
}
