package exception;

public class OutOfStockException extends RuntimeException {
    private String productName;
    private int requestedQty;
    private int stockQty;

    public OutOfStockException(String productName, int requestedQty, int stockQty) {
        super(String.format("Insufficient stock for product %s. Requested: %d, Available: %d",
                productName, requestedQty, stockQty));

        this.productName = productName;
        this.requestedQty = requestedQty;
        this.stockQty = stockQty;
    }
}
