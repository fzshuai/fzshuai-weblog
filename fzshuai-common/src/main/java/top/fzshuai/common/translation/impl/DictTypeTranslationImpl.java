package top.fzshuai.common.translation.impl;

import top.fzshuai.common.annotation.TranslationType;
import top.fzshuai.common.constant.TransConstant;
import top.fzshuai.common.core.service.DictService;
import top.fzshuai.common.translation.TranslationInterface;
import top.fzshuai.common.utils.StringUtils;
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
