const app = getApp();
let http = require('../../../utils/request')
Page({
    data: {
        StatusBar: app.globalData.StatusBar,
        CustomBar: app.globalData.CustomBar,
        TabbarBot: app.globalData.tabbar_bottom,
        TabCur: 0,scrollLeft:0,
        SortMenu: [{id:0,name:"贴子列表"},{id:1,name:"用户列表"}],
        key: '',
        commodity: [],
        shop: []
    },
    onLoad: function (options) {
        this.setData({ key: options.key })
        this.getGoodsFuzzy()
    },
    getGoodsFuzzy() {
        // console.log(this.data.key)
        http.get('selShopDetailList', { key: this.data.key }).then((r) => {
            this.setData({ commodity: r.post, shop: r.user })
		})
    },
    shopDeatil(e) {
		wx.navigateTo({
			url: '/pages/shop/index/index?shopId='+e.currentTarget.dataset.shopid+''
		});
	},
    postDetail(event) {
        wx.navigateTo({
            url: '/pages/coupon/detail/index?postId=' + event.currentTarget.dataset.postid + ''
        });
    },
    search() {
        this.getGoodsFuzzy()
    },
    getKeyValue(e) {
        console.log(e.detail.value)
		this.setData({ key: e.detail.value })
	},
    tabSelect(e) {
        console.log(e.currentTarget.dataset.id);
        this.setData({
            TabCur: e.currentTarget.dataset.id,
            scrollLeft: (e.currentTarget.dataset.id-1)*60
        })
    }
});
