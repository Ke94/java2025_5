package App.service;

import App.model.KindOfTransaction;
import App.model.ListOfTransaction;
import App.model.StorageManager;
import App.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StorageManagerTest {
    public static void main(String[] args){
        ListOfTransaction transactions = new ListOfTransaction(getTransactions());
        ListOfTransaction transactionOfMay= transactions.selectByMonth(5);
        StorageManager storageManager = new StorageManager();
        storageManager.saveTransactions(transactionOfMay.getList());
        List<Transaction> list2 = storageManager.loadTransactions();
        for(Transaction transaction : list2){
            System.out.println(transaction.toString());
        }
    }

    private static List<Transaction> getTransactions() {
        Transaction transaction0 = new Transaction(KindOfTransaction.EXPENSES, "午餐", 90, "");
        Transaction transaction1 = new Transaction(LocalDate.of(2025, 3, 2), KindOfTransaction.EXPENSES, "晚餐", 120, "");
        Transaction transaction2 = new Transaction(KindOfTransaction.INCOME, "發票", 200, "");
        Transaction transaction3 = new Transaction(LocalDate.of(2025, 1, 1), KindOfTransaction.EXPENSES, "交通費", 80, null);
        List<Transaction> list = new ArrayList<>();
        list.add(transaction0);
        list.add(transaction1);
        list.add(transaction2);
        list.add(transaction3);
        return list;
    }
}
