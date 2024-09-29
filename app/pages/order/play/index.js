const app = getApp();
let http = require('../../../utils/request')
const innerAudioContext = wx.createInnerAudioContext();
Page({
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    TabbarBot: app.globalData.tabbar_bottom,
    TabCur: 0, scrollLeft: 0,
    audiolist: [
      {
        audiosrc: "http://lcjl-uat-bucket.oss-cn-beijing.aliyuncs.com/2024/202402/20240223/9ad75843327248b78bfbfcee4791e9f1.mp3",
        poster: "https://cn.bing.com/th?id=OVFT.h-IoQ6aCPK-kCyaQhiEAeS&w=186&h=88&c=7&rs=2&qlt=80&pid=PopNow",
        title:"文章标题1"
      },
      {
        audiosrc: "http://lcjl-uat-bucket.oss-cn-beijing.aliyuncs.com/2024/202402/20240223/2470caa499f44f598a7fcce3e00e35c8.mp3",
        poster: "https://cn.bing.com/th?id=OVFT.h-IoQ6aCPK-kCyaQhiEAeS&w=186&h=88&c=7&rs=2&qlt=80&pid=PopNow",
        title:"文章标题2"
      }
    ],
    curPoster: "",
    curTitle: "",

    isPlayAudio: false, // 是否在播放
    audioSeek: 0,
    audioDuration: 0,
    showTime1: '00:00',
    showTime2: '00:00', 
    audioTime: 0,
    playbackRate: 2,
    actionSheetHidden: true, // 倍数弹窗
    actionSheetRate:[0.5, 1, 1.5, 2]
  },
  onLoad: function () {

  },
  onShow: function () {
    this.Initialization();
    this.loadaudio();
  },
  //初始化播放器，获取duration
  Initialization() {
    var that = this;
    if (this.data.audiolist[0].audiosrc.length != 0) {
      // 设置标题 potstrt
      that.setData({
        curPoster: this.data.audiolist[0].poster,
        curTitle: this.data.audiolist[0].title
      })

      //设置src
      innerAudioContext.src = this.data.audiolist[0].audiosrc;
      // 设置倍数  0.5 ~ 2
      innerAudioContext.playbackRate=this.data.playbackRate;
      //运行一次
      // innerAudioContext.play();
      // innerAudioContext.pause();
      innerAudioContext.onCanplay(() => {
        //初始化duration
        innerAudioContext.duration
        setTimeout(function () {
          //延时获取音频真正的duration
          var duration = innerAudioContext.duration;
          var min = parseInt(duration / 60);
          var sec = parseInt(duration % 60);
          if (min.toString().length == 1) {
            min = `0${min}`;
          }
          if (sec.toString().length == 1) {
            sec = `0${sec}`;
          }
          
          that.setData({ audioDuration: innerAudioContext.duration, showTime2: `${min}:${sec}` });
        }, 1000)
      })
    }
  },
  //拖动进度条事件
  sliderChange(e) {
    var that = this;
    innerAudioContext.src = this.data.audiolist[0].audiosrc;
    //获取进度条百分比
    var value = e.detail.value;
    this.setData({ audioTime: value });
    var duration = this.data.audioDuration;
    //根据进度条百分比及歌曲总时间，计算拖动位置的时间
    value = parseInt(value * duration / 100);
    //更改状态
    this.setData({ audioSeek: value, isPlayAudio: true });
    //调用seek方法跳转歌曲时间
    innerAudioContext.seek(value);
    //播放歌曲
    innerAudioContext.play();
  },
  //播放、暂停按钮
  playAudio() {
    //获取播放状态和当前播放时间
    var isPlayAudio = this.data.isPlayAudio;
    var seek = this.data.audioSeek;
    innerAudioContext.pause();
    //更改播放状态
    this.setData({ isPlayAudio: !isPlayAudio })
    if (isPlayAudio) {
      //如果在播放则记录播放的时间seek，暂停
      this.setData({ audioSeek: innerAudioContext.currentTime });
    } else {
      //如果在暂停，获取播放时间并继续播放
      innerAudioContext.src = this.data.audiolist[0].audiosrc;
      if (innerAudioContext.duration != 0) {
        this.setData({ audioDuration: innerAudioContext.duration });
      }
      //跳转到指定时间播放
      innerAudioContext.seek(seek);
      innerAudioContext.play();
    }
  },
  loadaudio() {
    var that = this;
    //设置一个计步器
    this.data.durationIntval = setInterval(function () {
      //当歌曲在播放时执行
      if (that.data.isPlayAudio == true) {
        //获取歌曲的播放时间，进度百分比
        var seek = that.data.audioSeek;
        var duration = innerAudioContext.duration;
        var time = that.data.audioTime;
        time = parseInt(100 * seek / duration);
        //当歌曲在播放时，每隔一秒歌曲播放时间+1，并计算分钟数与秒数
        var min = parseInt((seek + 1) / 60);
        var sec = parseInt((seek + 1) % 60);
        //填充字符串，使3:1这种呈现出 03：01 的样式
        if (min.toString().length == 1) {
          min = `0${min}`;
        }
        if (sec.toString().length == 1) {
          sec = `0${sec}`;
        }
        var min1 = parseInt(duration / 60);
        var sec1 = parseInt(duration % 60);
        if (min1.toString().length == 1) {
          min1 = `0${min1}`;
        }
        if (sec1.toString().length == 1) {
          sec1 = `0${sec1}`;
        }
        //当进度条完成，停止播放，并重设播放时间和进度条
        if (time >= 100) {
          innerAudioContext.stop();
          that.setData({ audioSeek: 0, audioTime: 0, audioDuration: duration, isPlayAudio: false, showTime1: `00:00` });
          return false;
        }
        //正常播放，更改进度信息，更改播放时间信息
        that.setData({ audioSeek: seek + 1, audioTime: time, audioDuration: duration, showTime1: `${min}:${sec}`, showTime2: `${min1}:${sec1}` });
      }
    }, 500)
  },
  onUnload: function () {
    //卸载页面，清除计步器
    clearInterval(this.data.durationIntval);
  },
  // 倍数弹窗
  actionSheetTap: function() {
    this.setData({
      actionSheetHidden:false
    });
  },
  // 取消弹窗
  actionSheetChange: function (e) {
    console.log('取消按钮被点击');
    this.setData({
      actionSheetHidden: true
    });
  },
  // 选择倍数
  selectRate(e){
    let rate = e.currentTarget.dataset.rate
    this.setData({
      playbackRate:rate,
      actionSheetHidden:true
    })
    this.Initialization()
  }
})