package com.hc.getOffBusReminder.entity.walkingPlan;

/**
 * @author liushuaibiao
 * @date 2023/4/4 11:32
 */

import java.util.List;

public class Path
{
    private String distance;

    private List<Step> steps;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
