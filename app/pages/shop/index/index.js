const app = getApp();
let http = require('../../../utils/request')
Page({
    data: {
        StatusBar: app.globalData.StatusBar + 6,
        TabbarBot: app.globalData.tabbar_bottom,
        TabCur: 0,scrollLeft:0,
        SortMenu: [{id:0,name:"单曲"},{id:1,name:"专辑"}],
        SortMenu1: [{id:0,name:"全部"},{id:1,name:"销量"},{id:2,name:"新品"},{id:3,name:"价格"}],
        ShopList: [],
        shopId: null,
        shopInfo: null,
        musicList: [],
        albumList: [],
        key: '',
        collectFlag: false
    },
    onLoad: function (options) {
        this.setData({
            shopId: options.shopId
        })
        this.selShopDetail(options.shopId)
    },
    postDetail(event) {
        wx.navigateTo({
            url: '/pages/coupon/detail/index?postId=' + event.currentTarget.dataset.postid + ''
        });
    },
    selShopDetail(shopId) {
        http.get('querySingerDetail', {userId: shopId}).then((r) => {
			// r.post.forEach(item => {
            //     if (item.images != null) {
            //         item.images = item.images.split(',')[0]
            //     }
			// });
            this.setData({
                shopInfo: r.singer,
                musicList: r.music,
                albumList: r.album
            })
            this.queryCollectPost()
        })
    },
    commoditSort(shopId, type) {
        http.get('shopCommoditSort', {shopId, type}).then((r) => {
            let ShopList = []
			r.data.forEach(item => {
                ShopList.push({ index: item.id, image: item.images.split(',')[0], title: item.name, price: item.price, sales: item.orderNum })
			});
            this.setData({
                ShopList
            })
        })
    },
    tabSelect(e) {
        // this.commoditSort(this.data.shopId, e.currentTarget.dataset.id)
        this.setData({
            TabCur: e.currentTarget.dataset.id,
            scrollLeft: (e.currentTarget.dataset.id-1)*60
        })
    },
    btnback: function () {
        wx.navigateBack();
    },
    getKeyValue(e) {
		this.setData({ key: e.detail.value })
	},
    search: function () {
        wx.navigateTo({
            url: '/pages/shop/search/index?key='+this.data.key+'&shopId='+this.data.shopId+''
        });
    },
    queryCollectPost() {
        wx.getStorage({
          key: 'userInfo',
          success: (res) => {
           http.get('querySubStatus', { singerId: this.data.shopInfo.id, userId: res.data.id }).then((r) => {
            this.setData({ collectFlag: r.data > 0 })
          })
          }
        })
      },
      collectPost() {
        wx.getStorage({
          key: 'userInfo',
          success: (res) => {
           http.get('addSubByMusic', { singerId: this.data.shopInfo.id, userId: res.data.id, type: 1 }).then((r) => {
            this.queryCollectPost()
          })
          }
        })
      },
      collectPostElse() {
        wx.getStorage({
          key: 'userInfo',
          success: (res) => {
           http.get('delSubByMusic', { singerId: this.data.shopInfo.id, userId: res.data.id, type: 2 }).then((r) => {
            this.queryCollectPost()
          })
          }
        })
      },
      chatUser() {
        wx.getStorage({
            key: 'userInfo',
            success: (res) => {
                wx.navigateTo({
                    url: '/pages/user/detail/index?takeUser=' + this.data.shopInfo.id + '&sendUser=' + res.data.id + '&otherName=' + this.data.shopInfo.name + ''
                });
            },
            fail: res => {
                wx.showToast({
                    title: '请先进行登录',
                    icon: 'none',
                    duration: 2000
                })
            }
        })
      }
});
