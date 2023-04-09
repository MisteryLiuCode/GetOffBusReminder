package com.hc.weathermail;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.hc.weathermail.entity.*;
import com.hc.weathermail.utils.ConfigUtil;
import com.hc.weathermail.utils.IGlobalCache;
import com.hc.weathermail.utils.SendSmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootTest
class WeatherMailApplicationTests {

    @Autowired
    private IGlobalCache globalCache;

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
}
