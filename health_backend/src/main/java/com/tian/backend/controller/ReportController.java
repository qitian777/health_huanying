package com.tian.backend.controller;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 统计报表
 * @Author QiGuang
 * @Date 2022/7/17
 * @Version 1.0
 */
@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "http://health-provider-consumer/report";

    /**
     * 会员数量统计
     *
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        try {
            Map<String, Object> map = restTemplate.getForObject(url + "/getMemberReport", Map.class);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            log.error("ReportController.getMemberReport ERROR:" + e.toString());
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    /**
     * @Author QiGuang
     * @Description 套餐占比统计
     * @Param
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        Map<String, Object> map = restTemplate.getForObject(url + "/getSetmealReport", Map.class);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
    }

    /**
     * @Author QiGuang
     * @Description 获取运营统计数据
     * @Param
     */
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> result = restTemplate.getForObject(url + "/getBusinessReportData", Map.class);
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, result);
        } catch (Exception e) {
            log.error("ReportController.getBusinessReportData ERROR:" + e.toString());
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * @Author QiGuang
     * @Description 导出Excel报表
     * @Param
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //远程调用报表服务获取报表数据
            Map<String, Object> result = restTemplate.getForObject(url + "/getBusinessReportData", Map.class);

            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //获得Excel模板文件绝对路径
            Resource resource = new ClassPathResource("static/template/report_template.xlsx");
            String temlateRealPath = resource.getFile().getPath();
            //读取模板文件创建Excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map map : hotSetmeal) {//热门套餐
                String name = (String) map.get("name");
                int setmeal_count = (int) map.get("setmeal_count");
                Double proportion = (Double) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }
            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();

            return null;
        } catch (Exception e) {
            log.error("ReportController.exportBusinessReport ERROR:" + e.toString());
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
        }
    }

    /**
     * @Author QiGuang
     * @Description 导出运营数据到pdf并提供客户端下载
     * @Param
     */
    @RequestMapping("/exportBusinessReportPDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request, HttpServletResponse response) {
        try {
            //通过输出流进行文件下载
            response.setHeader("content-disposition", "attachment;filename=report.pdf");
            response.setContentType("application/octet-stream");
            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/pdf");
//            response.setHeader("content-Disposition", "attachment;filename=report.pdf");

            //获得Excel模板文件绝对路径
            Resource resource = new ClassPathResource("static/template/report01.pdf");
            String temlateRealPath = resource.getFile().getPath();
            // 读入pdf表单
            //读取模板
            PdfReader  reader = new PdfReader(temlateRealPath);
            PdfStamper pdfStamper = new PdfStamper(reader, out);
            // 给表单添加中文字体
            BaseFont bf = BaseFont.createFont("/static/font/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //获取模板文件中的表单域
            AcroFields form = pdfStamper.getAcroFields();
            form.addSubstitutionFont(bf);

            //远程调用报表服务获取报表数据
            Map<String, Object> result = restTemplate.getForObject(url + "/getBusinessReportData", Map.class);
            form.setField("reportDate", (String) result.get("reportDate"));
            form.setField("todayNewMember", String.valueOf((Integer) result.get("todayNewMember")));
            form.setField("totalMember", String.valueOf((Integer) result.get("totalMember")));
            form.setField("thisWeekNewMember", String.valueOf((Integer) result.get("thisWeekNewMember")));
            form.setField("thisMonthNewMember", String.valueOf((Integer) result.get("thisMonthNewMember")));
            form.setField("todayOrderNumber", String.valueOf((Integer) result.get("todayOrderNumber")));
            form.setField("thisWeekOrderNumber", String.valueOf((Integer) result.get("thisWeekOrderNumber")));
            form.setField("thisMonthOrderNumber", String.valueOf((Integer) result.get("thisMonthOrderNumber")));
            form.setField("todayVisitsNumber", String.valueOf((Integer) result.get("todayVisitsNumber")));
            form.setField("thisWeekVisitsNumber", String.valueOf((Integer) result.get("thisWeekVisitsNumber")));
            form.setField("thisMonthVisitsNumber", String.valueOf((Integer) result.get("thisMonthVisitsNumber")));
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
            for (int i = 1; i <= hotSetmeal.size(); i++) {
                form.setField("hotSetmeal" + i, (String) hotSetmeal.get(i - 1).get("name"));
                form.setField("setmealCount" + i, String.valueOf((Integer) hotSetmeal.get(i - 1).get("setmeal_count")));
                form.setField("per" + i, String.valueOf((Double) hotSetmeal.get(i - 1).get("proportion")));
            }
            pdfStamper.setFormFlattening(true);
            pdfStamper.close();
            out.flush();
            out.close();
            reader.close();
            return null;
        } catch (Exception e) {
            log.error("ReportController.exportBusinessReportPDF ERROR:" + e.toString());
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}
