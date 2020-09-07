package com.github.cszxyang.ibatis.model;

import lombok.Data;

import java.util.Date;

/**
 * Container 实体类
 *
 * @author cszxyang
 * @since 2020-09-07
 */
@Data
public class Container {
    /** 主键 **/
    private int id;
    /** 容器编号 **/
    private String code;
    /** 扫箱占用时间 **/
    private Date occupiedTime;
    /** 关箱时间 **/
    private Date closeTime;
}
