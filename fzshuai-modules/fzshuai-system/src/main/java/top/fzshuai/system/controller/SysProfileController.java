package top.fzshuai.system.controller;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.io.FileUtil;
import top.fzshuai.common.annotation.Log;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.R;
import top.fzshuai.common.core.domain.entity.SysUser;
import top.fzshuai.common.enums.BusinessType;
import top.fzshuai.common.helper.LoginHelper;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.utils.file.MimeTypeUtils;
import top.fzshuai.system.domain.vo.SysOssVo;
import top.fzshuai.system.service.ISysOssService;
import top.fzshuai.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息 业务处理
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {

    private final ISysUserService userService;
    private final ISysOssService iSysOssService;

    /**
     * 个人信息
     */
    @GetMapping
    public R<Map<String, Object>> profile() {
        SysUser user = userService.queryUserById(getUserId());
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("user", user);
        ajax.put("roleGroup", userService.queryUserRoleGroup(user.getUserName()));
        ajax.put("postGroup", userService.queryUserPostGroup(user.getUserName()));
        return R.ok(ajax);
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> updateProfile(@RequestBody SysUser user) {
        if (StringUtils.isNotEmpty(user.getPhoneNumber()) && !userService.checkPhoneUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(getUserId());
        user.setUserName(null);
        user.setPassword(null);
        user.setAvatar(null);
        user.setDeptId(null);
        if (userService.updateUserProfile(user) > 0) {
            return R.ok();
        }
        return R.fail("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     *
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public R<Void> updatePwd(String oldPassword, String newPassword) {
        SysUser user = userService.queryUserById(LoginHelper.getUserId());
        String userName = user.getUserName();
        String password = user.getPassword();
        if (!BCrypt.checkpw(oldPassword, password)) {
            return R.fail("修改密码失败，旧密码错误");
        }
        if (BCrypt.checkpw(newPassword, password)) {
            return R.fail("新密码不能与旧密码相同");
        }

        if (userService.resetUserPwd(userName, BCrypt.hashpw(newPassword)) > 0) {
            return R.ok();
        }
        return R.fail("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     *
     * @param avatarfile 用户头像
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Map<String, Object>> avatar(@RequestPart("avatarfile") MultipartFile avatarfile) {
        Map<String, Object> ajax = new HashMap<>();
        if (!avatarfile.isEmpty()) {
            String extension = FileUtil.extName(avatarfile.getOriginalFilename());
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return R.fail("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
            SysOssVo oss = iSysOssService.upload(avatarfile);
            String avatar = oss.getUrl();
            if (userService.updateUserAvatar(getUsername(), avatar)) {
                ajax.put("imgUrl", avatar);
                return R.ok(ajax);
            }
        }
        return R.fail("上传图片异常，请联系管理员");
    }
}
