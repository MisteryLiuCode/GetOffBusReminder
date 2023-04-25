package com.liu.getOffBusReminder.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liu.getOffBusReminder.dao.UserInfoMapper;
import com.liu.getOffBusReminder.dao.entity.UserInfoDO;
import com.liu.getOffBusReminder.entity.user.UserReq;
import com.liu.getOffBusReminder.enums.LocationEnum;
import com.liu.getOffBusReminder.helper.GetOffBusHelper;
import com.liu.getOffBusReminder.utils.ConfigUtil;
import com.liu.getOffBusReminder.utils.IGlobalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private UserInfoMapper userInfoMapper;

    public Boolean saveUser(UserReq userReq){
        log.info("保存用户:{}", JSONObject.toJSONString(userReq));
        UserInfoDO userInfoDO = userInfoMapper.queryByUserId(userReq.getUserId());
        if (userInfoDO==null){
            userInfoDO=new UserInfoDO();
            userInfoDO.setUserId(userReq.getUserId());
            userInfoDO.setYn(1);
            String time = getOffBusHelper.getTime();
            userInfoDO.setAddTime(time);
            log.info("用户入库开始");
            int insert = userInfoMapper.insert(userInfoDO);
            return insert==1?true:false;
        }
        return true;
    }

    public Double getDistance(String oriLong, String oriLat, String userId) {
        log.info("获取直线距离入参:{},{}", oriLong, oriLat);
        Configuration weatherConfig = ConfigUtil.getHeFengWeatherConfig();
        //起始经纬度
        String startPoint = oriLong + "," + oriLat;
        //获取目的地经纬度
        String endPoint = getOffBusHelper.getEndPoint(userId);
        if (endPoint.equals("")) {
            return Double.valueOf(0);
        }
        Double distance = getOffBusHelper.getDistance(startPoint, endPoint,weatherConfig);
        if (distance < 1500 && !globalCache.hasKey("sendMessage")) {
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

    public String getLocation(String oriLong, String oriLat, String location, String userId) {
        log.info("保存上下班入参:{},{},{},{}", oriLong, oriLat,location,userId);
        String point = oriLong + "," + oriLat;
        try{
            UserInfoDO userInfoDO = new UserInfoDO();
            String time = getOffBusHelper.getTime();
            //如果为上班位置
            if(location.equals(LocationEnum.TYPE_1.getCode())){
                userInfoDO.setWorkDes(point);
                userInfoDO.setUserId(userId);
                userInfoDO.setUpdateTime(time);
                userInfoMapper.update(userInfoDO);
                log.info("保存上班位置信息成功");
            }else {
                userInfoDO.setHomeDes(point);
                userInfoDO.setUserId(userId);
                userInfoDO.setUpdateTime(time);
                userInfoMapper.update(userInfoDO);
                log.info("保存家位置信息成功");
            }
            return "success";
        }catch (Exception e){
            return "fail";
        }
    }

    public String getWorkAndHomeLocation(String location,String userId) {
        log.info("获取上下班位置信息入参:{},{}", location,userId);
        UserInfoDO userInfoDO = userInfoMapper.queryByUserId(userId);
        try{
            //如果为上班位置
            if(location.equals(LocationEnum.TYPE_1.getCode())){
                String workDes = userInfoDO.getWorkDes();
                log.info("获取的上班位置{}",workDes);
                return workDes;
            }else {
                String homeDes = userInfoDO.getHomeDes();
                log.info("获取的下班位置{}",homeDes);
                return homeDes;
            }
        }catch (Exception e){
            return "fail";
        }
    }


    public List<String> inputPrompt(String oriLong, String oriLat,String keywords) {
        log.info("输入提示入参:{},{},{},{}", oriLong, oriLat,keywords);
        Configuration weatherConfig = ConfigUtil.getHeFengWeatherConfig();
        //我的位置经纬度
        String myPoint = oriLong + "," + oriLat;
        List<String> nameList = getOffBusHelper.getInputPrompt(myPoint,keywords,weatherConfig);
        log.info("获取输入提示结果:{}", JSON.toJSONString(nameList));
        return nameList;
    }
}
