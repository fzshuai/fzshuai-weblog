package top.fzshuai.common.translation.impl;

import top.fzshuai.common.annotation.TranslationType;
import top.fzshuai.common.constant.TransConstant;
import top.fzshuai.common.core.service.DeptService;
import top.fzshuai.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 部门翻译实现
 *
 * @author Lion Li
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DEPT_ID_TO_NAME)
public class DeptNameTranslationImpl implements TranslationInterface<String> {

    private final DeptService deptService;

    @Override
    public String translation(Object key, String other) {
        return deptService.selectDeptNameByIds(key.toString());
    }
}
