<template>
    <div class="news">
        <!-- title -->
        <div class="news-title">
            <!-- 图标 -->
            <div class="news-title-logo">
                <img src="../../assets/img/图标/公告.png" alt="" class="news-title-logo-img">
            </div>
            <!-- text -->
            <div class="news-title-text">公告</div>
        </div>
        <!-- 翻页 -->
        <el-pagination layout="prev, next" :total="totalPage" class="news-el-pagination" @current-change="currentChange">
        </el-pagination>
        <!-- line -->
        <div class="separator-op-5"></div>
        <!-- news -->
        <div class="news-info" v-for="item in announcementList"
            @click="$router.push({ path: '/n_a_details', query: { id: item.announcementId, type: 'announcement' } })">
            <div class="news-info-title ">{{ item.announcementTitle }}</div>
            <div class="news-info-date ">{{ $moment(item.announcementPublishTime).format('MM.DD') }}</div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    data: () => ({
        announcementList: [],
        queryVo: {
            current: 1,
            size: 5
        },
        totalPage: 0
    }),
    methods: {
        ...mapActions(['selectAnnouncementPage']),
        // 分页触发
        currentChange(page) {
            this.queryVo.current = page
            this.init()
        },
        /* 初始化 */
        async init() {
            const { code, message, data } = await this.selectAnnouncementPage(this.queryVo);
            if (code === 200) {
                this.announcementList = [...data.page];
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
        width: 95%;
        margin-top: 5px;
        font-size: 15px;
        color: #464646;
        display: flex;

        .news-info-title {
            cursor: pointer;
            position: relative;
            width: 75%;
            height: 30px;
            line-height: 30px;
            left: 10px;
            top: 0;
            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
        }

        .news-info-date {
            position: absolute;
            width: 15%;
            height: 30px;
            line-height: 30px;
            text-align: right;
            right: 0;
            top: 0;
        }

        .news-info-title:hover {
            text-decoration: underline;
        }
    }

    .separator-op-5 {
        border-bottom: 1px solid #e4eaf1;
        background: -webkit-gradient(linear, left top, left bottom, from(white), to(#f7f7f7));
        background: -o-linear-gradient(white, #f7f7f7);
        background: linear-gradient(white, #f7f7f7);
        height: 7px;
        margin-bottom: 15px;
    }
}
</style>