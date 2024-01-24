package com.demo.task.model.enumFile;

import java.util.Arrays;
import java.util.List;

public enum Department {
    HR,
    IT,
    SALES,
    MARKETING,
    OPERATION;

    public static List<Department> getAllDepartments() {
        return Arrays.asList(Department.values());
    }
}
