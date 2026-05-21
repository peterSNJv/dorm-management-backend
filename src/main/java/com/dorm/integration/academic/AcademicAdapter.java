package com.dorm.integration.academic;

/**
 * 教务系统适配器接口（学生班级、院系等）。
 * 实现类与学校教务 API 对接，打破数据孤岛。
 */
public interface AcademicAdapter {

    /**
     * 根据学号获取学生班级等信息。
     */
    AcademicStudentInfo getStudentClassInfo(String studentNo);

    record AcademicStudentInfo(String studentNo, String className, String department) {}
}
