package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class Course {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "course_id")  // PK 값을 학수 번호로 설정
    private Long id;


    @Column(nullable = false, length = 20)
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

    private int popularity = 0;

    @Builder
    public Course(String course_number, String name, int openingGrade, String professorName, int credit, String room) {
        this.course_number = course_number;
        this.name = name;
        this.openingGrade = openingGrade;
        this.professorName = professorName;
        this.credit = credit;
        this.room = room;
    }

    @OneToMany(mappedBy = "course", cascade = ALL, orphanRemoval = true)
    private List<ClassTime> classTimes = new ArrayList<>();

    // Course를 먼저 persist 한 후 addClassTime을 진행햐야함
    public void addClassTime(Weekend dayOfWeek, int timePeriod) {
        ClassTime classTime = ClassTime.builder().course(this)
                .dayOfWeek(dayOfWeek)
                .timePeriod(timePeriod)
                .build();
        classTimes.add(classTime);
    }
}
