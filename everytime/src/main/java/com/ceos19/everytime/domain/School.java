package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
@ToString
public class School {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;
    @Column(nullable = false, length = 20)
    private String department;
}
