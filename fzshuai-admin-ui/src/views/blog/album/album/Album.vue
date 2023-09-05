<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title" style="font-size: 16px; font-weight: bold">
      {{ this.$route.meta.title }}
    </div>
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openModel(null)"
      >
        新建相册
      </el-button>
      <div style="margin-left: auto">
        <el-button
          type="text"
          size="small"
          icon="el-icon-delete"
          style="margin-right: 1rem"
          @click="checkDelete"
        >
          回收站
        </el-button>
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入相册名"
          style="width: 200px"
          @keyup.enter.native="searchAlbums"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="searchAlbums"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 相册列表 -->
    <el-row class="album-container" :gutter="12" v-loading="loading">
      <!-- 空状态 -->
      <el-empty v-if="albumList == null" description="暂无相册" />
      <el-col v-for="item of albumList" :key="item.albumId" :md="6">
        <div class="album-item" @click="checkPhoto(item)">
          <!-- 相册操作 -->
          <div class="album-opreation">
            <el-dropdown @command="handleCommand">
              <i class="el-icon-more" style="color: #fff" />
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="'update' + JSON.stringify(item)">
                  <i class="el-icon-edit" />编辑
                </el-dropdown-item>

                <el-dropdown-item :command="'delete' + item.albumId">
                  <i class="el-icon-delete" />删除
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div class="album-photo-count">
            <div>{{ item.photoCount }}</div>
            <i v-if="item.status == 2" class="iconfont el-icon-mymima" />
          </div>
          <el-image fit="cover" class="album-cover" :src="item.albumCover" />
          <div class="album-name">{{ item.albumName }}</div>
        </div>
      </el-col>
    </el-row>
    <!-- 分页 -->
    <el-pagination
      :hide-on-single-page="true"
      class="pagination-container"
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      layout="prev, pager, next"
    />
    <!-- 新增模态框 -->
    <el-dialog :visible.sync="addOrEdit" width="35%" top="10vh">
      <div class="dialog-title-container" slot="title" ref="albumTitle" />
      <el-form label-width="80px" size="medium" :model="albumForum">
        <el-form-item label="相册名称">
          <el-input style="width: 220px" v-model="albumForum.albumName" />
        </el-form-item>
        <el-form-item label="相册描述">
          <el-input style="width: 220px" v-model="albumForum.albumDesc" />
        </el-form-item>
        <el-form-item label="相册封面">
          <el-upload
            :headers="headers"
            class="upload-cover"
            drag
            :show-file-list="false"
            :action="uploadImgUrl"
            multiple
            :on-success="uploadCover"
            :on-remove="handleDelete"
          >
            <i class="el-icon-upload" v-if="albumForum.albumCover == ''" />
            <div class="el-upload__text" v-if="albumForum.albumCover == ''">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <img
              v-else
              :src="albumForum.albumCover"
              width="360px"
              height="180px"
            />
          </el-upload>
        </el-form-item>
        <el-form-item label="发布形式">
          <el-radio-group v-model="albumForum.status">
            <el-radio :label="1">公开</el-radio>
            <el-radio :label="2">私密</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditAlbum(albumForum.albumId)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 删除对话框 -->
    <el-dialog :visible.sync="isdelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color: #ff9900" />提示
      </div>
      <div style="font-size: 1rem">是否删除该相册？</div>
      <div slot="footer">
        <el-button @click="isdelete = false">取 消</el-button>
        <el-button type="primary" @click="handleDelete"> 确 定 </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import {
  listAlbum,
  getAlbum,
  delAlbum,
  addAlbum,
  updateAlbum,
} from "@/api/blog/album";
import { getToken } from "@/utils/auth";
export default {
  created() {
    this.getList();
  },
  data: function () {
    return {
      // 按钮loading
      buttonLoading: false,
      headers: { Authorization: "Bearer " + getToken() },
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/system/oss/upload", // 上传的图片服务器地址,
      keywords: "",
      loading: true,
      isdelete: false,
      addOrEdit: false,
      // 是否显示弹出层
      open: false,
      albumForum: {
        id: null,
        albumName: "",
        albumDesc: "",
        albumCover: "",
        status: 1,
      },
      albumList: [],
      current: 1,
      size: 8,
      count: 0,
      // 表单校验
      rules: {
        id: [{ required: true, message: "主键不能为空", trigger: "blur" }],
        albumName: [
          { required: true, message: "相册名不能为空", trigger: "blur" },
        ],
        albumDesc: [
          { required: true, message: "相册描述不能为空", trigger: "blur" },
        ],
        albumCover: [
          { required: true, message: "相册封面不能为空", trigger: "blur" },
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    openModel(item) {
      if (item) {
        this.albumForum = JSON.parse(item);
        this.$refs.albumTitle.innerHTML = "修改相册";
      } else {
        this.albumForum = {
          id: null,
          albumName: "",
          albumLabel: "",
          albumCover: "",
          status: 1,
        };
        this.$refs.albumTitle.innerHTML = "新建相册";
      }
      this.addOrEdit = true;
    },
    checkPhoto(item) {
      this.$router.push({
        path: "/album/photo",
        query: { albumId: item.albumId },
      });
    },
    checkDelete() {
      this.$router.push({ path: "/photos/delete" });
    },
    /** 查询相册管理列表 */
    getList() {
      this.loading = true;
      listAlbum(this.queryParams).then((response) => {
        this.albumList = response.rows;
        this.count = response.total;
        this.loading = false;
      });
    },
    addOrEditAlbum(id) {
      if (this.albumForum.albumName.trim() == "") {
        this.$modal.msgError("相册名称不能为空");
        return false;
      }
      if (this.albumForum.albumDesc.trim() == "") {
        this.$modal.msgError("相册描述不能为空");
        return false;
      }
      if (this.albumForum.albumCover == null) {
        this.$modal.msgError("相册封面不能为空");
        return false;
      }
      if (id != null) {
        updateAlbum(this.albumForum)
          .then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          })
          .finally(() => {
            this.addOrEdit = false;
          });
      } else {
        addAlbum(this.albumForum)
          .then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          })
          .finally(() => {
            this.addOrEdit = false;
          });
      }
      this.addOrEdit = false;
    },
    uploadCover(response) {
      this.albumForum.albumCover = response.data.url;
    },
    handleCommand(command) {
      const type = command.substring(0, 6);
      const data = command.substring(6);
      if (type == "delete") {
        this.handleDelete(data);
      } else {
        console.log("编辑按钮的修改或者新增");
        this.openModel(data);
      }
    },
    /** 删除按钮操作 */
    handleDelete(data) {
      const ids = data;
      this.$modal
        .confirm('是否确认删除相册管理编号为"' + ids + '"的数据项？')
        .then(() => {
          this.loading = true;
          return delAlbum(ids);
        })
        .then(() => {
          this.loading = false;
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {})
        .finally(() => {
          this.loading = false;
        });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    searchAlbums() {
      this.current = 1;
      this.getList();
    },
    sizeChange(size) {
      this.size = size;
      this.getList();
    },
    currentChange(current) {
      this.current = current;
      this.getList();
    },
  },
};
</script>

<style scoped>
.album-cover {
  position: relative;
  border-radius: 4px;
  width: 100%;
  height: 170px;
}
.album-cover::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
}
.album-photo-count {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 1.5rem;
  z-index: 1000;
  position: absolute;
  left: 0;
  right: 0;
  padding: 0 0.5rem;
  bottom: 2.6rem;
  color: #fff;
}
.album-name {
  text-align: center;
  margin-top: 0.5rem;
}
.album-item {
  position: relative;
  cursor: pointer;
  margin-bottom: 1rem;
}
.album-opreation {
  position: absolute;
  z-index: 1000;
  top: 0.5rem;
  right: 0.8rem;
}
.operation-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
  margin-top: 2.25rem;
}
</style>
