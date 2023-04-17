package com.liu.getOffBusReminder.helper;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liu.getOffBusReminder.enums.LocationEnum;
import com.liu.getOffBusReminder.utils.IGlobalCache;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

/**
 * @author liushuaibiao
 * @date 2023/4/17 14:23
 */
@Component
public class GetOffBusHelper {


    @Autowired
    private IGlobalCache globalCache;


    /**
     * 获取当下时间，true为上午，false为下午
     * @return
     */
    public Boolean des() {
        // 获取当前时间
        LocalTime now = LocalTime.now();

        // 中午12点
        LocalTime am12 = LocalTime.NOON;

        // 比较当前时间和上午12点、下午12点的时间
        boolean isAm = now.isBefore(am12);
        return isAm;
    }

    /**
     * 获取当下位置，根据时间判断，以中午12点为分割线，
     * 上午目的地为上班地铁站，下午目的为下班地铁站
     * @return
     */
    public String getEndPoint() {
        //判断现在时间是上午还是下午，true为上午，false为下午
        Boolean isAm = des();
        String endPoint = "";
        if (isAm) {
            //获取上班目的地经纬度
            endPoint = globalCache.get(LocationEnum.TYPE_1.getCode())==null?"":(String) globalCache.get(LocationEnum.TYPE_1.getCode());
        } else {
            //获取下班目的地经纬度
            endPoint =  globalCache.get(LocationEnum.TYPE_2.getCode())==null?"":(String) globalCache.get(LocationEnum.TYPE_2.getCode());
        }
        return endPoint;
    }


    /**
     * 经纬度算出两点间距离（通过api调用计算）
     */
    public long getDistance(String startLonLat, String endLonLat, Configuration weatherConfig) {
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = new Long(0);
        String key = weatherConfig.getString("key");
        String queryUrl = "http://restapi.amap.com/v3/distance?key=" + key + "&origins=" + startLonLat + "&destination=" + endLonLat + "&type=0";
        String queryResult = HttpUtil.get(queryUrl);
        JSONObject job = JSONObject.parseObject(queryResult);
        JSONArray ja = job.getJSONArray("results");
        JSONObject jobO = JSONObject.parseObject(ja.getString(0));
        result = Long.parseLong(jobO.get("distance").toString());
        return result;
    }

}
