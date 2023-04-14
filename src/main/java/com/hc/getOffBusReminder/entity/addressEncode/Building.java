package com.hc.getOffBusReminder.entity.addressEncode;

/**
 * @author liushuaibiao
 * @date 2023/4/4 11:32
 */
import java.util.List;
public class Building
{
    private List<String> name;

    private List<String> type;

    public void setName(List<String> name){
        this.name = name;
    }
    public List<String> getName(){
        return this.name;
    }
    public void setType(List<String> type){
        this.type = type;
    }
    public List<String> getType(){
        return this.type;
    }
}
