package eth.utils;

import java.math.BigDecimal;

public class PricePoint {
    public BigDecimal price;
    public boolean notified;

    public PricePoint() {}

    public PricePoint(BigDecimal price, boolean notified) {
        this.price = price;
        this.notified = notified;
    }
}