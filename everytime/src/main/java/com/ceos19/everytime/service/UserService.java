package com.ceos19.everytime.service;

import com.ceos19.everytime.domain.*;
import com.ceos19.everytime.exception.AppException;
import com.ceos19.everytime.exception.ErrorCode;
import com.ceos19.everytime.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ceos19.everytime.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final TimeTableRepository timeTableRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final TimeTableCourseRepository timeTableCourseRepository;
    private final PostRepository postRepository;
    private final ChatRepository chatRepository;
    private final CommentRepository commentRepository;
    private final SchoolRepository schoolRepository;

    public Long join(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            log.error("에러 내용: 유저 가입 실패 " +
                    "발생 원인: 이미 존재하는 아이디로 가입 시도");
            throw new AppException(DATA_ALREADY_EXISTED, "이미 존재하는 아이디입니다");
        }

        userRepository.save(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("에러 내용: 유저 조회 실패 " +
                    "발생 원인: 존재하지 않는 PK 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 유저입니다");
        }
        return optionalUser.get();
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            log.error("에러 내용: 유저 조회 실패 " +
                    "발생 원인: 존재하지 않는 아이디 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 유저입니다");
        }

        return optionalUser.get();
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.error("에러 내용: 유저 조회 실패 " +
                    "발생 원인: 존재하지 않는 이메일 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 유저입니다");
        }
        return optionalUser.get();
    }


    /**
     * 업데이트 로직
     */
    public void updatePassword(Long userId, String password) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("에러 내용: 유저 조회 실패 " +
                    "발생 원인: 존재하지 않는 PK 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 유저입니다");
        }
        User user = optionalUser.get();
        user.updatePassword(password);
    }

    public void updateName(Long userId, String name) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("에러 내용: 유저 조회 실패 " +
                    "발생 원인: 존재하지 않는 PK 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 유저입니다");
        }
        User user = optionalUser.get();
        user.updateName(name);
    }

    public void updateEmail(Long userId, String email) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("에러 내용: 유저 조회 실패 " +
                    "발생 원인: 존재하지 않는 PK 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 유저입니다");
        }
        User user = optionalUser.get();
        user.updateEmail(email);
    }

    public void updateSchool(Long userId, Long schoolId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("에러 내용: 유저 조회 실패 " +
                    "발생 원인: 존재하지 않는 PK 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 유저입니다");
        }
        User user = optionalUser.get();
        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        if (optionalSchool.isEmpty()) {
            log.error("에러 내용: 학교 조회 실패 " +
                    "발생 원인: 존재하지 않는 PK 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 학교입니다");
        }
        School school = optionalSchool.get();
        user.updateSchool(school);
    }

    public Long addTimeTable(Long userId, TimeTable timeTable) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("에러 내용: 유저 조회 실패 " +
                    "발생 원인: 존재하지 않는 PK 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 유저입니다");
        }
        User user = optionalUser.get();

        List<TimeTable> timeTables = timeTableRepository.findByUserId(userId);
        for (TimeTable table : timeTables) {
            String name = table.getName();
            int year = table.getYear();
            Semester semester = table.getSemester();

            if (name.equals(timeTable.getName()) &&
                    year == timeTable.getYear() &&
                    semester.equals(timeTable.getSemester())) {
                log.error("에러 내용: 시간표 생성 불가 " +
                        "발생 원인: 중복된 시간표 등록");
                throw new AppException(DATA_ALREADY_EXISTED, "중복된 시간표입니다");
            }
        }

        timeTable.setUser(user);
        timeTableRepository.save(timeTable);
        return timeTable.getId();
    }

    public void deleteTimeTable(Long userId, int year, Semester semester, String name) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error("에러 내용: 유저 조회 실패 " +
                    "발생 원인: 존재하지 않는 PK 값으로 조회");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 유저입니다");
        }
        User user = optionalUser.get();

        Optional<TimeTable> optionalTimeTable =
                timeTableRepository.findByUserIdAndYearAndSemesterAndName(userId, year, semester, name);
        if (optionalTimeTable.isEmpty()) {
            log.error("에러 내용: 시간표 조회 실패 " +
                    "발생 원인: 해당 조건에 맞는 시간표 없음");
            throw new AppException(DATA_NOT_FOUND, "존재하지 않는 시간표입니다");
        }
        TimeTable timeTable = optionalTimeTable.get();
        timeTableCourseRepository.deleteAllByTimeTableId(timeTable.getId());  // 중간 테이블 제거 (연관관계 제거)

        timeTableRepository.delete(timeTable);  // TimeTable 제거
    }
}
