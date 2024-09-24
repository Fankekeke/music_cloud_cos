const app = getApp();
let http = require('../../../utils/request')
Page({
	data: {
		StatusBar: app.globalData.StatusBar,
		CustomBar: app.globalData.CustomBar,
		hidden: true,
		region: ['重庆市', '重庆市', '江北区'],
		name: '',
		area: '',
		birthday: '',
		sex: '',
		profession: '',
		remark: '',
		userId: null,
		addressId: 0,
		userInfo: {
			name: ''
		}
	},
	onLoad: function (option) {
		this.setData({ addressId: option.addressId })
		this.getUserInfo()
	},
	getUserInfo() {
		wx.getStorage({
			key: 'userInfo',
			success: (res) => {
				console.log(res.data.id)
				http.get('selectUserInfo', { userId: res.data.id }).then((r) => {
					this.setData({
						userId: r.data.id,
						name: r.data.name,
						area: r.data.email,
						birthday: r.data.birthday,
						sex: r.data.sex,
						remark: r.data.remark
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
	onDisplay() {
    this.setData({ show: true });
  },
  onClose() {
    this.setData({ show: false });
  },
  formatDate(date) {
		date = new Date(date);
		return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
  },
  onConfirm(event) {
    this.setData({
      show: false,
      'birthday': this.formatDate(event.detail),
    });
  },
	delete() {
		let that = this
		wx.showModal({
			title: '提示',
			content: '确定要删除吗？',
			success: function (sm) {
				if (sm.confirm) {
					http.get('address/delete', { addressId: that.data.addressId }).then((r) => {
						wx.showToast({
							title: '删除成功',
							icon: 'success',
							duration: 2000
						})
						setTimeout(() => {
							wx.redirectTo({
								url: '/pages/user/address/index'
							});
						}, 1000)
					})
				} else if (sm.cancel) {
					console.log('取消')
				}
			}
		})
	},
	edit() {
		wx.getStorage({
			key: 'userInfo',
			success: (res) => {
				console.log(this.data.name)
				this.userEdit()
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
	onChange(event) {
    this.setData({
      'sex': event.detail,
    });
  },

  onClick(event) {
    const { name } = event.currentTarget.dataset;
    this.setData({
      radio: name,
    });
  },
	getNameValue(e) {
		this.setData({ name: e.detail.value })
	},
	getAddressValue(e) {
		this.setData({ address: e.detail.value })
	},
	getPhoneValue(e) {
		this.setData({ phone: e.detail.value })
	},
	userEdit() {
		http.post('editUserInfo', {
			id: this.data.userId,
			name: this.data.name,
			email: this.data.area,
			birthday: this.data.birthday,
			sex: this.data.sex,
			profession: this.data.profession,
			remark: this.data.remark
		}).then((r) => {
			wx.showToast({
				title: '修改成功',
				icon: 'success',
				duration: 2000
			})
			setTimeout(() => {
				wx.switchTab({
					url: '/pages/user/index/index'
				});
			}, 1000)
		})
	},
	switch1Change(event) {
		let defaultAddress = event.detail.value ? 1 : 0
		this.setData({ defaultAddress })
	}
});
