<template>
	<view >
		<view class="box">
			<uni-forms :modelValue="form" :rules="rules" ref="formRef" label-width="140rpx" label-align="right">
			  <uni-forms-item label="头像" name="avatar">
			   <uni-file-picker limit="1" :image-styles="imageStyles" :del-icon="false" :disable-preview="true" 
			   						fileMediatype="image" v-model="form.avatar"  @select="handleImgUploadSuccess"></uni-file-picker>
			  </uni-forms-item>
			  </uni-forms>
</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form:{},
				user:uni.getStorageSync("xm-user"),
				imageStyles: {
				  "height": 80,	// 边框高度
				  "width": 80,	// 边框宽度
				  "border":{ // 如果为 Boolean 值，可以控制边框显示与否
				    "color":"#eee",		// 边框颜色
				    "width":"1px",		// 边框宽度
				    "style":"solid", 	// 边框样式
				    "radius":"50%" 		// 边框圆角，支持百分比
				  }
				},
				imgs: [
				  { url: this.form.img }
				],   	   
			}
		},
		methods: {
			handleImgUploadSuccess(e) {
			  let _this = this
			  const filePath = e.tempFilePaths[0]
			  uni.uploadFile({
			    url: _this.$baseUrl +'/files/upload', //自己的后端接口（默认发送post请求） 注意 _this.$baseUrl需要在全局变量定义
			    filePath: filePath,
			    name:"file",  //这里应为自己后端文件形参的名字
			    success(res) {
			      let url = JSON.parse(res.data || '{}').data  // 获取返回的图像链接
			      _this.form.img = url    // 给表单图像属性赋值
			    }
			  })
			},      
		}
	}
</script>

<style>

</style>
