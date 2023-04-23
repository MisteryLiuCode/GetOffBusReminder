package com.liu.getOffBusReminder.controller;


import com.liu.getOffBusReminder.common.RespResult;
import com.liu.getOffBusReminder.entity.user.UserReq;
import com.liu.getOffBusReminder.service.GetOffBusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/getOffBus")
public class GetOffBusController {

    @Resource
    private GetOffBusService getOffBusService;


    @RequestMapping("/test")
    @ResponseBody
    public RespResult<String> test(){
        return new RespResult<>("success");
    }

    /**
     * 保存用户,第一次打开的时候保存用户基本信息
     * @return
     */
    @GetMapping("/saveUserInfo/{userId}")
    @ResponseBody
    public RespResult<Boolean> saveUserInfo(UserReq userReq) {
        return new RespResult<>(getOffBusService.saveUser(userReq));
    }

    /**
     * 获取直线距离
     * @param oriLong
     * @param oriLat
     * @return
     */
    @GetMapping("/getDistance/{oriLong}/{oriLat}/{userId}")
    @ResponseBody
    public RespResult<Double> getDistance(@PathVariable String oriLong, @PathVariable String oriLat,@PathVariable String userId) {
        return new RespResult<>(getOffBusService.getDistance(oriLong,oriLat,userId));
    }

    /**
     * 读取目的地，中文
     * @return
     */
    @RequestMapping("/getDestination")
    @ResponseBody
    public RespResult<String> getDes() {
        return new RespResult<>(getOffBusService.getDes());
    }

    /**
     * 保存上下班位置信息
     * @param oriLong
     * @param oriLat
     * @param location
     * @return
     */
    @RequestMapping("/saveLocation/{oriLong}/{oriLat}/{location}/{userId}")
    @ResponseBody
    public RespResult<String> saveLocation(@PathVariable String oriLong,
                               @PathVariable String oriLat,
                               @PathVariable String location,
                               @PathVariable String userId) {
        return new RespResult<>(getOffBusService.getLocation(oriLong,oriLat,location,userId));
    }

    /**
     * 输入提示
     */
    @RequestMapping("/inputPrompt/{oriLong}/{oriLat}/{keywords}")
    @ResponseBody
    public RespResult<List<String>> inputPrompt(@PathVariable String oriLong,
                                                @PathVariable String oriLat,
                                                @PathVariable String keywords){
        return new RespResult<>(getOffBusService.inputPrompt(oriLong, oriLat, keywords));
    }




}
