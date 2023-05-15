package cn.tycoding.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Field {
    private String name;
    private String question;
    private List<String> options = new ArrayList<>();
}
