<template>
	<view style="padding: 40rpx;">
		<view style="padding: 20rpx; margin: 80rpx 0; background-color: #fff; box-shadow: 0 2rpx 10rpx rgba(0,0,0,.1); border-radius: 10rpx;">
			<view style="margin: 50rpx 30rpx; font-size: 40rpx;">欢迎登录</view>
			<uni-forms ref="form" :modelValue="form" :rules="rules" validateTrigger="blur">
				<uni-forms-item required name="username">
					<uni-easyinput prefixIcon="person" v-model="form.username" placeholder="请输入账号" />
				</uni-forms-item>
				<uni-forms-item required name="password">
					<uni-easyinput prefixIcon="locked" type="password"  v-model="form.password" placeholder="请输入密码" />
				</uni-forms-item>
				<uni-forms-item style="display: flex; flex-direction: column; align-items: center;">
					<button @click="login" style="background-color: #ffd100; border-color: #ffd100; height: 70rpx; line-height: 70rpx;">登 录</button>
					<view @click="toRegister" style="width: 100%; display: flex; justify-content: flex-end; margin-top: 40rpx;">
						<text style="color: #ffd100;font-style: italic; text-decoration: underline;">没有账号，进行注册</text>
					</view>
				</uni-forms-item>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					username: '',
					password: '',
					role: 'USER'
				},
				rules: {
					username: {
						rules: [
							{
								required: true,
								errorMessage: '请输入账号',
							},
							{
								minLength: 3,
								maxLength: 10,
								errorMessage: '账号长度在 {minLength} 到 {maxLength} 个字符',
							}
						],
					},
					password: {
						rules: [
							{
								required: true,
								errorMessage: '请输入密码',
							}
						],
					}
				}
			}
		},
		methods: {
			login() {
				this.$refs.form.validate().then(valid => {
					if (valid) {
						this.$request.post('/login', this.form).then(res => {
							if (res.code === '200') {
								uni.showToast({
									icon: 'success',
									title: '登录成功'
								});
								uni.setStorageSync('xm-user', res.data);

								// 跳转主页
								 // 使用 setTimeout 在弹窗显示一秒后跳转到登录页
								                    setTimeout(() => {
								                        uni.switchTab({
								                        	url:"/pages/index/index"
								                        })
								                    }, 1000); // 等待 1000 毫秒 (1 秒)
								                }
							 else {
								uni.showToast({
									icon: 'error',
									title: res.msg
								});
							}
						});
					} else {
						uni.showToast({
							icon: 'error',
							title: '表单验证失败'
						});
					}
				});
			},
			toRegister() {
							uni.navigateTo({
								url: '/pages/register/register'
							})
						}
					}
				}
</script>

<style>

</style>
