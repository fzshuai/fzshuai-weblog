package top.fzshuai.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 搜索类型枚举
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Getter
@AllArgsConstructor
public enum SearchModeEnum {

    /**
     * mysql
     */
    MYSQL("mysql", "MySQL搜索策略"),

    /**
     * elasticsearch
     */
    ELASTICSEARCH("elasticsearch", "ES搜索策略");

    /**
     * 模式
     */
    private final String mode;

    /**
     * 策略
     */
    private final String strategy;

}
