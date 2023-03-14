<template>
    <div class="backstage-view1 ">
        <div class="backstage-model1 ">
            <!-- 头像 -->
            <!-- <img :src="visitUserHead()" alt="" class="user-head"> -->
            <el-upload class="avatar-uploader" action="#" :multiple="false" :auto-upload="true" :show-file-list="false"
                :before-upload="beforeAvatarUpload" :on-progress="uploadHead">
                <img v-if="user.userHead" :src="user.userHead" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <!-- name -->
            <div class="user-name ">
                <span style="margin-right: 10px;">
                    {{ user.userName }}
                </span>
                <span class="set-real-name-btn" v-if="user.realName == null || user.realName == ''"
                    @click="dialogVisible = true">
                    去实名>
                </span>
            </div>
            <!-- 实名 -->
            <el-dialog title="实名认证" class="thisblack-bgc" :modal="false" :visible.sync="dialogVisible" width="30%">
                <div class="real-name-dialog ">
                    <el-input maxlength="10" placeholder="请输入真实姓名" class="real-name-input" size="small" v-model="realName"
                        clearable>
                    </el-input>
                    <span style="margin-left: 10px;color: #999; font-size: 12px;">
                        （认证后无法修改，请慎重）
                    </span>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" size="small" round @click="setRealName(); dialogVisible = false;">确
                        定</el-button>
                    <el-button round size="small" @click="dialogVisible = false;">取 消</el-button>
                </span>
            </el-dialog>
            <!-- 身份 -->
            <div class="user-identity" style="background-color: #1b8d4e;" v-if="user.identity == 2">{{
                identity[user.identity] }}</div>
            <div class="user-identity" style="background-color: #17a7ac;" v-if="user.identity == 0 || user.identity == 1">{{
                identity[user.identity] }}</div>
            <!-- 入站时间 -->
            <div class="user-reg-time">入驻时间：{{ user.regDate.substring(0, 10) }}</div>
            <!-- 已实名 -->
            <div class="user-real-name" v-if="user.realName != null && user.realName != ''">
                已实名: {{ user.realName }}
            </div>
            <div class="model-down">
                <!-- 去个人主页 -->
                <div class="to-user-page ts02s" @click="$router.push({ path: '/user_details', query: { userId: user.id} })">个人主页
                    <i class="el-icon-arrow-right"></i>
                </div>
            </div>
        </div>
        <!-- 基本信息 -->
        <div class="backstage-model1 ">
            <div class="model2-title ">基本信息</div>
            <div class="line1"></div>
            <!-- list -->
            <div class="info-list">
                <!-- 1 -->
                <div class="info-item">
                    <span class="info-title ">用户昵称</span>
                    <span class="info-content " @mouseenter="mouseover(0)" @mouseleave="mouseleave(0)">
                        <span v-if="infoStatus[0]">
                            <span class="content-p">
                                {{ user.userName }}
                            </span>
                            <span class="info-edit-btn" v-if="infoEditBtnShow[0]" @click="infoStatusChange(0)">
                                <i class="el-icon-edit-outline"></i>
                                编辑
                            </span>
                        </span>
                        <span v-if="!infoStatus[0]">
                            <el-input v-model="user2.userName" placeholder="请输入内容" size="small"
                                style="width: 200px;margin-right: 20px;" maxlength="8"></el-input>
                            <el-button type="success" size="small" round @click="infoUpdate()">提交</el-button>
                            <el-button type="info" size="small" round @click="infoStatusChange(0)">取消</el-button>
                        </span>
                    </span>
                </div>
                <div class="info-item">
                    <span class="info-title "> 用&nbsp;&nbsp;户&nbsp;&nbsp;ID</span>
                    <span class="info-content ">{{ user.id }}</span>
                </div>
                <!-- 2 -->
                <div class="info-item">
                    <span class="info-title ">个人简介</span>
                    <span class="info-content " @mouseenter="mouseover(1)" @mouseleave="mouseleave(1)">
                        <span v-if="infoStatus[1]">
                            <span class="content-p">
                                {{ user.intro }}
                            </span>
                            <span class="info-edit-btn" v-if="infoEditBtnShow[1]" @click="infoStatusChange(1)">
                                <i class="el-icon-edit-outline"></i>
                                编辑
                            </span>
                        </span>
                        <span v-if="!infoStatus[1]">
                            <el-input type="textarea" resize="none" :rows="2" v-model="user2.intro" placeholder="请输入内容" size="small"
                                style="width: 500px;margin-right: 20px;"></el-input>
                            <el-button type="success" size="small" round @click="infoUpdate()">提交</el-button>
                            <el-button type="info" size="small" round @click="infoStatusChange(1)">取消</el-button>
                        </span>
                    </span>
                </div>
                <!-- 3 -->
                <div class="info-item">
                    <span class="info-title ">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</span>
                    <span class="info-content " @mouseenter="mouseover(2)" @mouseleave="mouseleave(2)">
                        <span v-if="infoStatus[2]">
                            <span class="content-p">
                                {{ sex[user.sex] }}
                            </span>
                            <span class="info-edit-btn" v-if="infoEditBtnShow[2]" @click="infoStatusChange(2)">
                                <i class="el-icon-edit-outline"></i>
                                编辑
                            </span>
                        </span>
                        <span v-if="!infoStatus[2]">
                            <el-radio v-model="user2.sex" :label="0" style="margin-right: 30px;">女</el-radio>
                            <el-radio v-model="user2.sex" :label="1" style="margin-right: 30px;">男</el-radio>
                            <el-button type="success" size="small" round @click="infoUpdate()">提交</el-button>
                            <el-button type="info" size="small" round @click="infoStatusChange(2)">取消</el-button>
                        </span>
                    </span>
                </div>
                <!-- 4 -->
                <div class="info-item">
                    <span class="info-title ">电话号码</span>
                    <span class="info-content " @mouseenter="mouseover(3)" @mouseleave="mouseleave(3)">
                        <span v-if="infoStatus[3]">
                            <span class="content-p">
                                {{ user.phone }}
                            </span>
                            <span class="info-edit-btn" v-if="infoEditBtnShow[3]" @click="infoStatusChange(3)">
                                <i class="el-icon-edit-outline"></i>
                                编辑
                            </span>
                        </span>
                        <span v-if="!infoStatus[3]">
                            <el-input v-model="user2.phone" placeholder="请输入内容" size="small"
                                style="width: 200px;margin-right: 20px;"></el-input>
                            <el-button type="success" size="small" round @click="infoUpdate()">提交</el-button>
                            <el-button type="info" size="small" round @click="infoStatusChange(3)">取消</el-button>
                        </span>
                    </span>
                </div>
                <!-- 5 -->
                <div class="info-item">
                    <span class="info-title ">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;院</span>
                    <span class="info-content " @mouseenter="mouseover(4)" @mouseleave="mouseleave(4)">
                        <span v-if="infoStatus[4]">
                            <span class="content-p">
                                {{ user.college }}
                            </span>
                            <span class="info-edit-btn" v-if="infoEditBtnShow[4]" @click="infoStatusChange(4)">
                                <i class="el-icon-edit-outline"></i>
                                编辑
                            </span>
                        </span>
                        <span v-if="!infoStatus[4]">
                            <el-select v-model="user2.college" class="reg-item-college-input"
                                style="width: 200px;margin-right: 20px;">
                                <el-option v-for="item in collegeOptions" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                            <el-button type="success" size="small" round @click="infoUpdate()">提交</el-button>
                            <el-button type="info" size="small" round @click="infoStatusChange(4)">取消</el-button>
                        </span>
                    </span>
                </div>
                <!-- 6 -->
                <div class="info-item">
                    <span class="info-title ">专业班级</span>
                    <span class="info-content " @mouseenter="mouseover(5)" @mouseleave="mouseleave(5)">
                        <span v-if="infoStatus[5]">
                            <span class="content-p">
                                {{ user.specialty }}{{ user.clazz }}
                            </span>
                            <span class="info-edit-btn" v-if="infoEditBtnShow[5]" @click="infoStatusChange(5)">
                                <i class="el-icon-edit-outline"></i>
                                编辑
                            </span>
                        </span>
                        <span v-if="!infoStatus[5]">
                            <el-input v-model="user2.specialty" placeholder="专业名称: 软件工程（例）" size="small"
                                style="width: 200px;margin-right: 20px;"></el-input>
                            <el-input v-model="user2.clazz" placeholder="班级: 192（例）" size="small"
                                style="width: 150px;margin-right: 20px;"></el-input>
                            <el-button type="success" size="small" round @click="infoUpdate()">提交</el-button>
                            <el-button type="info" size="small" round @click="infoStatusChange(5)">取消</el-button>
                        </span>
                    </span>
                </div>
                <!-- 7 -->
                <div class="info-item">
                    <span class="info-title ">{{
                        user.identity == 0 ? '学' :
                        '工'
                    }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</span>
                    <span class="info-content " @mouseenter="mouseover(6)" @mouseleave="mouseleave(6)">
                        <span v-if="infoStatus[6]">
                            <span class="content-p">
                                {{ user.num }}
                            </span>
                            <span class="info-edit-btn" v-if="infoEditBtnShow[6]" @click="infoStatusChange(6)">
                                <i class="el-icon-edit-outline"></i>
                                编辑
                            </span>
                        </span>
                        <span v-if="!infoStatus[6]">
                            <el-input v-model="user2.num" placeholder="请输入内容" size="small"
                                style="width: 200px;margin-right: 20px;"></el-input>
                            <el-button type="success" size="small" round @click="infoUpdate()">提交</el-button>
                            <el-button type="info" size="small" round @click="infoStatusChange(6)">取消</el-button>
                        </span>
                    </span>
                </div>
                <!-- 8 -->
                <div class="info-item">
                    <span class="info-title ">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</span>
                    <span class="info-content " @mouseenter="mouseover(7)" @mouseleave="mouseleave(7)">
                        <span v-if="infoStatus[7]">
                            <span class="content-p">
                                {{ user.email }}
                            </span>
                            <span class="info-edit-btn" v-if="infoEditBtnShow[7]" @click="infoStatusChange(7)">
                                <i class="el-icon-edit-outline"></i>
                                编辑
                            </span>
                        </span>
                        <span v-if="!infoStatus[7]">
                            <el-input v-model="user2.email" placeholder="请输入内容" size="small"
                                style="width: 400px;margin-right: 20px;"></el-input>
                            <el-button type="success" size="small" round @click="infoUpdate()">提交</el-button>
                            <el-button type="info" size="small" round @click="infoStatusChange(7)">取消</el-button>
                        </span>
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ElementUI from 'element-ui';
import { mapActions } from 'vuex';

export default {
    data: () => ({
        imageUrl: '',
        user: {
            id: '',
            userName: '',
            userHead: '',
            identity: '',
            phone: '',
            sex: '',
            college: '',
            specialty: '',
            clazz: '',
            intro: '',
            num: '',
            realName: '',
            email: '',
            status: '',
            regDate: '',
        },
        user2: {
            id: '',
            userName: '',
            userHead: '',
            identity: '',
            phone: '',
            sex: '',
            college: '',
            specialty: '',
            clazz: '',
            intro: '',
            num: '',
            realName: '',
            email: '',
            status: '',
            regDate: '',
        },
        collegeOptions: [{
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
        identity: ['学生', '教师', '管理员'],
        sex: ['女', '男'],
        infoEditBtnShow: [false, false, false, false, false, false, false],
        infoStatus: [true, true, true, true, true, true, true, true],
        // 实名
        dialogVisible: false,
        realName: ''
    }),
    mounted() {
        this.getUserInfo()
    },
    methods: {
        /**
         * 访问用户头像
         */
        // visitUserHead() {
        //     if (this.user.userHead == '') {
        //         // 空白头像
        //         return require('../../assets/img/图标/空白头像.png')
        //     } else {
        //         return this.user.userHead
        //     }
        // },
        ...mapActions('user', ['selectOneByPhone']),
        ...mapActions('user', ['updateOneById']),
        /**
         * 查用户信息
         */
        async getUserInfo() {
            let phone = JSON.parse(localStorage.getItem('phone'));
            const { code, message, data } = await this.selectOneByPhone(phone)
            if (code === 200) {
                this.user = { ...data }
                this.user2 = { ...data }
            } else {
                ElementUI.Message.error(this.$GLOBALERROR)
            }
        },
        mouseover(index) {
            this.$set(this.infoEditBtnShow, index, true)
        },
        mouseleave(index) {
            this.$set(this.infoEditBtnShow, index, false)
        },
        infoStatusChange(index) {
            this.infoStatus[index] = !this.infoStatus[index]
            // 用户信息校正
            this.user2 = { ...this.user }
        },
        ...mapActions("login", ["regVerifyUserName"]),
        ...mapActions("login", ["regVerifyPhone"]),
        ...mapActions("login", ["regVerifyNumber"]),
        ...mapActions("login", ["regVerifyEmail"]),
        async infoUpdate() {
            // 校验name
            if (this.user2.userName != this.user.userName) {
                if (this.user2.userName == '' || String(this.user2.userName).includes(" ")) {
                    ElementUI.Message.error('非法的用户昵称')
                    return
                }
                const { code, message, data } = await this.regVerifyUserName(this.user2.userName)
                if (code != 200) {
                    this.infoStatus[0] = true;
                    this.user2 = { ...this.user }
                    return;
                }
            }
            // 校验phone
            if (this.user2.phone != this.user.phone) {
                var pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
                if (!pattern.test(this.user2.phone)) {
                    ElementUI.Message.error('请输入正确的手机号码')
                    return
                }
                const { code, message, data } = await this.regVerifyPhone(this.user2.phone)
                if (code != 200) {
                    this.infoStatus[3] = true;
                    this.user2 = { ...this.user }
                    return;
                }
            }
            // 校验num
            if (this.user2.num != this.user.num) {
                if ((String(this.user2.num).length != 6 && String(this.user2.num).length != 12)
                    || this.user2.num == '' || String(this.user2.num).includes(" ")) {
                    ElementUI.Message.error('请输入正确的学/工号')
                    return
                }
                const { code, message, data } = await this.regVerifyNumber(this.user2.num)
                if (code != 200) {
                    this.infoStatus[6] = true;
                    this.user2 = { ...this.user }
                    return;
                }
            }
            // 校验email
            if (this.user2.email != this.user.email) {
                var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
                if (!pattern.test(this.user2.email)) {
                    ElementUI.Message.error('请输入正确的邮箱格式')
                    return
                }
                const { code, message, data } = await this.regVerifyEmail(this.user2.email)
                if (code != 200) {
                    this.infoStatus[7] = true;
                    this.user2 = { ...this.user }
                    return;
                }
            }
            /**
             * 修改个人信息
             */
            const { code, message, data } = await this.updateOneById(this.user2);
            if (code === 200) {
                ElementUI.Message.success(this.$EDITSUCCESS)
                localStorage.setItem("userName", this.user2.userName);
                setTimeout(() => {
                    location.reload()
                }, 1000)
            }
        },
        /**
         * 实名认证
         */
        async setRealName() {
            let pattern = /^(([a-zA-Z+\.?\·?a-zA-Z+]{2,30}$)|([\u4e00-\u9fa5+\·?\u4e00-\u9fa5+]{2,4}$))/
            if (!pattern.test(this.realName)) {
                ElementUI.Message.warning('请输入正确的姓名')
                this.realName = ''
                return
            }
            this.user2.realName = this.realName
            const { code, message, data } = await this.updateOneById(this.user2)
            if (code === 200) {
                ElementUI.Message.success('实名认证成功！')
                setTimeout(() => {
                    location.reload()
                }, 1000)
            }
        },
        /**
         * 上传头像
         * @param {*} event 
         * @param {*} file 
         * @param {*} fileList 
         */
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        ...mapActions('user', ['uploadHeadImg']),
        async uploadHead(event, file, fileList) {
            var formData = new window.FormData();
            formData.append("file", file.raw);
            var userId = localStorage.getItem('userId')
            formData.append("id", userId)
            const { code, message, data } = await this.uploadHeadImg(formData)
            if (code === 200) {
                // this.user.userHead = data
                ElementUI.Message.success(this.$UPLOADSUCCESS)
                localStorage.setItem('userHead', data)
                setTimeout(() => {
                    location.reload()
                }, 1000)
            }
        }
    }
}
</script>

<style lang="less" scoped>
.backstage-view1 {
    position: absolute;
    top: 80px;
    left: 565px;
    width: 950px;

    .backstage-model1 {
        position: relative;
        width: 100%;
        box-shadow: 0px 0px 15px #4d4d4d2c;
        // border: 1px solid #96969621;
        margin-bottom: 20px;
    }

    .backstage-model1:nth-child(1) {
        height: 200px;

        .user-head {
            position: absolute;
            top: 25px;
            left: 30px;
            width: 100px;
            height: 100px;
            border-radius: 50%;
        }

        ::v-deep .avatar-uploader .el-upload {
            border: 1px dashed #d9d9d9;
            // border-radius: 6px;
            cursor: pointer;
            // position: relative;
            overflow: hidden;

            position: absolute;
            top: 25px;
            left: 30px;
            width: 100px;
            height: 100px;
            border-radius: 50%;
        }

        ::v-deep .avatar-uploader .el-upload:hover {
            border-color: #409EFF;
        }

        ::v-deep .avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;

            text-align: center;

            position: absolute;
            top: 25px;
            left: 26.5px;
            width: 50px;
            height: 50px;
            line-height: 50px;
            border-radius: 50%;
        }

        ::v-deep .avatar {
            // width: 178px;
            // height: 178px;
            // display: block;

            position: relative;
            // top: 25px;
            // left: -1px;
            width: 100px;
            height: 100px;
            // border-radius: 50%;
        }

        .user-name {
            position: absolute;
            top: 30px;
            left: 145px;
            width: 200px;
            font-size: 20px;
            font-weight: 500;

            .set-real-name-btn {
                font-size: 13px;
                font-weight: lighter;
                cursor: pointer;
                color: #318ad3;

            }
        }

        .real-name-dialog {
            .real-name-input {
                width: 300px;
            }
        }

        .user-identity {
            position: absolute;
            top: 69px;
            left: 145px;
            font-size: 14px;
            // background-color: #1b8d4e;
            // background-color: #17a7ac;
            color: #ffffff;
            padding-top: 2px;
            padding-bottom: 2px;
            padding-left: 5px;
            padding-right: 5px;
            border-radius: 5px;
        }

        .user-reg-time {
            position: absolute;
            top: 105px;
            left: 145px;
            font-size: 14px;
        }

        .user-real-name {
            position: absolute;
            bottom: 42px;
            right: 17px;
            font-size: 13px;
            color: #318ad3;
        }

        .model-down {
            position: absolute;
            bottom: 0;
            height: 35px;
            width: 100%;
            background-color: #fafafa;

            .to-user-page {
                position: absolute;
                right: 0px;
                height: 35px;
                line-height: 35px;
                font-size: 14px;
                padding-left: 10px;
                padding-right: 10px;
                color: #909399;
                cursor: pointer;
            }

            .to-user-page:hover {
                color: #304ba1;
                background-color: #e0e0e034;
            }
        }
    }

    .backstage-model1:nth-child(2) {
        // height: 500px;
        padding-bottom: 50px;

        .model2-title {
            position: relative;
            font-size: 20px;
            font-weight: bold;
            height: 50px;
            line-height: 50px;
            padding-left: 20px;
        }

        .line1 {
            position: relative;
            border-top: 1px solid #90939938;
            margin-bottom: 10px;
        }

        .info-list {
            position: relative;
            width: 100%;
            font-size: 14px;

            .info-item {
                position: relative;
                width: 100%;
                height: 50px;
                line-height: 50px;

                .info-title {
                    position: absolute;
                    color: #222226;
                    width: 120px;
                    text-align: center;
                }

                .info-content {
                    position: absolute;
                    width: 750px;
                    color: #222226;
                    left: 120px;
                    text-align: left;
                    padding-left: 20px;
                    padding-right: 20px;

                    .content-p {
                        padding-right: 20px;
                    }

                    .info-edit-btn {
                        color: #318ad3;
                        cursor: pointer;
                    }

                }
            }
        }
    }
}


/* 修改下拉选择的样式 */
.el-range-editor.is-active,
.el-range-editor.is-active:hover,
::v-deep .el-select .el-input.is-focus .el-input__inner {
    border-color: #22aa66;

}

/* 修改button的样式 */
// ::v-deep .el-button:focus, .el-button:hover{
//     border-color: #DCDFE6;
//     color: #22aa66;
//     background-color: #ffffff00;
// }
::v-deep .el-button {

    width: 70px;
}
</style>