package top.fzshuai.system.service;

import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.domain.entity.SysDictData;
import top.fzshuai.common.core.page.TableDataInfo;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author Lion Li fzshuai
 */
public interface ISysDictDataService {

    TableDataInfo<SysDictData> queryPageDictDataList(SysDictData dictData, PageQuery pageQuery);

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    List<SysDictData> queryDictDataList(SysDictData dictData);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    String queryDictLabel(String dictType, String dictValue);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    SysDictData queryDictDataById(Long dictCode);

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    void deleteDictDataByIds(Long[] dictCodes);

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    List<SysDictData> insertDictData(SysDictData dictData);

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    List<SysDictData> updateDictData(SysDictData dictData);

}
