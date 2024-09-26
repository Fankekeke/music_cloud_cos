const app = getApp();
let http = require('../../../utils/request')
Page({
    data: {
        StatusBar: app.globalData.StatusBar + 6,
        TabbarBot: app.globalData.tabbar_bottom,
        swiperlist: [],
        goods: null,
        singerInfo: null,
        commoditId: null,
        collectFlag: false,
        evaluation: []
    },
    onLoad: function (options) {
        this.setData({ commoditId: options.musicId })
        this.getGoodsDetail(options.musicId)
        // this.getEvaluationByGoods(options.musicId)
        wx.getStorage({
            key: 'userInfo',
            success: (res) => {
                http.get('queryCollectStatus', { musicId: this.data.commoditId, userId: res.data.id }).then((r) => {
                    this.setData({
                        collectFlag: r.data
                    })
                })
            }
        })
    },
    getGoodsDetail(musicId) {
        http.get('queryMusicDetail', { musicId }).then((r) => {
            this.setData({
                swiperlist: r.music.images.split(','),
                goods: r.music,
                singerInfo: r.singer,
                evaluation: r.evaluate
            })
        })
    },
    queryCollectStatus () {
        wx.getStorage({
            key: 'userInfo',
            success: (res) => {
                http.get('queryCollectStatus', { musicId: this.data.commoditId, userId: res.data.id }).then((r) => {
                    this.setData({
                        collectFlag: r.data
                    })
                })
            },
            fail: res => {
                wx.showToast({
                    title: '请先进行登录',
                    icon: 'none',
                    duration: 2000
                })
            }
        })
    },
    addCollectByMusic() {
        wx.getStorage({
            key: 'userInfo',
            success: (res) => {
                http.get('addCollectByMusic', { musicId: this.data.commoditId, userId: res.data.id }).then((r) => {
                    http.get('queryCollectStatus', { musicId: this.data.commoditId, userId: res.data.id }).then((r) => {
                        this.setData({
                            collectFlag: r.data
                        })
                    })
                })
            },
            fail: res => {
                wx.showToast({
                    title: '请先进行登录',
                    icon: 'none',
                    duration: 2000
                })
            }
        })
    },
    delCollectByMusic() {
        wx.getStorage({
            key: 'userInfo',
            success: (res) => {
                http.get('delCollectByMusic', { musicId: this.data.commoditId, userId: res.data.id }).then((r) => {
                    http.get('queryCollectStatus', { musicId: this.data.commoditId, userId: res.data.id }).then((r) => {
                        this.setData({
                            collectFlag: r.data
                        })
                    })
                })
            },
            fail: res => {
                wx.showToast({
                    title: '请先进行登录',
                    icon: 'none',
                    duration: 2000
                })
            }
        })
    },
    getEvaluationByGoods(musicId) {
        http.get('getEvaluationByGoods', { musicId }).then((r) => {
            this.setData({
                evaluation: r.data
            })
        })
    },
    shopDetail() {
        wx.navigateTo({
			url: '/pages/shop/index/index?shopId='+this.data.goods.shopId+''
		});
    },
    message() {
        wx.getStorage({
            key: 'userInfo',
            success: (res) => {
                wx.navigateTo({
                    url: '/pages/user/detail/index?takeUser=' + this.data.goods.userId + '&sendUser=' + res.data.id + '&otherName=' + this.data.goods.userName + ''
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
    },
    cartView() {
        wx.switchTab({
            url: '/pages/scar/index/index'
        })
    },
    addCart() {
        if (this.data.goods.onPut == 0) {
            wx.showToast({
                title: '该商品已下架',
                icon: 'none',
                duration: 2000
            })
        } else {
            wx.getStorage({
                key: 'userInfo',
                success: (res) => {
                    http.get('selUserCartByGoodsId', { userId: res.data.id, commodityId: this.data.goods.id }).then((r) => {
                        if (r.data != 0) {
                            wx.showToast({
                                title: '商品已在购物车',
                                icon: 'none',
                                duration: 2000
                            })
                        } else {
                            let data = { userId: res.data.id, commodityId: this.data.goods.id, price: this.data.goods.price, }
                            http.post('addGoodsCart', data).then((r) => {
                                wx.showToast({
                                    title: '添加购物车成功',
                                    icon: 'success',
                                    duration: 2000
                                })
                            })
                        }
                    })
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
    },
    buyGoods() {
        if (this.data.goods.onPut == 0) {
            wx.showToast({
                title: '该商品已下架',
                icon: 'none',
                duration: 2000
            })
        } else {
            wx.getStorage({
                key: 'userInfo',
                success: (res) => {
                    http.get('selDefaultAddress', { userId: res.data.id }).then((r) => {
                        if (r.data == null) {
                            wx.showToast({
                                title: '请先设置默认收货地址',
                                icon: 'none',
                                duration: 1000
                            })
                        } else {
                            wx.navigateTo({
                                url: '/pages/scar/order/index?type=2&commodityId=' + this.data.commoditId + ''
                            })
                        }
                    })
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
    }
});
