package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
public class ClassTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "class_time_id")
    private Long id;

    @Enumerated(STRING)
    private Weekend dayOfWeek;
    private int timePeriod;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
