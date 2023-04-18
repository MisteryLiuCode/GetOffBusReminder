package com.liu.getOffBusReminder.controller;


import com.liu.getOffBusReminder.service.GetOffBusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


@RestController
@Slf4j
public class GetOffBusController {

    @Resource
    private GetOffBusService getOffBusService;


    /**
     * 保存用户,第一次打开的时候保存用户基本信息
     * @param userId
     * @return
     */
    @GetMapping("/saveUserInfo/{userId}")
    @ResponseBody
    public String saveUserInfo(@PathVariable String userId) {
        return getOffBusService.saveUser(userId);
    }

    /**
     * 获取直线距离
     * @param oriLong
     * @param oriLat
     * @return
     */
    @GetMapping("/getDistance/{oriLong}/{oriLat}/{userId}")
    @ResponseBody
    public long getDistance(@PathVariable String oriLong, @PathVariable String oriLat,@PathVariable String userId) {
        return getOffBusService.getDistance(oriLong,oriLat,userId);
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
    @GetMapping("/saveLocation/{oriLong}/{oriLat}/{location}/{userId}")
    @ResponseBody
    public String saveLocation(@PathVariable String oriLong,
                               @PathVariable String oriLat,
                               @PathVariable String location,
                               @PathVariable String userId) {
        return getOffBusService.getLocation(oriLong,oriLat,location,userId);
    }

}