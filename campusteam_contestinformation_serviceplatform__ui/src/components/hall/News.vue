<template>
    <div class="news ">
        <!-- title -->
        <div class="news-title">
            <!-- 图标 -->
            <div class="news-title-logo">
                <img src="../../assets/img/图标/头条.png" alt="" class="news-title-logo-img">
            </div>
            <!-- text -->
            <div class="news-title-text">头条</div>
        </div>
        <!-- 翻页 -->
        <el-pagination layout="prev, next" :total="totalPage" class="news-el-pagination" @current-change="currentChange">
        </el-pagination>
        <!-- line -->
        <div class="separator-op"></div>
        <!-- news -->
        <div class="news-info " v-for="item in newsList"
            @click="$router.push({ path: '/n_a_details', query: { id: item.newsId, type: 'news' } })">
            <!-- 新闻标题 -->
            <div class="news-info-title ">{{ item.newsTitle }}</div>
            <!-- 新闻摘要 -->
            <div class="news-info-abstract ">{{ item.newsDigest }}</div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    data: () => ({
        newsList: [],
        queryVo: {
            current: 1,
            size: 5
        },
        totalPage: 0
    }),
    methods: {
        ...mapActions(['selectNewsPage']),
        // 分页触发
        currentChange(page) {
            this.queryVo.current = page
            this.init()
        },
        /* 初始化 */
        async init() {
            const { code, message, data } = await this.selectNewsPage(this.queryVo);
            if (code === 200) {
                this.newsList = [...data.page];
                // 计算总页数
                let size = this.queryVo.size;
                let total = data.total
                this.totalPage = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            }
        }
    },
    created() {
        this.init();
    }
}
</script>

<style lang="less" scoped>
.news {
    position: absolute;
    width: 340px;
    height: 370px;

    // overflow: hidden;
    .news-title {
        position: absolute;
        width: 80%;
        height: 50px;
        top: -35px;
        display: flex;

        .news-title-logo {
            position: absolute;
            left: 10px;
            width: 25px;
            height: 25px;

            .news-title-logo-img {
                width: 25px;
                height: 25px;
            }
        }

        .news-title-text {
            position: absolute;
            left: 40px;
            font-size: 18px;
            font-weight: bold;
        }
    }

    .news-el-pagination {
        position: absolute;
        width: 20%;
        height: 50px;
        top: -35px;
        right: 10px;
    }

    .news-info {
        position: relative;
        width: 100%;
        height: 60px;
        margin-top: 15px;

        .news-info-title {
            position: absolute;
            width: 95%;
            height: 30px;
            line-height: 30px;
            left: 10px;
            top: 0;
            // text-overflow: ellipsis;
            // white-space: overflow;
            font-size: 15px;
            color: #464646;
            cursor: pointer;

            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
        }

        .news-info-title:hover {
            text-decoration: underline;
        }

        .news-info-abstract {
            position: absolute;
            width: 95%;
            height: 30px;
            line-height: 30px;
            left: 10px;
            top: 30px;
            font-size: 13px;
            color: #979797;
            cursor: pointer;

            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
        }
    }
}
</style>