<template>
    <div class="emoji">
        <el-tooltip placement="bottom" effect="light" trigger="click" :visible="show">
            <template slot="content">
                <div class="emojiList">
                    <template v-for="(emo, index) in emojiArr">
                        <div class="emoji-item" :key="index" @click="handleClick(emo)">
                            <span>{{ emo[emo.key] }}</span>
                        </div>
                    </template>
                </div>
            </template>
            <div @click="openEmoji" ref="icon">😃</div>
            <!-- <my-icon type="icon-biaoqing3" @click="openEmoji" ref="icon"></my-icon> -->
        </el-tooltip>
    </div>
</template>

<script>
import { emojiArr } from '../../assets/util/emoji';
export default {
    name: 'emoji',
    methods: {
        openEmoji() {
            //处理表情框显示
            this.show = !this.show;
        },
        handleClick(emo) {
            //处理点击到对应的表情
            this.show = false;
            //将选择到的表情传递到
            this.$emit('change', emo)
        },
        closeEmoji() {
            setTimeout(() => {
                this.show = false;
            }, 100);
        }
    },
    data() {
        return {
            emojiArr,
            show: false,
            icon: null
        }
    },
    mounted() {
        //注册一个事件，当表情失去焦点的时候触发
        this.icon = this.$refs.icon;
        //失去焦点关闭表情框
        this.icon.addEventListener('blur', this.closeEmoji, 100);
    },
    destroyed() {
        //取消监听事件
        this.icon.removeEventListener('blur', this.closeEmoji)
    }
}
</script>

<style lang="less" scoped>

.emoji {
    width: 20px;
}

.emojiList {
    display: flex;
    justify-content: left;
    align-items: center;
    flex-wrap: wrap;
    
    width: 225px;

    .emoji-item {
        width: 25px;
        // text-align: left;
        cursor: pointer;
    }
}
</style>