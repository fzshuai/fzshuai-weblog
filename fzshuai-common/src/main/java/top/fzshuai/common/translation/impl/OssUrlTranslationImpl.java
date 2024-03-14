package top.fzshuai.common.translation.impl;

import top.fzshuai.common.annotation.TranslationType;
import top.fzshuai.common.constant.TransConstant;
import top.fzshuai.common.core.service.OssService;
import top.fzshuai.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * OSS翻译实现
 *
 * @author Lion Li
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
public class OssUrlTranslationImpl implements TranslationInterface<String> {

    private final OssService ossService;

    @Override
    public String translation(Object key, String other) {
        return ossService.selectUrlByIds(key.toString());
    }
}
