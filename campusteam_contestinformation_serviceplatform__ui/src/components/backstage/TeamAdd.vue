<template>
    <div class="backteamLabel-view3 ">
        <div class="backteamLabel-model3 ">
            <div class="model-title">
                创建队伍&nbsp;
                <i class="el-icon-lollipop"></i>
                <span style="font-size: 13px;">
                    &nbsp;&nbsp;(需实名)
                </span>
            </div>
            <div class="line"></div>
            <!-- 队伍名称 -->
            <div class="form-item">
                <div class="item-title">
                    队&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名
                </div>
                <div class="item-content">
                    <el-input class="input1" placeholder="请输入内容" suffix-icon="el-icon-collection-tag" size="middle"
                        v-model="team.teamName">
                    </el-input>
                </div>
            </div>
            <!-- 头像 -->
            <div class="team-avator">
                <div class="avator-text">logo：</div>
                <template v-for="item, index in avators">
                    <div class="avator-item" @click="team.teamLogo = item; chooseLogo(index)">
                        <img :src="item" alt="" class="avator-img">
                    </div>
                </template>
            </div>
            <!-- 学院 -->
            <div class="form-item">
                <div class="item-title">
                    学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;院
                </div>
                <div class="item-content" style="display: flex;">
                    <el-select class="input4" v-model="team.teamCollege" size="middle" placeholder="请选择">
                        <el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                    <span style="position: relative;left: 20px;font-size: 12px;color: #c7c7c7;">
                        (非必填)
                    </span>
                </div>
            </div>
            <!-- 专业 -->
            <div class="form-item">
                <div class="item-title">
                    专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业
                </div>
                <div class="item-content" style="display: flex;">
                    <el-input class="input4" placeholder="请输入内容" suffix-icon="el-icon-menu" size="middle"
                        v-model="team.teamSpecialty">
                    </el-input>
                    <span style="position: relative;left: 20px;font-size: 12px;color: #c7c7c7;">
                        (非必填)
                    </span>
                </div>
            </div>
            <!-- 人数上限 -->
            <div class="form-item fi1">
                <div class="item-title-sp1">
                    &nbsp;人数上限
                </div>
                <!-- 队伍人数上下限设置 -->
                <div class="slider">
                    <el-slider class="slider-middle" v-model="team.teamMemberNumMax" :marks="marks" :min="1" :max="12">
                    </el-slider>
                    <div class="slider-right">
                        上限 {{ team.teamMemberNumMax }} 人
                    </div>
                </div>
            </div>
            <!-- 队伍简介 -->
            <div class="form-item fi3">
                <div class="item-title">
                    简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介
                </div>
                <!-- 内容添加与删除 -->
                <div class="item-content-sp3 ">
                    <div class="nr-input">
                        <el-input type="textarea" resize="none" :rows="4" placeholder="请输入内容" v-model="team.teamIntro">
                        </el-input>
                    </div>
                </div>
            </div>
            <!-- 标签 -->
            <div class="form-item fi2">
                <div class="item-title">
                    标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签
                </div>
                <!-- 标签添加与删除 -->
                <div class="item-content-sp2 ">
                    <div class="label-item ts02s" v-for="item, index in teamLabel" @mouseover="setDelBtn0(index)"
                        @mouseleave="setDelBtn1(index)">
                        <span class="label-content">
                            {{ item }}
                        </span>
                        <transition name="el-zoom-in-center">
                            <div class="label-del" v-show="teamLabelDelBtn[index]" @click="delLabel(index)">
                                <i class="el-icon-close"></i>
                            </div>
                        </transition>
                    </div>
                    <div class="label-add-btn3 el-icon-plus ts02s" @click="dialogVisible1 = true;"
                        v-if="teamLabel.length == 0">
                        添加标签</div>
                    <div class="label-add-btn el-icon-plus ts02s" @click="dialogVisible1 = true;"
                        v-if="teamLabel.length != 0 && teamLabel.length < 5">
                    </div>
                    <div class="label-add-btn2 ts02s" @click="teamLabel = []" v-if="teamLabel.length != 0">
                        清除
                    </div>
                </div>
            </div>
            <div class="form-item "></div>
            <!-- 确认按钮 -->
            <div class="form-item ">
                <div class="joined-is-open">创建后立即招新</div>
                <el-switch class="switch2" v-model="team.teamStatus" active-color="#13ce66" inactive-color="#909399">
                </el-switch>
                <div class="do-add-team-btn ts02s" @click="doAddTeam()">
                    创建
                </div>
            </div>
            <div class="form-item"></div>
            <!-- 添加标签 -->
            <el-dialog title="添加标签" class="thisblack-bgc" :modal="false" :visible.sync="dialogVisible1" width="30%">
                <div class="label-add-title">
                    <span class="title-content">
                        标签名
                    </span>
                    <el-input maxlength="10" placeholder="请输入内容" class="label-input" size="small" v-model="teamLabelName"
                        clearable>
                    </el-input>
                    <span style="margin-left: 10px;color: #999; font-size: 12px;">
                        （10字以内）
                    </span>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" round @click="addLabelitem();">确 定</el-button>
                    <el-button round @click="clearLabelitem();">取 消</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import ElementUI from 'element-ui';
import { mapActions } from 'vuex';

export default {
    data: () => ({
        team: {
            teamName: '',
            teamLogo: 'http://localhost:8088/resources/logo_1.png',
            teamCollege: '',
            teamSpecialty: '',
            teamMemberNumMax: 4,
            /**
             * 需要boolean转tinyint的 false 0 true 1
             */
            teamStatus: true,
            /**
             * 需要stringfy的
             */
            teamLabel: '',
            /**
             * 需要取值的
             */
            teamCaptionId: '', // 发起人id
        },
        avators: [
            'http://localhost:8088/resources/logo_1.png',
            'http://localhost:8088/resources/logo_2.png',
            'http://localhost:8088/resources/logo_3.png',
            'http://localhost:8088/resources/logo_4.png',
            'http://localhost:8088/resources/logo_5.png',
            'http://localhost:8088/resources/logo_6.png',
            'http://localhost:8088/resources/logo_7.png',
            'http://localhost:8088/resources/logo_8.png',
            'http://localhost:8088/resources/logo_9.png',
            'http://localhost:8088/resources/logo_10.png',
            'http://localhost:8088/resources/logo_11.png',
            'http://localhost:8088/resources/logo_12.png',
            'http://localhost:8088/resources/logo_13.png',
            'http://localhost:8088/resources/logo_14.png',
            'http://localhost:8088/resources/logo_15.png',
            'http://localhost:8088/resources/logo_16.png',
            'http://localhost:8088/resources/logo_17.png',
            'http://localhost:8088/resources/logo_18.png',
        ],
        /**
         * 选择人数组件值显示tip
         */
        marks: {
            1: '',
            2: '2',
            4: '4',
            6: '6',
            8: '8',
            10: '10',
            12: ''
        },
        /**
         * 学院的选择
         */
        options2: [{
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
        teamLabel: ['Java', 'C++', 'Python', 'ABC'],
        /**
         * 阶段
         */
        teamLabelDelBtn: [false, false, false, false, false, false],
        dialogVisible1: false,
        teamLabelName: '',
    }),
    methods: {
        ...mapActions('team', ['insertTeam']),
        /**
         * 选择logoCss
         * @param {*} index 
         */
        chooseLogo(index) {
            let logoArr = document.getElementsByClassName('avator-item')
            for (let i = 0; i < logoArr.length; i++) {
                if (i == index) {
                    logoArr[i].classList.add('avator-item-selected');
                    logoArr[i].classList.remove('avator-item-unselected');
                } else {
                    logoArr[i].classList.add('avator-item-unselected');
                    logoArr[i].classList.remove('avator-item-selected');
                }
            }
        },
        /**
         * 控制标签删除按钮的显示
         * @param {*} index 
         */
        setDelBtn0(index) {
            this.$set(this.teamLabelDelBtn, index, true)
        },
        /**
         * 控制标签删除按钮的隐藏
         * @param {*} index 
         */
        setDelBtn1(index) {
            this.$set(this.teamLabelDelBtn, index, false)
        },
        /**
         * 添加标签HTML
         */
        addLabelitem() {
            if (this.teamLabelName == '') {
                ElementUI.Message.error('标签信息为空')
                return
            }
            if (this.teamLabelName.length > 10) {
                ElementUI.Message.error('数据过长')
                return
            }
            this.teamLabel.push(this.teamLabelName)
            this.dialogVisible1 = false
        },
        /**
         * 删除标签HTML
         * @param {*} index 
         */
        delLabel(index) {
            this.teamLabel.splice(index, 1)
        },
        /**
         * 清除标签HTML
         */
        clearLabelitem() {
            this.teamLabelName = ''
            this.teamLabelIntro = ''
            this.teamLabelTime = []
            this.dialogVisible1 = false
        },
        /**
         * 创建队伍
         */
        async doAddTeam() {
            const team = { ...this.team }
            // TODO:判断各项非空
            // const arr = Object.values(team)
            // if (arr.includes("")) {
            //     ElementUI.Message.error('信息为空')
            //     return
            // }
            // 负责人 当前用户id
            team.teamCaptionId = parseInt(localStorage.getItem('userId'))
            // 招新 或不招新
            team.teamStatus = team.teamStatus ? 1 : 0
            // 标签
            team.teamLabel = JSON.stringify(this.teamLabel)
            // 提交数据库
            const { code, message, data } = await this.insertTeam(team)
            if (code === 200) {
                let resTeamId = data.teamId
                // 跳转详情页
                ElementUI.Message.success('成功创建新队伍，赶紧摇人吧！')
                setTimeout(() => {                    
                    ElementUI.Message.success('队伍邀请码：' + data.teamInviteCode)
                }, 1000)
                setTimeout(() => {
                    this.$router.push({ path: 'team_details', query: { teamId: resTeamId } })
                }, 3000)
            }
        },
        fun(a, obj) {
            return a in obj
        }
    },
    mounted() {
        this.chooseLogo(1)
    }
}
</script>

<style lang="less" scoped>
.backteamLabel-view3 {
    position: relative;
    height: auto;

    top: 80px;
    left: 565px;
    width: 950px;
    box-shadow: 0px 0px 10px #4d4d4d2c;

    .backteamLabel-model3 {
        position: relative;
        width: 100%;
        height: auto;


        .model-title {
            padding-left: 35px;
            font-size: 20px;
            height: 55px;
            line-height: 55px;
            font-weight: lighter;
        }

        .line {
            width: 100%;
            border-top: 1px dashed #90939957;
            margin-bottom: 20px;
        }

        .team-avator {
            position: absolute;
            width: 336px;
            height: 168px;
            top: 90px;
            left: 550px;
            z-index: 2;
            display: flex;
            flex-wrap: wrap;
            border-right: 3px dashed #ebebebd7;
            border-bottom: 3px dashed #ebebebd7;

            .avator-text {
                position: absolute;
                left: -45px;
                font-size: 14px;
                color: #909399;
            }

            .avator-item {
                width: 50px;
                height: 50px;
                // border: 3px dashed #96969600;
                cursor: pointer;
                border: 3px dashed #ebebebd7;
                border-right: 3px dashed #ebebeb00;
                border-bottom: 3px dashed #ebebeb00;
                font-size: 0;

                .avator-img {
                    position: relative;
                    left: 5px;
                    top: 4px;
                    width: 45px;
                    height: 45px;
                }

            }

            .avator-item:hover {
                border: 3px dashed #1adfadd7;
                // background-color: #b5ffbfe1;
            }

            .avator-item-selected {
                transition: all 0.5s ease-in-out;
                background-color: #c5ffcea8;
                border: 3px dashed #1adfadd7;
            }

            .avator-item-unselected {
                background-color: none;
                border: 3px dashed #ebebebd7;
                border-right: 3px dashed #ebebeb00;
                border-bottom: 3px dashed #ebebeb00;
            }
        }

        .form-item {
            position: relative;
            display: flex;
            font-size: 14px;
            text-align: left;
            height: 65px;
            line-height: 65px;
            width: 100%;
            // font-weight: lighter;

            .item-title {
                position: relative;
                width: 130px;
                text-align: center;
            }

            .item-title-sp1 {
                position: relative;
                left: 35px;
            }

            .item-content {
                position: absolute;
                left: 130px;
                height: 100%;

                .input1 {
                    width: 330px;
                }

                .input2 {
                    width: 260px;
                }

                .input3 {
                    width: 260px;
                }

                .input4 {
                    width: 260px;
                }

                .input5 {
                    width: 400px;
                }

                .input6 {
                    width: 500px;
                }

            }

            .item-content-sp2 {
                display: flex;

                .label-item {
                    position: relative;
                    top: 30px;
                    // width: 100px;
                    height: 25px;
                    line-height: 25px;
                    font-size: 12px;
                    text-align: center;
                    // overflow: hidden;
                    border: 3px dashed #60c270be;
                    color: #909399;
                    cursor: pointer;
                    background-color: #68dd7cda;
                    color: #fff;
                    margin-left: 10px;
                    // border-left: 3px dashed #90939900;
                    // border-right: 3px dashed #90939900;


                    .label-content {
                        // text-align: center;
                        position: relative;
                        height: 100%;
                        // top: -30px;
                        padding-left: 20px;
                        padding-right: 20px;
                    }

                    .label-del {
                        position: absolute;
                        top: -6px;
                        right: -8px;
                        width: 15px;
                        height: 15px;
                        font-size: 12px;
                        background-color: #ff5e5e;
                        color: #fff;
                        text-align: center;
                        line-height: 15px;
                        border-radius: 50%;
                        cursor: pointer;
                        // opacity: 0;
                    }

                }

                .label-item:hover {
                    border: 3px dashed #3fa350ea;
                }



                .label-add-btn {
                    position: relative;
                    top: 30px;
                    margin-left: 15px;
                    width: 30px;
                    height: 30px;
                    background-color: #5dcf70d7;
                    border-radius: 50%;
                    color: #fff;
                    line-height: 30px;
                    text-align: center;
                    cursor: pointer;
                }

                .label-add-btn2 {
                    position: relative;
                    top: 30px;
                    margin-left: 10px;
                    width: 50px;
                    height: 30px;
                    background-color: #c267606b;
                    border-radius: 20px;
                    color: #fff;
                    line-height: 30px;
                    text-align: center;
                    cursor: pointer;
                }

                .label-add-btn3 {
                    position: relative;
                    top: 30px;
                    margin-left: 10px;
                    width: 90px;
                    height: 30px;
                    background-color: #60c270fa;
                    border-radius: 20px;
                    color: #fff;
                    line-height: 30px;
                    text-align: center;
                    cursor: pointer;
                }

                .label-add-btn2:hover {
                    background-color: #c5473cc7;
                }

                .label-add-btn:hover {
                    background-color: #60c270;
                }

                .label-add-btn3:hover {
                    background-color: #60c270;
                }
            }

            .item-content-sp3 {
                position: relative;
                top: 5px;
                width: 750px;

                .nr-input {
                    position: relative;
                    top: -5px;
                }
            }

            .slider {
                position: relative;
                left: 75px;
                width: 600px;
                margin-left: 10px;
                margin-right: 20px;
                text-align: center;
                display: flex;

                .slider-middle {
                    position: relative;
                    top: 20px;
                    width: 500px;
                    line-height: normal;
                }

                .slider-right {
                    width: 120px;
                    margin-left: 20px;
                    font-weight: lighter;
                }
            }


            .joined-is-open {
                position: absolute;
                right: 300px;
                font-size: 12px;
                height: 50px;
                line-height: 50px;
                color: #909399;
            }

            .switch2 {
                position: absolute;
                right: 250px;
                font-size: 12px;
                height: 50px;
                line-height: 50px;
            }

            .do-add-team-btn {
                position: absolute;
                right: 60px;
                height: 50px;
                line-height: 50px;
                width: 160px;
                text-align: center;
                background-color: #eec147;
                color: #fff;
                font-size: 16px;
                border-radius: 3px;
                cursor: pointer;
            }

            .do-add-team-btn:hover {
                background-color: #e0b337;
            }

        }


        .fi1 {
            height: 80px;
            line-height: 80px;
        }

        .fi2 {
            height: 90px;
            line-height: 90px;
            margin-bottom: 20px;
        }

        .fi3 {
            margin-top: 15px;
            margin-bottom: 30px;
            height: auto;
            line-height: normal;
        }

        .thisblack-bgc {
            background-color: #00000049;

            .label-add-title {
                position: relative;
                font-size: 14px;
                margin-bottom: 25px;

                .title-content {
                    position: relative;
                    top: 0;
                    margin-left: 20px;
                    margin-right: 20px;
                }

                .label-input {
                    width: 250px;
                    font-size: 12px;
                }
            }
        }

    }
}

::v-deep .el-dialog__body {
    padding: 10px 20px;
}

::v-deep .el-dialog__header {
    padding: 20px 20px 20px;
}

// ::v-deep .el-slider__bar {
//     background-color: #3ac251;
// }
</style>

<style>
/* .el-slider__button {
    border: 2px solid #3ac251;
} */
</style>