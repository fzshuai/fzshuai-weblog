package top.fzshuai.demo.service;

import top.fzshuai.demo.domain.bo.TestTreeBO;
import top.fzshuai.demo.domain.vo.TestTreeVO;

import java.util.Collection;
import java.util.List;

/**
 * 测试树表Service接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
public interface ITestTreeService {
    /**
     * 查询单个
     *
     * @return
     */
    TestTreeVO queryById(Long id);

    /**
     * 查询列表
     */
    List<TestTreeVO> queryList(TestTreeBO bo);

    /**
     * 根据新增业务对象插入测试树表
     *
     * @param bo 测试树表新增业务对象
     * @return
     */
    Boolean insertByBo(TestTreeBO bo);

    /**
     * 根据编辑业务对象修改测试树表
     *
     * @param bo 测试树表编辑业务对象
     * @return
     */
    Boolean updateByBo(TestTreeBO bo);

    /**
     * 校验并删除数据
     *
     * @param ids     主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
