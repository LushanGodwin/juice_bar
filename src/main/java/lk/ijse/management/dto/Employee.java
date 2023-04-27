package lk.ijse.management.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Employee {
    private String employeeId;
    private String employeeName;
    private String employeeAddress;
    private String employeeContact;
}
