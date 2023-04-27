package lk.ijse.management.model.tm;

import javafx.scene.control.Button;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AddToCartTM {
    private String item_id;
    private String item_name;
    private Double item_price;
    private Integer item_qty;
    private Double item_total;
    private Button item_action;
}
