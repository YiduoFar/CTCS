<template>
    <div class="backstage-view ">
        <!-- 全平台管理 -->
        <div class="backstage-model ">
            <div class="model-title">轻管理</div>
            <el-input placeholder="搜索" v-model="systemDomainVo.keyword" size="mini" class="input"
                @keyup.enter.native="manage('select')">
                <el-button class="inner-btn" slot="append" icon="el-icon-search" @click="manage('select')"></el-button>
            </el-input>
            <el-select class="select" size="mini" v-model="systemDomainVo.clazzType" placeholder="请选择">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
            </el-select>
            <div class="line"></div>
            <div class="system-vo-list ">
                <el-empty :image-size="150" description="暂无内容" v-if="systemDomainVoList.length == 0"></el-empty>
                <div class="system-vo-item" v-for="item in systemDomainVoList">
                    <!-- id -->
                    <div class="vo-id" v-if="systemDomainVo.clazzType != 'user'">id: {{ item.id }}</div>
                    <div class="vo-id" v-if="systemDomainVo.clazzType == 'user' && item.userStatus != 2">id: {{ item.id }}
                    </div>
                    <div class="vo-user-tip" v-if="systemDomainVo.clazzType == 'user' && item.userStatus == 2">封禁中*</div>
                    <!-- name -->
                    <div class="vo-name" v-if="systemDomainVo.clazzType != 'comment'">
                        {{ title }}: &nbsp;{{ item.name }}
                    </div>
                    <div class="vo-name-comment" v-if="systemDomainVo.clazzType == 'comment'">
                        {{ title }}: &nbsp;{{ item.name }}
                    </div>
                    <!-- intro -->
                    <div class="vo-intro " v-if="systemDomainVo.clazzType != 'comment'">简介:&nbsp; {{ item.intro }}</div>
                    <!-- btn -->
                    <div class="vo-btn ts02s" v-if="systemDomainVo.clazzType != 'user'"
                        @click="systemDomainVo = { ...item }; manage('delete');">
                        删除</div>
                    <div class="vo-btn ts02s" v-if="systemDomainVo.clazzType == 'user' && item.userStatus != 2"
                        @click="systemDomainVo = { ...item }; manage('delete');">封禁</div>
                    <div class="vo-btn0 ts02s" v-if="systemDomainVo.clazzType == 'user' && item.userStatus == 2"
                        @click="systemDomainVo = { ...item }; doUnseal()">解封</div>
                    <div class="vo-btn2 ts02s" @click="toDetails(item)">详情</div>
                </div>
            </div>
        </div>
        <!-- 公告与新闻 -->
        <div class="backstage-model ">
            <div class="model-title">新闻与公告</div>
            <div class="line"></div>
            <div class="model-content ">
                <!-- news-info -->
                <div class="news ">
                    <el-empty :image-size="150" description="暂无新闻" v-if="newsList.length == 0"></el-empty>
                    <div class="news-item " v-for="item in newsList" @click="focusNews = { ...item }">
                        <!-- {{ item }} -->
                        <div class="item-title">
                            <span
                                @click="$router.push({ path: '/n_a_details', query: { id: item.newsId, type: 'news' } })">{{
                                    item.newsTitle
                                }}</span>
                        </div>
                        <div class="item-time">{{ $moment(item.newsPublishTime).format('YYYY.MM.DD HH:mm') }}</div>
                    </div>
                </div>
                <!-- news-btn -->
                <div class="del-btn ts03s" @click="doDelNews()">删除</div>
                <div class="add-btn ts03s" @click="$router.push({ path: '/news_add' })">发布新闻</div>
                <el-input class="del-input" disabled size="mini" v-model="focusNews.newsTitle"></el-input>
                <!-- line -->
                <div class="line2"></div>
                <!-- announcement-info -->
                <div class="announcement">
                    <el-empty :image-size="150" description="暂无公告" v-if="announcementList.length == 0"></el-empty>
                    <div class="announcement-item" v-for="item in announcementList"
                        @click="focusAnnouncement = { ...item }">
                        <div class="item-title ">
                            <span
                                @click="$router.push({ path: '/n_a_details', query: { id: item.announcementId, type: 'announcement' } })">{{
                                    item.announcementTitle
                                }}</span>
                        </div>
                        <div class="item-time">{{ $moment(item.announcementPublishTime).format('YYYY.MM.DD HH:mm') }}</div>
                    </div>
                </div>
                <!-- announcement-btn -->
                <div class="del-btn2 ts03s" @click="doDelAnnouncement()">删除</div>
                <div class="add-btn2 ts03s" @click="$router.push({ path: '/announcement_add' })">发布公告</div>
                <el-input class="del-input2" disabled size="mini" v-model="focusAnnouncement.announcementTitle"></el-input>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';
export default {
    data: () => ({
        systemDomainVoList: [],
        systemDomainVo: {
            id: '',
            name: '',
            intro: '',
            clazzType: 'user',
            actionType: '',
            keyword: ''
        },
        newsList: [],
        focusNews: '',
        announcementList: [],
        focusAnnouncement: '',
        options: [{
            value: 'user',
            label: '用户'
        }, {
            value: 'team',
            label: '队伍'
        }, {
            value: 'competition',
            label: '竞赛'
        }, {
            value: 'activity',
            label: '活动'
        }, {
            value: 'article',
            label: '帖子'
        }, {
            value: 'comment',
            label: '评论'
        }],
    }),
    methods: {
        ...mapActions(['systemMange']),
        ...mapActions('user', ['unseal']),
        ...mapActions(['deleteNews']),
        ...mapActions(['deleteAnnouncement']),
        init() {
            /* 获取全平台实体 */
            this.manage("select")
            /* 获取新闻 */
            this.getNews()
            /* 获取公告 */
            this.getAnnouncement()
        },
        /* 全平台管理 搜索与删除 */
        async manage(actionType) {
            this.systemDomainVo.actionType = actionType;
            if (actionType == 'delete') {
                let title = ''
                let content = this.systemDomainVo.name
                if (this.systemDomainVo.clazzType == 'user') {
                    title = '封禁该用户？'
                } else {
                    title = '删除该' + this.title + '？'
                }
                this.$confirm(content, title, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    const { code, message, data } = await this.systemMange(this.systemDomainVo)
                    if (code === 200) {
                        this.$message.info('操作成功')
                        setTimeout(() => {
                            location.reload()
                        }, 1000)
                    }
                }).catch(async () => {

                })
            } else {
                const { code, message, data } = await this.systemMange(this.systemDomainVo)
                if (code === 200) {
                    this.systemDomainVoList = [...data]
                } else {
                    this.systemDomainVoList = []
                }
            }
        },
        /* 解封用户 */
        async doUnseal() {
            this.$confirm(this.systemDomainVo.name, "解封该用户？", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                const { code, message, data } = await this.unseal(this.systemDomainVo)
                if (code === 200) {
                    this.$message.info('操作成功')
                    setTimeout(() => {
                        location.reload()
                    }, 1000)
                }
            }).catch(() => { })
        },
        /* 详情页 */
        toDetails(item) {
            if (item.clazzType == 'user') {
                this.$router.push({ path: 'user_details', query: { userId: item.id } })
            }
            if (item.clazzType == 'team') {
                this.$router.push({ path: 'team_details', query: { teamId: item.id } })
            }
            if (item.clazzType == 'competition') {
                this.$router.push({ path: 'competition_details', query: { competitionId: item.id } })
            }
            if (item.clazzType == 'activity') {
                this.$router.push({ path: 'activity_details', query: { activityId: item.id } })
            }
            if (item.clazzType == 'article') {
                this.$router.push({ path: 'article_details', query: { articleId: item.id } })
            }
            if (item.clazzType == 'comment') {
                this.$router.push({ path: 'article_details', query: { articleId: item.toArticleId } })
            }
        },
        ...mapActions(['selectNews']),
        ...mapActions(['selectAnnouncement']),
        /* 获取新闻 */
        async getNews() {
            const { code, message, data } = await this.selectNews()
            if (code === 200) {
                this.newsList = [...data]
                this.focusNews = this.newsList[0]
            }
        },
        /* 获取公告 */
        async getAnnouncement() {
            const { code, message, data } = await this.selectAnnouncement()
            if (code === 200) {
                this.announcementList = [...data]
                this.focusAnnouncement = this.announcementList[0]
            }
        },
        /* 删除新闻 */
        async doDelNews() {
            this.$confirm(this.focusNews.newsTitle, "删除新闻？", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                this.focusNews.newsPublishTime = this.$moment(this.focusNews.newsPublishTime).format('YYYY-MM-DD HH:mm:ss')
                const { code, message, data } = await this.deleteNews(this.focusNews)
                if (code === 200) {
                    this.$message.info('操作成功')
                    setTimeout(() => {
                        location.reload()
                    }, 1000)
                }
            }).catch(() => {

            })
        },
        /* 删除公告？ */
        async doDelAnnouncement() {
            this.$confirm(this.focusAnnouncement.announcementTitle, "删除公告？", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                this.focusAnnouncement.announcementPublishTime = this.$moment(this.focusAnnouncement.announcementPublishTime).format('YYYY-MM-DD HH:mm:ss')
                const { code, message, data } = await this.deleteAnnouncement(this.focusAnnouncement)
                if (code === 200) {
                    this.$message.info('操作成功')
                    setTimeout(() => {
                        location.reload()
                    }, 1000)
                }
            }).catch(() => {

            })
        }
    },
    created() {
        this.init()

    },
    mounted() {

    },
    destroyed() {

    },
    watch: {
        "systemDomainVo.clazzType"(e) {
            this.manage("select")
        }
    },
    computed: {
        title() {
            if (this.systemDomainVo.clazzType == 'user') {
                return '用户'
            }
            if (this.systemDomainVo.clazzType == 'team') {
                return '队伍'
            }
            if (this.systemDomainVo.clazzType == 'competition') {
                return '竞赛'
            }
            if (this.systemDomainVo.clazzType == 'activity') {
                return '活动'
            }
            if (this.systemDomainVo.clazzType == 'article') {
                return '帖子'
            }
            if (this.systemDomainVo.clazzType == 'comment') {
                return '评论'
            }
        }
    }
}
</script>

<style lang="less" scoped>
.backstage-view {
    position: absolute;
    top: 80px;
    left: 565px;
    width: 950px;


    .backstage-model {
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
            margin-bottom: 5px;
        }

    }

    .backstage-model:nth-child(1) {
        height: 400px;
        margin-bottom: 20px;

        .input {
            position: absolute;
            right: 140px;
            top: 15px;
            width: 200px;

        }

        .select {
            position: absolute;
            right: 15px;
            top: 15px;
            width: 100px;
        }

        .system-vo-list {
            position: absolute;
            width: 100%;
            height: 330px;
            overflow-y: scroll;
            overflow-x: hidden;

            .system-vo-item {
                position: relative;
                width: 100%;
                height: 40px;
                display: flex;
                margin-bottom: 6px;
                border-bottom: 1px dashed #90939957;
                border-top: 1px dashed #90939900;
                line-height: 40px;
                font-size: 14px;

                .vo-id {
                    margin-left: 50px;
                    width: 80px;
                    // height: 40px;
                    color: #a8a8a8;
                    font-weight: lighter;
                }

                .vo-name {
                    width: 210px;
                    color: #7c7c7c;
                    margin-right: 20px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .vo-name-comment {
                    width: 680px;
                    color: #7c7c7c;
                    margin-right: 20px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .vo-intro {
                    width: 470px;
                    color: #7c7c7c;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .vo-btn0 {
                    position: relative;
                    margin-left: 10px;
                    width: 40px;
                    background-color: #e4ebf3;
                    color: #008dcf;
                    border-radius: 5px;
                    font-size: 12px;
                    height: 25px;
                    text-align: center;
                    line-height: 25px;
                    cursor: pointer;
                    top: 5px;
                }

                .vo-btn0:hover {
                    background-color: #5daed3;
                    color: #fff;
                }

                .vo-user-tip {
                    margin-left: 50px;
                    width: 80px;
                    font-size: 12px;
                    color: #ff0000;
                }


                .vo-btn {
                    position: relative;
                    margin-left: 10px;
                    width: 40px;
                    background-color: #c2a3a3;
                    color: #f0f0f0;
                    border-radius: 5px;
                    font-size: 12px;
                    height: 25px;
                    text-align: center;
                    line-height: 25px;
                    cursor: pointer;
                    top: 5px;
                }

                .vo-btn:hover {
                    background-color: #ff4b4b;
                }

                .vo-btn2 {
                    position: relative;

                    margin-left: 10px;
                    width: 40px;
                    background-color: #62aa91;
                    color: #f0f0f0;
                    border-radius: 5px;
                    font-size: 12px;
                    height: 25px;
                    text-align: center;
                    line-height: 25px;
                    cursor: pointer;
                    top: 5px;
                }

                .vo-btn2:hover {
                    background-color: #00ca98;
                }

            }

            .system-vo-item:hover {
                // border: 1px dashed #377aff57;
            }

        }

    }

    .backstage-model:nth-child(2) {
        height: 430px;

        .model-content {
            width: 100%;
            display: flex;
        }

        .news {
            position: relative;
            width: 49.8%;
            left: 0;
            height: 310px;
            overflow-y: scroll;
            overflow-x: hidden;

            .news-item {
                display: flex;
                height: 30px;
                line-height: 30px;
                font-size: 14px;
                margin-bottom: 5px;
                border-top: 2px dashed #377aff00;
                border-bottom: 2px dashed #377aff00;

                .item-title {
                    margin-left: 35px;
                    width: 310px;
                    margin-right: 10px;
                    cursor: pointer;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .item-title:hover {
                    font-weight: bold;
                    color: #004fc5;
                    text-decoration: underline;
                }

                .item-time {
                    font-size: 12px;
                }
            }

            .news-item:hover {
                border-top: 2px dashed #5096ff;
                border-bottom: 2px dashed #5096ff;
            }
        }

        .del-btn {
            position: absolute;
            bottom: 20px;
            left: 130px;
            width: 50px;
            text-align: center;
            height: 25px;
            line-height: 25px;
            font-size: 14px;
            background-color: #ff8686;
            color: #ffffff;
            cursor: pointer;
        }

        .del-btn:hover {
            background-color: #c20000;
            color: #ffffff;
        }

        .del-input {
            position: absolute;
            bottom: 18.5px;
            left: 195px;
            width: 240px;
        }

        .del-input2 {
            position: absolute;
            bottom: 18.5px;
            left: 668px;
            width: 240px;
        }

        .add-btn {
            position: absolute;
            bottom: 20px;
            left: 35px;
            width: 80px;
            text-align: center;
            height: 25px;
            line-height: 25px;
            font-size: 14px;
            background-color: #5096ff;
            color: #ffffff;
            cursor: pointer;
        }

        .add-btn:hover {
            background-color: #004fc5;
            color: #ffffff;
        }

        .del-btn2 {
            position: absolute;
            bottom: 20px;
            left: 600px;
            width: 50px;
            text-align: center;
            height: 25px;
            line-height: 25px;
            font-size: 14px;
            background-color: #ff8686;
            color: #ffffff;
            cursor: pointer;
        }

        .del-btn2:hover {
            background-color: #c20000;
            color: #ffffff;
        }

        .add-btn2 {
            position: absolute;
            bottom: 20px;
            left: 507px;
            width: 80px;
            text-align: center;
            height: 25px;
            line-height: 25px;
            font-size: 14px;
            background-color: #b464ff;
            color: #ffffff;
            cursor: pointer;
        }

        .add-btn2:hover {
            background-color: #6b0fc2;
            color: #ffffff;
        }


        .line2 {
            position: relative;
            top: 35px;
            height: 270px;
            border-left: 1px dashed #b8b8b8;
        }

        .announcement {
            position: relative;
            width: 49.8%;
            right: 0;
            height: 310px;
            overflow-y: scroll;
            overflow-x: hidden;

            .announcement-item {
                display: flex;
                height: 30px;
                line-height: 30px;
                font-size: 14px;
                margin-bottom: 5px;
                border-top: 2px dashed #3971e000;
                border-bottom: 2px dashed #377aff00;

                .item-title {
                    margin-left: 35px;
                    width: 310px;
                    margin-right: 10px;
                    cursor: pointer;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .item-title:hover {
                    color: #5c04af;
                    text-decoration: underline;
                    font-weight: bold;
                }

                .item-time {
                    font-size: 12px;
                }
            }

            .announcement-item:hover {
                border-top: 2px dashed #aa50ff;
                border-bottom: 2px dashed #aa50ff;
            }
        }
    }
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
    box-shadow: inset 0 0 0px rgba(240, 240, 240, 0.5);
    background-color: rgba(250, 250, 250, 0.397);
}
</style>