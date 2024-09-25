<template>
  <a-modal v-model="show" title="修改歌曲" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        修改
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='歌曲名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='歌曲标签' v-bind="formItemLayout">
            <a-input v-decorator="[
            'tag',
            { rules: [{ required: true, message: '请输入歌曲标签!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='创作歌手' v-bind="formItemLayout">
            <a-select v-decorator="[
              'singerId',
              { rules: [{ required: true, message: '请输入专辑歌手!' }] }
              ]">
              <a-select-option v-for="(item, index) in singerList" :key="item.id" :value="item.id">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='歌曲类型' v-bind="formItemLayout">
            <a-select v-decorator="[
              'typeId',
              { rules: [{ required: true, message: '请输入歌曲类型!' }] }
              ]">
              <a-select-option v-for="(item, index) in typeList" :key="item.id" :value="item.id">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='所属专辑' v-bind="formItemLayout">
            <a-select v-decorator="[
              'albumId'
              ]">
              <a-select-option v-for="(item, index) in albumList" :key="item.id" :value="item.id">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='备注' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content',
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='歌曲上传' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              :file-list="fileMusicList"
              @change="musicHandleChange"
            >
              <a-button> <a-icon type="upload" :disabled="fileMusicList.length < 1"/> Upload </a-button>
            </a-upload>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='图册' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
              @change="picHandleChange"
            >
              <div v-if="fileList.length < 1">
                <a-icon type="plus" />
                <div class="ant-upload-text">
                  Upload
                </div>
              </div>
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'musicEdit',
  props: {
    musicEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.musicEditVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      fileMusicList: [],
      singerList: [],
      typeList: [],
      albumList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  mounted () {
    this.selectSingerList()
    this.selectMusicTypeList()
    this.selectAlbumList()
  },
  methods: {
    selectAlbumList () {
      this.$get(`/cos/album-info/list`).then((r) => {
        this.albumList = r.data.data
      })
    },
    selectSingerList () {
      this.$get(`/cos/singer-info/list`).then((r) => {
        this.singerList = r.data.data
      })
    },
    selectMusicTypeList () {
      this.$get(`/cos/music-type-info/list`).then((r) => {
        this.typeList = r.data.data
      })
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    musicHandleChange ({ fileList }) {
      this.fileMusicList = fileList
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    fileUrlInit (fileUrl) {
      if (fileUrl !== null && fileUrl !== '') {
        let imageList = []
        fileUrl.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileMusicList = imageList
      }
    },
    setFormValues ({...music}) {
      this.rowId = music.id
      let fields = ['name', 'tag', 'singerId', 'typeId', 'albumId', 'content']
      let obj = {}
      Object.keys(music).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(music['images'])
        }
        if (key === 'fileUrl') {
          this.fileMusicList = []
          this.fileUrlInit(music['fileUrl'])
        }
        if (key === 'rackUp') {
          music[key] = music[key].toString()
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = music[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      // 获取音乐List
      let music = []
      this.fileMusicList.forEach(image => {
        if (image.response !== undefined) {
          music.push(image.response)
        } else {
          music.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.images = images.length > 0 ? images.join(',') : null
        values.fileUrl = music.length > 0 ? music.join(',') : null
        if (!err) {
          this.loading = true
          this.$put('/cos/music-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
