package com.hc.getOffBusReminder.entity.walkingPlan;

/**
 * @author liushuaibiao
 * @date 2023/4/4 11:32
 */

public class Step
{
    private String instruction;

    private String orientation;

    private String road_name;

    private String step_distance;

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getRoad_name() {
        return road_name;
    }

    public void setRoad_name(String road_name) {
        this.road_name = road_name;
    }

    public String getStep_distance() {
        return step_distance;
    }

    public void setStep_distance(String step_distance) {
        this.step_distance = step_distance;
    }
}
