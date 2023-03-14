<template>
  <router-view />
</template>

<script>
import socket from '@/assets/util/socket';
export default {
  mounted() {
    socket.on('connect', function () {
      // console.log('websocket连接成功');
    });
    socket.on('disconnect', function () {
      // console.log('websocket已经下线');
    });

    /* 监听 强登/封禁 下线 */
    let _this = this
    socket.on('logout_event', function (data) {
      // 不能remove掉redis中的key，否则两边同时下线
      // let { code, message, data } = _this.logout()

      /* 断开socket.io连接 */
      socket.io.opts.query = "token=" + JSON.parse(localStorage.getItem('access-user'))
      socket.disconnect();

      /* 清空localstorage */
      window.localStorage.removeItem("access-user");

      if (data == '强登') {
        _this.$message.warning('当前账号在其他地方登录，3秒后自动注销')
      }

      if (data == '封禁') {
        _this.$message.warning('您的账号存在违规行为，已被平台封禁，3秒后自动注销')
      }

      // 定位到登录
      setTimeout(() => {
        _this.$router.replace({ path: '/login' })
      }, 3000)
    });
  }
}
</script>