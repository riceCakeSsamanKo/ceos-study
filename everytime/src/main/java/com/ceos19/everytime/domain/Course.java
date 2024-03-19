package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class Course {
    @Id
    @Column(name = "course_id")  // PK 값을 학수 번호로 설정
    private String course_number;

    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false)
    private int openingGrade;
    @Column(nullable = false, length = 50)
    private String professorName;
    @Column(nullable = false)
    private int credit;
    @Column(nullable = false, length = 50)
    private String room;
    private int popularity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "time_table_id")
    private TimeTable timeTable;

    @OneToMany(mappedBy = "course", cascade = PERSIST)
    private List<ClassTime> times = new ArrayList<>();
}
