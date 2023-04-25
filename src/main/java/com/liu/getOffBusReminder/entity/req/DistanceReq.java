package com.liu.getOffBusReminder.entity.req;

import lombok.Data;

/**
 * @author liushuaibiao
 * @date 2023/4/25 10:43
 */
@Data
public class DistanceReq {

    private String oriLong;

    private String oriLat;

    private String userId;

}
