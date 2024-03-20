package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@ToString
public class TimeTable extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "time_table_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String semester;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @Setter(value = PROTECTED)
    @ToString.Exclude
    private User user;

    @Builder
    public TimeTable(String name, String semester, User user) {
        this.name = name;
        this.semester = semester;
        this.user = user;
    }

    @OneToMany(mappedBy = "timeTable", cascade = PERSIST)
    private List<TimeTableCourse> timeTableCourses = new ArrayList<>();

    // timeTable이 먼저 persist된 이후에 addCourse 해야함
    public void addCourse(Course course) {
        TimeTableCourse timeTableCourse = new TimeTableCourse(this, course);
        timeTableCourses.add(timeTableCourse);
    }
}
