<template>
    <div class="me-main ">
        <div class="me-top ">
            <div class="tab">
                <span style="cursor: pointer;" @click="$router.push({ path: '/backstage', query: { focusIndex: 10 } })">
                    <i class="el-icon-arrow-left"></i>
                    公告管理
                </span>
            </div>
            <!-- 标题 -->
            <el-input placeholder="请输入标题" v-model="announcement.announcementTitle" class="input" clearable>
            </el-input>
            <!-- 发布按钮 -->
            <div class="publish-btn ts05s" @click="doAddArticle()">发布公告</div>
            <!-- 头像 -->
            <img :src="userHead" alt="" class="user-head">
        </div>
        <mavon-editor @change="getHtml" v-model="articleContent" :toolbars="toolbars" ref="md" class="me"
            :placeholder="placeholderText" fontSize="18px" :html="true" toolbarsBackground="#fff">
        </mavon-editor>
    </div>
</template>

<script>
import ElementUI from 'element-ui';
import { mapActions } from 'vuex';
export default {
    data: () => ({
        placeholderText: '写公告...',
        /* 文章 */
        announcement: {
            announcementTitle: '【公告】',
            announcementContent: '',
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
        ...mapActions(['insertAnnouncement']),
        ...mapActions(['announcementImageUpload']),
        ...mapActions(['announcementImageDelete']),
        /* 发布公告 */
        async doAddArticle() {
            let announcement = { ...this.announcement }
            if (announcement.announcementContent == '' || announcement.announcementTitle == '【公告】') {
                this.$message.info('请输入公告标题与内容')
                return
            }
            const { code, message, data } = await this.insertAnnouncement(announcement)
            if (code === 200) {
                let resId = data
                ElementUI.Message.success('公告发布成功！')
                setTimeout(() => {
                    this.$router.push({ path: '/n_a_details', query: { id: resId, type: 'announcement' } })
                }, 1000)
            }
        },
        /* 获取解析后的html */
        getHtml(value, render) {
            this.announcement.announcementContent = render
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
            background-color: #b464ff;
            cursor: pointer;
            border-radius: 3px;
        }

        .publish-btn:hover {
            background-color: #6b0fc2;
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
    background-color: #b464ff;
    border-color: #b464ff;
}

.el-button--primary:focus,
.el-button--primary:hover {
    transition: 0.5 all ease-in-out;
    background-color: #6b0fc2;
    border-color: #6b0fc2;
}
</style>