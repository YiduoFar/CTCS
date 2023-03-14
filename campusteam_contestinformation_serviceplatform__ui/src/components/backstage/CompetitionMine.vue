<template>
    <div class="backstage-view2 ">
        <!-- 进行中 -->
        <div class="backstage-model2">
            <div class="model-title">
                进行中
                <i class="el-icon-loading"></i>
                <!-- 搜索我的竞赛 -->
                <el-input class="model-search" size="mini" placeholder="搜索我的竞赛" prefix-icon="el-icon-search"
                    v-model="queryVo1.keyword" @keyup.enter.native="search1"></el-input>
            </div>
            <!-- 分割线 -->
            <div class="line"></div>
            <!-- 进行中 竞赛列表 -->
            <div class="competition-list ">
                <!-- 无数据时 显示 -->
                <el-empty description="没有数据" v-if="myCompetitionList1.length == 0"></el-empty>
                <!-- 竞赛item -->
                <div class="competition-item " v-for="item in myCompetitionList1">
                    <!-- 竞赛名称 -->
                    <div class="item-name " :title="item.competition.competitionName"
                        @click="$router.push({ path: '/competition_details', query: { competitionId: item.competition.competitionId } })"
                        v-if="item.competition.isTeamUp == 1">
                        {{ item.competition.competitionName }}
                    </div>
                    <!-- 无队伍名称的竞赛名称 宽度更大 -->
                    <div class="item-name2 " :title="item.competition.competitionName" v-if="item.competition.isTeamUp == 0"
                        @click="$router.push({ path: '/competition_details', query: { competitionId: item.competition.competitionId } })">
                        {{ item.competition.competitionName }}
                    </div>
                    <div>
                        <!-- 个人竞赛标标 -->
                        <span class="item-isTeamUp" style="background-color: #1baec2;"
                            v-show="item.competition.isTeamUp == 0">
                            {{ isTeamUp[item.competition.isTeamUp] }}
                        </span>
                        <!-- 团队竞赛标标 -->
                        <span class="item-isTeamUp " style="background-color: #22a74e;"
                            v-show="item.competition.isTeamUp == 1">
                            {{ isTeamUp[item.competition.isTeamUp] }}
                        </span>
                        <!-- 队伍附加信息 -->
                        <span class="item-isTeamUp-team-info " v-if="item.competition.isTeamUp == 1">
                            <!-- 队伍Logo -->
                            <img :src="item.teamLogo" alt="" class="team-logo">
                            <!-- 队伍名称 -->
                            <span class="team-name">
                                {{ item.teamName }}
                            </span>
                        </span>
                    </div>
                    <!-- 当前进行的步骤 -->
                    <div class="item-stage " v-if="item.competition.competitionStage">
                        {{
                            JSON.parse(item.competition.competitionStage)[item.competition.competitionStageNow].competitionStageName
                        }}
                    </div>
                    <!-- 竞赛类型 -->
                    <div class="item-type ">{{ competitionType[item.competition.competitionType] }}</div>
                    <!-- 竞赛开始时间 -->
                    <div class="item-time " v-if="item.competition.competitionBeginTime">
                        {{ item.competition.competitionBeginTime.substring(0, 10) }}
                    </div>
                    <!-- 更多 弹出框 -->
                    <el-popover placement="right" width="50" trigger="hover" class="item-more-btn">
                        <!-- 上传文件 -->
                        <div class="more-i ts05s"
                            @click="dialogVisible1 = true; competitionId = item.competition.competitionId">
                            <i class="el-icon-upload"></i>
                            上传文件
                        </div>
                        <!-- 退出竞赛 -->
                        <div class="more-i ts05s" @click="doQuitCompetition(item.competition.competitionId, item.teamId)">
                            <i class="el-icon-right"></i>
                            退出竞赛
                        </div>
                        <!-- ...图标 -->
                        <i class="el-icon-more" slot="reference" style="color: #909399;cursor: pointer;"></i>
                    </el-popover>
                </div>
            </div>
            <!-- 分页组件 -->
            <div class="competition-page ">
                <el-pagination layout="prev, next" :total="totalPage1" class="page-el-pagination"
                    @current-change="currentChange1">
                </el-pagination>
            </div>
        </div>
        <!-- 已结束 -->
        <div class="backstage-model2">
            <div class="model-title">
                已经结束
                <!-- 搜索我的竞赛 -->
                <el-input class="model-search" size="mini" placeholder="搜索我的竞赛" prefix-icon="el-icon-search"
                    v-model="queryVo2.keyword" @keyup.enter.native="search2"></el-input>
            </div>
            <!-- 分割线 -->
            <div class="line"></div>
            <!-- 已结束 竞赛列表 -->
            <div class="competition-list ">
                <!-- 无数据时显示 -->
                <el-empty description="没有数据" v-if="myCompetitionList2.length == 0"></el-empty>
                <!-- 竞赛item -->
                <div class="competition-item " v-for="item in myCompetitionList2">
                    <!-- 竞赛名称 -->
                    <div class="item-name " :title="item.competition.competitionName"
                        @click="$router.push({ path: '/competition_details', query: { competitionId: item.competition.competitionId } })"
                        v-if="item.competition.isTeamUp == 1">
                        {{ item.competition.competitionName }}
                    </div>
                    <!-- 无队伍名称的竞赛名称 宽度更大 -->
                    <div class="item-name2 " :title="item.competition.competitionName"
                        @click="$router.push({ path: '/competition_details', query: { competitionId: item.competition.competitionId } })"
                        v-if="item.competition.isTeamUp == 0">
                        {{ item.competition.competitionName }}
                    </div>
                    <div>
                        <!-- 个人竞赛标标 -->
                        <span class="item-isTeamUp" style="background-color: #1baec2;"
                            v-if="item.competition.isTeamUp == 0">
                            {{ isTeamUp[item.competition.isTeamUp] }}
                        </span>
                        <!-- 团队竞赛标标 -->
                        <span class="item-isTeamUp " style="background-color: #22a74e;"
                            v-if="item.competition.isTeamUp == 1">
                            {{ isTeamUp[item.competition.isTeamUp] }}
                        </span>
                        <!-- 队伍附加信息 -->
                        <span class="item-isTeamUp-team-info " v-if="item.competition.isTeamUp == 1">
                            <!-- 队伍Logo -->
                            <img :src="item.teamLogo" alt="" class="team-logo">
                            <!-- 队伍名称 -->
                            <span class="team-name">
                                {{ item.teamName }}
                            </span>
                        </span>
                    </div>
                    <!-- 步骤 统一：已结束 -->
                    <div class="item-stage ">已结束</div>
                    <!-- 竞赛类型 -->
                    <div class="item-type ">{{ competitionType[item.competition.competitionType] }}</div>
                    <!-- 竞赛开始时间 -->
                    <div class="item-time ">{{ item.competition.competitionBeginTime.substring(0, 10) }}</div>
                    <!-- 更多 弹出框 -->
                    <el-popover placement="right" width="50" trigger="click" class="item-more-btn">
                        <!-- 查看结果 -->
                        <div class="more-i2 ts05s" @click="doDownLoadResultFile(item.competition.competitionId)">
                            <i class="el-icon-cherry"></i>
                            查看结果
                        </div>
                        <!-- ...图标 -->
                        <i class="el-icon-more" slot="reference" style="color: #909399;cursor: pointer;"></i>
                    </el-popover>
                </div>
            </div>
            <!-- 分页 -->
            <div class="competition-page ">
                <el-pagination layout="prev, next" :total="totalPage2" class="page-el-pagination"
                    @current-change="currentChange2">
                </el-pagination>
            </div>
        </div>
        <!-- 上传文件的dialog -->
        <el-dialog title="文件上传" :visible.sync="dialogVisible1" width="30%" :modal="false" class="thisblack-bgc">
            <el-upload class="upload-demo" :before-upload="beforeAvatarUpload" ref="upload" action="#"
                :http-request="handleUpload" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
                <div slot="trigger" size="small" type="primary" class="add-file-btn">+</div>
                <div slot="tip" class="el-upload__tip">只能上传word、excal文件，且不超过5mb</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" round type="primary" @click="submitUpload(); dialogVisible1 = false">确
                    定</el-button>
                <el-button size="small" round @click="cancelUpload(); dialogVisible1 = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { mapActions } from 'vuex';
import ElementUI from 'element-ui';
export default {
    data: () => ({
        competitionType: ['工科竞赛', '文科竞赛'],
        isTeamUp: ['个人竞赛', '团队竞赛'],
        totalPage1: 0,
        myCompetitionList1: [],
        queryVo1: {
            current: 1,
            size: 5,
            keyword: '',
            userId: '',
            status: 1
        },
        totalPage2: 0,
        myCompetitionList2: [],
        queryVo2: {
            current: 1,
            size: 5,
            keyword: '',
            userId: '',
            status: 0
        },
        fileList: [],
        dialogVisible1: false,
        competitionId: 0
    }),
    mounted() {
        this.doGetCompetition1()
        this.doGetCompetition2()
    },
    watch: {
        'queryVo1.keyword'() {
            this.search1()
        },
        'queryVo2.keyword'() {
            this.search2()
        },
    },
    methods: {
        ...mapActions('competition', ['selectCompetitionByUserIdAndKeyWord']),
        /**
         * 进行中的
         */
        async doGetCompetition1() {
            await this.search1()
        },
        /**
         * 已结束的
         */
        async doGetCompetition2() {
            await this.search2()
        },
        /**
         * 搜索1
         */
        async search1() {
            this.queryVo1.userId = localStorage.getItem('userId')
            const { code, message, data } = await this.selectCompetitionByUserIdAndKeyWord(this.queryVo1);
            if (code === 200) {
                // 分页数据
                this.myCompetitionList1 = [...data.page]
                // 计算总页数
                let size = this.queryVo1.size;
                let total = data.total
                this.totalPage1 = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            }
        },
        /**
         * 搜索2
         */
        async search2() {
            this.queryVo2.userId = localStorage.getItem('userId')
            const { code, message, data } = await this.selectCompetitionByUserIdAndKeyWord(this.queryVo2);
            if (code === 200) {
                // 分页数据
                this.myCompetitionList2 = [...data.page]
                // 计算总页数
                let size = this.queryVo2.size;
                let total = data.total
                this.totalPage2 = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            }
        },
        /**
         * 分页1
         */
        async currentChange1(e) {
            this.queryVo1.current = e
            this.search1()
        },
        /**
         * 分页2
         */
        async currentChange2(e) {
            this.queryVo2.current = e
            this.search2()
        },
        /**
         * 上传文件
         * @param {*} file 
         */
        beforeAvatarUpload(file) {
            let pattern = /^.*(\.doc|\.docx|.xls|.xlsx)$/
            const isJPG = pattern.test(file.name);
            const isLt5M = file.size / 1024 / 1024 / 1024 < 5;
            if (!isJPG) {
                this.$message.error('上传的文件只能是 word 或 excal !');
            }
            if (!isLt5M) {
                this.$message.error('文件大小不能超过 5MB!');
            }
            return isJPG && isLt5M;
        },
        ...mapActions('competition', ['uploadCompetitionFile']),
        handleUpload(raw) {
            this.fileList.push(raw.file);
        },
        async submitUpload() {
            // 获取文件到this.fileList
            this.$refs.upload.submit();
            if (this.fileList.length == 0) {
                this.$message.warning('点击太快，没看清楚(^_^)9');
                return
            }
            // 添加文件
            var formData = new window.FormData();
            this.fileList.forEach(function (file) {
                formData.append('file', file);
            })
            // 添加竞赛id
            formData.append("competitionId", this.competitionId)
            // 设置类型
            formData.append("type", 2)
            // 上传
            const { code, message, data } = await this.uploadCompetitionFile(formData)
            if (code === 200) {
                ElementUI.Message.success(this.$UPLOADSUCCESS)
            }
            this.fileList = []
            this.$refs.upload.clearFiles();
        },
        cancelUpload() {
            this.fileList = []
            this.$refs.upload.clearFiles();
        },
        handFileSuccess(file) {
            this.fileList = []
            this.$refs.upload.clearFiles();
        },
        handleRemove(file, fileList) {
            this.fileList = fileList
        },
        /**
         * 退出竞赛
         */
        ...mapActions('user_competition', ['deleteUserCompetition']),
        ...mapActions('team_competition', ['deleteTeamCompetition']),
        doQuitCompetition(id2, teamId) {
            const _this = this
            this.$confirm('确定退出该竞赛?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                if (teamId) {
                    // 0申请 1非申请
                    let teamCompetition = {
                        teamId: teamId,
                        competitionId: id2,
                        status: 1,
                        type: 1 // type 操作类型 剔除3 婉拒2 退出1
                    }
                    const { code, message, data } = await _this.deleteTeamCompetition(teamCompetition);
                    if (code === 200) {
                        this.$message({
                            type: 'success',
                            message: '成功退出该竞赛'
                        });
                    } else {
                        this.$message({
                            type: 'error',
                            message: '系统错误'
                        });
                    }
                } else {
                    // 拿userId
                    let id = localStorage.getItem('userId')
                    // 0申请 1非申请
                    let userCompetitionDeleteVo = {
                        userId: id,
                        competitionId: id2,
                        status: 1,
                        type: 1 // type 操作类型 剔除3 婉拒2 退出1
                    }
                    const { code, message, data } = await _this.deleteUserCompetition(userCompetitionDeleteVo);
                    if (code === 200) {
                        this.$message({
                            type: 'success',
                            message: '成功退出该竞赛'
                        });
                    } else {
                        this.$message({
                            type: 'error',
                            message: '系统错误'
                        });
                    }
                }
                setTimeout(() => {
                    location.reload()
                }, 1000)
            })
        },
        ...mapActions('competition', ['selectFilePath']),
        /**
         * 查看结果 类型1
         */
        async doDownLoadResultFile(id) {
            const fileQueryVo = {
                id: id,
                type: 1
            }
            const { code, message, data } = await this.selectFilePath(fileQueryVo)
            if (code === 200) {
                let paths = [...data];
                paths.forEach((val, index) => {
                    // 多文件下载采用iframe
                    const iframe = document.createElement('iframe');
                    iframe.style.display = 'none';
                    iframe.src = val;
                    document.body.appendChild(iframe);
                    setTimeout(() => {
                        document.body.removeChild(iframe);
                    }, 200);
                })
            } else {
                ElementUI.Message.error('该竞赛暂无成绩结果')
            }
        }
    }
}
</script>

<style lang="less" scoped>
.backstage-view2 {
    position: absolute;
    top: 80px;
    left: 565px;
    width: 950px;

    .backstage-model2 {
        position: relative;
        width: 100%;
        box-shadow: 0px 0px 10px #4d4d4d2c;

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
        }

        .model-search {
            position: absolute;
            right: 15px;
            width: 200px;
        }

        .competition-page {
            position: relative;
            bottom: 0;
            text-align: right;
        }
    }

    .backstage-model2:nth-child(1) {
        height: 410px;
        margin-bottom: 15px;

        .competition-list {
            width: 100%;
            height: 310px;
            overflow: hidden;

            .competition-item {
                width: 100%;
                height: 60px;
                overflow: hidden;
                display: flex;
                font-size: 14px;
                line-height: 60px;
                border-bottom: 1px dashed #99999957;

                .item-name {
                    position: relative;
                    margin-left: 35px;
                    margin-right: 5px;
                    max-width: 260px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .item-name2 {
                    position: relative;
                    margin-left: 35px;
                    margin-right: 5px;
                    max-width: 340px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .item-isTeamUp {
                    position: relative;
                    color: #fff;
                    padding-top: 3px;
                    padding-bottom: 3px;
                    padding-left: 7px;
                    padding-right: 7px;
                    border-radius: 5px;
                    font-size: 12px;
                    margin-left: 10px;
                }

                .item-isTeamUp-team-info {
                    position: relative;
                    color: #fff;
                    background-color: #d38033ad;
                    padding-top: 3px;
                    padding-bottom: 3px;
                    padding-left: 10px;
                    padding-right: 20px;
                    border-radius: 5px;
                    margin-left: 10px;
                    font-size: 12px;

                    .team-logo {
                        position: absolute;
                        left: 5px;
                        top: 0;
                        width: 20px;
                        height: 20px;
                    }

                    .team-name {
                        position: relative;
                        left: 15px;
                        top: 0;
                    }
                }

                .item-stage {
                    color: #eb4d41;
                    width: 120px;
                    text-align: center;
                    position: absolute;
                    left: 500px;
                }

                .item-type {
                    width: 130px;
                    position: absolute;
                    left: 620px;
                    text-align: center;
                    font-weight: lighter;
                }

                .item-time {
                    width: 130px;
                    position: absolute;
                    left: 750px;
                    text-align: center;
                    font-weight: lighter;
                }

                .item-more-btn {
                    position: absolute;
                    left: 900px;
                }

            }
        }
    }

    .backstage-model2:nth-child(2) {
        height: 425px;

        .competition-list {
            width: 100%;
            height: 310px;
            overflow: hidden;

            .competition-item {
                width: 100%;
                height: 60px;
                overflow: hidden;
                display: flex;
                font-size: 14px;
                line-height: 60px;
                border-bottom: 1px dashed #99999957;

                .item-name {
                    position: relative;
                    margin-left: 35px;
                    margin-right: 5px;
                    max-width: 260px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .item-name2 {
                    position: relative;
                    margin-left: 35px;
                    margin-right: 5px;
                    max-width: 340px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .item-isTeamUp {
                    color: #fff;
                    padding-top: 3px;
                    padding-bottom: 3px;
                    padding-left: 7px;
                    padding-right: 7px;
                    border-radius: 5px;
                    font-size: 12px;
                    margin-left: 10px;
                }

                .item-isTeamUp-team-info {
                    position: relative;
                    color: #fff;
                    background-color: #d38033ad;
                    padding-top: 3px;
                    padding-bottom: 3px;
                    padding-left: 10px;
                    padding-right: 20px;
                    border-radius: 5px;
                    margin-left: 10px;
                    font-size: 12px;

                    .team-logo {
                        position: absolute;
                        left: 5px;
                        top: 0;
                        width: 20px;
                        height: 20px;
                    }

                    .team-name {
                        position: relative;
                        left: 15px;
                        top: 0;
                    }
                }

                .item-stage {
                    color: #a8a8a8;
                    width: 120px;
                    text-align: center;
                    position: absolute;
                    left: 500px;
                }

                .item-type {
                    width: 130px;
                    position: absolute;
                    left: 620px;
                    text-align: center;
                    font-weight: lighter;
                }

                .item-time {
                    width: 130px;
                    position: absolute;
                    left: 750px;
                    text-align: center;
                    font-weight: lighter;
                }

                .item-more-btn {
                    position: absolute;
                    left: 900px;
                }

            }
        }
    }

    .thisblack-bgc {
        background-color: #00000049;
    }
}

::v-deep .el-button {
    font-size: 14px;
    font-weight: lighter;
    width: 70px;
}

::v-deep .el-dialog__body {
    padding: 10px 20px;
}

::v-deep .el-dialog__header {
    padding: 20px 20px 0px;
}

.add-file-btn {
    width: 30px;
    height: 30px;
    text-align: center;
    font-size: 20px;
    position: absolute;
    bottom: 20px;
    border: 1px dashed #409EFF;
}
</style>

<style lang="less">
/* scope不生效 */
.el-popover {
    min-width: 80px;
    padding: 8px;

    .more-i,
    .more-i2 {
        height: 25px;
        line-height: 25px;
        text-align: center;
        cursor: pointer;
    }

    .more-i:nth-child(1) {
        margin-bottom: 5px;
    }

    .more-i:nth-child(1):hover {
        color: #1f6dc7;
    }

    .more-i:nth-child(2):hover {
        color: #c71f1f;
    }

    .more-i2:hover {
        color: #18c063;
    }
}
</style>