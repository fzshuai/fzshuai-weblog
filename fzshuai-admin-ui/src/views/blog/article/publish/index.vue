<template>
  <div class="app-container">

    <el-card class="main-card">
      <div class="title" style="  font-size: 16px;font-weight: bold;">{{ this.$route.meta.title }}</div>
      <!-- 文章标题 -->
      <div class="article-title-container">
        <el-input v-model="form.articleTitle" size="medium" placeholder="输入文章标题"/>
        <!--        <el-button type="danger" size="medium" class="save-btn" @click="saveArticleDraft"-->
        <!--                   v-if="form.id == null || form.status == 3">-->
        <!--          保存草稿-->
        <!--        </el-button>-->
        <el-button type="danger" size="medium" @click="handleUpdate" style="margin-left:10px">
          发布文章
        </el-button>
      </div>
      <!-- 文章内容 -->
      <v-md-editor v-model="form.articleContent"
                   class="ed"
                   placeholder="请输入内容"
                   :disabled-menus="[]"
                   @upload-image="handleUploadImage"
      >

      </v-md-editor>

      <!-- <mavon-editor ref="md" v-model="article.articleContent" @imgAdd="uploadImg" style="height:calc(100vh - 260px)" navigation/> -->
      <!-- 添加或修改文章列表对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>

        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <!-- 文章分类 -->
          <el-form-item label="文章分类">
            <el-tag type="success" v-show="form.categoryName" style="margin:0 1rem 0 0" :closable="true"
                    @close="removeCategory">
              {{ form.categoryName }}
            </el-tag>
            <!-- 分类选项 -->
            <el-popover placement="bottom-start" width="460" trigger="click" v-if="!form.categoryName">
              <div class="popover-title">分类</div>
              <!-- 搜索框 -->
              <el-autocomplete style="width:100%" v-model="categoryName" :fetch-suggestions="searchCategories"
                               placeholder="请输入分类名搜索，enter可添加自定义分类" :trigger-on-focus="false"
                               @keyup.enter.native="saveCategory"
                               @select="handleSelectCategories">
                <template slot-scope="{ item }">
                  <div>{{ item.categoryName }}</div>
                </template>
              </el-autocomplete>
              <!-- 分类 -->
              <div class="popover-container">
                <div v-for="item of categoryList" :key="item.categoryId" class="category-item" @click="addCategory(item)">
                  {{ item.categoryName }}
                </div>
              </div>
              <el-button type="success" plain slot="reference" size="small">
                添加分类
              </el-button>
            </el-popover>
          </el-form-item>

          <!-- 文章标签 -->
          <el-form-item label="文章标签">
            <el-tag v-for="(item, index) of form.tagNameList" :key="index" style="margin:0 1rem 0 0" :closable="true"
                    @close="removeTag(item)">
              {{ item }}
            </el-tag>
            <!-- 标签选项 -->
            <el-popover placement="bottom-start" width="460" trigger="click" v-if="form.tagNameList.length < 3">
              <div class="popover-title">标签</div>
              <!-- 搜索框 -->
              <el-autocomplete style="width:100%" v-model="tagName" :fetch-suggestions="searchTags"
                               placeholder="请输入标签名搜索，enter可添加自定义标签" :trigger-on-focus="false"
                               @keyup.enter.native="saveTag"
                               @select="handleSelectTag">
                <template slot-scope="{ item }">
                  <div>{{ item.tagName }}</div>
                </template>
              </el-autocomplete>
              <!-- 标签 -->
              <div class="popover-container">
                <div style="margin-bottom:1rem">添加标签</div>
                <el-tag v-for="(item, index) of tagList" :key="index" :class="tagClass(item)" @click="addTag(item)">
                  {{ item.tagName }}
                </el-tag>
              </div>
              <el-button type="primary" plain slot="reference" size="small">
                添加标签
              </el-button>
            </el-popover>
          </el-form-item>

          <el-form-item label="文章图片">
            <image-upload v-model="form.articleCover"/>
          </el-form-item>
          <el-form-item label="标题" prop="articleTitle">
            <el-input v-model="form.articleTitle" placeholder="请输入标题"/>
          </el-form-item>
          <el-form-item label="文章类型">
            <el-radio-group v-model="form.type">
              <el-radio
                v-for="dict in dict.type.article_type"
                :key="dict.value"
                :label="parseInt(dict.value)"
              >{{ dict.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="状态值">
            <el-radio-group v-model="form.status">
              <el-radio
                v-for="dict in dict.type.article_status"
                :key="dict.value"
                :label="parseInt(dict.value)"
              >{{ dict.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

    </el-card>

  </div>
</template>

<script>
import {listArticle, getArticle, delArticle, addArticle, updateArticle} from "@/api/blog/article";
import {listCategory} from "@/api/blog/category";
import {listTag} from "@/api/blog/tag";
import {uploadOssImage} from "@/api/blog/about";

export default {
  created() {
    this.findUser();
    const articleId = this.$route.query.articleId;
    if (articleId) {
      getArticle(articleId).then(response => {
        this.form = response.data;
      })
    } else {
      const article = sessionStorage.getItem("form")
      if (article) {
        this.form = JSON.parse(article)
      }
    }
    this.getList();
    this.searchCategories();
    this.searchTags();

  },
  name: "Publish",
  dicts: ['article_status', 'article_type', 'sys_yes_no'],
  data() {
    return {
      // 文章图片
      imgFile: [],
      // 当前登录用户名称
      username: "",
      // 当前修改的文章id
      articleId: this.$route.query.articleId,
      // 更新后的文章：
      newArticleContent: "",
      // 更新后的文章标题：
      newArticleTitle: "",
      // 文章分类
      categoryName: "",
      // 分类集合
      categoryList: [],
      // 标签名
      tagName: "",
      // 标签名集合
      tagList: [],
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
      // 文章列表表格数据
      articleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: undefined,
        categoryId: undefined,
        articleCover: undefined,
        articleTitle: undefined,
        articleContent: undefined,
        type: undefined,
        originalUrl: undefined,
        isTop: undefined,
        isDelete: undefined,
        status: undefined,
        likeCount: undefined,
        viewsCount: undefined,
      },
      // 表单参数
      form: {
        articleId: null,
        userId: null,
        username: "",
        articleTitle: "",
        categoryId: null,
        tagNameList: [],
        categoryName: "",
        articleContent: "",
        type: undefined,
        originalUrl: null,
        isTop: null,
        isDelete: null,
        status: undefined,
        likeCount: null,
        viewsCount: null,
        createBy: ""
      },
      // 表单校验
      rules: {
        articleId: [
          {required: true, message: "不能为空", trigger: "blur"}
        ],
        userId: [
          {required: true, message: "作者不能为空", trigger: "blur"}
        ],
        categoryId: [
          {required: true, message: "文章分类不能为空", trigger: "blur"}
        ],
        articleCover: [
          {required: true, message: "文章缩略图不能为空", trigger: "blur"}
        ],
        articleTitle: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        articleContent: [
          {required: true, message: "内容不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "文章类型 1原创 2转载 3翻译不能为空", trigger: "blur"}
        ],
        isTop: [
          {required: true, message: "是否置顶 0否 1是不能为空", trigger: "blur"}
        ],
        isDelete: [
          {required: true, message: "是否删除 0否 1是不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态值 1公开 2私密 3评论可见不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "发表时间不能为空", trigger: "blur"}
        ],
      }
    };
  },

  methods: {
    // 查询文章列表列表
    getList() {
      this.loading = true;
      listArticle(this.queryParams).then(response => {
        this.articleList = response.rows;
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
        articleId: undefined,
        userId: undefined,
        categoryId: undefined,
        tagNameList: [],
        categoryName: "",
        articleCover: undefined,
        articleTitle: undefined,
        articleContent: undefined,
        type: undefined,
        originalUrl: undefined,
        isTop: 0,
        isDelete: 0,
        status: undefined,
        createTime: undefined,
        updateTime: undefined,
        likeCount: undefined,
        viewsCount: undefined,
        createBy: undefined,
        updateBy: undefined
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
      this.ids = selection.map(item => item.articleId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文章列表";
    },
    /** 新增或者修改按钮操作 */
    handleUpdate() {

      if (this.form.articleTitle.trim() == "") {
        this.$message.error("文章标题不能为空");
        return false;
      }
      if (this.form.articleContent.trim() == "") {
        this.$message.error("文章内容不能为空");
        return false;
      }

      if (this.articleId == null) {
        /** 这里代表是新增操作 */
        this.newArticleTitle = this.form.articleTitle;
        this.newArticleContent = this.form.articleContent;
        this.handleAdd();
        this.form.articleContent = this.newArticleContent;
        this.form.articleTitle = this.newArticleTitle;
        this.form.createBy = this.username

      } else {
        this.newArticleContent = this.form.articleContent;
        this.newArticleTitle = this.form.articleTitle;
        this.loading = true;
        this.reset();
        getArticle(this.articleId).then(response => {
          this.loading = false;
          this.form = response.data;
          this.form.articleContent = this.newArticleContent;
          this.form.articleTitle = this.newArticleTitle;
          this.open = true;
          this.title = "修改文章列表";
        });
      }
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.articleId != null) {
            updateArticle(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            console.log(JSON.stringify(this.form))
            addArticle(this.form).then(response => {
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
      const ids = row.articleId || this.ids;
      this.$modal.confirm('是否确认删除文章列表编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delArticle(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 导出按钮操作
    handleExport() {
      this.download('article/article/export', {
        ...this.queryParams
      }, `article_${new Date().getTime()}.xlsx`)
    },

    // 文章分类操作
    removeCategory() {
      this.form.categoryName = null;
    },
    searchCategories() {
      listCategory(this.queryParams).then(response => {
        this.categoryList = response.rows;
      });
    },
    saveCategory() {
      if (this.categoryName.trim() != "") {
        this.addCategory({
          categoryName: this.categoryName,
        });
        this.categoryName = "";
      }
    },
    addCategory(item) {
      this.form.categoryName = item.categoryName;
    },
    handleSelectCategories(item) {
      this.addCategory({
        categoryName: item.categoryName,
      });
    },

    removeTag(item) {
      const index = this.form.tagNameList.indexOf(item);
      this.form.tagNameList.splice(index, 1);
    },
    searchTags() {
      listTag(this.queryParams).then(response => {
        this.tagList = response.rows;
      })
    },
    saveTag() {
      if (this.tagName.trim() != "") {
        this.addTag({
          tagName: this.tagName,
        });
        this.tagName = "";
      }
    },
    handleSelectTag(item) {
      this.addTag({
        tagName: item.tagName,
      });
    },
    addTag(item) {
      if (this.form.tagNameList.indexOf(item.tagName) == -1) {
        this.form.tagNameList.push(item.tagName);
      }
    },

    handleUploadImage(event, insertImage, files) {
      for (let i in files) {
        let formData = new FormData();
        formData.append('file', files[i]);
        uploadOssImage(formData).then(response => {
            this.$modal.msgSuccess("上传成功");
            insertImage({
              url: response.data.url,
              desc: response.data.fileName,
            })
          },
        )
      }
    },
    // 获取cookie中的作者名称
    findUser() {
      const strCookie = document.cookie;
      const arrCookie = strCookie.split("; ");
      let username;
      for (let i = 0; i < arrCookie.length; i++) {
        const arr = arrCookie[i].split("=");
        if ("username" == arr[0]) {
          username = arr[1];
          break;
        }
      }
      this.username = username;
    },
  },
  computed: {
    tagClass() {
      return function (item) {
        const index = this.form.tagNameList.indexOf(item.tagName);
        return index != -1 ? "tag-item-select" : "tag-item";
      };
    },
  },
};
</script>

<style scoped>
.ed {
  height: calc(100vh - 260px);
}

.article-title-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
  margin-top: 2.25rem;
}

.save-btn {
  margin-left: 0.75rem;
  background: #fff;
  color: #f56c6c;
}

.tag-item {
  margin-right: 1rem;
  margin-bottom: 1rem;
  cursor: pointer;
}

.tag-item-select {
  margin-right: 1rem;
  margin-bottom: 1rem;
  cursor: not-allowed;
  color: #ccccd8 !important;
}

.category-item {
  cursor: pointer;
  padding: 0.6rem 0.5rem;
}

.category-item:hover {
  background-color: #f0f9eb;
  color: #67c23a;
}

.popover-title {
  margin-bottom: 1rem;
  text-align: center;
}

.popover-container {
  margin-top: 1rem;
  height: 260px;
  overflow-y: auto;
}
</style>
