package com.example.Login.Entity.RoomPackages;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomPackages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pId;

    @Column(nullable = false)
    private String packageName;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer discountPrice;

    private String roomPackageImageLink;

}
