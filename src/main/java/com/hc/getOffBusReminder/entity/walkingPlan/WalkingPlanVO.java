/**
 * Copyright 2023 bejson.com
 */
package com.hc.getOffBusReminder.entity.walkingPlan;

/**
 * Auto-generated: 2023-04-04 11:26:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class WalkingPlanVO {

    private String status;
    private String info;
    private String infocode;
    private String count;
    private Route route;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getInfo() {
        return info;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }
    public String getInfocode() {
        return infocode;
    }

    public void setCount(String count) {
        this.count = count;
    }
    public String getCount() {
        return count;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
