package com.hc.getOffBusReminder.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hc.getOffBusReminder.entity.addressEncode.AddressEncodeVO;
import com.hc.getOffBusReminder.enums.LocationEnum;
import com.hc.getOffBusReminder.utils.ConfigUtil;
import com.hc.getOffBusReminder.utils.IGlobalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;

@RestController
@Slf4j
public class GetOffBusController {

    @Autowired
    private IGlobalCache globalCache;

    //    位置转码
    @RequestMapping("/hello")
    public ResponseEntity<AddressEncodeVO> addressEn() {
        Configuration weatherConfig = ConfigUtil.getHeFengWeatherConfig();
        RestTemplate restTemplate = ConfigUtil.getTemplate();
        String resUrl = ConfigUtil.getAddressEncodeUrl(weatherConfig);
        ResponseEntity<AddressEncodeVO> res = restTemplate.getForEntity(resUrl, AddressEncodeVO.class);
        AddressEncodeVO addressEncodeVO = res.getBody();
        log.info(addressEncodeVO.toString());
        return res;
    }

    //获取直线距离
    @GetMapping("/getDistance/{oriLong}/{oriLat}")
    @ResponseBody
    public long getDistance(@PathVariable String oriLong, @PathVariable String oriLat) {
        log.info("入参:{},{}", oriLong, oriLat);
        Configuration weatherConfig = ConfigUtil.getHeFengWeatherConfig();
        //起始经纬度
        String startPoint = oriLong + "," + oriLat;
        //获取目的地经纬度
        String endPoint = getEndPoint(weatherConfig);
        if (endPoint.equals("")){
            return 0;
        }
        long distance = getDistance(startPoint, endPoint, weatherConfig);
        if (distance < 1500 && !globalCache.hasKey("sendMessage")) {
            String url = "https://api.day.app/DMNK5oTh5FV3RvwpxKvxwB/马上到站了";//指定URL
            String result = HttpUtil.createGet(url).execute().body();
            log.info("发送通知结果：{}", result);
            globalCache.set("sendMessage",true,1800);
        }
        log.info("距离为：{}", distance);
        return distance;
    }

    //读取目的地，中文
    @GetMapping("/getDestination")
    @ResponseBody
    public String getDes() {
        log.info("获取中文目的地开始");
        //判断现在时间是上午还是下午，true为上午，false为下午
        Boolean isAm = des();
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

    //保存上下班位置信息
    @GetMapping("/getLocation/{oriLong}/{oriLat}/{location}")
    @ResponseBody
    public String getLocation(@PathVariable String oriLong, @PathVariable String oriLat,@PathVariable String location) {
        log.info("保存上下班入参:{},{}", oriLong, oriLat,location);
        String point = oriLong + "," + oriLat;
        LocationEnum locationEnum = LocationEnum.valueOf(location);
        try{
            //如果为上班位置
            if(locationEnum.getCode().equals(LocationEnum.TYPE_1.getCode())){
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

    //获取当下时间，true为上午，false为下午
    public Boolean des() {
        // 获取当前时间
        LocalTime now = LocalTime.now();

        // 中午12点
        LocalTime am12 = LocalTime.NOON;

        // 比较当前时间和上午12点、下午12点的时间
        boolean isAm = now.isBefore(am12);
        return isAm;
    }

    //    获取当下位置，根据时间判断，以中午12点为分割线，上午目的地为上班地铁站，下午目的为下班地铁站
    public String getEndPoint(Configuration weatherConfig) {
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
     * 2.经纬度算出两点间距离（通过api调用计算）
     */
    public long getDistance(String startLonLat, String endLonLat, Configuration weatherConfig) {
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = new Long(0);
        String key = weatherConfig.getString("key");
        String queryUrl = "http://restapi.amap.com/v3/distance?key=" + key + "&origins=" + startLonLat + "&destination=" + endLonLat + "&type=0";
        String queryResult = HttpUtil.get(queryUrl);
        //System.out.println(queryResult);
        JSONObject job = JSONObject.parseObject(queryResult);
        JSONArray ja = job.getJSONArray("results");
        JSONObject jobO = JSONObject.parseObject(ja.getString(0));
        result = Long.parseLong(jobO.get("distance").toString());
        //System.out.println("距离："+result);
        return result;
    }


//

    //        RestTemplate restTemplate = ConfigUtil.getTemplate();
//        String resUrl = ConfigUtil.getWalkingPlanUrl(weatherConfig, oriLong, oriLat);
//        //当距离太远会查不到，抛出异常
//        ResponseEntity<WalkingPlanVO> res = restTemplate.getForEntity(resUrl, WalkingPlanVO.class);
//        WalkingPlanVO walkingPlanVO = res.getBody();
//        Path path = walkingPlanVO.getRoute().getPaths().get(0);
//        if (Integer.parseInt(path.getDistance()) < 100 && !falg) {
//            String url = "https://api.day.app/DMNK5oTh5FV3RvwpxKvxwB/马上到站了";//指定URL
//            String result = HttpUtil.createGet(url).execute().body();
//            log.info("发送通知结果：{}", result);
//            falg=true;
//        }
}
