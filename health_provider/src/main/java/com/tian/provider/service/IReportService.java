package com.tian.provider.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/17
 * @Version 1.0
 */
public interface IReportService {
    Map<String, Object> getBusinessReport() throws Exception;
}
