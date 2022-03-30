package com.mike.jdbcdemo.model.entity;

import lombok.Data;

@Data
public class MenuDTO {
    private String menuId;
    private String rootMenuId;
    private String parentMenuId;
    private String menuDesc;
    private String taskId;
    private Integer orderNo;
    }
