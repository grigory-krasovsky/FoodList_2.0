package com.foodlist.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ingredient", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "name")
    private String name;

    public Ingredient(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
