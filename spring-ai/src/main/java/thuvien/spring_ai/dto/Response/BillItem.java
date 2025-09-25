package thuvien.spring_ai.dto.Response;

public record BillItem(String itemName, String category, Integer Quantity, Double Price, Double subTotal ) {
}
