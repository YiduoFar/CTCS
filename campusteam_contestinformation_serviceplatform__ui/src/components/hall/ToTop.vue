<template>
    <div class="corner-buttons-container ts05s">
        <img src="../../assets/img/图标/ToTop.png" alt="" class="corner-buttons-container-img">
    </div>
</template>

<script>
export default {
    data: () => ({
        // showBtn: false, // 回到顶部，默认是false，就是隐藏起来
    }),
    mounted() {
        // body.window.addEventListener("scroll", this.handleScroll, true)
        document.addEventListener("scroll", this.handleScroll, true);
    },
    methods: {
        // 获取页面滚动距离
        handleScroll() {
            let top = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
            // console.log(top, '滚动距离')
            // console.log(window.pageYOffset, 'window.pageYOffset');
            // console.log(document.documentElement.scrollTop, 'document.documentElement.scrollTop');
            // console.log(document.body.scrollTop, 'document.body.scrollTop');
        }
    },
    // 滚动重置
    beforeDestroy() {
        window.removeEventListener('scroll', this.handleScroll)
    },

}
</script>

<style lang="less" scoped>
.corner-buttons-container {
    position: fixed;
    bottom: 90px;
    right: 30px;
    width: 48px;
    height: 48px;
    border-radius: 8px;
    cursor: pointer;
    box-shadow: 0px 2px 8px #dbdbdb;

    // overflow: scroll;


    .corner-buttons-container-img {
        position: absolute;
        width: 30px;
        height: 30px;
        left: 9px;
        top: 9px;
    }
}

.corner-buttons-container:hover {
    box-shadow: 0px 2px 15px #b1b1b1;

}
</style>

