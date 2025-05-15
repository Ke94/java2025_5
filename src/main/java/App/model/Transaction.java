package App.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 代表一筆交易資料，例如收入或支出。
 */
public class Transaction {
    private LocalDateTime createdTime;
    private KindOfTransaction kind;
    private String category;
    private int amount;

    /**
     * 建立一筆交易，會自動記錄創建時間。
     * @param kind INCOME 或 EXPENSES
     * @param category 類別名稱，例如 "Food"、"Salary"
     * @param amount 金額（正整數）
     */
    public Transaction(KindOfTransaction kind, String category, int amount) {
        this.kind = kind;
        this.category = category;
        this.amount = amount;
        this.createdTime = LocalDateTime.now();
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    public KindOfTransaction getKind() {
        return kind;
    }
    public String getCategory() {
        return category;
    }
    public int getAmount() {
        return amount;
    }

    @Override
    public String toString(){
        return createdTime+"\n"+kind+"\n"+category+"\n"+amount;
    }
}
