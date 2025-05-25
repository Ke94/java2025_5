package App.model;

public class CategorySummary {
    private final String category;
    private final int totalAmount;
    private final double percentage;

    public CategorySummary(String category, int totalAmount, double percentage) {
        this.category = category;
        this.totalAmount = totalAmount;
        this.percentage = percentage;
    }

    public String getCategory() { return category; }
    public int getTotalAmount() { return totalAmount; }
    public double getPercentage() { return percentage; }
    public String getFormattedPercentage() { return String.format("%.1f%%", percentage); }
}
