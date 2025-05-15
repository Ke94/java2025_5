package App.service;

import App.model.Transaction;
import App.model.KindOfTransaction;

public class TransactionTest {
    public static void main(String[] args) {
        Transaction transaction = new Transaction(KindOfTransaction.EXPENSES, "晚餐", 120, null);
        System.out.println(transaction);
    }
}
