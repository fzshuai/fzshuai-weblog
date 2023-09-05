package com.fzshuai.system.service;

import com.fzshuai.common.core.domain.PageQuery;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.system.domain.bo.SysOssBO;
import com.fzshuai.system.domain.vo.SysOssVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 文件上传 服务层
 *
 * @author Lion Li
 */
public interface ISysOssService {

    TableDataInfo<SysOssVO> queryPageList(SysOssBO sysOss, PageQuery pageQuery);

    List<SysOssVO> listByIds(Collection<Long> ossIds);

    SysOssVO getById(Long ossId);

    SysOssVO upload(MultipartFile file);

    SysOssVO upload(File file);

    void download(Long ossId, HttpServletResponse response) throws IOException;

    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
