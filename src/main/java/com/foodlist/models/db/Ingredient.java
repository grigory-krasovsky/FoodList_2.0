package com.foodlist.models.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "ingredient", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID uuid;
    private String name;
    private Timestamp updated_at;

    public Ingredient(UUID uuid, String name, Timestamp updated_at) {
        this.uuid = uuid;
        this.name = name;
        this.updated_at = updated_at;
    }
}
