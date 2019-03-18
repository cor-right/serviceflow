package me.jiaxu.demo.model;

import lombok.Data;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 */
@Data
public class BaseResponse<T> {

    private String code;


    private String message;


    private T data;

}
