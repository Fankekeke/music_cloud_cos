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
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    userInfo: null,
    messageList: [],
    theme: 'light',
    playing: false, // 播放状态
    pause: false,
    playTime: 0, // 播放时长
    formatedPlayTime: '00:00:00' // 格式化后的播放时长
  },
  onShow() {
    this.isLogin()
  },
  /**
* 页面相关事件处理函数--监听用户下拉动作
*/
  onPullDownRefresh() {
    this.isLogin()
  },
  isLogin() {
    wx.getStorage({
      key: 'userInfo',
      success: (res) => {
        this.setData({ userInfo: res.data })
        this.messageList()
      },
      fail: res => {
        wx.showToast({
          title: '请先进行登录',
          icon: 'none',
          duration: 2000
        })
        setTimeout(() => {
          wx.switchTab({
            url: '../index/index'
          })
        }, 1500)
      }
    })
  },
  musicDetail(event) {
    wx.navigateTo({
			url: '/pages/order/music/index?musicId='+event.currentTarget.dataset.musicId
		});
  },
  messageDetail(event) {
    let takeUser = event.currentTarget.dataset.take
    let sendUser = event.currentTarget.dataset.send
    let takeUserName = event.currentTarget.dataset.takename
    let sendUserName = event.currentTarget.dataset.sendname
    let otherName = sendUser == this.data.userInfo.id ? takeUserName : sendUserName
    wx.navigateTo({
			url: '/pages/user/detail/index?takeUser='+takeUser+'&sendUser='+sendUser+'&otherName='+otherName+''
		});
  },
  messageList() {
    http.get('queryCollectByUser', { userId: this.data.userInfo.id }).then((r) => {
      // r.data.forEach(item => {
      //   item.days = this.timeFormat(item.createDate)
      //   if (item.sendUserAvatar && !item.sendUserAvatar.includes('http')) {
      //     item.sendUserAvatar = 'http://127.0.0.1:9527/imagesWeb/' + item.sendUserAvatar
      //   }
      //   if (item.takeUserAvatar && !item.takeUserAvatar.includes('http')) {
      //     item.takeUserAvatar = 'http://127.0.0.1:9527/imagesWeb/' + item.takeUserAvatar
      //   }
      // });
      this.setData({ messageList: r.data })
    })
  },
  timeFormat(time) {
    var nowTime = new Date();
    var day = nowTime.getDate();
    var hours = parseInt(nowTime.getHours());
    var minutes = nowTime.getMinutes();
    // 开始分解付入的时间
    var timeday = time.substring(8, 10);
    var timehours = parseInt(time.substring(11, 13));
    var timeminutes = time.substring(14, 16);
    var d_day = Math.abs(day - timeday);
    var d_hours = hours - timehours;
    var d_minutes = Math.abs(minutes - timeminutes);
    if (d_day <= 1) {
      switch (d_day) {
        case 0:
          if (d_hours == 0 && d_minutes > 0) {
            return d_minutes + '分钟前';
          } else if (d_hours == 0 && d_minutes == 0) {
            return '1分钟前';
          } else {
            return d_hours + '小时前';
          }
          break;
        case 1:
          if (d_hours < 0) {
            return (24 + d_hours) + '小时前';
          } else {
            return d_day + '天前';
          }
          break;
      }
    } else if (d_day > 1 && d_day < 10) {
      return d_day + '天前';
    } else {
      return time;
    }
  },
  submit: function () {
    wx.navigateTo({
      url: '/pages/scar/order/index'
    })
  },

  onShow: function () {
    if (!backgroundAudioManager.paused && backgroundAudioManager.paused !== undefined) {
      this._enableInterval()
      this.setData({
        playing: true
      })
    }
  },
  play(event) {
    let music = event.currentTarget.dataset.music
    console.log(music)
    backgroundAudioManager.title = music.musicName // 必填
    backgroundAudioManager.epname = music.albumName // 必填
    backgroundAudioManager.singer = music.singerName
    backgroundAudioManager.coverImgUrl = 'http://127.0.0.1:9527/imagesWeb/' + music.musicImages

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
        backgroundAudioManager.src = 'http://127.0.0.1:9527/imagesWeb/' + music.fileUrl
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

  onLoad() {
    this.isLogin()
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
