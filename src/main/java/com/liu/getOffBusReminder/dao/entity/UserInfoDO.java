package com.liu.getOffBusReminder.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author liushuaibiao
 * @date 2023/4/17 18:43
 */
@Data
public class UserInfoDO {

    private static final long serialVersionUID = -91969758749726312L;
    /**
     * 唯一标识用户编号
     */
    private String user_id;
    /**
     * 上班中文描述
     */
    private String work_des_cn;
    /**
     * 家位置中文描述
     */
    private String home_des_cn;

    /**
     * 上班经纬度信息
     */
    private String work_des;

    /**
     * 家位置经纬度信息
     */
    private String home_des;

    /**
     * 添加时间
     */
    private Date add_time;

    /**
     * 修改时间
     */
    private Date update_time;

    /**
     * 是否有效  (0否 1是)
     */
    private Integer yn;
}
