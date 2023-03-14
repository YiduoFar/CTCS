<template>
    <!-- 往届竞赛列表 -->
    <div class="model-old-competition-list">
        <!-- item-往届竞赛 --> 
        <!-- 无数据 -->
        <el-empty description="没有您想要的内容" :image-size="150" v-if="competitionList.length == 0"></el-empty>
        <div class="old-competition-list-item" v-for="item in competitionList">
            <div class="o-c-l-item-name ts02s"
                @click="$router.push({ path: '/competition_details', query: { competitionId: item.competitionId } })">
                {{ item.competitionName }}
            </div>
            <div class="o-c-l-item-begin-time">{{ $moment(item.competitionPublishDate).format('YYYY.MM.DD HH:mm') }}</div>
            <div class="o-c-l-item-face">{{ competitionAudience[item.competitionAudience] }}</div>
            <div class="o-c-l-item-status ">比赛已结束</div>
            <div class="o-c-l-item-details "
                @click="$router.push({ path: '/competition_details', query: { competitionId: item.competitionId } })">
                查看详情
            </div>
        </div>
        <!-- 分页 -->
        <div class="old-competition-list-page ">
            <el-pagination layout="prev, pager, next" :total="totalPage" class="page-el-pagination"
                @current-change="currentChange">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    data: () => ({
        competitionList: [],
        queryVo: {
            current: 1,
            size: 6,
            keyword: '',
            status: 0
        },
        totalPage: 0,
        competitionAudience: ['面向全校', '面向学院', '面向专业', '面向个人'],
    }),
    created() {
        this.search()

    },
    methods: {
        ...mapActions('competition', ['selectCompetitionByStatus']),
        async search() {
            const { code, message, data } = await this.selectCompetitionByStatus(this.queryVo);
            if (code === 200) {
                // 分页数据
                this.competitionList = [...data.page]
                this.competitionList.forEach((item) => {
                    item.competitionPublishDate = this.$moment(item.competitionPublishDate).format('YYYY.MM.DD HH:mm')
                })
                // 计算总页数
                let size = this.queryVo.size;
                let total = data.total
                this.totalPage = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            }
        },
        currentChange(page) {
            this.queryVo.current = page
            this.search()
        },

    }
}
</script>

<style lang="less" scoped>
.model-old-competition-list {
    width: 1010px;
    height: 375px;
    border: 1px solid #e8e8ed;
    border-radius: 10px;

    .old-competition-list-item {
        position: relative;
        width: 100%;
        height: 55px;
        border-bottom: 1px solid #e8e8ed;

        .o-c-l-item-name {
            position: absolute;
            top: 10px;
            left: 20px;
            width: 250px;
            cursor: pointer;
            font-weight: 600;
            font-size: 14px;
            color: #222226;

            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;


        }

        .o-c-l-item-name:hover {
            color: #40a9ff;
        }

        .o-c-l-item-begin-time {
            position: absolute;
            top: 30px;
            left: 20px;
            font-size: 10px;
            color: #9d9d9d;
        }

        .o-c-l-item-face {
            position: absolute;
            top: 18px;
            left: 450px;
            font-size: 14px;
            color: #707070;
        }

        .o-c-l-item-status {
            position: absolute;
            top: 18px;
            left: 680px;
            font-size: 14px;
            color: #707070;
        }

        .o-c-l-item-details {
            position: absolute;
            top: 18px;
            left: 920px;
            font-size: 14px;
            color: #277ccc;
            cursor: pointer;
        }
    }

    .old-competition-list-item:hover {
        background-color: #f5f6f7b3;
    }

    .old-competition-list-page {
        position: absolute;
        bottom: 0;
        left: 0;
        height: 40px;
        width: 100%;
        display: flex;
        justify-content: center;

        .page-el-pagination {
            position: absolute;
            top: 3px;
        }
    }
}
</style>