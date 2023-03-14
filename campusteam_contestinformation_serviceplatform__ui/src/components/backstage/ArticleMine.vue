<template>
    <div class="backstage-view5 ">
        <!-- 无数据 -->
        <!-- <el-empty description="您还没写过帖子" style="position: relative;top: 100px;" ></el-empty> -->
        <!-- 有数据 -->
        <!-- 我写的帖子 -->
        <template v-for="item, index in myArticleList">
            <div class="backstage-model5 l">
                <!-- 题目 -->
                <div class="art-title "><span class="title-content ts02s"
                        @click="$router.push({ path: '/article_details', query: { articleId: item.articleId } })">{{
                            item.articleTitle
                        }}</span></div>
                <!-- 摘要 -->
                <div class="art-digest ">{{ item.articleDigest }}</div>
                <!-- 时间 -->
                <div class="art-time">{{ $moment(item.articlePublishDate).format('YYYY.MM.DD HH:mm') }}</div>
                <!-- 访问量 -->
                <div class="art-page-view ">
                    <i class="el-icon-view"></i>
                    {{ item.articlePageView }}
                </div>
                <!-- 评论量 -->
                <div class="art-comment-num ">
                    <i class="el-icon-chat-line-square"></i>
                    {{ item.articleCommentNum }}
                </div>
                <!-- 点赞量 -->
                <div class="art-like-num ">
                    👍 {{ item.articleLikeNum }}
                </div>
                <!-- 点踩量 -->
                <div class="art-trample-num ">
                    👎 {{ item.articleTrampleNum }}
                </div>
                <!-- 更多 弹出框 -->
                <el-popover placement="right" width="50" trigger="hover" class="more-btn">
                    <!--  删除 -->
                    <div class="delete-btn ts05s" @click="doDeleteArticle(item)">
                        ❌ &nbsp;&nbsp;删除帖子
                    </div>
                    <!-- ...图标 -->
                    <i class="el-icon-more" slot="reference" style="color: #909399;cursor: pointer;"></i>
                </el-popover>
            </div>
        </template>
    </div>
</template>

<script>
import { mapActions } from 'vuex';
import ElementUI from 'element-ui';
export default {
    data: () => ({
        /* 我写的帖子 */
        myArticleList: [],
    }),
    methods: {
        ...mapActions('article', ['selectArticleByAuthorId']),
        ...mapActions('article', ['deleteArticle']),
        async init() {
            // 用户id
            let userId = localStorage.getItem("userId");
            const { code, message, data } = await this.selectArticleByAuthorId(userId)
            if (code === 200) {
                this.myArticleList = [...data];
            }
        },
        /* 删除帖子 */
        async doDeleteArticle(article) {
            article.articlePublishDate = this.$moment(article.articlePublishDate).format('YYYY-MM-DD HH:mm:ss')
            const { code, message, data } = await this.deleteArticle(article)
            if (code === 200) {
                ElementUI.Message.info('删除成功')
                setTimeout(() => {
                    location.reload()
                }, 1000)
            }
        }
    },
    async created() {
        // 初始化数据
        await this.init()
    },
    computed: {
    },
    components: {
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
        height: 90px;
        width: 95%;
        transition: top 0.7s ease-in-out;
        top: 0px;
        margin: 5px;
        margin-bottom: 10px;
        box-shadow: 0 0 10px #4d4d4d1a;
        overflow: hidden;
        border: 5px dashed #03c27200;
        opacity: 1;
        display: flex;


        .art-title {
            position: absolute;
            left: 30px;
            top: 10px;
            width: 700px;
            font-size: 20px;
            color: #818181;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
        }

        .title-content:hover {
            color: #535353;
            cursor: pointer;
        }

        .art-digest {
            position: absolute;
            left: 30px;
            top: 40px;
            width: 700px;
            font-size: 12px;
            color: #979797;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
        }

        .art-time {
            position: absolute;
            right: 5px;
            bottom: 0px;
            width: 100px;
            font-size: 12px;
            color: #afafafbd;
        }

        .art-page-view {
            position: absolute;
            bottom: 5px;
            left: 30px;
            width: 50px;
            font-size: 12px;
            color: #999999;
        }

        .art-comment-num {
            position: absolute;
            bottom: 5px;
            left: 100px;
            width: 50px;
            font-size: 12px;
            color: #999999;
        }

        .art-like-num {
            position: absolute;
            bottom: 5px;
            left: 160px;
            width: 50px;
            font-size: 12px;
            color: #999999;
        }

        .art-trample-num {
            position: absolute;
            bottom: 5px;
            left: 230px;
            width: 50px;
            font-size: 12px;
            color: #999999;
        }

        .more-btn {
            position: absolute;
            top: 5px;
            right: 10px;
            font-size: 12px;
            color: #6e6e6e;
        }

    }

}
</style>

<style lang="less">
.el-popover {

    /* scope不生效 */
    .delete-btn {
        font-size: 12px;
        cursor: pointer;
    }

    .delete-btn:hover {
        color: #ce3e3e;
    }

}
</style>


