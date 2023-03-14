<template>
    <div class="me-main ">
        <div class="me-top ">
            <div class="tab">
                <span style="cursor: pointer;" @click="$router.push({ path: '/backstage', query: { focusIndex: 10 } })">
                    <i class="el-icon-arrow-left"></i>
                    新闻管理
                </span>
            </div>
            <!-- 标题 -->
            <el-input placeholder="请输入标题" maxlength="65" v-model="news.newsTitle" class="input" clearable>
            </el-input>
            <!-- 发布按钮 -->
            <div class="publish-btn ts05s" @click="beforeAddArticle()">发布新闻</div>
            <!-- 头像 -->
            <img :src="userHead" alt="" class="user-head">
        </div>
        <mavon-editor @change="getHtml" v-model="articleContent" :toolbars="toolbars" ref="md" class="me"
            :placeholder="placeholderText" fontSize="18px" :html="true" toolbarsBackground="#fff" :imageFilter="imageFilter"
            @imgAdd="imgAdd" @imgDel="imgDel">
        </mavon-editor>
        <!-- 摘要 -->
        <el-dialog title="摘要" class="thisblack-bgc" :modal="false" :visible.sync="dialogVisible" width="30%">
            <div>
                <el-input type="textarea" resize="none" :rows="5" maxlength="100" placeholder="文章摘要..."
                    v-model="news.newsDigest" clearable>
                </el-input>
                <span style="position: relative; top: 20px;color: #999; font-size: 12px;">
                    （100字以内）
                </span>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" round @click="doAddArticle();dialogVisible = false">确定并发布</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import ElementUI from 'element-ui';
import { mapActions } from 'vuex';
export default {
    data: () => ({
        placeholderText: '写文章...',
        /* 文章 */
        news: {
            newsTitle: '【新闻】',
            newsDigest: '',
            newsContent: '',
        },
        articleContent: '',
        dialogVisible: false,
        articleLabelName: '',
        /* 工具栏设置 */
        toolbars: {
            bold: true, // 粗体
            italic: true, // 斜体
            header: true, // 标题
            underline: true, // 下划线
            strikethrough: true, // 中划线
            mark: true, // 标记
            superscript: true, // 上角标
            subscript: true, // 下角标
            quote: true, // 引用
            ol: true, // 有序列表
            ul: true, // 无序列表
            link: true, // 链接
            imagelink: true, // 图片链接
            code: true, // code
            table: true, // 表格
            fullscreen: true, // 全屏编辑
            readmodel: true, // 沉浸式阅读
            htmlcode: true, // 展示html源码
            help: true, // 帮助
            /* 1.3.5 */
            undo: true, // 上一步
            redo: true, // 下一步
            trash: true, // 清空
            save: false, // 保存（触发events中的save事件）
            /* 1.4.2 */
            navigation: true, // 导航目录
            /* 2.1.8 */
            alignleft: true, // 左对齐
            aligncenter: true, // 居中
            alignright: true, // 右对齐
            /* 2.2.1 */
            subfield: true, // 单双栏模式
            preview: true, // 预览
        },
        imageList: [],
    }),
    methods: {
        ...mapActions(['insertNews']),
        ...mapActions(['newsImageUpload']),
        ...mapActions(['newsImageDelete']),
        /*  */
        beforeAddArticle() {
            if (this.news.newsContent == '' || this.news.newsTitle == '【新闻】') {
                this.$message.info('请输入新闻标题与内容')
                return
            }
            this.dialogVisible = true
        },
        /* 发布新闻 */
        async doAddArticle() {
            let news = { ...this.news }
            if (this.news.newsDigest == '') {
                this.$message.info('请输入摘要')
                return
            }
            const { code, message, data } = await this.insertNews(news)
            if (code === 200) {
                let resId = data
                ElementUI.Message.success('新闻发布成功！')
                setTimeout(() => {
                    this.$router.push({ path: '/n_a_details', query: { id: resId, type: 'news' } })
                }, 1000)
            }
        },
        /* 获取解析后的html */
        getHtml(value, render) {
            this.news.newsContent = render
        },
        /* 上传图片前的钩子 */
        imageFilter(image) {
            let pattern = /^.*(\.jpg|\.png)$/
            const isJPG = pattern.test(image.name);
            const isLt2M = image.size / 1024 / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.warning('图片只限 jpg/png 格式');
            }
            if (!isLt2M) {
                this.$message.warning('文件大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        /* 图片添加 */
        async imgAdd(pos, $file) {
            // 第一步.将图片上传到服务器.
            var formData = new FormData();
            formData.append('image', $file);
            const { code, message, data } = await this.newsImageUpload(formData);
            if (code === 200) {
                // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                // $vm.$img2Url 详情见本页末尾
                this.$refs.md.$img2Url(pos, data);
                // 存储文件名
                this.imageList[pos - 1] = data;
            }
        },
        /* 图片删除 */
        async imgDel(pos) {
            // 从imageList中删除
            this.imageList = this.imageList.filter(item => item != pos[0])
            // 从服务器上删除
            const { code, message, data } = await this.newsImageDelete(pos[0]);
        },
    },
    created() {
    },
    mounted() {
    },
    computed: {
        userHead() {
            return localStorage.getItem('userHead')
        }
    },
    watch: {
        "articleContent"() {
            
        }
    }
}
</script>

<style lang="less" scoped>
.me-main {
    position: relative;
    width: 100%;
    min-height: 100vh;
    // color: #dbffee56;

    .me-top {
        position: absolute;
        top: 0;
        width: 100%;
        height: 7%;

        .tab {
            position: absolute;
            left: 0;
            top: 0;
            width: 147px;
            height: 65px;
            text-align: center;
            line-height: 65px;
            font-size: 20px;
            color: #6b6b6b;
        }

        .input {
            position: absolute;
            width: 77%;
            top: 13px;
            left: 150px;
            font-size: 20px;
        }

        .publish-btn {
            position: absolute;
            width: 150px;
            height: 35px;
            top: 15px;
            right: 110px;
            text-align: center;
            line-height: 35px;
            color: #fff;
            background-color: #5096ff;
            cursor: pointer;
            border-radius: 3px;
        }

        .publish-btn:hover {
            background-color: #004fc5;
        }

        .user-head {
            position: absolute;
            top: 7px;
            right: 30px;
            width: 50px;
            border-radius: 50%;
        }
    }

    .me {
        position: absolute;
        // top: 100px;
        width: 100%;
        height: 93%;
        top: 7%;
    }

    ::v-deep .v-note-wrapper .v-note-op .v-left-item .op-icon,
    .v-note-wrapper .v-note-op .v-right-item .op-icon {
        font-size: 20px;
        height: 38px;
        width: 38px;
    }

}

.el-button--primary {
    background-color: #5096ff;
    border-color: #5096ff;
}

.el-button--primary:focus,
.el-button--primary:hover {
    transition: 0.5 all ease-in-out;
    background-color: #004fc5;
    border-color: #004fc5;
}
</style>