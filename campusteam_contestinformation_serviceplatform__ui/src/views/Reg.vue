<template>
    <div class="main-reg">
        <Nav></Nav>
        <Transition name="el-zoom-in-center">
            <div class="reg-form" v-show="show">
                <div class="reg-form-point "></div>
                <!-- title -->
                <div class="reg-form-title ">
                    Sign Up
                </div>
                <!-- inputs -->
                <div class="reg-form-inputs ">
                    <!-- item-昵称和手机号 -->
                    <div class="reg-form-inputs-item ">
                        <!-- 昵称 -->
                        <div class="reg-item-username ">
                            <div class="reg-item-username-text">昵称：</div>
                            <el-input class="reg-item-username-input " placeholder="请输入1~8个字符的昵称" v-model="user.userName"
                                clearable size="medium" suffix-icon="el-icon-user" minlength="1" maxlength="8"
                                @change="userNameChange" @focus="userNameFocus"></el-input>
                            <!-- 绿标 -->
                            <i class="username-icon-yes el-icon-circle-check" v-show="userNameTipsGroupShow[0]"></i>
                            <!-- 红标 -->
                            <i class="username-icon-no el-icon-circle-close opacity0"
                                v-show="!userNameTipsGroupShow[0]"></i>
                            <!-- 提示词 -->
                            <div class="username-tips ">
                                <!-- 非法昵称 -->
                                <div class="illegal-username" v-show="userNameTipsGroupShow[1]" style="color: #ff0101;">
                                    非法昵称
                                </div>
                                <!-- 昵称已被占用 -->
                                <div class="occupied-username" v-show="userNameTipsGroupShow[2]" style="color: #ff0101;">
                                    已存在的昵称（被占用）</div>
                            </div>
                        </div>
                        <!-- 手机号 -->
                        <div class="reg-item-phone ">
                            <div class="reg-item-phone-text ">手机号：</div>
                            <el-input class="reg-item-phone-input " placeholder="请输入手机号" v-model="user.phone" clearable
                                size="medium" suffix-icon="el-icon-phone" type="number" maxlength="11" @change="phoneChange"
                                @focus="phoneFocus"></el-input>
                            <!-- 绿标 -->
                            <i class="phone-icon-yes el-icon-circle-check" v-show="phoneTipsGroupShow[0]"></i>
                            <!-- 红标 -->
                            <i class=" phone-icon-no el-icon-circle-close opacity0" v-show="!phoneTipsGroupShow[0]"></i>
                            <!-- 提示词 -->
                            <div class="phone-tips ">
                                <!-- 非法昵称 -->
                                <div class="illegal-phone" v-show="phoneTipsGroupShow[1]" style="color: #ff0101;">非法手机号
                                </div>
                                <!-- 昵称已被占用 -->
                                <div class="occupied-phone" v-show="phoneTipsGroupShow[2]" style="color: #ff0101;">
                                    已注册的手机号（被占用）</div>
                            </div>
                        </div>
                    </div>
                    <!-- item-学号/工号 和 学院 -->
                    <div class="reg-form-inputs-item ">
                        <!-- 学院 -->
                        <div class="reg-item-college ">
                            <div class="reg-item-college-text ">学院：</div>
                            <el-select v-model="user.college" placeholder="请选择" class="reg-item-college-input">
                                <el-option v-for="item in options" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                        <!-- 学号/工号 -->
                        <div class="reg-item-num ">
                            <div class="reg-item-num-text">学号/工号：</div>
                            <el-input class="reg-item-num-input " placeholder="请输入学号/工号" v-model="user.num" clearable
                                size="medium" suffix-icon="el-icon-star-off" maxlength="12" @change="numberChange"
                                @focus="numberFocus"></el-input>
                            <!-- 绿标 -->
                            <i class="num-icon-yes el-icon-circle-check " v-show="numberTipsGroupShow[0]"></i>
                            <!-- 红标 -->
                            <i class="num-icon-no el-icon-circle-close opacity0" v-show="!numberTipsGroupShow[0]"></i>
                            <!-- 提示词 -->
                            <div class="num-tips ">
                                <!-- 非法 学号/工号 -->
                                <div class="illegal-num" v-show="numberTipsGroupShow[1]" style="color: #ff0101;">非法
                                    学号/工号
                                </div>
                                <!-- 学号/工号 已被占用 -->
                                <div class="occupied-num" v-show="numberTipsGroupShow[2]" style="color: #ff0101;">已存在的
                                    学号/工号
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- item-密码 -->
                    <div class="reg-form-inputs-item ">
                        <!-- 密码 -->
                        <div class="reg-item-password">
                            <div class="reg-item-password-text">输入密码：</div>
                            <el-input class="reg-item-password-input " placeholder="请输入6~18位数密码" v-model="password"
                                clearable size="medium" suffix-icon="el-icon-key" minlength="6" maxlength="18" type="number"
                                show-password @change="passwordChange" @focus="passwordFocus"></el-input>
                            <!-- 绿标 -->
                            <i class="password-icon-yes el-icon-circle-check " v-show="passwordTipsGroupShow[0]"></i>
                            <!-- 红标 -->
                            <i class="password-icon-no el-icon-circle-close opacity0"
                                v-show="!passwordTipsGroupShow[0]"></i>
                            <!-- 提示词 -->
                            <div class="password-tips ">
                                <!-- 密码过短 -->
                                <div v-show="passwordTipsGroupShow[1]" style="color: #ff0101;">密码过短或过长</div>
                            </div>
                        </div>
                    </div>
                    <!-- item-确认密码 -->
                    <div class="reg-form-inputs-item">
                        <!-- 确认密码 -->
                        <div class="reg-item-password2">
                            <div class="reg-item-password2-text">确认密码：</div>
                            <el-input class="reg-item-password2-input " placeholder="请再输入一次相同的密码" v-model="password2"
                                clearable size="medium" suffix-icon="el-icon-key" minlength="6" maxlength="11" type="number"
                                show-password @change="password2Change"></el-input>
                            <!-- 绿标 -->
                            <i class="password2-icon-yes el-icon-circle-check opacity0" v-show="!password2Verify"></i>
                            <!-- 红标 -->
                            <i class="password2-icon-no el-icon-circle-close " v-show="password2Verify"></i>
                            <!-- 提示词 -->
                            <div class="password2-tips ">
                                <!-- 密码不一致 -->
                                <div v-show="password2Verify" style="color: #ff0101;">密码不一致</div>
                            </div>
                        </div>
                    </div>
                    <!-- item-邮箱 -->
                    <div class="reg-form-inputs-item">

                        <div class="reg-item-email">
                            <div class="reg-item-email-text">邮箱号：</div>
                            <el-input placeholder="请输入您的邮箱" v-model="user.email" class="reg-item-email-input"
                                @change="emailChange" @focus="emailFocus">
                                <template slot="append">.com</template>
                            </el-input>
                            <!-- 绿标 -->
                            <i class="email-icon-yes el-icon-circle-check " v-show="emailTipsGroupShow[0]"></i>
                            <!-- 红标 -->
                            <i class="email-icon-no el-icon-circle-close opacity0" v-show="!emailTipsGroupShow[0]"></i>
                            <!-- 提示词 -->
                            <div class="email-tips ">
                                <!-- 非法邮箱 -->
                                <div v-show="emailTipsGroupShow[1]" style="color: #ff0101;">非法邮箱</div>
                                <!-- 非法邮箱 -->
                                <div v-show="emailTipsGroupShow[2]" style="color: #ff0101;">邮箱已注册</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="deal-tip">已有账号，去登录
                    <span style="color: #95a56f;cursor: pointer;" @click="toLogin">Sign in</span>
                </div>
                <!-- button -->
                <div class="reg-form-button ts1s" @click="doReg">提交</div>
                <!-- 动漫背景人物 -->
                <div class="reg-genie">
                    <img src="../assets/img/ai/注册精灵.png" alt="" class="reg-genie-img">
                </div>
            </div>
        </Transition>
        <!-- 装饰球 -->
        <div class="trims-ball ball-right " id="ball-right"></div>
        <div class="trims-ball ball-left " id="ball-left"></div>
        <!-- foot -->
        <Foot class="reg-foot"></Foot>
    </div>
</template>

<script>
import { mapActions } from "vuex";
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import ElementUI from 'element-ui'
export default {
    data: () => ({
        input: '',
        /**
         * 渐变效果控制变量
         */
        show: false,
        /**
         * 固定盐
         */
        salt: 'salt^.^',
        /**
         * 密码1
         */
        password: '',
        /**
         * 密码2
         */
        password2: '',
        /**
         * 注册绑定对象
         */
        user: {
            userName: '',
            phone: '',
            college: '',
            num: '',
            password: '',
            email: ''
        },
        /**
         * 学院选择对象数组
         */
        options: [{
            value: '信息与通信工程学院',
            label: '信息与通信工程学院'
        }, {
            value: '轮机学院',
            label: '轮机学院'
        }, {
            value: '艺术学院',
            label: '艺术学院'
        }, {
            value: '外语学院',
            label: '外语学院'
        }, {
            value: '邮轮游艇学院',
            label: '邮轮游艇学院'
        }, {
            value: '海事学院',
            label: '海事学院'
        }],
        /**
         * 昵称 ui提示数组 成功判断变量
         */
        userNameTipsGroupShow: [false, false, false],
        userNameVerify: false,
        /**
         * 昵称 ui提示 成功判断变量
         */
        phoneTipsGroupShow: [false, false, false],
        phoneVerify: false,
        /**
         * 学号/工号 ui提示 成功判断变量
         */
        numberTipsGroupShow: [false, false, false],
        numberVerify: false,
        /**
         * 密码 ui提示 成功判断变量
         */
        passwordTipsGroupShow: [false, false],
        passwordVerify: false,
        /**
         * 邮箱 ui提示 成功判断变量
         */
        emailTipsGroupShow: [false, false, false],
        emailVerify: false,
    }),
    computed: {
        /**
         * 确认密码 成功判断变量
         */
        password2Verify() {
            return (this.password == '' && this.password2 == '') || (this.password != '' && this.password2 == '')
                ? false : this.password !== this.password2
        }
    },
    methods: {
        /**
         * 注册接口
         */
        ...mapActions("login", ["register"]),
        /**
         * 检测重复接口
         */
        ...mapActions("login", ["regVerifyUserName"]),
        ...mapActions("login", ["regVerifyNumber"]),
        ...mapActions("login", ["regVerifyPhone"]),
        ...mapActions("login", ["regVerifyEmail"]),
        /**
         * 去登陆页面
         */
        toLogin() {
            this.$router.push('/login')
        },
        /**
         * UserName Input 提示控制
         */
        userNameFocus() {
            document.getElementsByClassName('username-icon-no')[0].classList.add('opacity0')
            if (this.user.userName == '') {
                this.userNameTipsGroupShow = [false, false, false]
            }
        },
        async userNameChange(userName) {
            document.getElementsByClassName('username-icon-no')[0].classList.remove('opacity0')
            if (userName == '' || String(userName).includes(" ")) {
                this.userNameTipsGroupShow = [false, true, false]
                this.userNameVerify = false
                return
            }
            let { code, message, data } = await this.regVerifyUserName(userName)
            if (code === 200) {
                this.userNameTipsGroupShow = [true, false, false]
                this.userNameVerify = true
            } else {
                this.userNameTipsGroupShow = [false, false, true]
                this.userNameVerify = false
            }
        },
        /**
         * Phone Input 提示控制 
         * 
         */
        phoneFocus() {
            document.getElementsByClassName('phone-icon-no')[0].classList.add('opacity0')
            if (this.user.phone == '') {
                this.phoneTipsGroupShow = [false, false, false]
            }
        },
        async phoneChange(phone) {
            document.getElementsByClassName('phone-icon-no')[0].classList.remove('opacity0')
            var pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
            if (!pattern.test(phone)) {
                this.phoneTipsGroupShow = [false, true, false]
                this.phoneVerify = false
                return
            }
            let { code, message, data } = await this.regVerifyPhone(phone)
            if (code === 200) {
                this.phoneTipsGroupShow = [true, false, false]
                this.phoneVerify = true
            } else {
                this.phoneTipsGroupShow = [false, false, true]
                this.phoneVerify = false
            }
        },
        /**
         * Number Input 提示控制
         */
        numberFocus() {
            document.getElementsByClassName('num-icon-no')[0].classList.add('opacity0')
            if (this.user.number == '') {
                this.numberTipsGroupShow = [false, false, false]
            }
        },
        async numberChange(number) {
            document.getElementsByClassName('num-icon-no')[0].classList.remove('opacity0')
            if ((String(number).length != 6 && String(number).length != 12)
                || number == '' || String(number).includes(" ")) {
                this.numberTipsGroupShow = [false, true, false]
                this.numberVerify = false
                return
            }
            let { code, message, data } = await this.regVerifyNumber(number)
            if (code === 200) {
                this.numberTipsGroupShow = [true, false, false]
                this.numberVerify = true
            } else {
                this.numberTipsGroupShow = [false, false, true]
                this.numberVerify = false
            }
        },
        /**
         * Password Input 提示控制
         */
        passwordFocus() {
            document.getElementsByClassName('password-icon-no')[0].classList.add('opacity0')
            if (this.user.password == '') {
                this.passwordTipsGroupShow = [false, false]
            }
        },
        passwordChange(password) {
            document.getElementsByClassName('password-icon-no')[0].classList.remove('opacity0')
            if (String(password).length < 6 || String(password).length > 18 || password == '') {
                this.passwordTipsGroupShow = [false, true]
                this.passwordVerify = false
                return
            } else {
                this.passwordTipsGroupShow = [true, false]
                this.passwordVerify = true
            }
        },
        password2Change(password2) {
            if (password2 == '') {
                document.getElementsByClassName('password2-icon-yes')[0].classList.add('opacity0')
            } else {
                document.getElementsByClassName('password2-icon-yes')[0].classList.remove('opacity0')
            }
        },
        /**
         * Email Input 提示控制
         */
        emailFocus() {
            document.getElementsByClassName('email-icon-no')[0].classList.add('opacity0')
            if (this.user.email == '') {
                this.emailTipsGroupShow = [false, false, false]
            }
        },
        async emailChange(email) {
            document.getElementsByClassName('email-icon-no')[0].classList.remove('opacity0')
            var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
            if (!pattern.test(email)) {
                this.emailTipsGroupShow = [false, true, false]
                this.emailVerify = false
                return
            }
            let { code, message, data } = await this.regVerifyEmail(email)
            if (code === 200) {
                this.emailTipsGroupShow = [true, false, false]
                this.emailVerify = true
            } else {
                this.emailTipsGroupShow = [false, false, true]
                this.emailVerify = false
            }
        },
        /**
        * 注册
        */
        async doReg() {
            if (this.userNameVerify && this.phoneVerify &&
                !this.phoneVerify2 && this.numberVerify &&
                this.passwordVerify && this.emailVerify &&
                this.user.college != '' && !this.password2Verify) {
                // 加密
                this.user.password = this.$md5(this.salt + this.password);
                // console.log('password after md5', this.user.password);
                // 注册
                let { code, message, data } = await this.register(this.user)
                if (code === 200) {
                    ElementUI.Message.success('注册成功！')
                    setTimeout(() => {
                        this.$router.replace({ path: '/login' })
                    }, 500)
                }
            } else {
                ElementUI.Message.error('注册信息有误或不完整')
            }
        },
    },
    mounted() {
        /**
         * 渐变效果
         */
        setTimeout(() => {
            this.show = !this.show
            setTimeout(() => {
                document.getElementById('ball-left').classList.add('trims-ball-active-left')
                document.getElementById('ball-right').classList.add('trims-ball-active-right')
                setTimeout(() => {
                }, 500)
            }, 250)
        }, 300)
    },
    components: {
        Nav, Foot
    },
}
</script>

<style lang="less" scoped>
.main-reg {
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

    .reg-foot {
        position: absolute;
        width: 100%;
        bottom: 0;
    }
}

.reg-form {
    position: absolute;
    width: 40%;
    height: 75%;
    background-color: white;
    box-shadow: 5px 5px 20px #80808088;
    border-radius: 10px;
    z-index: 1;

    .reg-form-title {
        position: absolute;
        top: 0;
        font-size: 25px;
        width: 20%;
        height: 80px;
        font-weight: lighter;
        line-height: 80px;
        text-align: center;
    }

    .reg-form-point {
        position: absolute;
        top: 3.5%;
        left: 92.5%;
        width: 25px;
        height: 25px;
        border-radius: 50%;
        // background-color: #78eba4;
        background-image: linear-gradient(to top, #fceab2 0%, #78eba4 100%);
        box-shadow: 2px 2px 5px inset #616161b4;
    }

    .reg-form-inputs {
        position: relative;
        width: 90%;
        height: 70%;
        left: 7%;
        top: 15%;
        z-index: 1;

        .reg-form-inputs-item {
            width: 100%;
            height: 60px;
            color: #373737;
            font-weight: 300;
            margin-bottom: 15px;


            .reg-item-username {
                position: absolute;
                width: 47%;
                height: 37px;
                left: 0;

                .reg-item-username-text {
                    position: absolute;
                    left: 0;
                    height: 37px;
                    line-height: 37px;
                }

                .reg-item-username-text {
                    width: 50px;
                }

                .reg-item-username-input {
                    position: absolute;
                    width: 220px;
                    left: 50px;

                }

                ::v-deep.input-red-border {
                    border: #ff0101;
                }

                .username-icon-yes {
                    position: absolute;
                    left: 280px;
                    top: 9px;
                    color: #19b45a;
                    font-size: 20px;
                }

                .username-icon-no {
                    position: absolute;
                    left: 280px;
                    top: 9px;
                    color: #ff0101;
                    font-size: 20px;
                }

                .username-tips {
                    position: absolute;
                    bottom: -20px;
                    left: 95px;
                    width: 50%;
                    font-size: 13px;
                    text-align: right;
                }

            }

            .reg-item-num {
                position: absolute;
                width: 53%;
                right: 0;
                height: 37px;

                .reg-item-num-text {
                    position: absolute;
                    left: 0;
                    height: 37px;
                    line-height: 37px;
                }

                .reg-item-num-text {
                    width: 100px;
                }

                .reg-item-num-input {
                    position: absolute;
                }

                .reg-item-num-input {
                    width: 195px;
                    left: 90px;
                }

                .num-icon-yes {
                    position: absolute;
                    left: 295px;
                    top: 9px;
                    color: #19b45a;
                    font-size: 20px;
                }

                .num-icon-no {
                    position: absolute;
                    left: 295px;
                    top: 9px;
                    color: #ff0101;
                    font-size: 20px;
                }

                .num-tips {
                    position: absolute;
                    bottom: -20px;
                    left: 95px;
                    width: 50%;
                    font-size: 13px;
                    text-align: right;
                }

            }


            .reg-item-phone {
                position: absolute;
                width: 53%;
                height: 37px;
                right: 0;

                .reg-item-phone-text {
                    position: absolute;
                    width: 65px;
                    left: 0;
                    height: 37px;
                    line-height: 37px;
                }

                .reg-item-phone-input {
                    position: absolute;
                    width: 220px;
                    left: 65px;
                }

                .phone-icon-yes {
                    position: absolute;
                    left: 295px;
                    top: 9px;
                    color: #19b45a;
                    font-size: 20px;
                }

                .phone-icon-no {
                    position: absolute;
                    left: 295px;
                    top: 9px;
                    font-size: 20px;
                    color: #ff0101;
                }

                .phone-tips {
                    position: absolute;
                    bottom: -20px;
                    left: 95px;
                    width: 50%;
                    font-size: 13px;
                    text-align: right;
                }
            }

            .reg-item-college {
                position: absolute;
                width: 47%;
                height: 37px;
                left: 0;

                .reg-item-college-text {
                    position: absolute;
                    width: 65px;
                    left: 0;
                    height: 37px;
                    line-height: 37px;
                }

                .reg-item-college-input {
                    position: absolute;
                    width: 220px;
                    left: 50px;
                }
            }

            .reg-item-password {
                position: absolute;
                width: 100%;
                height: 37px;
                left: 0;

                .reg-item-password-text {
                    position: absolute;
                    width: 95px;
                    left: 0;
                    height: 37px;
                    line-height: 37px;
                }

                .reg-item-password-input {
                    position: absolute;
                    width: 300px;
                    left: 80px;
                }

                .password-icon-yes {
                    position: absolute;
                    left: 390px;
                    top: 9px;
                    color: #19b45a;
                    font-size: 20px;
                }

                .password-icon-no {
                    position: absolute;
                    left: 390px;
                    top: 9px;
                    font-size: 20px;
                    color: #ff0101;
                }

                .password-tips {
                    position: absolute;
                    bottom: -20px;
                    left: 10px;
                    width: 50%;
                    font-size: 13px;
                    text-align: right;
                }
            }

            .reg-item-password2 {
                position: absolute;
                width: 100%;
                height: 37px;
                left: 0;

                .reg-item-password2-text {
                    position: absolute;
                    width: 95px;
                    left: 0;
                    height: 37px;
                    line-height: 37px;
                }

                .reg-item-password2-input {
                    position: absolute;
                    width: 300px;
                    left: 80px;
                }

                .password2-icon-yes {
                    position: absolute;
                    left: 390px;
                    top: 9px;
                    color: #19b45a;
                    font-size: 20px;
                }

                .password2-icon-no {
                    position: absolute;
                    left: 390px;
                    top: 9px;
                    font-size: 20px;
                    color: #ff0101;
                }

                .password2-tips {
                    position: absolute;
                    bottom: -20px;
                    left: 10px;
                    width: 50%;
                    font-size: 13px;
                    text-align: right;
                }
            }

            .reg-item-email {
                position: absolute;
                width: 100%;
                height: 37px;
                left: 0;

                .reg-item-email-text {
                    position: absolute;
                    width: 95px;
                    left: 0;
                    height: 37px;
                    line-height: 37px;
                }

                .reg-item-email-input {
                    position: absolute;
                    width: 300px;
                    left: 80px;
                }

                .email-icon-yes {
                    position: absolute;
                    left: 390px;
                    top: 9px;
                    color: #19b45a;
                    font-size: 20px;
                }

                .email-icon-no {
                    position: absolute;
                    left: 390px;
                    top: 9px;
                    font-size: 20px;
                    color: #ff0101;
                }

                .email-tips {
                    position: absolute;
                    bottom: -25px;
                    left: -45px;
                    width: 50%;
                    font-size: 13px;
                    text-align: right;
                }
            }


        }
    }

    .reg-form-button {
        position: absolute;
        right: 5%;
        bottom: 5%;
        width: 15%;
        height: 50px;
        background-color: #28c275bb;
        color: white;
        text-align: center;
        line-height: 50px;
        font-size: 18px;
        cursor: pointer;
        z-index: 1;
    }

    .reg-form-button:hover {
        background-color: #1b8a36;
    }


    .deal-tip {
        position: absolute;
        bottom: 3%;
        left: 5%;
        width: 80%;
        height: 40px;
        line-height: 20px;
        font-size: 15px;
        text-align: left;
        color: #8a8a8a;
        cursor: default;
    }

    .reg-genie {
        position: absolute;
        left: 490px;
        top: 250px;
        z-index: 0;

        .reg-genie-img {
            width: 220px;
            // background: rgba(255, 255, 255, 0.3);
            // filter: blur(3px);
        }
    }
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

/* 修改element—ui button样式 */
.el-input.isactive .el-input__inner,
::v-deep .el-input__inner:focus {
    border-color: #22aa66;
}

::v-deep input::-webkit-outer-spin-button,
::v-deep input::-webkit-inner-spin-button {
    -webkit-appearance: none !important;
}

::v-deep input[type='number'] {
    -moz-appearance: textfield !important;
}

/* 修改下拉选择的样式 */
.el-range-editor.is-active,
.el-range-editor.is-active:hover,
::v-deep .el-select .el-input.is-focus .el-input__inner {
    border-color: #22aa66;

}

// ::v-deep .el-form-item__label{
//     text-align: left;
// }

// .el-input__inner{
//     left: 0;
// }

// [data-v-0915376c]  ::v-deep .el-form-item__label{
//     width: 80px !important;
// }
</style>