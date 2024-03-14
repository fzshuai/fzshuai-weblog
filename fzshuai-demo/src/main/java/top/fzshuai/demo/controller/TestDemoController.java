package top.fzshuai.demo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.fzshuai.common.annotation.Log;
import top.fzshuai.common.annotation.RepeatSubmit;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.domain.R;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import top.fzshuai.common.core.validate.QueryGroup;
import top.fzshuai.common.enums.BusinessType;
import top.fzshuai.common.excel.ExcelResult;
import top.fzshuai.common.utils.ValidatorUtils;
import top.fzshuai.common.utils.poi.ExcelUtil;
import top.fzshuai.demo.domain.TestDemo;
import top.fzshuai.demo.domain.bo.TestDemoBO;
import top.fzshuai.demo.domain.bo.TestDemoImportVO;
import top.fzshuai.demo.domain.vo.TestDemoVO;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试单表Controller
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/demo")
public class TestDemoController extends BaseController {

    private final top.fzshuai.demo.service.ITestDemoService iTestDemoService;

    /**
     * 查询测试单表列表
     */
    @SaCheckPermission("demo:demo:list")
    @GetMapping("/list")
    public TableDataInfo<TestDemoVO> list(@Validated(QueryGroup.class) TestDemoBO bo, PageQuery pageQuery) {
        return iTestDemoService.queryPageList(bo, pageQuery);
    }

    /**
     * 自定义分页查询
     */
    @SaCheckPermission("demo:demo:list")
    @GetMapping("/page")
    public TableDataInfo<TestDemoVO> page(@Validated(QueryGroup.class) TestDemoBO bo, PageQuery pageQuery) {
        return iTestDemoService.customPageList(bo, pageQuery);
    }

    /**
     * 导入数据
     *
     * @param file 导入文件
     */
    @Log(title = "测试单表", businessType = BusinessType.IMPORT)
    @SaCheckPermission("demo:demo:import")
    @PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Void> importData(@RequestPart("file") MultipartFile file) throws Exception {
        ExcelResult<TestDemoImportVO> excelResult = ExcelUtil.importExcel(file.getInputStream(), TestDemoImportVO.class, true);
        List<TestDemoImportVO> volist = excelResult.getList();
        List<TestDemo> list = BeanUtil.copyToList(volist, TestDemo.class);
        iTestDemoService.saveBatch(list);
        return R.ok(excelResult.getAnalysis());
    }

    /**
     * 导出测试单表列表
     */
    @SaCheckPermission("demo:demo:export")
    @Log(title = "测试单表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated TestDemoBO bo, HttpServletResponse response) {
        List<TestDemoVO> list = iTestDemoService.queryList(bo);
        // 测试雪花id导出
//        for (TestDemoVO vo : list) {
//            vo.setId(1234567891234567893L);
//        }
        ExcelUtil.exportExcel(list, "测试单表", TestDemoVO.class, response);
    }

    /**
     * 获取测试单表详细信息
     *
     * @param id 测试ID
     */
    @SaCheckPermission("demo:demo:query")
    @GetMapping("/{id}")
    public R<TestDemoVO> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable("id") Long id) {
        return R.ok(iTestDemoService.queryById(id));
    }

    /**
     * 新增测试单表
     */
    @SaCheckPermission("demo:demo:add")
    @Log(title = "测试单表", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 2, timeUnit = TimeUnit.SECONDS, message = "{repeat.submit.message}")
    @PostMapping()
    public R<Void> add(@RequestBody TestDemoBO bo) {
        // 使用校验工具对标 @Validated(AddGroup.class) 注解
        // 用于在非 Controller 的地方校验对象
        ValidatorUtils.validate(bo, AddGroup.class);
        return toAjax(iTestDemoService.insertByBo(bo));
    }

    /**
     * 修改测试单表
     */
    @SaCheckPermission("demo:demo:edit")
    @Log(title = "测试单表", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TestDemoBO bo) {
        return toAjax(iTestDemoService.updateByBo(bo));
    }

    /**
     * 删除测试单表
     *
     * @param ids 测试ID串
     */
    @SaCheckPermission("demo:demo:remove")
    @Log(title = "测试单表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iTestDemoService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
