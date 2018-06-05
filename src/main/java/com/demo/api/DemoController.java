package com.demo.api;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求头中必须有token的请求示例
 */
@RestController
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    @RequestMapping(value = "/api/hello", method = {RequestMethod.GET})
    public JSONObject queryApplicationCategory() {
        JSONObject json = new JSONObject();
        json.put("c", "123");
        json.put("flag", "需要token校验");
        List<String> list = new ArrayList<>(16);
        for (int i = 0; i < 10; i++) {
            list.add("元素" + i);
        }
        json.put("data", list);
        return json;
    }

}
