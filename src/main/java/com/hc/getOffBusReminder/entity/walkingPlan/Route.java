package com.hc.getOffBusReminder.entity.walkingPlan;

/**
 * @author liushuaibiao
 * @date 2023/4/4 11:32
 */

import java.util.List;

public class Route {
    private String origin;

    private String destination;

    private List<Path> paths;


    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }
}
