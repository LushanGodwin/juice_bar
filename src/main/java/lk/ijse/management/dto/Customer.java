package lk.ijse.management.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Customer {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerContact;
}
