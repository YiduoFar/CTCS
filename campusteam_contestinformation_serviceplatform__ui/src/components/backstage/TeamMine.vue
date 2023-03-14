<template>
    <div class="backstage-view5 ">
        <!-- 无数据 -->
        <el-empty description="您暂未参加任何队伍" style="position: relative;top: 100px;" v-if="noneDate1"></el-empty>
        <el-empty description="您暂未创建任何队伍" style="position: relative;top: 100px;" v-if="noneDate2"></el-empty>
        <!-- 有数据 -->
        <!-- 我加入的队伍 -->
        <template v-for="item, index in myJoinedTeam" v-if="show">
            <div class="backstage-model5">
                <!-- 队长头像 -->
                <img :src="item.caption.userHead" alt="" class="caption-avator">
                <img :src="item.caption.userHead" alt="" class="caption-avator-bg">
                <!-- 队长昵称 -->
                <div class="caption-name ">{{ item.caption.userName }}</div>
                <!-- 队员头像 -->
                <div class="mem-list ">
                    <img src="../../assets/img/图标/空白头像.png" alt="" class="mem-avator" v-if="item.memberList.length == 0">
                    <img :src="e.userHead" :title="e.userName" class="mem-avator" v-for="e in item.memberList">
                </div>
                <!-- 队伍Logo -->
                <img :src="item.team.teamLogo" alt="" class="team-logo">
                <!-- 队伍名称 -->
                <div class="team-name">
                    <span class="name-content ts02s"
                        @click="$router.push({ path: '/team_details', query: { teamId: item.team.teamId } })">{{
                            item.team.teamName
                        }}</span>
                </div>
                <!-- 队伍简介 -->
                <div class="team-intro ">{{ item.team.teamIntro }}</div>
                <!-- 队伍学院 -->
                <div class="team-college ">{{ item.team.teamCollege }}</div>
                <!-- 队伍专业 -->
                <div class="team-specialty ">{{ item.team.teamSpecialty }}</div>
                <!-- 队伍标签 -->
                <div class="team-label ">
                    <span class="label-item" v-for="e in JSON.parse(item.team.teamLabel)">{{
                        e
                    }}</span>
                </div>
                <!-- 创建时间 -->
                <div class="team-create-date"></div>
                <!-- 操作按钮 -->
                <div class="item-action " @click="doQuitTeam(item)">
                    <div class="action-text">
                        退出
                        <i class="el-icon-right"></i>
                    </div>
                </div>
            </div>
        </template>
        <!-- 我创建的队伍 -->
        <template v-for="item, index in myCreatedTeam" v-if="!show && change">
            <div class="backstage-model5-2">
                <!-- 队长头像 -->
                <img :src="item.caption.userHead" alt="" class="caption-avator">
                <img :src="item.caption.userHead" alt="" class="caption-avator-bg">
                <!-- 队长昵称 -->
                <div class="caption-name ">{{ item.caption.userName }}</div>
                <!-- 队员头像 -->
                <div class="mem-list ">
                    <img src="../../assets/img/图标/空白头像.png" alt="" class="mem-avator" v-if="item.memberList.length == 0">
                    <span class="invite-text" v-if="item.memberList.length == 0">邀请码 {{ item.team.teamInviteCode }}</span>
                    <img :src="e.userHead" :title="e.userName" class="mem-avator" v-for="e in item.memberList">
                </div>
                <!-- 队伍Logo -->
                <img :src="item.team.teamLogo" alt="" class="team-logo">
                <!-- 队伍名称 -->
                <div class="team-name">
                    <span class="name-content2 ts02s"
                        @click="$router.push({ path: '/team_details', query: { teamId: item.team.teamId } })">{{
                            item.team.teamName
                        }}</span>
                </div>
                <!-- 队伍简介 -->
                <div class="team-intro ">{{ item.team.teamIntro }}</div>
                <!-- 申请列表 -->
                <el-badge :value="item.applyNum" :max="99" class="nav-online-message-box-logo team-apply-list-btn"
                    slot="reference">
                    <div class="apply-list-btn" @click="team = { ...item.team }; getApplyMemList(); dialogVisible = true">
                        申请列表
                    </div>
                </el-badge>
                <!-- 队伍学院 -->
                <div class="team-college ">{{ item.team.teamCollege }}</div>
                <!-- 队伍专业 -->
                <div class="team-specialty ">{{ item.team.teamSpecialty }}</div>
                <!-- 队伍标签 -->
                <div class="team-label ">
                    <span class="label-item2" v-for="e in JSON.parse(item.team.teamLabel)">{{
                        e
                    }}</span>
                </div>
                <!-- 创建时间 -->
                <div class="team-create-date"></div>
                <!-- 操作按钮 -->
                <div class="item-action " @click="team = { ...item.team }; openEdit();">
                    <div class="action-text">
                        编辑
                        <i class="el-icon-edit"></i>
                    </div>
                </div>
            </div>
        </template>
        <div class="changeBtn " v-if="change">
            <div class="btn ts02s" @click="chooseTeam(1)">我加入的</div>
            <div class="btn ts02s" @click="chooseTeam(2)">我创建的</div>
        </div>
        <!-- 申请列表 -->
        <el-dialog title="申请列表" :visible.sync="dialogVisible" width="30%" :modal="false" class="thisblack-bgc">
            <div class="team-apply-list ">
                <!-- none -->
                <el-empty :image-size="100" description="暂无申请" v-if="applyMemlist.length == 0"></el-empty>
                <!-- list -->
                <div class="apply-item " v-for="item in applyMemlist">
                    <img :src="item.userHead" alt="" class="apply-mem-head">
                    <div class="apply-mem-name ">
                        <span class="name-content">
                            {{ item.userName }}
                        </span>
                    </div>
                    <div class="apply-mem-real-name ">真实姓名：{{ item.realName }}</div>
                    <div class="apply-mem-from " :title="item.college">{{ item.specialty }}{{ item.clazz }}</div>
                    <div class="apply-btn" @click="applyJoin(item, item.id)">同意</div>
                    <div class="rejust-btn" @click="rejectJoin(item, item.id)">婉拒</div>
                </div>
                <div class="apply-item-count" v-if="applyMemlist.length != 0">共收到 {{ applyMemlist.length }} 条申请</div>
            </div>
        </el-dialog>
        <TeamEdit v-if="!change" v-bind:teamId="team.teamId"></TeamEdit>
    </div>
</template>

<script>
import { mapActions } from 'vuex';
import ElementUI from 'element-ui';
import TeamEdit from './TeamEdit.vue';
export default {
    data: () => ({
        /* 控制显示两个列表 */
        show: false,
        /* 我加入的队伍 */
        myJoinedTeam: [],
        /* 我创建的队伍 */
        myCreatedTeam: [],
        /* team */
        team: {},
        /* 申请列表 */
        applyMemlist: [],
        dialogVisible: false,
        /* 编辑页面 */
        change: true,
    }),
    methods: {
        ...mapActions('team', ['selectTeamMemVoByCaptionId']),
        async init() {
            // 用户id
            let userId = localStorage.getItem("userId");
            const { code, message, data } = await this.selectTeamMemVoByCaptionId(userId)
            if (code === 200) {
                console.log("data", data);
                this.myJoinedTeam = [...data.myJoinedTeam];
                this.myCreatedTeam = [...data.myCreatedTeam];
            }
        },
        /* 选择队伍类型 */
        chooseTeam(i) {
            if (i == 1) {
                // css
                document.getElementsByClassName('btn')[0].classList.add('btn-1-active');
                document.getElementsByClassName('btn')[1].classList.remove('btn-2-active');
                document.getElementsByClassName('btn')[1].classList.add('btn-2-normal');
                document.getElementsByClassName('btn')[0].classList.remove('btn-1-normal');
                // list
                this.show = true;
                this.controDom()
                // 另一个list的dom
                let domArr = document.getElementsByClassName('backstage-model5-2')
                for (let i = 0; i < domArr.length; i++) {
                    domArr[i].style.top = '200px';
                    domArr[i].style.opacity = '0'
                }
            }
            if (i == 2) {
                // css
                document.getElementsByClassName('btn')[0].classList.remove('btn-1-active');
                document.getElementsByClassName('btn')[1].classList.add('btn-2-active');
                document.getElementsByClassName('btn')[1].classList.remove('btn-2-normal');
                document.getElementsByClassName('btn')[0].classList.add('btn-1-normal');
                // list
                this.show = false;
                this.controDom()
                // 另一个list的dom
                let domArr = document.getElementsByClassName('backstage-model5')
                for (let i = 0; i < domArr.length; i++) {
                    domArr[i].style.top = '200px';
                    domArr[i].style.opacity = '0'
                }
            }
        },
        /* 操作dom */
        controDom() {
            this.$nextTick(() => {
                if (this.show) {
                    let domArr = document.getElementsByClassName('backstage-model5')
                    for (let i = 0; i < domArr.length; i++) {
                        let time = 100
                        setTimeout(() => {
                            domArr[i].style.top = '0px'
                            domArr[i].style.opacity = '1'
                        }, i * time)
                    }
                } else {
                    let domArr = document.getElementsByClassName('backstage-model5-2')
                    for (let i = 0; i < domArr.length; i++) {
                        let time = 100
                        setTimeout(() => {
                            domArr[i].style.top = '0px'
                            domArr[i].style.opacity = '1'
                        }, i * time)
                    }
                }
            })
        },
        ...mapActions('user_team', ['deleteUserTeam']),
        /* 退出 */
        async doQuitTeam(item) {
            this.$confirm(item.team.teamName + "", '确定离开该队伍？', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                let userTeamDeleteVo = {
                    userId: localStorage.getItem("userId"),
                    teamId: item.team.teamId,
                    status: 1,
                    type: 1 // type 操作类型 剔除3 婉拒2 退出1
                }
                const { code, message, data } = await this.deleteUserTeam(userTeamDeleteVo)
                if (code === 200) {
                    ElementUI.Message.success('成功退出队伍')
                    setTimeout(() => {
                        location.reload()
                    }, 1000)
                }
            }).catch(() => {

            })
        },
        ...mapActions('user_team', ['selectUserListByTeamIdAndStatus']),
        /**
         * 取申请者列表
         */
        async getApplyMemList() {
            const queryVo = {
                teamId: this.team.teamId,
                status: 0 // 1已确认参加 0已申请未确认
            }
            const { code, messge, data } = await this.selectUserListByTeamIdAndStatus(queryVo)
            if (code === 200) {
                this.applyMemlist = [...data]
            } else {
                this.applyMemlist = []
            }
        },
        /**
         * 同意申请
         */
        ...mapActions('user_team', ['updateUserTeamStatusById']),
        // 同意
        async applyJoin(item, id) {
            // 同意申请
            const userTeam = {
                userId: id,
                teamId: this.team.teamId,
                status: 1
            }
            const { code, message, data } = await this.updateUserTeamStatusById(userTeam)
            if (code === 200) {
                this.applyMemlist = this.applyMemlist.filter(e => e != item)
                this.myCreatedTeam.forEach(e => {
                    if (e.team.teamId == this.team.teamId) {
                        e.applyNum -= 1
                    }
                })
            }
        },
        // 拒绝
        async rejectJoin(item, id) {
            // 拒绝申请
            const userTeamDeleteVo = {
                userId: id,
                teamId: this.team.teamId,
                status: 0,
                type: 2 // type 操作类型 剔除3 婉拒2 退出1
            }
            const { code, message, data } = await this.deleteUserTeam(userTeamDeleteVo)
            if (code === 200) {
                this.applyMemlist = this.applyMemlist.filter(e => e != item)
                this.myCreatedTeam.forEach(e => {
                    if (e.team.teamId == this.team.teamId) {
                        e.applyNum -= 1
                    }
                })
            }
        },
        openEdit() {
            this.$parent.editStatus3(false)
            this.change = false
        },
        closeEdit() {
            this.$parent.editStatus3(true)
            this.change = true
            setTimeout(()=>{
                this.chooseTeam(2)
            }, 10)
        },
        closeEdit2() {
            this.$parent.editStatus3(true)
            this.change = true
            location.reload()
        }
    },
    async created() {
        // 初始化数据
        await this.init()
        // 选择我加入的队伍
        this.chooseTeam(1)
        // 操作dom
        this.controDom()
    },
    computed: {
        noneDate1() {
            return this.myJoinedTeam.length === 0 && this.show
        },
        noneDate2() {
            return this.myCreatedTeam.length === 0 && !this.show
        },
    },
    components:{
        TeamEdit
    }
}
</script>

<style lang="less" scoped>
.backstage-view5 {
    position: absolute;
    top: 80px;
    left: 555px;
    width: 1000px;

    .backstage-model5 {
        position: relative;
        height: 130px;
        width: 95%;
        transition: top 0.7s ease-in-out;
        top: 200px;
        margin: 5px;
        margin-bottom: 10px;
        box-shadow: 0 0 10px #4d4d4d1a;
        overflow: hidden;
        border: 5px dashed #03c27200;
        opacity: 0;
        display: flex;


        .item-action {
            position: absolute;
            left: 950px;
            height: 100%;
            width: 0px;
            border: none;
            transition: all 0.3s ease-in-out;
            // background-color: #03c272dc;
            cursor: pointer;

            .action-text {
                position: absolute;
                color: #7c7c7c;
                top: 40px;
                left: 15px;
                text-align: center;
                font-size: 14px;
                width: 14px;
            }
        }

        .item-action:hover {
            background-color: #00b66adc;
        }

        .item-action:hover .action-text {
            color: #fff;
        }
    }

    .backstage-model5:hover {
        width: 100%;
        border: 5px dashed #03c272dc;
        border-left: 5px dashed #03c27200;
        border-right: 3px dashed #03c27200;
        transition: all 0.3s ease-in-out;
    }

    .backstage-model5:hover .item-action {
        width: 50px;
    }



    /*  */
    /*  */
    /*  */
    /*  */
    /*  */
    /*  */
    /*  */
    /*  */
    /*  */
    /*  */
    /*  */
    /*  */


    .backstage-model5-2 {
        position: relative;
        height: 130px;
        width: 95%;
        transition: top 0.7s ease-in-out;
        top: 200px;
        margin: 5px;
        margin-bottom: 10px;
        box-shadow: 0 0 10px #4d4d4d1a;
        overflow: hidden;
        border: 5px dashed #03c27200;
        opacity: 0;
        display: flex;


        .item-action {
            position: absolute;
            left: 950px;
            height: 100%;
            width: 0px;
            border: none;
            transition: all 0.3s ease-in-out;
            // background-color: #06c2e4dc;
            color: #7c7c7c;
            cursor: pointer;

            .action-text {
                position: absolute;
                // color: #fff;、
                top: 40px;
                left: 15px;
                text-align: center;
                font-size: 14px;
                width: 14px;
            }
        }

        .item-action:hover {
            background-color: #02b8d8dc;
        }

        .item-action:hover .action-text {
            color: #fff;
        }
    }

    .backstage-model5-2:hover {
        width: 100%;
        border: 5px dashed #06c2e4dc;
        border-left: 5px dashed #03c27200;
        border-right: 3px dashed #03c27200;
        transition: all 0.3s ease-in-out;
    }

    .backstage-model5-2:hover .item-action {
        width: 50px;
    }

    .changeBtn {
        position: absolute;
        right: -65px;
        top: 10px;
        height: 80px;
        width: 40px;
        font-size: 12px;
        letter-spacing: 3px;

        .btn {
            padding-left: 3px;
            padding-top: 5px;
            text-align: center;
            line-height: 15px;
            width: 40px;
            height: 37px;
            border: 3px dashed #06c3e400;
            cursor: pointer;
            box-shadow: 2px 2px 10px #e0e0e094;
            color: #b1b1b1;
        }

        .btn:nth-child(1):hover {
            border: 3px dashed #03c272;
            background-color: #0edf88dc;
            color: #fff;
            text-shadow: 2px 2px 5px #00000048;
        }

        .btn:nth-child(2):hover {
            border: 3px dashed #06c3e4;
            background-color: #06c3e47e;
            color: #fff;
            text-shadow: 2px 2px 5px #00000048;
        }

        .btn-1-normal {
            border: none;
            background-color: none;
            color: #b1b1b1;

            text-shadow: none;
            border: 3px dashed #06c3e400;

        }

        .btn-2-normal {
            border: none;
            background-color: none;
            color: #b1b1b1;

            text-shadow: none;
            border: 3px dashed #06c3e400;

        }

        .btn-1-active {
            border: 3px dashed #03c272;
            background-color: #0edf88dc;
            color: #fff;
            text-shadow: 2px 2px 5px #00000048;
        }

        .btn-2-active {
            border: 3px dashed #06c3e4;
            background-color: #06c3e47e;
            color: #fff;
            text-shadow: 2px 2px 5px #00000048;
        }
    }

    .backstage-model5,
    .backstage-model5-2 {
        .caption-avator {
            position: absolute;
            width: 80px;
            height: 80px;
            top: 10px;
            left: 50px;
            border-radius: 50%;

        }

        .caption-avator-bg {
            position: absolute;
            top: -250px;
            left: 50px;
            width: 1000px;
            height: 500px;
            z-index: -1;
            // 背景模糊100px
            filter: blur(200px) brightness(180%);
        }

        .caption-name {
            position: absolute;
            top: 93px;
            font-size: 14px;
            color: #7c7c7c;
            left: 45px;
            width: 90px;
            text-align: center;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
            text-shadow: 2px 2px 5px #ffffff70;
        }

        .mem-list {
            position: absolute;
            top: 65px;
            left: 140px;
            width: 250px;
            height: 40px;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;

            .mem-avator {
                width: 40px;
                border-radius: 50%;
                margin-left: 5px;
            }

            .invite-text {
                font-size: 12px;
                position: relative;
                top: -15px;
                left: 10px;
                color: #c7c7c7;
                transition: all 0.2s ease-in-out;
                text-shadow: 0px 0px 10px #fff;
            }

        }

        .team-logo {
            position: absolute;
            left: 500px;
            top: 15px;
            width: 60px;
            height: 60px;
            border-radius: 50%;
            box-shadow: 0 0 10px #ffffff0e;
        }

        .team-name {
            position: absolute;
            left: 150px;
            top: 20px;
            font-size: 20px;
            width: 230px;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
            text-shadow: 2px 2px 5px #ffffff70;

            .name-content {
                cursor: pointer;
            }

            .name-content:hover {
                color: #03c272dc;
            }

            .name-content2 {
                cursor: pointer;
            }

            .name-content2:hover {
                color: #06c3e4;
            }
        }

        .team-intro {
            position: absolute;
            color: #7c7c7c;
            left: 435px;
            width: 200px;
            height: 35px;
            // line-height: 30px;
            text-align: center;
            top: 80px;
            font-size: 12px;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            text-shadow: 2px 2px 5px #ffffffb7;
        }

        .team-apply-list-btn {
            position: absolute;
            left: 852px;
            font-size: 12px;
            color: #07a9c5;
            top: 20px;
            text-shadow: 2px 2px 5px #ffffff70;
            cursor: pointer;

            .apply-list-btn {
                text-align: right;
                width: 73px;
                height: 20px;
                line-height: 20px;
                // margin-right: 10px;
            }
        }

        .team-apply-list-btn:hover {
            color: #06c3e4;
        }

        .team-college {
            position: absolute;
            left: 780px;
            width: 145px;
            font-size: 12px;
            text-align: right;
            overflow: hidden;
            color: #7c7c7c;
            top: 75px;
            text-shadow: 2px 2px 5px #ffffff70;
        }


        .team-specialty {
            position: absolute;
            left: 780px;
            width: 145px;
            font-size: 12px;
            text-align: right;
            overflow: hidden;
            color: #7c7c7c;
            top: 55px;
            text-shadow: 2px 2px 5px #ffffff70;
        }

        .team-label {
            position: absolute;
            left: 725px;
            width: 200px;
            font-size: 12px;
            text-align: right;
            overflow: hidden;
            color: #7c7c7c;
            top: 100px;

            .label-item {
                background-color: #03c272;
                margin-left: 5px;
                padding-left: 5px;
                padding-right: 5px;
                padding-bottom: 2px;
                padding-top: 2px;
                color: #fff;
                border-radius: 5px;
            }

            .label-item2 {
                background-color: #06c3e4;
                margin-left: 5px;
                padding-left: 5px;
                padding-right: 5px;
                padding-bottom: 2px;
                padding-top: 2px;
                color: #fff;
                border-radius: 5px;
            }
        }
    }

    .thisblack-bgc {
        background-color: #00000049;
    }

    .team-apply-list {
        position: relative;
        max-height: 500px;
        min-height: 100px;
        padding-bottom: 30px;

        .apply-item {
            position: relative;
            height: 40px;
            line-height: 40px;
            border: 3px dashed #90939954;
            margin-bottom: 10px;

            .apply-mem-head {
                width: 40px;
            }

            .apply-mem-name {
                position: absolute;
                left: 60px;
                top: 0;
                width: 130px;

                .name-content {
                    cursor: pointer;
                }

                .name-content:hover {
                    color: #0b72a1;
                }
            }

            .amn-bgc {
                background-color: #96969646;
            }

            .apply-mem-real-name {
                position: absolute;
                left: 200px;
                top: 0;
                width: 140px;
                color: #909399;
            }

            .apply-mem-from {
                position: absolute;
                left: 350px;
                top: 0;
                width: 140px;
                color: #909399;
            }

            .apply-btn {
                position: absolute;
                right: 60px;
                top: 0;
                color: #0b72a1;
                cursor: pointer;
            }

            .apply-btn:hover {
                color: #2d9dd1;
            }

            .rejust-btn {
                position: absolute;
                right: 20px;
                top: 0;
                color: #0b72a1;
                cursor: pointer;
            }

            .rejust-btn:hover {
                color: #a1150b;
            }
        }

        .apply-item:hover {
            border: 3px dashed #2b5fc76b;
        }

        .apply-item-count {
            font-size: 12px;
            color: #909399;
        }
    }

}

::v-deep .el-badge__content.is-fixed {
    transform: translateY(-0%) translateX(0%);
    right: 52px;
}

::v-deep .el-dialog__body {
    padding: 10px 20px;
}

::v-deep .el-dialog__header {
    padding: 20px 20px 10px;
}
</style>

