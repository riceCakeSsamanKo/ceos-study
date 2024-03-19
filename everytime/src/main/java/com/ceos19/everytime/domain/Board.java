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
public class Board {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;
}
