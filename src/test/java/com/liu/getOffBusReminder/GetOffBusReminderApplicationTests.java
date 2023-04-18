package com.liu.getOffBusReminder;

import com.liu.getOffBusReminder.dao.UserInfoMapper;
import com.liu.getOffBusReminder.dao.entity.UserInfoDO;
import com.liu.getOffBusReminder.utils.IGlobalCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
class GetOffBusReminderApplicationTests {

    @Autowired
    private IGlobalCache globalCache;

    @Resource
    private UserInfoMapper userInfoMapper;

    //比较当下时间是上午还是下午
    @Test
    public void testTime(){
        // 获取当前时间
        LocalTime now = LocalTime.now();

        // 中午12点
        LocalTime am12 = LocalTime.NOON;

        // 比较当前时间和上午12点、下午12点的时间
        boolean isAm = now.isBefore(am12);
        boolean isPm = now.isAfter(am12) && !isAm;

        // 打印判断结果
        System.out.println("现在是上午吗？ " + isAm);
        System.out.println("现在是下午吗？ " + isPm);
    }

    @Test
    public void test() {
        globalCache.set("key2", "value3");
        globalCache.lSetAll("list", Arrays.asList("hello", "redis"));
        List<Object> list = globalCache.lGet("list", 0, -1);
        System.out.println(globalCache.get("key2"));
    }

    /**
     * 数据插入测试
     */
    @Test
    public void dataInsertTest(){
//        UserInfoDO userInfoDO = new UserInfoDO();
//        userInfoDO.setUser_id("1111");
//        int insert = userInfoDao.insert(userInfoDO);
        UserInfoDO userInfoDO = userInfoMapper.queryById("1");
        log.info("插入数据结果:{}",userInfoDO);
    }
}
