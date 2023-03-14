<template>
    <div class="nav">
        <!-- LOGO -->
        <div class="nav-logo">
            <img src="../assets/img/logo.png" alt="" class="nav-logo-img">
        </div>
        <!-- 菜单栏 -->
        <div class="nav-menu ">
            <div class="nav-menu-item ts1s " v-for="item, index in navMenuItems" @click="gotoPage(index)">
                {{ item }}
            </div>
        </div>
        <!-- 未登录态 -->
        <div class="nav-no-login " v-if="!isLogin">
            <div class="no-login-item no-login-item-real ts1s" @click="toRegPage()">注册</div>
            <div class="no-login-item" style="color: #a8a8a8;">或</div>
            <div class="no-login-item no-login-item-real ts1s" @click="toLoginPage()">登录</div>
        </div>
        <!-- 登录态 -->
        <div class="nav-online" v-if="isLogin">
            <!-- 消息盒子 -->
            <el-popover placement="bottom" width="330" trigger="click">
                <div style="height: 250px;overflow: hidden;">
                    <!-- 消息 -->
                    <el-empty description="暂无消息" :image-size="100" v-if="messageList.length == 0"></el-empty>
                    <div class="message-list">
                        <div class="message-item" v-for="item in messageList">
                            <div class="message-type ">
                                <div class="m-type1 " style="background-color: #138542;"
                                    v-if="item.messageType == 0 || item.messageType == 1">
                                    {{ messageType[item.messageType] }}
                                </div>
                                <div class="m-type1 " style="background-color: #dfa019e8;"
                                    v-if="item.messageType == 2 || item.messageType == 3 || item.messageType == 4">
                                    {{ messageType[item.messageType] }}
                                </div>
                                <div class="m-type1 ">
                                    {{ messageType[item.messageType] }}
                                </div>
                                <div class="m-type2 " style="background-color: #096e96;" v-if="item.messageObjectType == 0">
                                    个人
                                </div>
                                <div class="m-type2 " style="background-color: #ce740e;" v-if="item.messageObjectType == 1">
                                    队伍
                                </div>
                                <div class="m-type2 " style="background-color: #e44646;" v-if="item.messageObjectType == 2">
                                    新闻
                                </div>
                                <div class="m-type2 " style="background-color: #2ad8ac;color:#fff;"
                                    v-if="item.messageObjectType == 3">
                                    公告
                                </div>
                            </div>
                            <div class="message-content " :title="item.messageContent"
                                @click="$router.push(item.messageUrl)">
                                {{ item.messageContent }}
                            </div>
                            <div class="message-time ">
                                {{ item.messageTime }}
                            </div>
                        </div>
                    </div>
                    <div class="doDelAllMessage-btn ts02s" @click="doDelAllMessage()">
                        <i class="el-icon-delete"></i>
                        清空
                    </div>
                </div>
                <!-- 图标入口 -->
                <el-badge :value="messageList.length" :max="99" class="nav-online-message-box-logo" slot="reference">
                    <img src="../assets/img/message-box.png" alt="" class="message-box-img">
                </el-badge>

            </el-popover>
            <!-- 个人功能 -->
            <el-popover placement="bottom" width="300" trigger="click">
                <div class="admin-card">
                    <!-- 头像 -->
                    <div class="admin-card-head" @click="jumpToBackStage(1)">
                        <img :src="visitUserHead()" alt="" class="admin-card-head-img">
                    </div>
                    <!-- 昵称 -->
                    <div class="admin-card-username ">{{ userName }}</div>
                    <!-- 身份 -->
                    <div class="admin-card-identity ">{{ identity }}</div>
                    <!-- 方块菜单 -->
                    <div class="admin-card-menu ">
                        <div class="admin-card-menu-item ts01s" @click="jumpToBackStage(2)">
                            <img class="admin-card-menu-item-img " src="../assets/img/0.png" alt="">
                            <div class="admin-card-menu-item-text ">我的竞赛</div>
                        </div>
                        <div class="admin-card-menu-item ts01s" @click="jumpToBackStage(5)">
                            <img class="admin-card-menu-item-img " src="../assets/img/1.png" alt="">
                            <div class="admin-card-menu-item-text ">我的活动</div>
                        </div>
                        <div class="admin-card-menu-item ts01s" @click="jumpToBackStage(7)">
                            <img class="admin-card-menu-item-img " src="../assets/img/2.png" alt="">
                            <div class="admin-card-menu-item-text ">我的队伍</div>
                        </div>
                        <div class="admin-card-menu-item ts01s" @click="jumpToBackStage(9)">
                            <img class="admin-card-menu-item-img " src="../assets/img/3.png" alt="">
                            <div class="admin-card-menu-item-text ">我的帖子</div>
                        </div>
                        <div class="admin-card-menu-item ts01s"
                            @click="$router.push({ path: 'user_details', query: { userId: userId } })">
                            <img class="admin-card-menu-item-img " src="../assets/img/4.png" alt="">
                            <div class="admin-card-menu-item-text ">个人主页</div>
                        </div>
                        <div class="admin-card-menu-item ts01s" @click="jumpToBackStage(1)">
                            <img class="admin-card-menu-item-img " src="../assets/img/5.png" alt="">
                            <div class="admin-card-menu-item-text ">管理中心</div>
                        </div>
                    </div>
                    <!-- 列表菜单 -->
                    <div class="admin-card-menu2 ">
                        <div class="admin-card-menu2-item ts01s" v-if="show1" @click="jumpToBackStage(3)">
                            <img class="admin-card-menu2-item-img " src="../assets/img/02.png" alt="">
                            <div class="admin-card-menu2-item-text ">发布新竞赛</div>
                        </div>
                        <div class="admin-card-menu2-item ts01s" @click="jumpToBackStage(6)">
                            <img class="admin-card-menu2-item-img " src="../assets/img/03.png" alt="">
                            <div class="admin-card-menu2-item-text ">发起新活动</div>
                        </div>
                        <div class="admin-card-menu2-item ts01s" @click="jumpToBackStage(8)">
                            <img class="admin-card-menu2-item-img " src="../assets/img/04.png" alt="">
                            <div class="admin-card-menu2-item-text ">创建组队</div>
                        </div>
                        <div class="admin-card-menu2-item ts01s" @click="$router.push({ path: '/article_add' })">
                            <img class="admin-card-menu2-item-img " src="../assets/img/05.png" alt="">
                            <div class="admin-card-menu2-item-text ">茶话会写帖子</div>
                        </div>
                        <div class="admin-card-menu2-item ts01s">
                            <img class="admin-card-menu2-item-img " src="../assets/img/06.png" alt="">
                            <div class="admin-card-menu2-item-text ">About Us</div>
                        </div>
                        <div class="admin-card-menu2-item ts01s " @click="doLogout">
                            <img class="admin-card-menu2-item-img " src="../assets/img/01.png" alt="">
                            <div class="admin-card-menu2-item-text ">退出</div>
                        </div>
                    </div>
                </div>
                <!-- 头像入口 -->
                <div class="nav-admin-head ts1s" slot="reference">
                    <img :src="visitUserHead()" alt="" class="nav-admin-head-img">
                </div>
            </el-popover>
        </div>
        <!-- 消息盒子 -->
        <!-- <Transition name="fade">
            <div class="nav-message-box">
            </div>
        </Transition> -->
    </div>
</template>


<script>
import ElementUI from 'element-ui';
import { mapActions } from 'vuex';
import socket from '@/assets/util/socket';
export default {
    data: () => ({
        navMenuItems: ['首页', '竞赛', '活动', '组队', '茶话会', '关于我们'],
        messageList: [],
        timer: '',
        messageType: ['通知', '申请', '评论', '推送', '系统公告']
    }),
    computed: {
        isLogin() {
            let token = JSON.parse(window.localStorage.getItem("access-user"))
            if (!token) {
                return false
            } else {
                return true
            }
        },
        show1() {
            let identity = JSON.parse(localStorage.getItem('identity'))
            return identity == 1 ? true : identity == 2 ? true : false;
        },
        userId() {
            return localStorage.getItem('userId')
        },
        userName() {
            return localStorage.getItem('userName')
        },
        identity() {
            let iden = JSON.parse(localStorage.getItem('identity'))
            return iden == 0 ? '学生' : iden == 1 ? '教师' : '管理员'
        },
    },
    mounted() {
        if (this.isLogin) {
            /* 轮询得到消息 间隔5秒 不稳定 */
            this.getMessage()
            // this.timer = setInterval(() => {
            //     // 需要定时执行的代码
            //     this.getMessage()
            // }, 5000)
        }
        /* 监听 新闻和公告 */
        socket.on('article_event', (data) => {
            data.messageTime = '刚刚'
            this.messageList.unshift(data)
        })
    },
    // 销毁
    destroyed() {
        clearInterval(this.timer)
    },
    components: {
    },
    methods: {
        ...mapActions('login', ['logout']),
        ...mapActions('message', ['deleteMessageByUserId']),
        /**
         * 访问用户头像
         */
        visitUserHead() {
            const userHead = localStorage.getItem('userHead')
            if (userHead == 'null') {
                // 空白头像
                return require('../assets/img/图标/空白头像.png')
            } else {
                return userHead
            }
        },
        /* 去登录页 */
        toLoginPage() {
            this.$router.replace({ path: '/login' })
        },
        /* 去注册页 */
        toRegPage() {
            this.$router.replace({ path: '/reg' })
        },
        /* 导航 */
        gotoPage(index) {
            switch (index) {
                case 0: {
                    this.$router.replace({ path: '/main' })
                    break;
                };
                case 1: {
                    this.$router.replace({ path: '/competition_hall' })
                    break;
                };
                case 2: {
                    this.$router.replace({ path: '/activity_hall' })
                    break;
                }
                case 3: {
                    this.$router.replace({ path: '/team_hall' })
                    break;
                }
                case 4: {
                    this.$router.replace({ path: '/bbs' })
                    break;
                }
            }
        },
        // 页面跳后台
        jumpToBackStage(index) {
            // 如果已经在后台
            if (this.$route.name == 'BackStage') {
                this.$router.push({ query: { focusIndex: index } })
                location.reload()
                // 如果在其他页面
            } else {
                this.$router.push({ path: '/backstage', query: { focusIndex: index } })
            }
        },
        /**
         * 注销
         */
        async doLogout() {
            // TODO
            let { code, message, data } = await this.logout()
            if (code === 200) {
                /* 断开socket.io连接 */
                socket.io.opts.query = "token=" + JSON.parse(localStorage.getItem('access-user'))
                socket.disconnect();

                /* 清空localstorage */
                window.localStorage.removeItem("access-user");
                ElementUI.Message.success('成功注销')

                // 定位到登录
                setTimeout(() => {
                    this.$router.replace({ path: '/login' })
                }, 1000)
            }
        },
        /**
         * 查询消息
         */
        ...mapActions('message', ['selectMessageByUserId']),
        async getMessage() {
            let userId = localStorage.getItem('userId')
            const { code, message, data } = await this.selectMessageByUserId(userId)
            if (code === 200) {
                this.messageList = [...data]
                this.messageList.forEach((item) => {
                    let now = this.$moment(new Date()).valueOf()
                    let time = this.$moment(item.messageTime).valueOf()
                    let x = now - time;
                    if (now - time < 3600000) {
                        item.messageTime = '刚刚'
                    } else if (x > 3600000 && x < 7200000) {
                        item.messageTime = '1小时前'
                    } else if (x > 7200000 && x < 86400000) {
                        item.messageTime = '今天'
                    } else if (x > 86400000 && x < 2592000000) {
                        item.messageTime = '1天前'
                    } else if (x > 2592000000) {
                        item.messageTime = '1个月前'
                    } else {
                        item.messageTime = this.$moment(item.messageTime).format('MM-DD')
                    }
                })
            }
        },
        /**
         * 清空消息
         */
        async doDelAllMessage() {
            let userId = localStorage.getItem('userId')
            var formData = new window.FormData()
            formData.append('userId', userId)
            const { code, message, data } = await this.deleteMessageByUserId(formData)
            if (code === 200) {
                this.messageList = []
            }
        }
    }
}
</script>

<style lang="less" scoped>
.nav {
    position: fixed;
    top: 0;
    height: 60px;
    width: 100%;
    overflow: hidden;
    box-shadow: 2px 2px 10px #dbdbdb;
    background-color: white;
    z-index: 1;
    // color: #2ad8ac;

    .nav-logo {
        position: relative;
        left: 16.5%;
        top: 9%;

        .nav-logo-img {
            width: 2.5%;
        }
    }

    .nav-menu {
        position: absolute;
        top: 5%;
        left: 22%;
        width: 50%;
        height: 90%;
        display: flex;
        align-items: center;
        // color: #dfa019e8;

        .nav-menu-item {
            height: 50%;
            line-height: 150%;
            margin-right: 45px;
            font-size: 18px;
            cursor: pointer;
            color: #6d6d6d;
        }

        .nav-menu-item:hover {
            color: #000000;
        }
    }

    .nav-no-login {
        position: absolute;
        top: 5%;
        left: 80%;
        width: 50%;
        height: 90%;
        display: flex;
        align-items: center;

        .no-login-item {
            font-size: 20px;
            color: #6d6d6d;
            margin-right: 10px;
        }

        .no-login-item-real {
            cursor: pointer;
        }

        .no-login-item-real:hover {
            color: #000000;
        }
    }

    .nav-online {
        position: absolute;
        top: 5%;
        left: 80%;
        width: 50%;
        height: 90%;
        display: flex;
        align-items: center;

        ::v-deep .el-badge__content.is-fixed {
            right: 18px;
            top: 15px;
            font-size: 12px;
        }

        .nav-online-message-box-logo {
            position: relative;
            right: 0px;
            width: 40px;
            height: 40px;
            border-radius: 20%;
            margin-right: 20px;
            cursor: pointer;

            .message-box-img {
                width: 40px;
                height: 40px;
            }

            .message-box-red-point {
                position: absolute;
                top: 10px;
                right: 8px;
                border: 1px solid red;
                background-color: red;
                border-radius: 50%;
                width: 5px;
                height: 5px;
            }
        }

        .nav-online-message-box:hover {
            background-color: #ebebeb;
        }

        .nav-admin-head {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            overflow: hidden;
            cursor: pointer;

            .nav-admin-head-img {
                width: 40px;
                height: 40px;
            }
        }

        // .admin-card::v-deep{
        //     height: 500px !important;
        // }

    }

}

.message-list {
    height: 210px;
    overflow-y: scroll;

    .message-item {
        // border: 1px dashed #989898;
        position: relative;
        height: 25px;
        line-height: 25px;
        font-size: 12px;
        overflow: hidden;
        margin-top: 5px;
        padding-bottom: 5px;
        border-bottom: 1px dashed #c9c9c9b7;

        .m-line {
            // margin-top: 5px;
            position: relative;
            bottom: 0;
            border-bottom: 1px dashed #909399;
        }

        .message-type {
            position: absolute;
            left: 10px;
            width: 70px;
            height: 25px;
            text-align: center;
            color: #ebebeb;

            .m-type1 {
                position: absolute;
                left: 0;
                top: 2.5px;
                height: 20px;
                line-height: 20px;
                width: 33px;
                // background-color: #138542;
                border-radius: 5px;
                color: #ffffff;
            }

            .m-type2 {
                position: absolute;
                right: 0;
                height: 20px;
                top: 2.5px;
                line-height: 20px;
                width: 33px;
                border-radius: 5px;

            }
        }



        .message-content {
            position: absolute;
            left: 85px;
            width: 180px;
            cursor: pointer;
            // font-weight: lighter;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
        }

        .message-time {
            position: absolute;
            left: 270px;
            width: 50px;
            text-align: right;
            color: #909399;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
        }
    }
}

.doDelAllMessage-btn {
    position: relative;
    // top: 190px;
    line-height: 30px;
    text-align: center;
    height: 30px;
    // color: #fff;
    background-color: #e9e9e962;
    cursor: pointer;
}

.doDelAllMessage-btn:hover {
    background-color: #dfdfdf62;
}

/* 定义滚动条样式 */
::-webkit-scrollbar {
    width: 4px;
    height: 6px;
    background-color: rgba(240, 240, 240, 0);
}

/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track {
    box-shadow: inset 0 0 0px rgba(240, 240, 240, 0);
    border-radius: 10px;
    background-color: rgba(240, 240, 240, 0);
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
    border-radius: 10px;
    box-shadow: inset 0 0 0px rgba(240, 240, 240, 0.185);
    background-color: rgba(158, 158, 158, 0.089);
}
</style>