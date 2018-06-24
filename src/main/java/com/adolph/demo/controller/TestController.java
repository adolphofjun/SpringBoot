package com.adolph.demo.controller;

import com.adolph.demo.dao.BaobiaoDao;
import com.adolph.demo.service.BaobiaoService;
import com.adolph.demo.utils.PoiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    @Autowired
    private BaobiaoService baobiaoService;
    @RequestMapping(value = "test")
    @ResponseBody
    public  Collection test(HttpServletResponse response){
        Map map = baobiaoService.findAll(null);
        Collection valueCollection = map.values();
        return valueCollection;
    }

    @RequestMapping(value = "/exportEmp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportEmp() {
        System.out.println("到处");
        Map map = baobiaoService.findAll(null);
        Collection<Map> valueCollection = map.values();
        return PoiUtils.exportBaobiaoExcel(valueCollection);
    }
}
