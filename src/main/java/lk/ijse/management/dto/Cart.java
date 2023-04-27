package lk.ijse.management.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Cart {

    String code;
    Integer qty;
    Double unitPrice;
}
