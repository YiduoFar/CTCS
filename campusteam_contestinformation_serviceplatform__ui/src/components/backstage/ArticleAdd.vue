<template>
    <div class="me-main ">
        <div class="me-top ">
            <div class="tab">
                <span style="cursor: pointer;" @click="$router.push({ path: '/backstage', query: { focusIndex: 9 } })">
                    <i class="el-icon-arrow-left"></i>
                    我的帖子
                </span>
            </div>
            <!-- 标题 -->
            <el-input placeholder="请输入标题" v-model="article.articleTitle" class="input" clearable>
            </el-input>
            <!-- 发布按钮 -->
            <div class="publish-btn ts05s" @click="dialogVisible2 = true">发布帖子</div>
            <!-- 头像 -->
            <img :src="userHead" alt="" class="user-head">
        </div>
        <mavon-editor @change="getHtml" v-model="articleContent" :toolbars="toolbars" ref="md" class="me"
            :placeholder="placeholderText" fontSize="18px" :html="true" toolbarsBackground="#fff" :imageFilter="imageFilter"
            @imgAdd="imgAdd" @imgDel="imgDel">
            <!-- 自定义摘要按钮  -->
            <template slot="left-toolbar-after">
                <button type="button" @click="dialogVisible = true" class="op-icon" aria-hidden="true" title="摘要"><i
                        class="el-icon-document"></i></button>
            </template>
        </mavon-editor>
        <!-- 摘要 -->
        <el-dialog title="摘要" class="thisblack-bgc" :modal="false" :visible.sync="dialogVisible" width="30%">
            <div>
                <el-input type="textarea" resize="none" :rows="5" maxlength="100" placeholder="文章摘要..."
                    v-model="article.articleDigest" clearable>
                </el-input>
                <span style="position: relative; top: 20px;color: #999; font-size: 12px;">
                    （100字以内）
                </span>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" round @click="dialogVisible = false">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 标签 -->
        <el-dialog title="添加帖子标签" class="thisblack-bgc" :modal="false" :visible.sync="dialogVisible2" width="30%">
            <!-- 标签添加与删除 -->
            <div class="item-content-sp2 ">
                <!-- 标签显示 -->
                <div class="label-item ts02s" v-for="item, index in article.articleLabel" @mouseover="setDelBtn0(index)"
                    @mouseleave="setDelBtn1(index)">
                    <span class="label-content">
                        {{ item }}
                    </span>
                    <transition name="el-zoom-in-center">
                        <div class="label-del" v-show="articleLabelDelBtn[index]" @click="delLabel(index)">
                            <i class="el-icon-close"></i>
                        </div>
                    </transition>
                </div>
                <!-- 清除标签 -->
                <div class="label-add-btn2 ts02s" @click="article.articleLabel = []"
                    v-if="article.articleLabel.length != 0">
                    清除
                </div>
                <div class="label-add-tip">（最多添加3个标签）</div>
            </div>
            <!-- 输入标签 -->
            <el-input maxlength="7" size="middle" class="input2" placeholder="帖子标签..." v-model="articleLabelName" clearable>
            </el-input>
            <!-- 添加按钮 -->
            <div class="label-add-btn el-icon-plus ts05s" @click="addLabelitem()">
                添加</div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" round @click="dialogVisible2 = false; doAddArticle()">确定并发布</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import ElementUI from 'element-ui';
import { mapActions } from 'vuex';
export default {
    data: () => ({
        placeholderText: '畅所欲言吧...',
        /* 文章 */
        article: {
            articleAuthorId: '',
            articleTitle: '【无标题】',
            articleDigest: '',
            articleContent: '',
            articleLabel: [],
        },
        articleContent: '',
        dialogVisible: false,
        dialogVisible2: false,
        articleLabelDelBtn: [false, false, false, false, false, false],
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
        imageList:[],
    }),
    methods: {
        /* 发布帖子 */
        ...mapActions('article', ['insertArticle']),
        async doAddArticle() {
            let article = { ...this.article }
            // 作者id
            article.articleAuthorId = localStorage.getItem('userId')
            // 序列化标签
            article.articleLabel = JSON.stringify(article.articleLabel)
            const { code, message, data } = await this.insertArticle(article)
            if (code === 200) {
                ElementUI.Message.success('帖子发布成功！')
                setTimeout(() => {
                    this.$router.push({ path: '/backstage', query: { focusIndex: 9 } })
                }, 1000)
            }
        },
        /* 获取解析后的html */
        getHtml(value, render) {
            this.article.articleContent = render
        },
        ...mapActions('article', ['articleImageUpload']),
        ...mapActions('article', ['articleImageDelete']),
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
            var formdata = new FormData();
            formdata.append('image', $file);
            const { code, message, data } = await this.articleImageUpload(formdata);
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
            const { code, message, data } = await this.articleImageDelete(pos[0]);
        },
        /**
         * 控制标签删除按钮的显示
         * @param {*} index 
         */
        setDelBtn0(index) {
            this.$set(this.articleLabelDelBtn, index, true)
        },
        /**
         * 控制标签删除按钮的隐藏
         * @param {*} index 
         */
        setDelBtn1(index) {
            this.$set(this.articleLabelDelBtn, index, false)
        },
        /**
         * 添加标签HTML
         */
        addLabelitem() {
            if (this.article.articleLabel.length >= 3) {
                ElementUI.Message.warning('不能再添加了')
                return
            }
            if (this.articleLabelName == '') {
                return
            }
            if (this.articleLabelName.length > 7) {
                ElementUI.Message.warning('数据过长')
                return
            }
            this.article.articleLabel.push(this.articleLabelName)
            this.articleLabelName = ''
        },
        /**
         * 删除标签HTML
         * @param {*} index 
         */
        delLabel(index) {
            this.article.articleLabel.splice(index, 1)
        },
        /**
         * 清除标签HTML
         */
        clearLabelitem() {
            this.article.articleLabel = []
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
            background-color: #fc5454;
            cursor: pointer;
            border-radius: 3px;
        }

        .publish-btn:hover {
            background-color: #df3535;
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

    .item-content-sp2 {
        display: flex;

        .label-item {
            position: relative;
            // top: 30px;
            // width: 100px;
            height: 25px;
            line-height: 25px;
            font-size: 12px;
            text-align: center;
            // overflow: hidden;
            border: 3px dashed #76cdff;
            color: #909399;
            cursor: pointer;
            background-color: #a0dcffb4;
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
            border: 3px dashed #25afff;
        }

        .label-add-btn {
            position: relative;
            margin-left: 15px;
            width: 30px;
            height: 30px;
            background-color: #979797;
            border-radius: 50%;
            color: #fff;
            line-height: 30px;
            text-align: center;
            cursor: pointer;
        }

        .label-add-btn2 {
            position: relative;
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

        .label-add-tip {
            font-size: 12px;
            margin-left: 10px;
            height: 25px;
            line-height: 30px;
        }

        .label-add-btn3 {
            position: relative;
            margin-left: 10px;
            width: 90px;
            height: 30px;
            background-color: #979797;
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
            background-color: #979797;
        }

        .label-add-btn3:hover {
            background-color: #979797;
        }
    }

    .input2 {
        position: relative;
        margin-top: 15px;
        left: 10px;
        width: 400px;
    }

    .label-add-btn {
        position: relative;
        left: 30px;
        width: 70px;
        text-align: center;
        height: 30px;
        line-height: 30px;
        background-color: #76cdff;
        color: #fff;
        cursor: pointer;
    }

    .label-add-btn:hover {
        background-color: #55ade0;
    }

}

.el-button--primary {
    background-color: #fc5454;
    border-color: #fc5454;
}

.el-button--primary:focus,
.el-button--primary:hover {
    transition: 0.5 all ease-in-out;
    background-color: #f04141;
    border-color: #f04141;
}
</style>