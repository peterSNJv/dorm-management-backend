-- 宿舍管理系统数据库初始化脚本
-- 应用启动时由 H2 数据库自动执行（spring.sql.init.mode=always）
-- 启动时先 DELETE 再 INSERT，保证每次重启数据一致（幂等）

-- ============================================================
-- 清理旧数据（保证重复执行时幂等）
-- ============================================================
DELETE FROM dorm_allocation;
DELETE FROM dorm_room;
DELETE FROM student;

-- ============================================================
-- 学生用户（密码统一为 123456，由 gen-bcrypt.mjs 生成 BCrypt 哈希）
-- ============================================================
INSERT INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
VALUES ('2024001', '张三', '计算机2401班', '13800001111', '$2b$10$/9FYW/pl1koM6d.nSkAtuuWZ2nMaP7.661uMy7uMohFsdCgmAVM5u', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
VALUES ('2024002', '李四', '计算机2401班', '13800001112', '$2b$10$uV9Ug.rGRSaXuCYdIJsJu.YJoPq7/YxbSUR.NNWxlKM1mhcmbJwye', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
VALUES ('2024003', '王五', '计算机2402班', '13800001113', '$2b$10$SH28Zljy1Qm0HJuanosaEuEbMe0yZCLWvD.CMLTxx3Tr/H3a2zrxC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
VALUES ('2024004', '赵六', '计算机2402班', '13800001114', '$2b$10$2.Y276h32irylgIh1KpZPO2YhcwBlcpWevUMr5EvDJGKrQR/MRUzO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
VALUES ('2024005', '孙琦', '软件工程2401班', '13800001115', '$2b$10$6fJVLDxZW1NJMNZfTahj1eRX6ZcwyVwNO2voxy3ws8Q6ohB6sktui', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
VALUES ('2024006', '周八', '软件工程2401班', '13800001116', '$2b$10$OQS4Xi3JdEjCvyGr5QTnIueED65/ch.VBbtDLLuktz6BkEcpAPvIK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
VALUES ('2024007', '吴九', '网络工程2401班', '13800001117', '$2b$10$eVMSzGkyi4imkeaRQmuNxuEvSrZm03OFCp6JskSorG9iV6a17pJ3C', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
VALUES ('2024008', '郑十', '网络工程2401班', '13800001118', '$2b$10$7Hvzy0z4f.hMKHTjIKmOMOoM3XLAZrNO56GLz39EmNC0qnZszmgAW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================================
-- 宿舍房间（A栋/B栋 各楼层示例）
-- ============================================================
INSERT INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
VALUES ('A栋', '1楼', '101', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
VALUES ('A栋', '1楼', '102', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
VALUES ('A栋', '2楼', '201', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
VALUES ('A栋', '2楼', '202', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
VALUES ('A栋', '3楼', '301', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
VALUES ('B栋', '1楼', '101', 6, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
VALUES ('B栋', '2楼', '201', 6, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
VALUES ('B栋', '3楼', '301', 6, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
