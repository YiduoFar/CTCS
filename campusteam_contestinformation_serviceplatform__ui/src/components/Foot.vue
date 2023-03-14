<template>
    <footer class="foot ">
        <!-- line -->
        <div class="foot-line"></div>
        <!-- left-text -->
        <div class="left-text lt1">@广州交通大学信通学院（广州）版权所有</div>
        <div class="left-text lt2">软件工程系2019级</div>
        <!-- right-text -->
        <div class="right-text1">
            <div class="right-text-info" v-for="item in tipsGroup">{{ item }}</div>
        </div>
        <div class="right-text2">CSDN@莉可妮丝的猫</div>
    </footer>
</template>

<script>
export default{
    data:()=>({
        tipsGroup:['作者介绍', '问题反馈', '帮助中心', '加入我们', '使用条例', '隐私政策']
    })
}
</script>

<style lang="less" scoped>
.foot{
    position: relative;
    width: 100%;
    height: 80px;
    background-color: #ffffff56;
    z-index: 999;

    .foot-line{
        position: absolute;
        width: 75%;
        left: 12.5%;
        border-top: 1px solid #eee;
    }
    .left-text{
        position: absolute;
        width: 30%;
        left: 12.5%;
        height: 20px;
        font-weight: 300;
        color: #373737;
        cursor: pointer;
    }
    .lt1{
        top: 20px;
        font-size: 14px;
    }
    .lt2{
        top: 40px;
        font-size: 12px;
    }

    .right-text1{
        display: flex;
        justify-content: space-evenly;
        position: absolute;
        width: 25%;
        right: 12%;
        height: 20px;
        font-weight: 300;
        color: #373737;
        top: 20px;
        font-size: 14px;
        
        .right-text-info{
            width: 16%;
            text-align: center;
            border-right: 1px solid #c4c4c4;
            cursor: pointer;
        }

        .right-text-info:last-child{
            border: none;
        }
    }

    .right-text2{
        position: absolute;
        width: 30%;
        right: 12.7%;
        height: 20px;
        font-weight: 300;
        color: #373737;
        top: 42px;
        font-size: 13px;
        text-align: right;
    }
}


</style>