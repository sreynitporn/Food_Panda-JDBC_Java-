package model.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;


import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Boolean isDeleted;
    private Date creatDate;
}
