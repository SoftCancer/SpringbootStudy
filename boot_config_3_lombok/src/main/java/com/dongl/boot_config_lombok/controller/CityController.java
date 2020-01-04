package com.dongl.boot_config_lombok.controller;

import com.dongl.boot_config_lombok.entity.CityVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2019/12/31 21:58
 */
@RestController
@Slf4j
public class CityController {

    @GetMapping("/city")
    public CityVO findCity(){
        log.info("获取城市信息");
        CityVO cityVO = new CityVO();
        cityVO.setId("123");
        cityVO.setName("北京市");
        cityVO.setZipCode("1100123");
        return cityVO;
    }
}
