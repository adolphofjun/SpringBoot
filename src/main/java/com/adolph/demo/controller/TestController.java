package com.adolph.demo.controller;

import com.adolph.demo.dao.BaobiaoDao;
import com.adolph.demo.service.BaobiaoService;
import com.adolph.demo.utils.PoiUtils;
import com.adolph.demo.utils.ResultMsg;
import com.adolph.demo.utils.ResultUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "getBaobiaoData",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "测试", notes = "测试")
    public  Collection getBaobiaoData(HttpServletResponse response){
        Map map = baobiaoService.findAll(null);
        Collection valueCollection = map.values();
        return valueCollection;
    }

    @ApiOperation(value = "测试", notes = "测试")
    @RequestMapping(value = "/exportEmp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportEmp() {
        System.out.println("到处");
        Map map = baobiaoService.findAll(null);
        Collection<Map> valueCollection = map.values();
        return PoiUtils.exportBaobiaoExcel(valueCollection);
    }

    @ApiOperation(value = "保存日期", notes = "保存日期")
    @RequestMapping(value = "/saveDate", method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg saveDate(@RequestBody @ApiParam(name = "保存日期", value = "传入json格式{\"baobiaoId\":\"1\",\"daoliaoDate\":\"2018-09-12\"}", required = true) Map map) {
        ResultMsg resultMsg;
        try {
            baobiaoService.saveDate(map);
            resultMsg = ResultUtil.success("",map);
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg = ResultUtil.systemError();
        }
        return resultMsg;
    }
}
