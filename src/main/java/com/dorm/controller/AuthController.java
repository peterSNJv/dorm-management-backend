package com.dorm.controller;

import com.dorm.domain.entity.Student;
import com.dorm.repository.StudentRepository;
import com.dorm.security.AuditService;
import com.dorm.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final JwtService jwtService;
    private final AuditService auditService;
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(
            JwtService jwtService,
            AuditService auditService,
            StudentRepository studentRepository) {
        this.jwtService = jwtService;
        this.auditService = auditService;
        this.studentRepository = studentRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String studentNo = body.get("studentNo");
        String password = body.get("password");

        if (studentNo == null || studentNo.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "学号不能为空"));
        }
        if (password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "密码不能为空"));
        }

        Student student = studentRepository.findByStudentNo(studentNo).orElse(null);
        if (student == null) {
            auditService.log(studentNo, "LOGIN", "auth", "FAIL_USER_NOT_FOUND",
                    request.getRemoteAddr(), request.getHeader("User-Agent"));
            return ResponseEntity.status(401).body(Map.of("message", "学号或密码错误"));
        }

        if (!passwordEncoder.matches(password, student.getPassword())) {
            auditService.log(studentNo, "LOGIN", "auth", "FAIL_WRONG_PASSWORD",
                    request.getRemoteAddr(), request.getHeader("User-Agent"));
            return ResponseEntity.status(401).body(Map.of("message", "学号或密码错误"));
        }

        String role = "STUDENT";
        String token = jwtService.createToken(studentNo, role);
        auditService.log(studentNo, "LOGIN", "auth", "SUCCESS",
                request.getRemoteAddr(), request.getHeader("User-Agent"));

        String displayName = student.getName() != null ? student.getName() : ("学生" + studentNo);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "userId", student.getId(),
                "studentNo", studentNo,
                "name", displayName,
                "role", role
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String studentNo = body.get("studentNo");
        String password = body.get("password");
        String name = body.get("name");

        if (studentNo == null || studentNo.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "学号不能为空"));
        }
        if (password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "密码不能为空"));
        }
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "姓名不能为空"));
        }

        if (studentRepository.findByStudentNo(studentNo).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "该学号已注册"));
        }

        Student student = new Student();
        student.setStudentNo(studentNo);
        student.setName(name);
        student.setPassword(passwordEncoder.encode(password));
        student.setClassName(body.get("className"));
        student.setPhone(body.get("phone"));
        studentRepository.save(student);

        return ResponseEntity.ok(Map.of("message", "注册成功"));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if ("zhouzhike".equals(username) && "zzk248600".equals(password)) {
            String token = jwtService.createToken("zhouzhike", "ADMIN");
            return ResponseEntity.ok(Map.of("token", token, "role", "ADMIN", "name", "管理员"));
        }
        return ResponseEntity.status(401).body(Map.of("message", "用户名或密码错误"));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}