package App.model;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime createdTime;
    private KindOfTransaction kind;
    private String category;
    private int amount;

    Transaction(KindOfTransaction kind, String category, int amount) {
        this.kind = kind;
        this.category = category;
        this.amount = amount;
        this.createdTime = getCreatedTime().now();
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}
