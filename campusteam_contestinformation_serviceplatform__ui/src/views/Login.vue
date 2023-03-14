<template>
    <!-- 屏幕框 -->
    <div class="main-login" id="main">
        <Nav></Nav>
        <!-- 主框 -->
        <transition name="fade">
            <div class="login-main " v-show="show">
                <!-- 登录 -->
                <div class="sign-in ">
                    <!-- 登录主框 -->
                    <div class="sign-in-main ">
                        <div class="sign-in-tip" v-show="methodChoose == 0">Sign in</div>
                        <!-- 密码登录方式 -->
                        <div class="sign-in-item sign-in-methods">
                            <div class="sign-in-methods-default" style="margin-left: 5px;" v-show="methodChoose == 0">
                                密码登录</div>
                        </div>
                        <!-- 密码登录 -->
                        <transition name="fade">
                            <div class="sign-in-password" v-show="methodChoose == 0">
                                <!-- phone -->
                                <div class="password-item ">
                                    <el-input placeholder="请输入手机号" v-model="user.phone" clearable></el-input>
                                </div>
                                <!-- code -->
                                <div class="password-item ">
                                    <el-input placeholder="请输入密码" v-model="user.password" show-password
                                        @keyup.enter.native="doLogin"></el-input>
                                </div>
                                <!-- sign-in-button -->
                                <div class="password-item">
                                    <div class="sign-in-button " @click="doLogin">Sign in</div>
                                </div>
                            </div>
                        </transition>
                    </div>
                    <!-- 协议声明 -->
                    <div class="deal-tip">
                        <div>没有账号？去注册
                            <span style="color: #95a56f;cursor: pointer;" @click="toReg">Sign up</span>
                        </div>
                        <div>
                            登录或完成注册即代表您同意
                            <span style="color: #95a56f;cursor: pointer;">用户协议</span>
                            和
                            <span style="color: #95a56f;cursor: pointer;">隐私协议</span>
                        </div>
                    </div>
                </div>
                <!-- 中间的线 -->
                <div class="line"></div>
                <!-- logo -->
                <div class="logo ">
                    <!-- logo主框 -->
                    <div class="logo-main ">
                        <!-- logo图片框 -->
                        <transition name="el-zoom-in-center">
                            <div class="logo-img-div " v-show="show2">
                                <img class="logo-img " src="../assets/img/logo.png" alt="">
                            </div>
                        </transition>
                        <!-- 品牌介绍/口号 -->
                        <transition name="el-fade-in-linear">
                            <div class="logo-item intro-text" v-show="show2">
                                <div>校园组队与竞赛信息服务平台</div>
                            </div>
                        </transition>
                        <transition name="el-fade-in-linear">
                            <!-- About -->
                            <div class="logo-item about-text" v-show="show2">
                                <div>About Us</div>
                            </div>
                        </transition>
                    </div>
                </div>
            </div>
        </transition>
        <Foot class="reg-foot"></Foot>
        <!-- 装饰球 -->
        <div class="trims-ball ball-right " id="ball-right"></div>
        <div class="trims-ball ball-left " id="ball-left"></div>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import ElementUI from 'element-ui'
import { mapActions } from "vuex";
import socket from '@/assets/util/socket';
export default {
    data: () => ({
        /**
         * 渐变效果控制变量
         */
        show: false,
        /**
         * 渐变效果控制变量2
         */
        show2: false,
        /**
         * 登录方式选择变量，此项目只实现了密码登录
         */
        methodChoose: 0,
        /**
         * md5加密固定盐
         */
        salt: 'salt^.^',
        /**
         * 密码登录绑定对象
         */
        user: {
            phone: '',
            password: ''
        }
    }),
    mounted() {
        setTimeout(() => {
            /**
             * 渐变弹出效果控制
             */
            this.show = !this.show
            setTimeout(() => {
                document.getElementById('ball-left').classList.add('trims-ball-active-left')
                document.getElementById('ball-right').classList.add('trims-ball-active-right')
                setTimeout(() => {
                    this.show2 = !this.show2
                }, 500)
            }, 250)
        }, 300)
    },
    methods: {
        /**
         * 登录
         */
        ...mapActions("login", ["login"]),
        /**
         * 去往注册页面
         */
        toReg() {
            this.$router.push('/reg')
        },
        /**
         * 登录函数
         */
        async doLogin() {
            // 判断非空
            if (this.user.phone == '' || this.user.password == '') {
                ElementUI.Message.error('请输入完整的账号密码')
                return
            }
            // md5盐加密 不可在网络中传递明文密码
            this.user.password = this.$md5(this.salt + this.user.password);
            // 登录
            const { code, message, data } = await this.login(this.user)
            if (code === 200) {
                // 设置localStorage
                localStorage.setItem("access-user", JSON.stringify(data.token));
                localStorage.setItem("userId", data.id);
                localStorage.setItem("phone", JSON.stringify(data.phone));
                localStorage.setItem("identity", JSON.stringify(data.identity));
                localStorage.setItem("userName", data.userName);
                localStorage.setItem("userHead", data.userHead);
                // 成功提示
                ElementUI.Message.success('登录成功！')
                // 重定向到首页
                this.$router.replace({ path: "/" });
                // socketio 建立连接
                socket.io.opts.query = "token=" + data.token
                socket.connect();

            }

        },
    },
    components: {
        Nav, Foot
    },
}
</script>


<style lang="less" scoped>
.main-login {
    width: 100%;
    height: 100vh;
    // overflow: hidden;
    transition: all 1s;
    /* flex布局 */
    display: flex;
    /* flex布局 元素水平居中 */
    justify-content: center;
    /* flex布局 元素竖直居中 */
    align-items: center;
    /* 背景渐变色 */
    background-image: linear-gradient(to top, #fceab2 0%, #78eba4 100%);
    overflow: scroll;

    .reg-foot {
        position: absolute;
        width: 100%;
        bottom: 0;
    }

}


.login-main {
    position: relative;
    width: 1000px;
    height: 650px;
    /* flex布局 */
    display: flex;
    /* flex布局 元素水平居中 */
    justify-content: center;
    /* flex布局 元素竖直居中 */
    align-items: center;
    /* 阴影 */
    box-shadow: 5px 5px 20px 2px rgba(0, 0, 0, 0.1);
    /* Z-index */
    z-index: 1;


    .sign-in {
        position: absolute;
        left: 0;
        height: 100%;
        width: 49.5%;
        background-color: #ffffffe8;
        /* flex */
        display: flex;
        flex-direction: column;
        /* flex布局 元素水平居中 */
        justify-content: center;
        /* flex布局 元素竖直居中 */
        align-items: center;
        /* 圆角 */
        border-radius: 0 45px 45px 0;

        .sign-in-main {
            position: relative;
            height: 80%;
            width: 80%;
            font-family: "Microsoft YaHei";
            font-weight: lighter;

            /* Sign in 字样 */
            .sign-in-tip {
                font-size: 30px;
            }

            /* sign-in-item */
            .sign-in-item {
                width: 100%;
                height: 50px;
                margin-top: 20px;
                margin-bottom: 20px;
            }

            /* 登录方式 */
            .sign-in-methods {
                display: flex;
                font-size: 15px;
                /* 垂直居中 */
                align-items: center;

                .sign-in-methods-default {
                    height: 20px;
                    // cursor: pointer;
                    // transition: all 0.2s;
                }

                // .sign-in-methods-default:hover {
                //     color: #95a56f;
                // }
            }

            /* 短信、密码 登录框 */
            .sign-in-sms,
            .sign-in-password {
                position: absolute;
                width: 100%;

                .sms-item,
                .password-item {
                    width: 100%;
                    margin-top: 20px;
                    margin-bottom: 30px;
                }

                /* 验证码发送按钮 */
                .send-code {
                    cursor: pointer;
                }

                /* 登录按钮 */
                .sign-in-button {
                    height: 45px;
                    line-height: 45px;

                    font-size: 19px;
                    text-align: center;

                    color: #fff;
                    background-color: #2ddd85;

                    transition: all 1s;
                    margin-top: 60px;

                    cursor: pointer;
                }

                .sign-in-button:hover {
                    background-color: #22aa66;
                }

            }


        }

        /* 协议 */
        .deal-tip {
            position: relative;
            width: 80%;
            height: 40px;
            line-height: 20px;
            font-size: 14px;
            text-align: left;
            color: #b3b3b3;
            cursor: default;
        }
    }


    .line {
        position: absolute;
        height: 80%;
        border-right: 1px solid rgba(209, 209, 209, 0.103);
        background-color: rgba(255, 255, 255, 0.411);
    }

    .logo {
        position: absolute;
        right: 0;
        height: 100%;
        width: 49.5%;
        background-color: #ffffffe8;
        /* flex */
        display: flex;
        flex-direction: column;
        /* flex布局 元素水平居中 */
        justify-content: center;
        /* flex布局 元素竖直居中 */
        align-items: center;
        /* 圆角 */
        border-radius: 45px 0 0 45px;

        .logo-main {
            position: relative;
            width: 70%;
            height: 80%;
            /* flex */
            display: flex;
            flex-direction: column;
            /* flex布局 元素水平居中 */
            justify-content: center;
            /* flex布局 元素竖直居中 */
            align-items: center;
            border-bottom: 1px solid #6261a72c;


            /* LOGO */
            .logo-img-div {
                position: absolute;
                right: 80px;
                top: 30px;
                z-index: 2;

                .logo-img {
                    width: 180px;
                    height: 180px;
                }
            }

            .logo-item {
                margin-top: 30px;
            }

            /* 口号 */
            .intro-text {
                font-size: 20px;
                font-weight: lighter;
                font-family: "宋体";
                color: #6e6e6e;
            }

            /* About Us */
            .about-text {
                font-size: 20px;
                font-weight: lighter;
                font-family: "微软雅黑";
                color: #6e6e6e;
                transition: all 1s;
                z-index: 3;
                cursor: pointer;
            }

            .about-text:hover {
                color: #95a56f;
            }
        }
    }
}

/* 球 */
.trims-ball {
    position: absolute;
    border-radius: 50%;
    /* 过度 */
    transition: all 3.5s;
    /* Z-index */
    z-index: 0;
    /* 可见度 */
    opacity: 0;
}

.ball-right {
    /* 渐变色 */
    background-image: linear-gradient(60deg, #fbed962f 0%, #f8f29531 100%);
    // background-image: linear-gradient(to top, #ff9a9e 0%, #fecfef 99%, #fecfef 100%);
    /* 阴影 */
    box-shadow: 10px 10px 30px 0 #8cbb7e85;
    width: 600px;
    height: 600px;
    right: -500px;
    top: -500px;
}

.ball-left {
    background-image: linear-gradient(60deg, #abecd625 0%, #9ceba33b 100%);
    // background-image: linear-gradient(to top, #43eb9c 0%, #fab236 100%);
    /* 阴影 */
    box-shadow: 10px 10px 30px 0 #8cbb7e85;
    width: 600px;
    height: 600px;
    left: -500px;
    bottom: -500px;
}

.trims-ball-active-right {
    width: 1100px;
    height: 1100px;
    opacity: 1;
}

.trims-ball-active-left {
    width: 1100px;
    height: 1100px;
    opacity: 1;
}

/**fade fade2 渐变效果 */
.fade-enter-active,
.fade-leave-active {
    transition: opacity 2s;
}

.fade-leave-to,
.fade-enter {
    opacity: 0
}

.fade-enter-to,
.fade-leave {
    opacity: 1
}



.fade2-enter-active,
.fade2-leave-active {
    transition: opacity 2s;
}

.fade2-enter-to,
.fade2-leave {
    opacity: 1
}

.fade2-leave-to,
.fade2-enter {
    opacity: 0
}

/* 修改element—ui button样式 */
.el-input.isactive .el-input__inner,
::v-deep .el-input__inner:focus {
    border-color: #22aa66;
}
</style>