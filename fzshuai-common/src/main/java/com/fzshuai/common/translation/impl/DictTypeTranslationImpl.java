package com.fzshuai.common.translation.impl;

import com.fzshuai.common.annotation.TranslationType;
import com.fzshuai.common.constant.TransConstant;
import com.fzshuai.common.core.service.DictService;
import com.fzshuai.common.translation.TranslationInterface;
import com.fzshuai.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 字典翻译实现
 *
 * @author Lion Li
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DICT_TYPE_TO_LABEL)
public class DictTypeTranslationImpl implements TranslationInterface<String> {

    private final DictService dictService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String && StringUtils.isNotBlank(other)) {
            return dictService.getDictLabel(other, key.toString());
        }
        return null;
    }
}
