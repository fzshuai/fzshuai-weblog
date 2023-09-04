<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title" style="  font-size: 16px;font-weight: bold;">{{ this.$route.meta.title }}</div>
    <!-- 相册信息 -->
    <div class="album-info">
      <el-image fit="cover" class="album-cover" :src="albumInfo.albumCover" />
      <div class="album-detail">
        <div style="margin-bottom:0.6rem">
          <span class="album-name">{{ albumInfo.albumName }}</span>
          <span class="photo-count">{{ albumInfo.photoCount }}张</span>
        </div>
        <div>
          <span v-if="albumInfo.albumDesc" class="album-desc">
            {{ albumInfo.albumDesc }}
          </span>
          <el-button
            icon="el-icon-picture"
            type="primary"
            size="small"
            @click="uploadPhoto = true"
          >
            上传照片
          </el-button>
        </div>
      </div>
      <!-- 相册操作 -->
      <div class="operation">
        <div class="all-check">
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
          >
            全选
          </el-checkbox>
          <div class="check-count">已选择{{ selectPhotoIdList.length }}张</div>
        </div>
        <el-button
          type="success"
          @click="movePhoto = true"
          :disabled="selectPhotoIdList.length == 0"
          size="small"
          icon="el-icon-deleteItem"
        >
          移动到
        </el-button>
        <el-button
          type="danger"
          @click="batchDeletePhoto = true"
          :disabled="selectPhotoIdList.length == 0"
          size="small"
          icon="el-icon-deleteItem"
        >
          批量删除
        </el-button>
      </div>
    </div>
    <!-- 照片列表 -->
    <el-row class="photo-container" :gutter="10" v-loading="loading">
      <!-- 空状态 -->
      <el-empty v-if="photoList.length == 0" description="暂无照片" />
      <el-checkbox-group
        v-model="selectPhotoIdList"
        @change="handleCheckedPhotoChange"
      >
        <el-col :md="6" v-for="item of photoList" :key="item.id">
          <el-checkbox :label="item.id">
            <div class="photo-item">
              <!-- 照片操作 -->
              <div class="photo-opreation">
                <el-dropdown @command="handleCommand">
                  <i class="el-icon-more" style="color:#fff" />
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item :command="JSON.stringify(item)">
                      <i class="el-icon-edit" />编辑
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <el-image
                fit="cover"
                class="photo-img"
                :src="item.photoSrc"
                :preview-photoSrc-list="photoList"
              />
              <div class="photo-name">{{ item.photoName }}</div>
            </div>
          </el-checkbox>
        </el-col>
      </el-checkbox-group>
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
    <!-- 上传模态框 -->
    <el-dialog :visible.sync="uploadPhoto" width="70%" top="10vh">
      <div class="dialog-title-container" slot="title">
        上传照片
      </div>
      <!-- 上传 -->
      <div class="upload-container">
        <el-upload
          :headers="headers"
          v-show="uploadList.length > 0"
          :action= "uploadImgUrl"
          list-type="picture-card"
          :file-list="uploadList"
          multiple
          :on-success="upload"
          :on-remove="handleRemove"
        >
          <i class="el-icon-plus" />
        </el-upload>

        <div class="upload">
          <el-upload
            :headers="headers"
            v-show="uploadList.length == 0"
            drag
            :action= "uploadImgUrl"
            multiple
            :on-success="upload"
            :show-file-list="false"
            :on-remove="handleRemove"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div class="el-upload__tip" slot="tip">
              支持上传jpg/png文件
            </div>
          </el-upload>
        </div>
      </div>
      <div slot="footer">
        <div class="upload-footer">
          <div class="upload-count">共上传{{ uploadList.length }}张照片</div>
          <div style="margin-left:auto">
            <el-button @click="uploadPhoto = false">取 消</el-button>
            <el-button
              @click="savePhotos"
              type="primary"
              :disabled="uploadList.length == 0"
            >
              开始上传
            </el-button>
          </div>
        </div>
      </div>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog :visible.sync="editPhoto" width="30%">
      <div class="dialog-title-container" slot="title">
        修改信息
      </div>
      <el-form label-width="80px" size="medium" :model="photoForm">
        <el-form-item label="照片名称">
          <el-input style="width:220px" v-model="photoForm.photoName" />
        </el-form-item>
        <el-form-item label="照片描述">
          <el-input style="width:220px" v-model="photoForm.photoDesc" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="editPhoto = false">取 消</el-button>
        <el-button type="primary" @click="updatePhoto">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="batchDeletePhoto" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中照片？</div>
      <div slot="footer">
        <el-button @click="batchDeletePhoto = false">取 消</el-button>
        <el-button type="primary" @click="updatePhotoDelete(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 移动对话框 -->
    <el-dialog :visible.sync="movePhoto" width="30%">
      <div class="dialog-title-container" slot="title">
        移动照片
      </div>
      <el-empty v-if="albumList.length < 2" description="暂无其他相册" />
      <el-form v-else label-width="80px" size="medium" :model="photoForm">
        <el-radio-group v-model="albumId">
          <div class="album-check-item">
            <template v-for="item of albumList">
              <el-radio
                v-if="item.id != albumInfo.id"
                :key="item.id"
                :label="item.id"
                style="margin-bottom:1rem"
              >
                <div class="album-check">
                  <el-image
                    fit="cover"
                    class="album-check-cover"
                    :src="item.albumCover"
                  />
                  <div style="margin-left:0.5rem">{{ item.albumName }}</div>
                </div>
              </el-radio>
            </template>
          </div>
        </el-radio-group>
      </el-form>
      <div slot="footer">
        <el-button @click="movePhoto = false">取 消</el-button>
        <el-button
          :disabled="albumId == null"
          type="primary"
          @click="updatePhotoAlbum"
        >
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import {getAlbum, listAlbum} from "@/api/album/album";
import {getToken} from "@/utils/auth";
import {addPhoto, delPhoto, listPhoto, updatePhoto, updatePhotoAlbum} from "@/api/photo/photo";
export default {
  created() {
    this.getAlbumInfo();
    this.listAlbums();
    this.listPhotos();
  },
  data: function() {
    return {
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/system/oss/upload", // 上传的图片服务器地址,
      headers: {Authorization: 'Bearer ' + getToken()},
      loading: true,
      checkAll: false,
      isIndeterminate: false,
      uploadPhoto: false,
      editPhoto: false,
      movePhoto: false,
      batchDeletePhoto: false,
      uploadList: [
      ],
      // 表单参数
      form: {
        albumid:null,
        photoUrlList:[],
        photoNameList:[]

      },
      updatePhotoForm: {
        ids: [],
        albumid:[]

      },
      photoList: [],
      photoIdList: [],
      selectPhotoIdList: [],
      albumList: [],
      albumInfo: {
        id: null,
        albumName: "",
        albumDesc: "",
        albumCover: "",
        photoCount: 0
      },
      UpdateAlbum: {
        albumId:null,
        newAlbumId:null
      },
      photoForm: {
        id: null,
        photoName: "",
        photoDesc: "",
        albumid: null
      },
      albumId: null,
      current: 1,
      size: 18,
      count: 0,
      // 查询参数
      queryParams: {
        // pageNum: 1,
        // pageSize: 10,
        albumId: undefined,
        photoName: undefined,
        photoDesc: undefined,
        photoSrc: undefined,
        isDelete: undefined,
      },
    };
  },
  methods: {
    getAlbumInfo() {
      getAlbum(this.$route.query.id).then(res =>{
        this.albumInfo = res.data;
      })
    },
    /** 查询相册管理列表 */
    listAlbums() {
      this.loading = true;
      listAlbum(this.queryParams).then(response => {
        this.albumList = response.rows;
        this.count = response.total;
        this.loading = false;
      });
    },
    listPhotos() {
      this.queryParams.albumId = this.$route.query.id
      listPhoto(this.queryParams).then(res => {
        this.photoList = res.rows;
        this.count = res.total;
        this.loading = false;
      })
    },
    sizeChange(size) {
      this.size = size;
      this.listPhotos();
    },
    currentChange(current) {
      this.current = current;
      this.listPhotos();
    },
    savePhotos() {
      this.uploadList.forEach(item => {
        this.form.photoUrlList.push(item.url)
        this.form.photoNameList.push(item.names)
      });
      this.form.albumid = this.$route.query.id;
      addPhoto(this.form).then(res=>{
        if (res.code = 200){
          this.$modal.msgSuccess("上传成功");
          this.uploadList = [];
          this.listPhotos();
        }else {
          this.$modal.msgError("上传失败");
        }
      })
      this.uploadPhoto = false;
    },
    updatePhoto() {
      if (this.photoForm.photoName.trim() == "") {
        this.$modal.msgError("照片名称不能为空");
        return false;
      }
      updatePhoto(this.photoForm).then(res =>{
        if (res.code == 200){
          this.$modal.msgSuccess("修改成功")
          this.listPhotos();
        }else {
          this.$modal.msgError("修改失败");
        }
        this.editPhoto = false;
      })

    },
    updatePhotoAlbum() {
      this.updatePhotoForm.albumid = this.albumId;
      this.updatePhotoForm.ids = this.selectPhotoIdList;
      updatePhotoAlbum(this.updatePhotoForm).then(res =>{
        if (res.code == 200){
          this.$modal.msgSuccess("转移成功")
          this.getAlbumInfo();
          this.listPhotos();
        }else {
          this.$modal.msgError(res.msg)
        }
        this.movePhoto = false;
      })
    },
    handleRemove(file) {
      this.uploadList.forEach((item, index) => {
        if (item.url == file.url) {
          this.uploadList.splice(index, 1);
        }
      });
    },
    upload(response) {
      this.uploadList.push({ url: response.data.url, names: response.data.fileName});
      console.log(this.uploadList)
    },
    handleCheckAllChange(val) {
      this.selectPhotoIdList = val ? this.photoIdList : [];
      this.isIndeterminate = false;
    },
    handleCheckedPhotoChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.photoIdList.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.photoIdList.length;
    },
    handleCommand(command) {
      this.photoForm = JSON.parse(command);
      this.editPhoto = true;
    },
    updatePhotoDelete(id) {
      var param = {};
      if (id == null) {
        param = { ids: this.selectPhotoIdList};
      } else {
        param = { ids: [id]};
      }
      console.log(JSON.stringify(param)+"666")
      delPhoto(param.ids).then(res =>{
        if (res.code == 200){
          this.$modal.msgSuccess("删除成功");
          this.listPhotos();
        }else {
          this.$modal.msgError("删除失败")

        }
      })
      this.batchDeletePhoto = false;
    }
  },
  watch: {
    photoList() {
      this.photoIdList = [];
      this.photoList.forEach(item => {
        this.photoIdList.push(item.id);
      });
    }
  }
};
</script>

<style scoped>
.album-info {
  display: flex;
  margin-top: 2.25rem;
  margin-bottom: 2rem;
}
.album-cover {
  border-radius: 4px;
  width: 5rem;
  height: 5rem;
}
.album-check-cover {
  border-radius: 4px;
  width: 4rem;
  height: 4rem;
}
.album-detail {
  padding-top: 0.4rem;
  margin-left: 0.8rem;
}
.album-desc {
  font-size: 14px;
  margin-right: 0.8rem;
}
.operation {
  padding-top: 1.5rem;
  margin-left: auto;
}
.all-check {
  display: inline-flex;
  align-items: center;
  margin-right: 1rem;
}
.check-count {
  margin-left: 1rem;
  font-size: 12px;
}
.album-name {
  font-size: 1.25rem;
}
.photo-count {
  font-size: 12px;
  margin-left: 0.5rem;
}
.photo-item {
  width: 100%;
  position: relative;
  cursor: pointer;
  margin-bottom: 1rem;
}
.photo-img {
  width: 100%;
  height: 10rem;
  border-radius: 4px;
}
.photo-name {
  font-size: 14px;
  margin-top: 0.3rem;
  text-align: center;
}
.upload-container {
  height: 400px;
}
.upload {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.upload-footer {
  display: flex;
  align-items: center;
}
.photo-opreation {
  position: absolute;
  z-index: 1000;
  top: 0.3rem;
  right: 0.5rem;
}
.album-check {
  display: flex;
  align-items: center;
}

.photo-container {

}
</style>
