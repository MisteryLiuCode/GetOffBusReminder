package com.hc.getOffBusReminder.service;

import cn.hutool.http.HttpUtil;
import com.hc.getOffBusReminder.enums.LocationEnum;
import com.hc.getOffBusReminder.helper.GetOffBusHelper;
import com.hc.getOffBusReminder.utils.ConfigUtil;
import com.hc.getOffBusReminder.utils.IGlobalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * @author liushuaibiao
 * @date 2023/4/17 14:21
 */
@Service
@Slf4j
public class GetOffBusService {

    @Resource
    private GetOffBusHelper getOffBusHelper;

    @Autowired
    private IGlobalCache globalCache;

    public long getDistance(@PathVariable String oriLong, @PathVariable String oriLat) {
        log.info("获取直线距离入参:{},{}", oriLong, oriLat);
        Configuration weatherConfig = ConfigUtil.getHeFengWeatherConfig();
        //起始经纬度
        String startPoint = oriLong + "," + oriLat;
        //获取目的地经纬度
        String endPoint = getOffBusHelper.getEndPoint();
        if (endPoint.equals("")) {
            return 0;
        }
        long distance = getOffBusHelper.getDistance(startPoint, endPoint,weatherConfig);
        if (distance < 1000 && !globalCache.hasKey("sendMessage")) {
            String url = "https://api.day.app/DMNK5oTh5FV3RvwpxKvxwB/马上到站了";//指定URL
            String result = HttpUtil.createGet(url).execute().body();
            log.info("发送通知结果：{}", result);
            globalCache.set("sendMessage", true, 1800);
        }
        log.info("距离为：{}", distance);
        return distance;
    }

    public String getDes() {
        log.info("获取中文目的地开始");
        //判断现在时间是上午还是下午，true为上午，false为下午
        Boolean isAm = getOffBusHelper.des();
        Configuration weatherConfig = ConfigUtil.getHeFengWeatherConfig();
        String des = "";
        if (isAm) {
            //获取上班目的地，中文
            des = weatherConfig.getString("upDes");
        } else {
            //获取下班目的，中文
            des = weatherConfig.getString("downDes");
        }
        log.info("获取目的地为{}", des);
        return des;
    }

    public String getLocation(@PathVariable String oriLong, @PathVariable String oriLat,@PathVariable String location) {
        log.info("保存上下班入参:{},{}", oriLong, oriLat,location);
        String point = oriLong + "," + oriLat;
        try{
            //如果为上班位置
            if(location.equals(LocationEnum.TYPE_1.getCode())){
                globalCache.set("work",point);
                log.info("保存上班位置信息成功");
            }else {
                globalCache.set("home",point);
                log.info("保存家位置信息成功");
            }
            return "success";
        }catch (Exception e){
            return "fail";
        }
    }

}
