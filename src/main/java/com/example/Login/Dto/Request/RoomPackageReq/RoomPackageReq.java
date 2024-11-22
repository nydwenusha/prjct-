package com.example.Login.Dto.Request.RoomPackageReq;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class RoomPackageReq {

    private String packageName;
    private Date startDate;
    private Date endDate;
    private String description;
    private Integer discountPrice;
    private String roomPackageImageLink;
}
