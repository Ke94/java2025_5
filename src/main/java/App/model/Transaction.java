package App.model;

import java.time.LocalDate;

/**
 * 代表一筆交易資料，例如收入或支出。
 */
public class Transaction {
    private LocalDate createdTime;
    private KindOfTransaction kind;
    private String category;
    private int amount;
    private String note;

    /**
     * 建立一筆交易，會自動記錄創建時間。
     * @param kind INCOME 或 EXPENSES
     * @param category 類別名稱，例如 "飲食" "投資"
     * @param amount 金額（正整數）
     * @param note 註解
     */
    public Transaction(KindOfTransaction kind, String category, int amount, String note) {
        this.kind = kind;
        this.category = category;
        this.amount = amount;
        this.createdTime = LocalDate.now();
        this.note = note;
    }

    public LocalDate getCreatedTime() { return createdTime; }
    public KindOfTransaction getKind() { return kind; }
    public String getCategory() { return category; }
    public int getAmount() { return amount; }
    public String getNote() { return note; }

    @Override
    public String toString(){
        return createdTime+"\n"+kind+"\n"+category+"\n"+amount;
    }
}
