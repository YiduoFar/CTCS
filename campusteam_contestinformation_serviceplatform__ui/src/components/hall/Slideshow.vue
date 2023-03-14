<template>
    <div class="slideshow">
        <!-- 标题 -->
        <div class="slide-title">竞赛推荐</div>
        <!-- 轮播图 -->
        <div class="slide-show">
            <el-carousel :interval="5000" arrow="hover" height="390px" trigger="click">
                <el-carousel-item v-for="item, index in slideImgList" :key="index">
                    <h3>
                        <img :src="item.competitionCover" alt="" class="slide-img"
                            @click="$router.push({ path: '/competition_details', query: { competitionId: item.competitionId } })">
                        {{ index }}
                    </h3>
                </el-carousel-item>
            </el-carousel>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';
export default {
    data: () => ({
        slideImgList: []
    }),
    methods: {
        ...mapActions('competition', ['selectSlideShowVo']),
        async init() {
            const { code, message, data } = await this.selectSlideShowVo()
            if (code === 200) {
                this.slideImgList = [...data]
            }
        }
    },
    created() {
        this.init()
    },
}
</script>

<style lang="less" scoped>
.el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
}

.slide-title {
    height: 50px;
    line-height: 50px;
    font-size: 18px;
    color: #979797;
    width: 200px;
    margin-left: 20px;
    cursor: default;
}

.slide-show {
    width: 1010px;
    height: 390px;
    overflow: hidden;
    border-radius: 20px;
    box-shadow: 2px 2px 10px #dadada;
}

.slide-img{
    width: 100%;
    height: 100%;
}
</style>