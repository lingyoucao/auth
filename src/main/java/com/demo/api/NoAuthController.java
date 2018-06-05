package com.demo.api;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NoAuthController {
    private static final Logger logger = LoggerFactory.getLogger(NoAuthController.class);

    @RequestMapping(value = "/noAuth", method = {RequestMethod.GET})
    public JSONObject queryApplicationCategory() {

        JSONObject json = new JSONObject();
        json.put("a", "1");
        json.put("b", "2");
        json.put("flag", "不用token校验的请求");

        List<String> list = new ArrayList<>(16);
        for (int i = 0; i < 10; i++) {
            list.add("元素" + i);
        }
        json.put("data", list);
        return json;
    }
}
