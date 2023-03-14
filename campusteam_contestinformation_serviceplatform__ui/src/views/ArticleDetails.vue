<template>
    <div class="article-details">
        <Nav></Nav>
        <!-- 面包屑 -->
        <el-breadcrumb separator-class="el-icon-arrow-right" class="bread">
            <el-breadcrumb-item :to="{ path: '/bbs' }">帖子</el-breadcrumb-item>
            <el-breadcrumb-item>{{ articleVo.article.articleTitle }}</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 帖子 -->
        <div class="article " ref="article">
            <!-- 题目 -->
            <div class="art-title ">{{ articleVo.article.articleTitle }}</div>
            <!-- bar -->
            <div class="bar1 ">
                <!-- 时间 -->
                <div class="art-time">{{ $moment(articleVo.article.articlePublishDate).format('YYYY.MM.DD HH:mm') }}</div>
                <div class="art-page-view">{{ articleVo.article.articlePageView }} 阅读</div>
                <div class="art-like-num">{{ articleVo.article.articleLikeNum }} 喜欢</div>
                <div class="art-comment-num">{{ articleVo.article.articleCommentNum }} 评论</div>
            </div>
            <!-- 作者 -->
            <div class="author ">
                <img :src="articleVo.authorVo.userHead" alt="" class="avator">
                <div class="name ">{{ articleVo.authorVo.userName }}</div>
                <div class="bar2 ">
                    <div class="team-num ">队伍: {{ articleVo.authorVo.teamNum }}</div>
                    <div class="art-num ">帖子: {{ articleVo.authorVo.articleNum }}</div>
                </div>
                <div class="details"
                    @click="$router.push({ path: '/user_details', query: { userId: articleVo.authorVo.userId } })">
                    个人主页
                </div>
            </div>
            <!-- line -->
            <div class="line"></div>
            <!-- 内容 -->
            <div class="content" v-html="articleVo.article.articleContent"></div>
            <!-- line -->
            <div class="line2"></div>
        </div>
        <!-- 评论模块 -->
        <div class="comment " ref="comment">
            <!-- 写评论 -->
            <!-- 标题 -->
            <div class="title ">{{ articleVo.article.articleCommentNum }}&nbsp;&nbsp;评论</div>
            <div class="write ">
                <!-- 头像 -->
                <img :src="userHead" alt="" class="avator">
                <!-- 评论框 -->
                <textarea name="" id="" cols="30" rows="4" v-model="textContent" ref="textarea" class="input"
                    placeholder="发一条友善的评论"></textarea>
                <!-- 发表btn -->
                <div class="write-btn ts02s" @click="doPublishComment()">
                    <div class="btn-text ">
                        发表评论
                    </div>
                </div>
                <!-- emoji -->
                <emoji class="emoji" @change="getEmoji"></emoji>
            </div>
            <!-- 一级评论 -->
            <template v-for="item in commentVoList">
                <div class="comment-item ">
                    <!-- line -->
                    <div class="line3"></div>
                    <!-- 头像 -->
                    <img :src="item.fromUser.userHead" alt="" class="com-avator">
                    <!-- 名字 -->
                    <div class="com-user-name">{{ item.fromUser.userName }}</div>
                    <!-- 内容 -->
                    <div class="com-content ">{{ item.comment.commentContent }}</div>
                    <div class="bar3 ">
                        <!-- 时间 -->
                        <div class="com-time">{{ $moment(item.comment.commentCreateTime).format('YYYY.MM.DD HH:mm') }}</div>
                        <!-- 点赞 -->
                        <div class="com-like-btn" @click="addCommentLikeById(item.comment.commentId)">👍 {{
                            item.comment.commentLikeNum }}
                        </div>
                        <!-- 点踩 -->
                        <div class="com-trample-btn" @click="addCommentTrampleById(item.comment.commentId)">👎 {{
                            item.comment.commentTrampleNum }}
                        </div>
                        <!-- 回复btn -->
                        <div class="com-reply ts02s" @click="handleReply(item)">回复</div>
                    </div>
                    <!-- 二级评论 -->
                    <template v-for="e in item.comment2VoList">
                        <div class="comment-item2 ">
                            <!-- 头像 -->
                            <img :src="e.fromUser.userHead" alt="" class="com-avator">
                            <!-- 名字 -->
                            <div class="com-user-name">{{ e.fromUser.userName }}</div>
                            <!-- 内容 -->
                            <div class="com-content ">{{ e.comment.commentContent }}</div>
                            <div class="bar3 ">
                                <!-- 时间 -->
                                <div class="com-time">{{ $moment(e.comment.commentCreateTime).format('YYYY.MM.DD HH:mm') }}
                                </div>
                                <!-- 点赞 -->
                                <div class="com-like-btn" @click="addCommentLikeById(e.comment.commentId)">👍 {{
                                    e.comment.commentLikeNum }}
                                </div>
                                <!-- 点踩 -->
                                <div class="com-trample-btn" @click="addCommentTrampleById(e.comment.commentId)">👎 {{
                                    e.comment.commentTrampleNum }}</div>
                            </div>
                        </div>
                    </template>
                    <!-- 回复 -->
                    <div class="write2 " v-if="item.replyShow">
                        <!-- 头像 -->
                        <img :src="userHead" alt="" class="avator">
                        <!-- 评论框 -->
                        <textarea name="" id="" cols="30" rows="4" v-model="textContent2" ref="textarea2" class="input"
                            placeholder="发一条友善的评论"></textarea>
                        <!-- 发表btn -->
                        <div class="write-btn ts02s" @click="doPublishComment2(item)">
                            <div class="btn-text ">
                                发表评论
                            </div>
                        </div>
                    </div>
                </div>
            </template>
            <div class="line4"></div>
            <div class="none-data">没有更多评论了</div>
        </div>
        <!-- 按钮 -->
        <div class="fit-bar ">
            <!-- 点赞帖子 -->
            <div class="bar-btn " @click="addLikeById()">
                <img class="btn-img" src="../assets/img/图标/like.png" alt="">
                <div class="btn-data">{{ articleVo.article.articleLikeNum }}</div>
            </div>
            <!-- 点踩帖子 -->
            <div class="bar-btn" @click="addTrampleById()">
                <img class="btn-img" src="../assets/img/图标/trample.png" alt="">
                <div class="btn-data">{{ articleVo.article.articleTrampleNum }}</div>
            </div>
            <!-- 滑到评论 -->
            <div class="bar-btn" @click="toWriteComment()">
                <i class="el-icon-chat-dot-square btn-img"></i>
                <div class="btn-data">{{ articleVo.article.articleCommentNum }}</div>
            </div>
        </div>
        <!-- 回到顶部 -->
        <div class="fit-to-top el-icon-arrow-up" @click="toTop()">
        </div>
        <Foot class="art-foot"></Foot>
    </div>
</template>

<script>
import Nav from '@/components/Nav.vue';
import Foot from '@/components/Foot.vue';
import emoji from '../components/backstage/emoji.vue';
import { mapActions } from 'vuex';
import ElementUI from 'element-ui';
export default {
    data: () => ({
        textContent: '',
        textContent2: '',
        /* 帖子Vo */
        articleVo: {
            article: {
                articleTitle: '【随笔】忧郁的周一，我什么都不相干',
                articlePublishDate: '2023-03-02 14:06',
                articlePageView: 1234,
                articleLikeNum: 66,
                articleCommentNum: 12,
                articleContent: ''
            },
            authorVo: {
                userId: '',
                userName: 'John',
                userHead: '',
                teamNum: 12,
                articleNum: 80,
            }
        },
        /* 评论列表 */
        commentVoList: [{
            /* 评论人 */
            fromUser: {
                userId: 16,
                userHead: 'http://localhost:8088/resources/16.jpg',
                userName: '浪里个浪'
            },
            /* 一级评论 */
            comment: {
                commentContent: '我是一级评论',
                commentCreateTime: '2020-01-01 00:00',
                commentLikeNum: 100,
                commentTrampleNum: 10,
            },
            /* 二级评论列表 */
            comment2VoList: [{
                fromUser: {
                    userId: 16,
                    userHead: 'http://localhost:8088/resources/18.jpg',
                    userName: '浪里个浪'
                },
                comment: {
                    commentContent: '我是二级评论',
                    commentCreateTime: '2020-01-01 00:00',
                    commentLikeNum: 100,
                    commentTrampleNum: 10,
                },
            }, {
                fromUser: {
                    userId: 16,
                    userHead: 'http://localhost:8088/resources/19.jpg',
                    userName: '浪里个浪'
                },
                comment: {
                    commentContent: '我是二级评论',
                    commentCreateTime: '2020-01-01 00:00',
                    commentLikeNum: 100,
                    commentTrampleNum: 10,
                },
            }],
            /* 回复显示控制 */
            replyShow: false
        }]
    }),
    methods: {
        //接收到表情事件
        getEmoji(emo) {
            //获取点击对应的表情
            let textArea = this.$refs.textarea;
            // let textArea = document.getElementsByClassName('input')[0];
            function changeSelectedText(obj, str) {
                if (window.getSelection) {
                    // 非IE浏览器
                    textArea.setRangeText(str);
                    // 在未选中文本的情况下，重新设置光标位置
                    textArea.selectionStart += str.length;
                    textArea.focus();
                } else if (document.selection) {
                    // IE浏览器
                    obj.focus();
                    var sel = document.selection.createRange();
                    sel.text = str;
                }
            }
            changeSelectedText(textArea, emo[emo.key]);
            this.textContent = textArea.value; // 要同步data中的数据
            return;
        },
        /* 轮询二级回复 */
        handleReply(item) {
            this.commentVoList.forEach((e) => {
                if (e.replyShow == true) {
                    e.replyShow = false;
                }
            })
            item.replyShow = true;
            this.textContent2 = '回复 @' + item.fromUser.userName + ' '
        },
        ...mapActions('article', ['selectArticleById']),
        ...mapActions('article', ['doAddPageView']),
        ...mapActions('article', ['doLikeById']),
        ...mapActions('article', ['doTrampleById']),
        ...mapActions('article_comment', ['selectCommentVoByArticleId']),
        /* 初始化数据 */
        async init() {
            this.doGetArticle()
            this.doGetComment()
            // 访问量 + 1
            this.doAddPageView(this.$route.query.articleId)
        },
        /* 得到帖子Vo */
        async doGetArticle() {
            const { code, message, data } = await this.selectArticleById(this.$route.query.articleId);
            if (code === 200) {
                this.articleVo = { ...data };
            }
        },
        /* 得到评论Vo */
        async doGetComment() {
            const { code, message, data } = await this.selectCommentVoByArticleId(this.$route.query.articleId);
            if (code === 200) {
                this.commentVoList = [...data];
            } else {
                this.commentVoList = [];
            }
        },
        /* 点赞 */
        async addLikeById() {
            const { code, message, data } = await this.doLikeById(this.$route.query.articleId);
            if (code === 200) {
                this.$notify({
                    title: '点赞成功',
                    type: 'success'
                });
            }
        },
        /* 点踩 */
        async addTrampleById() {
            const { code, message, data } = await this.doTrampleById(this.$route.query.articleId);
            if (code === 200) {
                this.$notify({
                    title: '点踩成功',
                    type: 'info'
                });
            }
        },
        /* 滑到评论区 */
        toWriteComment() {
            document.getElementsByClassName('article-details')[0].scrollTo({ top: this.$refs.article.offsetHeight, behavior: "smooth" });
        },
        ...mapActions('article_comment', ['insertComment']),
        /* 评论文章 */
        async doPublishComment() {
            if (this.textContent == "") {
                ElementUI.Message.warning('您还没有评论内容')
                return
            }
            let comment = {
                toArticleId: this.$route.query.articleId,
                fromUserId: localStorage.getItem('userId'),
                commentContent: this.textContent
            }
            const { code, message, data } = await this.insertComment(comment);
            if (code === 200) {
                ElementUI.Message.success('评论成功')
                setTimeout(() => {
                    location.reload()
                }, 1000)
            }
        },
        /* 评论一级评论 */
        async doPublishComment2(item) {
            if (this.textContent2 == "") {
                ElementUI.Message.warning('您还没有评论内容')
                return
            }
            let comment = {
                toArticleId: this.$route.query.articleId,
                toCommentId: item.comment.commentId,
                fromUserId: localStorage.getItem('userId'),
                commentContent: this.textContent2
            }
            const { code, message, data } = await this.insertComment(comment);
            if (code === 200) {
                ElementUI.Message.success('评论成功')
                setTimeout(() => {
                    location.reload()
                }, 1000)
            }
        },
        ...mapActions('article_comment', ['doCommentLikeById']),
        ...mapActions('article_comment', ['doCommentTrampleById']),
        /* 点赞评论 */
        async addCommentLikeById(commentId) {
            const { code, message, data } = await this.doCommentLikeById(commentId);
            if (code === 200) {
                this.$notify({
                    title: '点赞成功',
                    type: 'success'
                });
            }
        },
        /* 点踩评论 */
        async addCommentTrampleById(commentId) {
            const { code, message, data } = await this.doCommentTrampleById(commentId);
            if (code === 200) {
                this.$notify({
                    title: '点踩成功',
                    type: 'info'
                });
            }
        },
        /* 到顶部 */
        async toTop() {
            document.getElementsByClassName('article-details')[0].scrollTo({ top: 0, behavior: "smooth" });
        }
    },
    created() {
        this.init();
    },
    computed: {
        userHead() {
            return localStorage.getItem('userHead');
        }
    },
    components: {
        Nav, Foot, emoji
    }
}
</script>


<style lang="less" scoped>
.article-details {
    width: 100%;
    height: 100%;
    background-color: #f6f7f9;
    overflow-x: hidden;
    overflow-y: scroll;

    .bread {
        position: relative;
        left: 530px;
        font-size: 12px;
    }

    .article {
        position: relative;
        width: 48%;
        left: 26%;
        top: 100px;
        min-height: 500px;
        background-color: #fff;
        padding-bottom: 80px;
        box-shadow: 0 0 10px #f6f7f9;

        .art-title {
            position: relative;
            width: 70%;
            left: 15%;
            font-size: 28px;
            color: #222;
            font-weight: bold;
            padding-top: 25px;
            padding-bottom: 20px;

        }

        .bar1 {
            position: relative;
            left: 15%;
            display: flex;
            color: #999;
            text-align: center;
            // padding-bottom: 20px;
            width: 70%;

            .art-time {
                position: relative;
                font-size: 12px;
                margin-right: 20px;
            }

            .art-page-view {
                position: relative;
                font-size: 12px;
                margin-right: 20px;
            }

            .art-like-num {
                position: relative;
                margin-right: 20px;
                font-size: 12px;
            }

            .art-comment-num {
                position: relative;
                margin-right: 20px;
                font-size: 12px;
            }
        }

        .author {
            position: relative;
            left: 15%;
            display: flex;
            // padding-bottom: 20px;
            width: 70%;
            height: 90px;

            .avator {
                position: absolute;
                top: 22.5px;
                width: 45px;
                height: 45px;
                border-radius: 50%;
            }

            .name {
                position: absolute;
                color: #1b1b1b;
                font-size: 15px;
                left: 60px;
                top: 22px;
                width: 200px;
                // 省略效果
                overflow: hidden;
                display: -webkit-box;
                /*第几行裁剪*/
                -webkit-line-clamp: 1;
                -webkit-box-orient: vertical;
            }

            .bar2 {
                position: absolute;
                left: 60px;
                top: 50px;
                width: 200px;
                display: flex;


                .team-num {
                    position: relative;
                    color: #929292;
                    font-size: 13px;
                    height: 20px;
                    margin-right: 10px;
                }

                .art-num {
                    position: relative;
                    color: #929292;
                    font-size: 13px;
                    height: 20px;
                }
            }

            .details {
                position: absolute;
                right: 0px;
                top: 35px;
                width: 100px;
                font-size: 13px;
                background-color: #00a29a;
                color: #fff;
                text-align: center;
                height: 30px;
                line-height: 30px;
                border-radius: 5px;
                cursor: pointer;
            }
        }

        .line {
            position: relative;
            border-top: 1px solid #e9e9e9;
            width: 80%;
            left: 10%;
            padding-bottom: 40px;
        }

        .content {
            position: relative;
            width: 70%;
            left: 15%;
            min-height: 500px;

            ::v-deep img {
                // 保持原图的宽高比

                // object-fit: cover;

                // object-fit: scale-down;

                width: auto;
                height: auto;
                max-width: 100%;
                max-height: 100%;
            }

        }


        .line2 {
            position: relative;
            border-bottom: 1px solid #e9e9e9;
            width: 80%;
            left: 10%;
            padding-top: 40px;
        }



    }

    .comment {
        position: relative;
        top: 120px;
        width: 48%;
        left: 26%;
        // height: 300px;
        // min-height: 500px;
        background-color: #fff;
        // padding-bottom: 80px;
        box-shadow: 0 0 10px #f6f7f9;
        padding-bottom: 50px;

        .line4 {
            position: relative;
            height: 30px;
            border-bottom: 1px solid #e9e9e9;
            width: 90%;
            left: 5%;
        }

        .none-data {
            font-size: 12px;
            width: 100%;
            text-align: center;
            color: #c7c7c7;
            padding-top: 15px;
        }

        .title {
            position: relative;
            padding-top: 20px;
            padding-bottom: 45px;
            font-size: 18px;
            padding-left: 35px;
            color: #222;
        }

        .write {
            position: relative;
            height: 130px;
            width: 90%;
            left: 4%;
            border-top: 1px solid #e9e9e9;
            padding-top: 50px;
            padding-left: 20px;

            .avator {
                width: 50px;
                border-radius: 50%;
            }

            .input {
                position: absolute;
                width: 76%;
                top: 45px;
                font-size: 13px;
                outline: none;
                margin-left: 20px;
                padding-left: 12px;
                padding-top: 5px;
                resize: none;
                border: 1px #0085b9 solid;
                border-radius: 5px;
            }

            .write-btn {
                position: absolute;
                right: 18px;
                top: 45px;
                height: 68px;
                width: 65px;
                background-color: #00b5e5;
                color: #fff;
                border-radius: 5px;
                cursor: pointer;

                .btn-text {
                    position: relative;
                    font-size: 14px;
                    width: 28px;
                    height: 28px;
                    left: 19px;
                    top: 13px;
                }
            }

            .write-btn:hover {
                background-color: #0089be;
            }

            .emoji {
                position: absolute;
                top: 120px;
                left: 90px;
                cursor: pointer;
            }
        }

        .comment-item {
            position: relative;
            width: 89.5%;
            left: 4%;
            // height: 80px;
            padding-top: 20px;
            // padding-bottom: 10px;
            padding-left: 25px;
            // display: flex;

            .line3 {
                position: relative;
                top: -20px;
                border-top: 1px solid #e9e9e9;
                width: 90%;
                left: 8%;
            }

            .com-avator {
                width: 50px;
                border-radius: 50%;
            }

            .com-user-name {
                position: relative;
                top: -50px;
                left: 65px;
                font-size: 12px;
                color: #6d757a;
                font-weight: 600;
            }

            .com-content {
                position: relative;
                width: 700px;
                left: 65px;
                top: -40px;
                font-size: 14px;
            }

            .bar3 {
                position: relative;
                font-size: 12px;
                top: -25px;
                left: 65px;
                width: 500px;
                display: flex;
                height: 0;

                .com-time {
                    position: relative;
                    margin-right: 25px;
                }

                .com-like-btn {
                    margin-right: 25px;
                    position: relative;
                    cursor: pointer;
                }

                .com-trample-btn {
                    margin-right: 20px;
                    position: relative;
                    cursor: pointer;
                }

                .com-reply {
                    margin-right: 20px;
                    position: relative;
                    cursor: pointer;
                    font-size: 12px;
                    color: #929292;
                }

                .com-reply:hover {
                    color: #222;
                }


            }

            .write2 {
                position: relative;
                height: 100px;
                width: 90%;
                left: 4%;
                // padding-top: 50px;
                padding-left: 25px;

                .avator {
                    position: relative;
                    top: 20px;
                    width: 50px;
                    border-radius: 50%;
                }

                .input {
                    position: absolute;
                    width: 73%;
                    top: 15px;
                    font-size: 13px;
                    outline: none;
                    margin-left: 20px;
                    padding-left: 12px;
                    padding-top: 5px;
                    resize: none;
                    border: 1px #0085b9 solid;
                    border-radius: 5px;
                }

                .write-btn {
                    position: absolute;
                    right: 18px;
                    top: 16px;
                    height: 65px;
                    width: 65px;
                    background-color: #00b5e5;
                    color: #fff;
                    border-radius: 5px;
                    cursor: pointer;

                    .btn-text {
                        position: relative;
                        font-size: 14px;
                        width: 28px;
                        height: 28px;
                        left: 19px;
                        top: 13px;
                    }
                }

                .write-btn:hover {
                    background-color: #0089be;
                }

                .emoji {
                    position: absolute;
                    top: 85px;
                    left: 90px;
                    cursor: pointer;
                    height: 0;
                }
            }

        }

        .comment-item2 {
            position: relative;
            width: 92%;
            left: 4%;
            // height: 80px;
            padding-top: 20px;
            // padding-bottom: 10px;
            padding-left: 25px;
            // display: flex;

            .line3 {
                position: relative;
                top: -20px;
                border-top: 1px solid #e9e9e9;
                width: 90%;
                left: 8%;
            }

            .com-avator {
                position: relative;
                width: 50px;
                border-radius: 50%;
            }

            .com-user-name {
                position: relative;
                top: -50px;
                left: 65px;
                font-size: 12px;
                color: #6d757a;
                font-weight: 600;
            }

            .com-content {
                position: relative;
                width: 680px;
                left: 65px;
                top: -40px;
                font-size: 14px;
                // height: 500px;
            }

            .bar3 {
                position: relative;
                font-size: 12px;
                top: -25px;
                left: 65px;
                width: 500px;
                height: 0;
                display: flex;

                .com-time {
                    position: relative;
                    margin-right: 25px;
                }

                .com-like-btn {
                    margin-right: 25px;
                    position: relative;
                    cursor: pointer;
                }

                .com-trample-btn {
                    margin-right: 20px;
                    position: relative;
                    cursor: pointer;
                }
            }
        }
    }


    .fit-bar {
        position: fixed;
        bottom: 190px;
        right: 420px;
        width: 55px;
        height: 210px;
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 0 10px #e6e6e68f;

        .bar-btn {
            width: 100%;
            height: 65px;
            text-align: center;
            margin-bottom: 5px;
            cursor: pointer;
            pointer-events: auto;

            .btn-img {
                position: relative;
                top: 10px;
                width: 40px;
                height: 40px;
                font-size: 35px;
                color: #545454;
            }

            .btn-data {
                margin-top: 3px;
                color: #999;
                font-size: 13px;
            }
        }

    }

    .fit-to-top {
        position: fixed;
        bottom: 120px;
        right: 420px;
        width: 55px;
        height: 55px;
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 0 10px #e6e6e68f;
        text-align: center;
        line-height: 55px;
        font-size: 20px;
        cursor: pointer;
    }

    .art-foot {
        position: relative;
        top: 150px;
    }
}
</style>