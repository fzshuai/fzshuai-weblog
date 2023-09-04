<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="链接名" prop="linkName">
        <el-input
          v-model="queryParams.linkName"
          placeholder="请输入链接名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="链接头像" prop="linkAvatar">
        <el-input
          v-model="queryParams.linkAvatar"
          placeholder="请输入链接头像"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="链接地址" prop="linkAddress">
        <el-input
          v-model="queryParams.linkAddress"
          placeholder="请输入链接地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="链接介绍" prop="linkIntro">
        <el-input
          v-model="queryParams.linkIntro"
          placeholder="请输入链接介绍"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['blog:friendLink:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['blog:friendLink:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['blog:friendLink:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['blog:friendLink:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="friendLinkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="friendLinkId" v-if="true"/>
      <el-table-column label="链接名" align="center" prop="linkName" />
      <el-table-column label="链接头像" align="center" prop="linkAvatar" />
      <el-table-column label="链接地址" align="center" prop="linkAddress" />
      <el-table-column label="链接介绍" align="center" prop="linkIntro" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['blog:friendLink:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['blog:friendLink:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改友人链接对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="链接名" prop="linkName">
          <el-input v-model="form.linkName" placeholder="请输入链接名" />
        </el-form-item>
        <el-form-item label="链接头像" prop="linkAvatar">
          <el-input v-model="form.linkAvatar" placeholder="请输入链接头像" />
        </el-form-item>
        <el-form-item label="链接地址" prop="linkAddress">
          <el-input v-model="form.linkAddress" placeholder="请输入链接地址" />
        </el-form-item>
        <el-form-item label="链接介绍" prop="linkIntro">
          <el-input v-model="form.linkIntro" placeholder="请输入链接介绍" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFriendLink, getFriendLink, delFriendLink, addFriendLink, updateFriendLink } from "@/api/blog/friendLink";

export default {
  name: "FriendLink",
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 友人链接表格数据
      friendLinkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        linkName: undefined,
        linkAvatar: undefined,
        linkAddress: undefined,
        linkIntro: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        friendLinkId: [
          { required: true, message: "主键不能为空", trigger: "blur" }
        ],
        linkName: [
          { required: true, message: "链接名不能为空", trigger: "blur" }
        ],
        linkAvatar: [
          { required: true, message: "链接头像不能为空", trigger: "blur" }
        ],
        linkAddress: [
          { required: true, message: "链接地址不能为空", trigger: "blur" }
        ],
        linkIntro: [
          { required: true, message: "链接介绍不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询友人链接列表 */
    getList() {
      this.loading = true;
      listFriendLink(this.queryParams).then(response => {
        this.friendLinkList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        friendLinkId: undefined,
        linkName: undefined,
        linkAvatar: undefined,
        linkAddress: undefined,
        linkIntro: undefined,
        createTime: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.friendLinkId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加友人链接";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const friendLinkId = row.friendLinkId || this.ids
      getFriendLink(friendLinkId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改友人链接";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.friendLinkId != null) {
            updateFriendLink(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addFriendLink(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const friendLinkIds = row.friendLinkId || this.ids;
      this.$modal.confirm('是否确认删除友人链接编号为"' + friendLinkIds + '"的数据项？').then(() => {
        this.loading = true;
        return delFriendLink(friendLinkIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('blog/friendLink/export', {
        ...this.queryParams
      }, `friendLink_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
