<template>
    <div class="calendar">
        <div class="cal-title">Time</div>
        <el-calendar>
        </el-calendar>
    </div>
</template>

<script>
export default {
    data: () => ({

    })
}
</script>

<style lang="less">
.calendar {
    width: 380px;
    height: 430px;
    // overflow: hidden;
    // top: 200px;
    // position: absolute;
}

.el-calendar__header{
    width: 300px;
    height: 25px;
    // border: 1px red solid;
    background-color: #ffffff5b;
    border-radius: 20px 20px 0 0;
    box-shadow: 2px 2px 10px #dadada;
    position: absolute;
    top: 20px;
    left: 20px;
    z-index: 0;
}

.el-calendar{
    border-radius: 10px;
}

.el-calendar__body {
    background-color: #ffffff5b;
    box-shadow: 5px 5px 10px #dadada;
    border-radius: 0 0 20px 20px;
    width: 300px;
    position: absolute;
    top: 70px;
    left: 20px;
    z-index: 1;
}

.el-calendar-table .el-calendar-day {
    // border-radius: 10px;
    height: 40px;
}

.el-button-group>.el-button:first-child{
    // width: 50px;
    text-align: center;
}
.cal-title{
    position: absolute;
    top: -12px;
    left: 35px;
    font-size: 18px;
    color: #979797;
    cursor: default;
}
</style>