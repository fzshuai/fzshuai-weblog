<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="评论用户Id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入评论用户Id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论主题id" prop="topicId">
        <el-input
          v-model="queryParams.topicId"
          placeholder="请输入评论主题id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回复用户id" prop="replyUserId">
        <el-input
          v-model="queryParams.replyUserId"
          placeholder="请输入回复用户id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父评论id" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父评论id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除  0否 1是" prop="isDelete">
        <el-input
          v-model="queryParams.isDelete"
          placeholder="请输入是否删除  0否 1是"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否审核" prop="isReview">
        <el-input
          v-model="queryParams.isReview"
          placeholder="请输入是否审核"
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
          v-hasPermi="['blog:comment:add']"
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
          v-hasPermi="['blog:comment:edit']"
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
          v-hasPermi="['blog:comment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['blog:comment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="commentId" v-if="true"/>
      <el-table-column label="评论用户Id" align="center" prop="userId" />
      <el-table-column label="评论主题id" align="center" prop="topicId" />
      <el-table-column label="评论内容" align="center" prop="commentContent" />
      <el-table-column label="回复用户id" align="center" prop="replyUserId" />
      <el-table-column label="父评论id" align="center" prop="parentId" />
      <el-table-column label="评论类型 1.文章 2.友链 3.说说" align="center" prop="type" />
      <el-table-column label="是否删除  0否 1是" align="center" prop="isDelete" />
      <el-table-column label="是否审核" align="center" prop="isReview" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['blog:comment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['blog:comment:remove']"
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

    <!-- 添加或修改文章评论对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评论用户Id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入评论用户Id" />
        </el-form-item>
        <el-form-item label="评论主题id" prop="topicId">
          <el-input v-model="form.topicId" placeholder="请输入评论主题id" />
        </el-form-item>
        <el-form-item label="评论内容">
          <editor v-model="form.commentContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="回复用户id" prop="replyUserId">
          <el-input v-model="form.replyUserId" placeholder="请输入回复用户id" />
        </el-form-item>
        <el-form-item label="父评论id" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父评论id" />
        </el-form-item>
        <el-form-item label="是否删除  0否 1是" prop="isDelete">
          <el-input v-model="form.isDelete" placeholder="请输入是否删除  0否 1是" />
        </el-form-item>
        <el-form-item label="是否审核" prop="isReview">
          <el-input v-model="form.isReview" placeholder="请输入是否审核" />
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
import { listComment, getComment, delComment, addComment, updateComment } from "@/api/blog/comment";

export default {
  name: "Comment",
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
      // 文章评论表格数据
      commentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: undefined,
        topicId: undefined,
        commentContent: undefined,
        replyUserId: undefined,
        parentId: undefined,
        type: undefined,
        isDelete: undefined,
        isReview: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        commentId: [
          { required: true, message: "主键不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "评论用户Id不能为空", trigger: "blur" }
        ],
        topicId: [
          { required: true, message: "评论主题id不能为空", trigger: "blur" }
        ],
        commentContent: [
          { required: true, message: "评论内容不能为空", trigger: "blur" }
        ],
        replyUserId: [
          { required: true, message: "回复用户id不能为空", trigger: "blur" }
        ],
        parentId: [
          { required: true, message: "父评论id不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "评论类型 1.文章 2.友链 3.说说不能为空", trigger: "change" }
        ],
        isDelete: [
          { required: true, message: "是否删除  0否 1是不能为空", trigger: "blur" }
        ],
        isReview: [
          { required: true, message: "是否审核不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "评论时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询文章评论列表 */
    getList() {
      this.loading = true;
      listComment(this.queryParams).then(response => {
        this.commentList = response.rows;
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
        commentId: undefined,
        userId: undefined,
        topicId: undefined,
        commentContent: undefined,
        replyUserId: undefined,
        parentId: undefined,
        type: undefined,
        isDelete: undefined,
        isReview: undefined,
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
      this.ids = selection.map(item => item.commentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文章评论";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const commentId = row.commentId || this.ids
      getComment(commentId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改文章评论";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.commentId != null) {
            updateComment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addComment(this.form).then(response => {
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
      const commentIds = row.commentId || this.ids;
      this.$modal.confirm('是否确认删除文章评论编号为"' + commentIds + '"的数据项？').then(() => {
        this.loading = true;
        return delComment(commentIds);
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
      this.download('blog/comment/export', {
        ...this.queryParams
      }, `comment_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
