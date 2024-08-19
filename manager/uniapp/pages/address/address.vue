<template>
	<view style="padding: 20rpx;">
		<view style="margin-bottom: 110rpx;">
			<view v-for="item in addressList" :key="item.id" style="margin-bottom: 20rpx; padding: 20rpx 20rpx;border-radius: 15rpx; background-color: white;" @click="selectAddress(item.id)">
				<view class="address-info">{{ item.address }}</view>
				<view class="user-info">
					<text>{{ item.user }}</text>
					<text class="phone">{{ item.phone }}</text>
				</view>
				<view class="action-icons">
					<uni-icons type="compose" size="16" @click.stop.prevent="handleEdit(item.id)"></uni-icons>
					<uni-icons type="trash" size="16" class="delete-icon" @click.stop="del(item.id)"></uni-icons>

				</view>
			</view>
		</view>
		
		<navigator url="/pages/addAddress/addAddress">
			<button type="primary" class="add-address-button">新增收货地址</button>
		</navigator>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: uni.getStorageSync('xm-user'),
				addressList: []
			}
		},
		onShow() {
		    this.load()
		},
		methods: {
			selectAddress(addressId) {
							let xmOrders = uni.getStorageSync('xm-orders') || {}
							xmOrders.addressId = addressId
							uni.setStorageSync('xm-orders', xmOrders)
							uni.navigateBack()
						},
			handleEdit(addressId) {
				uni.navigateTo({
					url: '/pages/addAddress/addAddress?addressId=' + addressId
				})
			},
			del(addressId) {
			    this.$request.del('/address/delete/' + addressId).then(res => {
			        if (res.code === '200') {
			            uni.showToast({
			                icon: "success",
			                title: '操作成功'
			            })
			            this.load()
			        } else {
			            uni.showToast({
			                icon: "error",
			                title: res.msg
			            })
			        }
			    })
			},

			load() {
				this.$request.get('/address/selectAll', { userId: this.user.id }).then(res => {
					this.addressList = res.data || []
				})
			}
		}
	}
</script>

<style>
.box {
	background: #fff;
	margin-bottom: 10rpx;
}
.address-info {
	margin-bottom: 10rpx;
}
.user-info {
	color: #888;
	margin-bottom: 10rpx;
}
.phone {
	margin-left: 20rpx;
}
.action-icons {
	text-align: right;
}
.delete-icon {
	margin-left: 20rpx;
}
.add-address-button {
	background-color: dodgerblue;
	border-color: dodgerblue;
	position: fixed;
	bottom: 10rpx;
	width: calc(100% - 40rpx);
}
</style>
