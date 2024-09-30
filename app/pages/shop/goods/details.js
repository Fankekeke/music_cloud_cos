const app = getApp();
let http = require('../../../utils/request')
const backgroundAudioManager = wx.getBackgroundAudioManager()
let updateInterval
Page({
    formatTime(time) {
        if (typeof time !== 'number' || time < 0) {
          return time
        }
    
        const hour = parseInt(time / 3600, 10)
        time %= 3600
        const minute = parseInt(time / 60, 10)
        time = parseInt(time % 60, 10)
        const second = time
    
        return ([hour, minute, second]).map(function (n) {
          n = n.toString()
          return n[1] ? n : '0' + n
        }).join(':')
      },
    data: {
        StatusBar: app.globalData.StatusBar + 6,
        TabbarBot: app.globalData.tabbar_bottom,
        swiperlist: [],
        goods: null,
        singerInfo: null,
        commoditId: null,
        collectFlag: false,
        evaluation: [],
        theme: 'light',
        playing: false, // 播放状态
        pause: false,
        playTime: 0, // 播放时长
        formatedPlayTime: '00:00:00' // 格式化后的播放时长
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
    },
    onShow: function () {
        if (!backgroundAudioManager.paused && backgroundAudioManager.paused !== undefined) {
          this._enableInterval()
          this.setData({
            playing: true
          })
        }
      },
      play() {
        backgroundAudioManager.title = this.data.goods.name // 必填
        backgroundAudioManager.epname = this.data.goods.name // 必填
        backgroundAudioManager.singer = this.data.singerInfo.name
        backgroundAudioManager.coverImgUrl = 'http://127.0.0.1:9527/imagesWeb/' + this.data.goods.images
    
        const that = this
        if (that.data.pause) {
          backgroundAudioManager.play()
          this.setData({
            playing: true,
          })
        } else {
          that.setData({
            playing: true,
          }, () => {
            // 设置src后会自动播放
            backgroundAudioManager.src = 'http://127.0.0.1:9527/imagesWeb/' + this.data.goods.fileUrl
          })
        }
      },
    
      seek(e) {
        backgroundAudioManager.seek(e.detail.value)
      },
    
      pause() {
        clearInterval(updateInterval)
        backgroundAudioManager.pause()
    
      },
    
      stop() {
        clearInterval(updateInterval)
        backgroundAudioManager.stop()
      },
    
      _enableInterval() {
        const that = this
    
        function update() {
          that.setData({
            playTime: backgroundAudioManager.currentTime + 1,
            formatedPlayTime: that.formatTime(backgroundAudioManager.currentTime + 1)
          })
        }
        updateInterval = setInterval(update, 1000)
      },
    
      onUnload() {
        clearInterval(updateInterval)
      },
    
      onLoad(options) {
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
        this.setData({
          theme: wx.getSystemInfoSync().theme || 'light'
        })
    
        if (wx.onThemeChange) {
          wx.onThemeChange(({
            theme
          }) => {
            this.setData({
              theme
            })
          })
        }
        const that = this
        // 监听播放事件
        backgroundAudioManager.onPlay(() => {
          // 刷新播放时间
          that._enableInterval()
          that.setData({
            pause: false,
          })
        })
    
        // 监听暂停事件
        backgroundAudioManager.onPause(() => {
          clearInterval(updateInterval)
          that.setData({
            playing: false,
            pause: true,
          })
        })
    
        backgroundAudioManager.onEnded(() => {
          clearInterval(updateInterval)
          that.setData({
            playing: false,
            playTime: 0,
            formatedPlayTime: that.formatTime(0)
          })
        })
    
        backgroundAudioManager.onStop(() => {
          clearInterval(updateInterval)
          that.setData({
            playing: false,
            playTime: 0,
            formatedPlayTime: that.formatTime(0)
          })
        })
      }
});
