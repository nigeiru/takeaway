<template>
	<view>
		<uni-segmented-control
		  :current="current" 
		  :values="items" 
		  @clickItem="onClickItem" 
		  styleType="button" 
		  activeColor="#dce214"
		inActiveColor="#f7f7f7"
		   style="margin: 50rpx 0; border: 1px solid #ccc;border-radius: 20%;">
		</uni-segmented-control v-if >
			<!-- 如果 ordersList 为空，则显示提示信息 -->
						<view v-if="ordersList.length === 0" style="text-align: center; color: #666; padding: 40rpx;">
							近六个月未有符合条件的订单
						</view>
		 	<view>
				<view  v-for="item in ordersList" :key="item.id" style="margin-bottom: 30rpx;background: #fff; padding: 20rpx; 0" >
					<view style="display: flex; align-items: baseline; margin-bottom: 10rpx;"@click="goOrderItem(item.id)">
						<navigator 
						  :url="'/pages/detail/detail?businessId=' + item.businessId"
						  style="flex: 1; font-size: 32rpx; text-decoration: none; background: none;">
						  {{ item.businessName }}  
						  <uni-icons 
						    type="right" 
						    size="16" 
						    color="#666" 
						    style="position: relative; top: 2rpx;">
						  </uni-icons>
						</navigator>
						<view style="font-size: 24rpx; color: #666;">{{ item.status }}</view>
					</view>
					<view style="display: flex; align-items: center; grid-gap: 20rpx; margin-bottom: 10rpx;">
						<view><image style="display: block; width: 160rpx; height: 160rpx; border-radius: 10rpx;" :src="item.cover"></image></view>
						<view style="flex: 1;">{{ item.name }}</view>
						<view>实付￥<text style="font-size: 36rpx; font-weight: bold; color: red;">{{ item.actual }}</text></view>
					</view>
					<view style="display: flex; min-height: 40rpx;">
						<view style="flex: 1;">
							<view v-if="item.status === '已取消' || item.status === '已完成' || item.status === '已退款'" @click="del(item.id)">
								<uni-icons type="trash" size="16" color="#666" style="position: relative; top: 2rpx;"></uni-icons>
								<text style="font-size: 24rpx; color: #666;">删除</text>
							</view>
						 </view>
						 <view style="flex: 1; text-align: right;">
							 <uni-tag v-if="item.status === '未支付'" text="支付" size="mini" type="primary" @click="changeStatus(item, '待发货')"></uni-tag>
							 <uni-tag v-if="item.status === '待发货'" text="申请退款" size="mini" type="error" style="margin-right: 10rpx;" @click="changeStatus(item, '已退款')"></uni-tag>
							  <uni-tag v-if="item.status === '待收货'" text="确认收货" size="mini" type="warning" @click="changeStatus(item, '待评价')"></uni-tag>
							  <uni-tag v-if="item.status === '待评价'" text="评价" size="mini" type="warning" @click="goComment(item.id)"></uni-tag>
						 </view>
					</view>
				</view>
			</view>
		</view>	
	</view>
</template>

<script>
	export default {
		data() {
			return {
				current: 0,
				items: ['全部', '进行中', '待评价', '已退款'],
				ordersList: [],
				user: uni.getStorageSync('xm-user')
			}
		},
		onShow() {
			this.loadOrders()
		},
		methods: {
			goComment(orderId){
				uni.navigateTo({
					url:"/pages/comment/comment?orderId="+orderId
				})
			},
			goOrderItem(orderId){
				uni.navigateTo({
					url:"/pages/ordersItem/ordersItem?orderId="+orderId
				})
			},
			del(orderId) {
			    this.$request.post('/orders/delete/', orderId).then(res => {
			        if (res.code === '200') {
			            uni.showToast({
			                icon: "success",
			                title: '操作成功'
			            })
			            this.loadOrders()
			        } else {
			            uni.showToast({
			                icon: "error",
			                title: res.msg
			            })
			        }
			    })
			},
			changeStatus(orders, status) {
				let form = JSON.parse(JSON.stringify(orders))
				form.status = status
				this.$request.post('/orders/update', form).then(res => {
					if (res.code === '200') {
					  uni.showToast({
					    icon: "success",
					    title: '操作成功'
					  })
					  this.loadOrders()
					} else {
					  uni.showToast({
					    icon: "error",
					    title: res.msg
					  })
					}
				})
			},
			loadOrders() {
				let status = '全部'
				switch(this.current) {
					case 0: status = '全部'; break
					case 1: status = '进行中'; break
					case 2: status = '待评价'; break
					case 3: status = '已退款'; break
				}
				this.$request.get('/orders/selectAll', { userId:  this.user.id, status: status }).then(res => {
					this.ordersList = res.data || []
				})
			},
			onClickItem(e) {
			  if (this.current != e.currentIndex) {
				this.current = e.currentIndex;
				this.loadOrders()
			  }
			}
		}
	}
</script>

<style>

</style>