package com.adolph.demo.service.impl;

import com.adolph.demo.dao.BaobiaoDao;
import com.adolph.demo.service.BaobiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("baobiaoService")
public class BaobiaoServiceImpl implements BaobiaoService {


    @Autowired
    private BaobiaoDao baobiaoDao;
    @Override
    public Map findAll(Map map) {
        baobiaoDao.deleteTemp();
        baobiaoDao.createTemp();
        List<Map> mapList = baobiaoDao.getAllList(null);
        Map data = new HashMap();
        data = getDataUtil(mapList);
        baobiaoDao.deleteTemp();
        System.out.println("===="+data.toString());
        return data;
    }

    @Override
    public void saveDate(Map map) {
        baobiaoDao.saveDate(map);
    }

    private Map getDataUtil(List<Map> mapList) {
        Map data = new HashMap();
        for(int i=0; i<mapList.size(); i++){
            Map temp = mapList.get(i);
            int iden = (int) temp.get("detIden");
            System.out.println("========"+iden);
            if(data.containsKey(iden+"")){
                Map tempM = (Map) data.get(""+iden);
                List list = (List) tempM.get("gx");
                String gxName = temp.get("gxName")+"";
                //String gxName = temp.get("gxName")+"";
                //String gxName = temp.get("gxName")+"";
                double wgNum = (double) temp.get("quantity_make");
                double ddNum = (double) temp.get("quantity");
                int yq = (int) temp.get("yq");
                String type = "";
                if(yq==0){
                    type = "danger";
                }else if(yq==1){
                    type = "success";
                }else if (yq==2){
                    type = "info";
                }
                Map tempMap = new HashMap();
                tempMap.put("gxName",gxName);
                tempMap.put("wgNum",wgNum);
                tempMap.put("ddNum",ddNum);
                tempMap.put("yq",yq);
                tempMap.put("type",type);
                String real_s_other1 = temp.get("real_s_other1")+"";
                String real_s_other2 = temp.get("real_s_other2")+"";
                tempMap.put("real_s_other1",real_s_other1);
                tempMap.put("real_s_other2",real_s_other2);
                list.add(tempMap);
                tempM.put("gx",list);


                data.put(""+iden,tempM);
            }else{
                List list = new ArrayList();
                temp.put("num",1);
                String gxName = temp.get("gxName")+"";
                String real_s_other1 = temp.get("real_s_other1")+"";
                String real_s_other2 = temp.get("real_s_other2")+"";
                double wgNum = (double) temp.get("quantity_make");
                double ddNum = (double) temp.get("quantity");
                int yq = (int) temp.get("yq");
                String type = "";
                if(yq==0){
                    type = "danger";
                }else if(yq==1){
                    type = "success";
                }else if (yq==2){
                    type = "info";
                }
                Map tempMap = new HashMap();
                tempMap.put("gxName",gxName);
                tempMap.put("wgNum",wgNum);
                tempMap.put("ddNum",ddNum);
                tempMap.put("type",type);
                tempMap.put("yq",yq);
                tempMap.put("real_s_other1",real_s_other1);
                tempMap.put("real_s_other2",real_s_other2);
                list.add(tempMap);
                temp.put("gx",list);
                data.put(""+iden,temp);
            }
        }
        return  data;
    }


}
