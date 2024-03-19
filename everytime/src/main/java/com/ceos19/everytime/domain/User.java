package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false, updatable = false, length = 50)
    private String username;
    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false,length = 20)
    private String studentNo;

    @Email
    @Column(nullable = false, length = 50)
    private String email;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_id")
    private School school;
}
