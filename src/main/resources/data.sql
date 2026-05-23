-- 宿舍管理系统数据库初始化脚本
-- 应用启动时由 H2 数据库自动执行（spring.sql.init.mode=always）
-- 启动时先 DELETE 再 INSERT，保证每次重启数据一致（幂等）

-- ============================================================
-- 学生用户（仅在表为空时插入，保留用户自行注册的账号）
-- 密码统一为 123456，由 gen-bcrypt.mjs 生成 BCrypt 哈希
-- ============================================================
MERGE INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
KEY(student_no)
SELECT '2024001', '张三', '计算机2401班', '13800001111', '$2b$10$/9FYW/pl1koM6d.nSkAtuuWZ2nMaP7.661uMy7uMohFsdCgmAVM5u', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM student WHERE student_no = '2024001');

MERGE INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
KEY(student_no)
SELECT '2024002', '李四', '计算机2401班', '13800001112', '$2b$10$uV9Ug.rGRSaXuCYdIJsJu.YJoPq7/YxbSUR.NNWxlKM1mhcmbJwye', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM student WHERE student_no = '2024002');

MERGE INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
KEY(student_no)
SELECT '2024003', '王五', '计算机2402班', '13800001113', '$2b$10$SH28Zljy1Qm0HJuanosaEuEbMe0yZCLWvD.CMLTxx3Tr/H3a2zrxC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM student WHERE student_no = '2024003');

MERGE INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
KEY(student_no)
SELECT '2024004', '赵六', '计算机2402班', '13800001114', '$2b$10$2.Y276h32irylgIh1KpZPO2YhcwBlcpWevUMr5EvDJGKrQR/MRUzO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM student WHERE student_no = '2024004');

MERGE INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
KEY(student_no)
SELECT '2024005', '孙琦', '软件工程2401班', '13800001115', '$2b$10$6fJVLDxZW1NJMNZfTahj1eRX6ZcwyVwNO2voxy3ws8Q6ohB6sktui', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM student WHERE student_no = '2024005');

MERGE INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
KEY(student_no)
SELECT '2024006', '周八', '软件工程2401班', '13800001116', '$2b$10$OQS4Xi3JdEjCvyGr5QTnIueED65/ch.VBbtDLLuktz6BkEcpAPvIK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM student WHERE student_no = '2024006');

MERGE INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
KEY(student_no)
SELECT '2024007', '吴九', '网络工程2401班', '13800001117', '$2b$10$eVMSzGkyi4imkeaRQmuNxuEvSrZm03OFCp6JskSorG9iV6a17pJ3C', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM student WHERE student_no = '2024007');

MERGE INTO student (student_no, name, class_name, phone, password, created_at, updated_at)
KEY(student_no)
SELECT '2024008', '郑十', '网络工程2401班', '13800001118', '$2b$10$7Hvzy0z4f.hMKHTjIKmOMOoM3XLAZrNO56GLz39EmNC0qnZszmgAW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM student WHERE student_no = '2024008');

-- ============================================================
-- 宿舍房间（仅在表为空时插入）
-- ============================================================
MERGE INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
KEY(building, room_no)
SELECT 'A栋', '1楼', '101', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_room WHERE building = 'A栋' AND room_no = '101');

MERGE INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
KEY(building, room_no)
SELECT 'A栋', '1楼', '102', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_room WHERE building = 'A栋' AND room_no = '102');

MERGE INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
KEY(building, room_no)
SELECT 'A栋', '2楼', '201', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_room WHERE building = 'A栋' AND room_no = '201');

MERGE INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
KEY(building, room_no)
SELECT 'A栋', '2楼', '202', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_room WHERE building = 'A栋' AND room_no = '202');

MERGE INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
KEY(building, room_no)
SELECT 'A栋', '3楼', '301', 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_room WHERE building = 'A栋' AND room_no = '301');

MERGE INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
KEY(building, room_no)
SELECT 'B栋', '1楼', '101', 6, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_room WHERE building = 'B栋' AND room_no = '101');

MERGE INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
KEY(building, room_no)
SELECT 'B栋', '2楼', '201', 6, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_room WHERE building = 'B栋' AND room_no = '201');

MERGE INTO dorm_room (building, floor, room_no, total_beds, occupied_beds, created_at, updated_at)
KEY(building, room_no)
SELECT 'B栋', '3楼', '301', 6, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_room WHERE building = 'B栋' AND room_no = '301');

-- ============================================================
-- 宿舍分配（仅在表为空时插入，预设分配数据）
-- ============================================================
MERGE INTO dorm_allocation (student_id, room_id, bed_no, move_in_date, created_at, updated_at)
KEY(student_id)
SELECT (SELECT id FROM student WHERE student_no = '2024001'), (SELECT id FROM dorm_room WHERE building = 'A栋' AND room_no = '101'), '1号床', '2024-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_allocation WHERE student_id = (SELECT id FROM student WHERE student_no = '2024001'));

MERGE INTO dorm_allocation (student_id, room_id, bed_no, move_in_date, created_at, updated_at)
KEY(student_id)
SELECT (SELECT id FROM student WHERE student_no = '2024002'), (SELECT id FROM dorm_room WHERE building = 'A栋' AND room_no = '101'), '2号床', '2024-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_allocation WHERE student_id = (SELECT id FROM student WHERE student_no = '2024002'));

MERGE INTO dorm_allocation (student_id, room_id, bed_no, move_in_date, created_at, updated_at)
KEY(student_id)
SELECT (SELECT id FROM student WHERE student_no = '2024003'), (SELECT id FROM dorm_room WHERE building = 'A栋' AND room_no = '102'), '1号床', '2024-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_allocation WHERE student_id = (SELECT id FROM student WHERE student_no = '2024003'));

MERGE INTO dorm_allocation (student_id, room_id, bed_no, move_in_date, created_at, updated_at)
KEY(student_id)
SELECT (SELECT id FROM student WHERE student_no = '2024004'), (SELECT id FROM dorm_room WHERE building = 'A栋' AND room_no = '102'), '2号床', '2024-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_allocation WHERE student_id = (SELECT id FROM student WHERE student_no = '2024004'));

MERGE INTO dorm_allocation (student_id, room_id, bed_no, move_in_date, created_at, updated_at)
KEY(student_id)
SELECT (SELECT id FROM student WHERE student_no = '2024005'), (SELECT id FROM dorm_room WHERE building = 'B栋' AND room_no = '101'), '1号床', '2024-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP WHERE NOT EXISTS (SELECT 1 FROM dorm_allocation WHERE student_id = (SELECT id FROM student WHERE student_no = '2024005'));
