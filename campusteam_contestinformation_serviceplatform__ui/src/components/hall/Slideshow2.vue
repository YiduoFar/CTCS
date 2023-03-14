<template>
    <div class="slideshow">
        <!-- 无数据 -->
        <el-empty description="没有您想要的内容" :image-size="150" style="position: relative;left: 420px;" v-if="slideImgList.length == 0"></el-empty>
        <!-- 轮播图 -->
        <div class="slide-show" v-if="slideImgList.length != 0">
            <el-carousel :interval="5000" arrow="hover" height="260px" trigger="click">
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

.slide-show {
    width: 1010px;
    height: 260px;
    overflow: hidden;
    border-radius: 10px;
    box-shadow: 2px 2px 10px #dadada;
}

.slide-img {
    position: relative;
    width: 100%;
    height: 100%;
    cursor: pointer;
}
</style>