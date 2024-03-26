package top.fzshuai.demo.mapper;

import top.fzshuai.common.annotation.DataColumn;
import top.fzshuai.common.annotation.DataPermission;
import top.fzshuai.common.core.mapper.BaseMapperPlus;
import top.fzshuai.demo.domain.TestTree;
import top.fzshuai.demo.domain.vo.TestTreeVO;

/**
 * 测试树表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTreeMapper, TestTree, TestTreeVO> {

}
