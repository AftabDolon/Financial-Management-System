package com.demo.task.model.enumFile;

import com.demo.task.model.enumFile.deserializer.DesignationDeserializer;
import com.demo.task.model.enumFile.deserializer.SalaryGradeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = SalaryGradeDeserializer.class)
public enum SalaryGrade {
    GRADE_1 {
        @Override
        public String toString() {
            return "GRADE ONE";
        }
    },
    GRADE_2 {
        @Override
        public String toString() {
            return "GRADE TWO";
        }
    },
    GRADE_3 {
        @Override
        public String toString() {
            return "GRADE THREE";
        }
    },
    GRADE_4 {
        @Override
        public String toString() {
            return "GRADE FOUR";
        }
    },
    GRADE_5 {
        @Override
        public String toString() {
            return "GRADE FIVE";
        }
    },
    GRADE_6 {
        @Override
        public String toString() {
            return "GRADE SIX";
        }
    };

    public static List<String> getAllSalaryGrade() {
        return Arrays.stream(values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

}
