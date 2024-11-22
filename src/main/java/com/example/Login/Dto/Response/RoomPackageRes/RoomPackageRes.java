package com.example.Login.Dto.Response.RoomPackageRes;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomPackageRes {

    private String packageName;
    private Date startDate;
    private Date endDate;
    private String description;
    private Integer discountPrice;
    private String roomPackageImageLink;
}
