package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
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

    public User(String username, String password, String name, String studentNo, String email, School school) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.studentNo = studentNo;
        this.email = email;
        this.school = school;
    }

    @OneToMany(mappedBy = "author", cascade = ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "commenter", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<TimeTable> timeTables = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<MessageBox> messageBoxes = new ArrayList<>();
}
