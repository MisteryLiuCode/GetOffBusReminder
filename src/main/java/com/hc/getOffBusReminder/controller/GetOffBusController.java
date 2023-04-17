package com.hc.getOffBusReminder.controller;


import com.hc.getOffBusReminder.service.GetOffBusService;
import com.hc.getOffBusReminder.utils.IGlobalCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


@RestController
@Slf4j
public class GetOffBusController {

    @Resource
    private GetOffBusService getOffBusService;


    @GetMapping("/saveUserInfo/{userId}")
    @ResponseBody
    public Boolean saveUserInfo(@PathVariable String userId) {
//        return getOffBusService.getDistance(userId);
        return true;
    }

    /**
     * 获取直线距离
     * @param oriLong
     * @param oriLat
     * @return
     */
    @GetMapping("/getDistance/{oriLong}/{oriLat}")
    @ResponseBody
    public long getDistance(@PathVariable String oriLong, @PathVariable String oriLat) {
        return getOffBusService.getDistance(oriLong,oriLat);
    }

    /**
     * 读取目的地，中文
     * @return
     */
    @GetMapping("/getDestination")
    @ResponseBody
    public String getDes() {
        return getOffBusService.getDes();
    }

    /**
     * 保存上下班位置信息
     * @param oriLong
     * @param oriLat
     * @param location
     * @return
     */
    @GetMapping("/getLocation/{oriLong}/{oriLat}/{location}")
    @ResponseBody
    public String getLocation(@PathVariable String oriLong, @PathVariable String oriLat,@PathVariable String location) {
        return getOffBusService.getLocation(oriLong,oriLat,location);
    }

}
