package dataclass;

import lombok.Data;
import lombok.NoArgsConstructor;
import utility.DataUtil;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ItemData {
    private String name;
    private String description;
    private String price;

    public BigDecimal getPriceBigDecimal() {
        return this.price == null ? BigDecimal.valueOf(0) : DataUtil.convertPriceStringToBigDecimal(this.price);
    }
}
