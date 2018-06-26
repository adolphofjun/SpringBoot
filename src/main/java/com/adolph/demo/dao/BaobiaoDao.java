package com.adolph.demo.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaobiaoDao {

    List<Map> getAllList(Map map);
    void createTemp();
    void deleteTemp();
    void saveDate(Map map);
}
