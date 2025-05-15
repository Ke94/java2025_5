package App.model;

public enum KindOfTransaction {
    INCOME("收入"),
    EXPENSES("支出");

    private final String displayName;

    KindOfTransaction(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
