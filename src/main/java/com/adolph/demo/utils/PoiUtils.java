package com.adolph.demo.utils;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PoiUtils {
    public static ResponseEntity<byte[]> exportBaobiaoExcel(Collection<Map> maps) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("生产进度表");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            int max = 0;
            java.util.Iterator it = maps.iterator();
            while(it.hasNext()){
                Map map = (Map) it.next();
                List list = (List) map.get("gx");
                int maxi = list.size();
                if(maxi>max) max = maxi;
            }

            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 20* 256);
            sheet.setColumnWidth(2, 20 * 256);
            sheet.setColumnWidth(3, 20 * 256);
            sheet.setColumnWidth(4, 20 * 256);
            sheet.setColumnWidth(5, 20 * 256);
            sheet.setColumnWidth(6, 20 * 256);
            sheet.setColumnWidth(7, 20 * 256);
            sheet.setColumnWidth(8, 20 * 256);
            sheet.setColumnWidth(9, 20 * 256);

            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("序号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("生产单号");
            cell1.setCellStyle(headerStyle);
           HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("客户名称");
            cell2.setCellStyle(headerStyle);
           /* HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("产品编码");
            cell3.setCellStyle(headerStyle);*/
            HSSFCell cell4 = headerRow.createCell(3);
            cell4.setCellValue("产品名称");
            cell4.setCellStyle(headerStyle);
            HSSFCell cell5 = headerRow.createCell(4);
            cell5.setCellValue("单位");
            cell5.setCellStyle(headerStyle);
            HSSFCell cell6 = headerRow.createCell(5);
            cell6.setCellValue("产品规格");
            cell6.setCellStyle(headerStyle);
            HSSFCell cell7 = headerRow.createCell(6);
            cell7.setCellValue("工单号");
            cell7.setCellStyle(headerStyle);
           HSSFCell cell8 = headerRow.createCell(7);
            cell8.setCellValue("交货日期");
            cell8.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(8);
            cell3.setCellValue("派工日期");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell9 = headerRow.createCell(9);
            cell9.setCellValue("到料日期");
            cell9.setCellStyle(headerStyle);
            HSSFCell cell10 = headerRow.createCell(10);
            int tint = 10;
            cell10.setCellValue("跟踪人");
            cell10.setCellStyle(headerStyle);
            for(int i=0; i<max; i++){
                HSSFCell cellt = headerRow.createCell(tint+i*3+1);
                cellt.setCellValue("工序"+(i+1));
                cellt.setCellStyle(headerStyle);
                HSSFCell cellt1 = headerRow.createCell(tint+i*3+2);
                cellt1.setCellValue("计划数");
                cellt1.setCellStyle(headerStyle);
                HSSFCell cellt2 = headerRow.createCell(tint+i*3+3);
                cellt2.setCellValue("完工数");
                cellt2.setCellStyle(headerStyle);
            }
            HSSFCell cell11 = headerRow.createCell(max*3+tint+1);
            cell11.setCellValue("总加工费");
            cell11.setCellStyle(headerStyle);
            HSSFCell cell12 = headerRow.createCell(max*3+tint+2);
            cell12.setCellValue("总积分");
            cell12.setCellStyle(headerStyle);
            //6.装数据
            java.util.Iterator it1 = maps.iterator();
            int i=0;
            while(it1.hasNext()){
                HSSFRow row = sheet.createRow(i + 1);
                Map map = (Map) it1.next();
                row.createCell(0).setCellValue(""+(i+1));
                row.createCell(1).setCellValue(MapUtils.getStringValue(map,"orderSn"));
                row.createCell(2).setCellValue(MapUtils.getStringValue(map,"clientName"));
                row.createCell(3).setCellValue(MapUtils.getStringValue(map,"wareName"));
                row.createCell(4).setCellValue(MapUtils.getStringValue(map,"unitName"));
                row.createCell(5).setCellValue(MapUtils.getStringValue(map,"wareModel"));
                row.createCell(6).setCellValue(MapUtils.getStringValue(map,"orderCode"));
                row.createCell(7).setCellValue(MapUtils.getStringValue(map,"orderDate"));
                //row.createCell(8).setCellValue(MapUtils.getStringValue(map,"orderDate"));
                row.createCell(8).setCellValue(MapUtils.getStringValue(map,"pgDate"));
                row.createCell(9).setCellValue(MapUtils.getStringValue(map,"daoliaoDate"));
                row.createCell(10).setCellValue(MapUtils.getStringValue(map,"gzManName"));
                List list = (List) map.get("gx");
                int tint2 = 10;
                int maxj = list.size();
                double Zmoney = 0d;
                double Zjf = 0d;
                for(int j=0; j<maxj; j++){
                    Map tmap = (Map) list.get(j);
                    double price = MapUtils.getDoubleValue(tmap,"real_s_other1");
                    double jf = MapUtils.getDoubleValue(tmap,"real_s_other2");
                    row.createCell(tint2+j*3+1).setCellValue(MapUtils.getStringValue(tmap,"gxName"));
                    double wgNum = MapUtils.getDoubleValue(tmap,"wgNum");
                    double ddNum = MapUtils.getDoubleValue(tmap,"ddNum");
                    double money = price* wgNum;
                    double sumJf = jf* wgNum;
                    Zmoney+= money;
                    Zjf += sumJf;
                    row.createCell(tint2+j*3+2).setCellValue(ddNum);
                    row.createCell(tint2+j*3+3).setCellValue(wgNum);
                }
                row.createCell(max*3+tint2+1).setCellValue(Zjf);
                row.createCell(max*3+tint2+2).setCellValue(Zmoney);
                i++;

            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", new String("生产进度表.xls".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static double getDoubleFromString(String s){
        if(s==null) return  0d;
        if("".equals(s)) return  0d;
        return  Double.valueOf(s);
    }



}
