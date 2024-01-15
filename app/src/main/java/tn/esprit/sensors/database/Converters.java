package tn.esprit.sensors.database;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<String> fromString(String value) {
        return value == null ? new ArrayList<>() : Arrays.asList(value.split(","));
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder value = new StringBuilder();
        for (String item : list) {
            value.append(item).append(",");
        }
        return value.substring(0, value.length() - 1);
    }
}
