<template>
    <div class="bbs">
        <Nav></Nav>
        <div class="bbs-body">
            <img src="../assets/img/背景/竞赛大厅.png" alt="" class="bg">
            <div class="bbs-view">
                <div class="bbs-title ">茶话会</div>
                <!-- tips -->
                <el-popover placement="top-start" title="" width="100" trigger="hover" :content="bbsArticleListTip"
                    class="title-icon">
                    <i slot="reference" class="el-icon-hot-water"></i>
                </el-popover>
                <!-- 搜索 -->
                <div class="bbs-search">
                    <el-input placeholder="搜索帖子" v-model="queryVo.keyword" @keyup.enter.native="search" size="small"
                        style="width:300px;">
                        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
                    </el-input>
                </div>
                <!-- 发帖子 -->
                <div class="write-article-btn ts02s" @click="$router.push({ path: '/article_add' })"><i
                        class="el-icon-edit"></i> 发布新帖子</div>
                <!-- 帖子list -->
                <div class="bbs-article-list ">
                    <!-- 无数据 -->
                    <el-empty description="空空如也" :image-size="200" style="font-size: 12px;top: 110px;position:relative;"
                        v-if="bbsArticleList.length == 0"></el-empty>
                    <!-- 有数据 -->
                    <div class="list-item " v-for="item in bbsArticleList">
                        <img class="item-author-head" :src="item.authorVo.userHead" alt="">
                        <div class="item-author-name-date">
                            <span style="color: #303030;cursor: pointer;"
                                @click="$router.push({ path: 'user_details', query: { userId: item.authorVo.userId } })">{{
                                    item.authorVo.userName }}</span>
                            &nbsp;
                            <span style="color: #5c5c5c;">{{
                                $moment(item.article.articlePublishDate).format('YYYY.MM.DD HH:mm')
                            }}</span>
                        </div>
                        <div class="item-title" style="cursor: pointer;"
                            @click="$router.push({ path: 'article_details', query: { articleId: item.article.articleId } })">
                            {{
                                item.article.articleTitle }}</div>
                        <div class="item-digest">{{ item.article.articleDigest }}</div>
                        <div class="item-num-view-list">
                            <div class="i-n-v-l-item item-page-view "><i class="el-icon-view"></i> {{
                                item.article.articlePageView
                            }}</div>
                            <div class="i-n-v-l-item item-comment-num"><i class="el-icon-chat-line-square"></i> {{
                                item.article.articleCommentNum
                            }}</div>
                            <div class="i-n-v-l-item item-like-num">赞 {{ item.article.articleLikeNum }}</div>
                            <div class="i-n-v-l-item item-trample-num">踩 {{ item.article.articleTrampleNum }}</div>
                        </div>
                        <div class="item-label">
                            <div class="item-label-i" v-for="i in JSON.parse(item.article.articleLabel)">{{ i }}</div>
                        </div>
                    </div>
                </div>
                <!-- 分页 -->
                <div class="bbs-page ">
                    <el-pagination layout="prev, pager, next" :total="totalPage" class="page-el-pagination"
                        @current-change="currentChange">
                    </el-pagination>
                </div>
            </div>
            <Foot class="bbs-foot"></Foot>
        </div>
    </div>
</template>

<script>
import Nav from '../components/Nav.vue'
import Foot from '../components/Foot.vue'
import { mapActions } from 'vuex'
export default {
    data: () => ({
        bbsArticleList: [{
            authorVo: {
                userHead: ''
            },
            article: {

            }
        }],
        bbsArticleListTip: '根据最新发布时间 排序',
        queryVo: {
            current: 1,
            size: 7,
            keyword: ''
        },
        totalPage: 0,
    }),
    methods: {
        ...mapActions('article', ['selectArticleByKeyword']),
        /* 搜索 */
        async search() {
            const { code, message, data } = await this.selectArticleByKeyword(this.queryVo)
            if (code === 200) {
                this.bbsArticleList = [...data.page]
                // 计算总页数
                let size = this.queryVo.size;
                let total = data.total
                this.totalPage = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            } else {
                this.bbsArticleList = []
                this.totalPage = 0;
            }
        },
        /* 初始化 */
        init() {
            this.search()
        },
        currentChange(e) {
            this.queryVo.current = e
            this.search()
            document.getElementsByClassName('bbs-body')[0].scrollTo({ top: 0, behavior: "smooth" });
        }
    },
    created() {
        this.init()
    },
    components: {
        Nav,
        Foot,
    }
}
</script>

<style lang="less" scoped>
.bbs {
    width: 100%;
    height: auto;

    .bbs-body {
        position: relative;
        width: 100%;
        min-height: 100vh;
        overflow-y: scroll;
        overflow-x: hidden;
        z-index: 0;

        .bg {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            filter: blur(0px) brightness(100%);
            z-index: -1;
            transform: scale(1.96);
        }

        .bbs-view {
            position: absolute;
            width: 74%;
            height: 1260px;
            top: 80px;
            left: 13%;
            background-color: #ffffffcb;
            box-shadow: 0px 0px 10px #e9e9e9b9;

            .bbs-title {
                position: absolute;
                top: 10px;
                left: 25px;
                font-size: 20px;
                font-weight: bold;
                color: #2b2b2ba6;

                // font-style: italic;
            }

            .title-icon {
                position: absolute;
                top: 8.2px;
                left: 91px;
                width: 20px;
                height: 20px;
                cursor: pointer;
                font-size: 24px;
                color: #a59e9e;
            }

            .bbs-search {
                position: absolute;
                top: 15px;
                right: 185px;
            }

            .write-article-btn {
                position: absolute;
                top: 15px;
                right: 16px;
                height: 31px;
                width: 155px;
                border-radius: 15px;
                background-color: #0eb833;
                cursor: pointer;
                font-size: 14px;
                text-align: center;
                line-height: 31px;
                color: #ececec;
            }

            .write-article-btn:hover {
                background-color: #15c73c;
                color: #ffffff;
            }

            .line {
                position: absolute;
                width: 100%;
                top: 60px;
            }

            .bbs-article-list {
                position: absolute;
                width: 100%;
                top: 65px;
                height: 1130px;
                overflow: hidden;

                .list-item {
                    width: 100%;
                    height: 160px;
                    border-top: 1px solid #dfdfdfbe;
                    position: relative;

                    .item-author-head {
                        position: absolute;
                        width: 30px;
                        height: 30px;
                        left: 30px;
                        top: 15px;
                        border-radius: 50%;
                    }

                    .item-author-name-date {
                        position: absolute;
                        font-size: 16px;
                        top: 20px;
                        left: 70px;
                        color: #303030;
                    }

                    .item-title {
                        position: absolute;
                        font-size: 17px;
                        font-weight: bold;
                        top: 50px;
                        left: 30px;
                        color: #161616;
                    }

                    .item-digest {
                        position: absolute;
                        font-size: 14px;
                        color: #525252;
                        top: 75px;
                        left: 30px;
                        width: 95%;
                        // 省略效果
                        overflow: hidden;
                        display: -webkit-box;
                        /*第几行裁剪*/
                        -webkit-line-clamp: 2;
                        -webkit-box-orient: vertical;
                    }

                    .item-num-view-list {
                        position: absolute;
                        left: 30px;
                        bottom: 15px;
                        font-size: 14px;
                        color: #969696;
                        display: flex;
                        cursor: default;

                        .i-n-v-l-item {
                            margin-right: 30px;
                        }
                    }

                    .item-label {
                        position: absolute;
                        right: 30px;
                        bottom: 15px;
                        display: flex;

                        .item-label-i {
                            font-size: 13px;
                            margin-left: 10px;
                            background-color: #32ad4c;
                            color: #fff;
                            padding-top: 1px;
                            padding-bottom: 1px;
                            padding-left: 5px;
                            padding-right: 5px;
                            height: 20px;
                            line-height: 20px;
                            border-radius: 5px;
                        }
                    }
                }

                .list-item:nth-child(1) {
                    border: none;
                }
            }

            .bbs-page {
                position: absolute;
                bottom: 0;
                left: 0;
                height: 60px;
                width: 100%;
                display: flex;
                align-items: center;
                background-color: #ffffff;
                justify-content: center;

                .page-el-pagination {
                    position: absolute;
                    top: 10px;

                }
            }
        }

        .bbs-foot {
            position: absolute;
            top: 1360px;
            background-color: #ffffff48;

        }
    }
}

// ::v-deep .el-pagination button:disabled{
//     background-color: #ffffff00;
// }
</style>