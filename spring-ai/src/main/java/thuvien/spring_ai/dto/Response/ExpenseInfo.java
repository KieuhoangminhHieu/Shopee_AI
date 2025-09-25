package thuvien.spring_ai.dto.Response;

public record ExpenseInfo(
        String category,
        String itemName,
        double amount) {
}
