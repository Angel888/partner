package cn.tycoding.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tycoding
 * @date 2019-06-13
 */
@Data
public class UserInfo implements Serializable {

    private Long id;

    private String nickname;

    private String avatar;

    public void setName(String name) {
        this.nickname = name.trim();
    }
}
