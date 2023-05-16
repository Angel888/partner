package cn.tycoding.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Activity {
    private String scheme;
    private List<Field> fields;
}
