package com.example.Login.Entity.Property.LocalAttraction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocalAttraction {
    @Id
    public String id;

}
