package com.demo.task.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    public boolean hasId() {
        return id != null && id > 0;
    }
}
