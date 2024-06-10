package eth.types;

import java.math.BigDecimal;

public class PriceEvent {
    private String userId;
    private BigDecimal price;

    public PriceEvent(String userId, BigDecimal price) {
        this.userId = userId;
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
