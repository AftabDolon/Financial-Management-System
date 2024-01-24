package com.demo.task.model.enumFile;

import com.demo.task.model.enumFile.deserializer.BloodGroupDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = BloodGroupDeserializer.class)
public enum BloodGroup {
    A_POSITIVE {
        @Override
        public String toString() {
            return "A+";
        }
    },
    A_NEGATIVE {
        @Override
        public String toString() {
            return "A-";
        }
    },
    B_POSITIVE {
        @Override
        public String toString() {
            return "B+";
        }
    },
    B_NEGATIVE {
        @Override
        public String toString() {
            return "B-";
        }
    },
    O_POSITIVE {
        @Override
        public String toString() {
            return "O+";
        }
    },
    O_NEGATIVE {
        @Override
        public String toString() {
            return "O-";
        }
    },
    AB_POSITIVE {
        @Override
        public String toString() {
            return "AB+";
        }
    },
    AB_NEGATIVE {
        @Override
        public String toString() {
            return "AB-";
        }
    };

    public static List<String> getAllBloodGroups() {
        return Arrays.stream(values()).map(Enum::toString).collect(Collectors.toList());
    }
}
