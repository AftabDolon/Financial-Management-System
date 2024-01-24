package com.demo.task.model.enumFile;

import com.demo.task.model.enumFile.deserializer.DesignationDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = DesignationDeserializer.class)
public enum Designation {
    OFFICER,
    MANAGER,
    EXECUTIVE,
    SENIOR_OFFICER {
        @Override
        public String toString() {
            return "SENIOR OFFICER";
        }
    },
    JUNIOR_OFFICER {
        @Override
        public String toString() {
            return "JUNIOR OFFICER";
        }
    },
    HEAD_OF_THE_DEPARTMENT {
        @Override
        public String toString() {
            return "HEAD OF THE DEPARTMENT";
        }
    },
    ASSISTANT_MANAGER {
        @Override
        public String toString() {
            return "ASSISTANT MANAGER";
        }
    },
    ACCOUNT_EXECUTIVE {
        @Override
        public String toString() {
            return "ACCOUNT EXECUTIVE";
        }
    };

    public static List<String> getAllDesignations() {
        return Arrays.stream(values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

}
