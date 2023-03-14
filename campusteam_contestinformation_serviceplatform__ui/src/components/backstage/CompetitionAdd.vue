<template>
    <div class="backstage-view3 ">
        <div class="backstage-model3 ">
            <div class="model-title">
                发布新的竞赛
                <i class="el-icon-magic-stick"></i>
            </div>
            <div class="line"></div>
            <!-- name -->
            <div class="form-item">
                <div class="item-title">
                    竞赛名称
                </div>
                <div class="item-content">
                    <el-input class="input1" placeholder="请输入内容" suffix-icon="el-icon-cpu" size="middle"
                        v-model="competition.competitionName">
                    </el-input>
                </div>
            </div>
            <!-- 承办方 -->
            <div class="form-item">
                <div class="item-title">
                    承&nbsp;&nbsp;办&nbsp;&nbsp;方
                </div>
                <div class="item-content" style="display: flex;">
                    <el-input class="input2" placeholder="请输入内容" suffix-icon="el-icon-user" size="middle"
                        v-model="competition.competitionUndertaker">
                    </el-input>
                    <div class="item-title_inner1">
                        校内承办单位
                    </div>
                    <el-input class="input3" placeholder="请输入内容" suffix-icon="el-icon-user" size="middle"
                        v-model="competition.competitionUndertaker2">
                    </el-input>
                </div>
            </div>
            <!-- 受众 -->
            <div class="form-item">
                <div class="item-title">
                    受&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;众
                </div>
                <div class="item-content" style="display: flex;">
                    <el-select class="select1" v-model="competition.competitionAudience" size="middle" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                    <transition name="el-zoom-in-top">
                        <div class="item-title_inner2" v-if="competition.competitionAudience != 0">
                            {{
                                competition.competitionAudience == 1 ? '学院' :
                                competition.competitionAudience == 2 ? '专业' : '个人真实'
                            }}{{
    competition.competitionAudience == 1 ? '名称' : competition.competitionAudience == 2 ?
    '名称' : '姓名'
}}
                        </div>
                    </transition>
                    <el-select class="input4" v-if="competition.competitionAudience == 1" v-model="competition.competitionAudienceName" size="middle" placeholder="请选择">
                        <el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                    <el-input class="input4" v-if="competition.competitionAudience != 0 && competition.competitionAudience != 1" placeholder="请输入内容"
                        suffix-icon="el-icon-menu" size="middle" v-model="competition.competitionAudienceName">
                    </el-input>
                </div>
            </div>
            <!-- isTeamUp -->
            <div class="form-item fi1" v-if="competition.competitionAudience != 3">
                <div class="item-title-sp1">
                    是否需要组队报名
                </div>
                <div class="item-content-sp1">
                    <el-switch class="switch1" v-model="competition.isTeamUp" active-color="#13ce66"
                        inactive-color="#909399">
                    </el-switch>
                </div>
                <transition name="el-zoom-in-top">
                    <div class="slider" v-if="competition.isTeamUp == 1">
                        <div class="slider-left">
                            队伍最少人数
                        </div>
                        <el-slider class="slider-middle" v-model="teamMemberNum" range :marks="marks" :min="1" :max="12">
                        </el-slider>
                        <div class="slider-right">
                            队伍最多人数
                        </div>
                    </div>
                </transition>
            </div>
            <!-- 阶段 -->
            <div class="form-item fi2">
                <div class="item-title">
                    阶&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;段
                </div>
                <div class="item-content-sp2 ">
                    <div class="jd-item ts02s" v-for="item, index in stage" @mouseover="setDelBtn0(index)"
                        @mouseleave="setDelBtn1(index)">
                        <span class="jd-1">
                            {{ item.competitionStageName }}
                        </span>
                        <span class="jd-2">
                            {{ item.competitionStageIntro }}
                        </span>
                        <span class="jd-3">
                            {{ item.competitionStageBeginTime.substring(5, 10) }}
                            {{ item.competitionStageEndTime == '' ? '' : '~' }}
                            {{ item.competitionStageEndTime.substring(5, 10) }}
                        </span>
                        <transition name="el-zoom-in-center">
                            <!-- del -->
                            <div class="jd-del" v-show="stageDelBtn[index]" @click="delStage(index)">
                                <i class="el-icon-close"></i>
                            </div>
                        </transition>
                    </div>
                    <div class="jd-add-btn3 el-icon-plus ts02s" @click="dialogVisible1 = true;" v-if="stage.length == 0">
                        添加阶段</div>
                    <div class="jd-add-btn el-icon-plus ts02s" @click="dialogVisible1 = true;"
                        v-if="stage.length != 0 && stage.length < 5">
                    </div>
                    <div class="jd-add-btn2 ts02s" @click="stage = []" v-if="stage.length != 0">
                        清除
                    </div>
                </div>
            </div>
            <!-- 内容 -->
            <div class="form-item fi3">
                <div class="item-title">
                    内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容
                </div>
                <div class="item-content-sp3 ">
                    <div class="nr-item" v-for="(item, index) in competitionInformationList" @mouseover="setDelBtn2(index)"
                        @mouseleave="setDelBtn3(index)">
                        <transition name="el-fade-in">
                            <!-- del -->
                            <div class="cont-del" v-show="contentDelBtn[index]" @click="delContent(index)">
                                <i class="el-icon-close"></i>
                            </div>
                        </transition>
                        <span class="nr-title">{{ index + 1 }}.{{ item.infoTitle }}</span>
                        <div class="nr-content">{{ item.infoContent }}</div>
                    </div>
                    <div class="nr-input">
                        <el-button class="nr-add-btn" type="primary" round size="small" @click="beforeAddContent()">
                            添加内容
                            <i class="el-icon-top" v-if="competitionInformationList.length != 0"></i>
                            <i class="el-icon-plus" v-if="competitionInformationList.length == 0"></i>
                        </el-button>
                        <el-input type="textarea" resize="none" :rows="5" placeholder="请输入内容" v-model="infoItemContent">
                        </el-input>
                    </div>
                </div>
            </div>
            <!-- 附件 -->
            <div class="form-item">
                <div class="item-title">
                    附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件
                </div>
                <div class="item-content">
                    <span class="fj-add-btn ts02s" @click="dialogVisible3 = true">
                        &nbsp;添加附件
                    </span>
                </div>
            </div>
            <!-- 指导老师 -->
            <div class="form-item">
                <div class="item-title">
                    指导老师
                </div>
                <div class="item-content">
                    <el-input class="input5" placeholder="请输入内容" suffix-icon="el-icon-s-custom" size="middle"
                        v-model="competition.competitionAdvisorList">
                    </el-input>
                </div>
            </div>
            <!-- 时间 -->
            <div class="form-item">
                <div class="item-title">
                    时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间
                </div>
                <div class="item-content">
                    <el-date-picker v-model="competitionTime" type="datetimerange" align="center" range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期">
                    </el-date-picker>
                </div>
            </div>
            <div class="form-item">
                <div class="item-title">
                    类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型
                </div>
                <div class="item-action">
                    <el-radio-group v-model="competition.competitionType" size="small">
                        <el-radio :label="0" border>工科竞赛</el-radio>
                        <el-radio :label="1" border>文科竞赛</el-radio>
                    </el-radio-group>
                </div>
            </div>
            <div class="form-item">
                <div class="item-title">
                    预计加分
                </div>
                <div class="item-action">
                    <el-input-number size="mini" v-model="competition.competitionExtraPoints"
                        controls-position="right"></el-input-number>
                </div>
            </div>
            <!-- btn -->
            <div class="form-item ">
                <div class="joined-is-open">发布后立即开放预报名

                </div>
                <el-switch class="switch2" v-model="competition.competitionJoinedIsOpen" active-color="#13ce66"
                    inactive-color="#909399">
                </el-switch>
                <div class="do-add-competition-btn ts02s" @click="doAddCompetition()">
                    提交并发布
                </div>
            </div>
            <div class="form-item"></div>

            <!-- 添加阶段 -->
            <el-dialog title="添加阶段" class="thisblack-bgc" :modal="false" :visible.sync="dialogVisible1" width="30%">
                <div class="jd-add-title">
                    <span class="title-content">
                        阶段名称
                    </span>
                    <el-input maxlength="4" placeholder="请输入内容" class="jd-input" size="small" v-model="competitionStageName"
                        clearable>
                    </el-input>
                    <span style="margin-left: 10px;color: #999; font-size: 12px;">
                        （4字以内）
                    </span>
                </div>
                <div class="jd-add-title">
                    <span class="title-content ">
                        内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容
                    </span>
                    <el-input maxlength="10" class="jd-input " placeholder="请输入内容" size="small"
                        v-model="competitionStageIntro">
                    </el-input>
                    <span style="margin-left: 10px;color: #999; font-size: 12px;">
                        （10字以内）
                    </span>
                </div>
                <div class="jd-add-title">
                    <span class="title-content ">
                        启止时间
                    </span>
                    <el-date-picker size="large" v-model="competitionStageTime" type="daterange" align="right" unlink-panels
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                    </el-date-picker>
                </div>

                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" round @click="addJDitem();">确 定</el-button>
                    <el-button round @click="clearJDitem();">取 消</el-button>
                </span>
            </el-dialog>
            <!-- 添加内容 -->
            <el-dialog title="内容标题" class="thisblack-bgc" :modal="false" :visible.sync="dialogVisible2" width="30%">
                <div class="jd-add-title">
                    <span class="title-content">
                        内容标题
                    </span>
                    <el-input maxlength="10" placeholder="请输入内容" class="jd-input" size="small" v-model="infoItemTitle"
                        clearable>
                    </el-input>
                    <span style="margin-left: 10px;color: #999; font-size: 12px;">
                        （10字以内）
                    </span>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" round @click="addContent();">确 定</el-button>
                    <el-button round @click="dialogVisible2 = false;">取 消</el-button>
                </span>
            </el-dialog>
            <!-- 上传附件 -->
            <el-dialog title="附件上传" :visible.sync="dialogVisible3" width="30%" :modal="false" class="thisblack-bgc">
                <el-upload class="upload-demo" :before-upload="beforeAvatarUpload" ref="upload" action="#"
                    :http-request="handleUpload" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
                    <div slot="trigger" size="small" type="primary" class="add-file-btn">+</div>
                    <div slot="tip" class="el-upload__tip">只能上传word、excal、pdf文件，且不超过5mb</div>
                </el-upload>
                <span slot="footer" class="dialog-footer">
                    <el-button size="small" round type="primary" @click="dialogVisible3 = false">确
                        定</el-button>
                    <el-button size="small" round @click="cancelUpload(); dialogVisible3 = false">取 消</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import ElementUI from 'element-ui';
import { mapActions } from 'vuex';

export default {
    data: () => ({
        competition: {
            competitionId: '',
            competitionName: '',
            competitionType: 0,
            competitionAudience: 0,
            competitionAudienceName: '',
            competitionUndertaker: '',
            competitionUndertaker2: '',
            competitionExtraPoints: 0,
            competitionAdvisorList: '',
            // 需要boolean转tinyint的 false 0 true 1
            isTeamUp: false,
            competitionJoinedIsOpen: true,
            // 需要parse的
            competitionBeginTime: '',
            competitionEndTime: '',
            // 需要JSON的
            competitionStage: '',
            competitionInformationList: '',
            // 需要取值的
            signUpMinTeamMemberNum: 0,
            signUpMaxTeamMemberNum: 0,
            competitionUndertaker3Id: ''
        },
        teamMemberNum: [2, 8],
        marks: {
            1: '',
            2: '2',
            4: '4',
            6: '6',
            8: '8',
            10: '10',
            12: ''
        },
        options: [{
            value: 0,
            label: '面向全校'
        }, {
            value: 1,
            label: '面向学院'
        }, {
            value: 2,
            label: '面向专业'
        }, {
            value: 3,
            label: '面向个人'
        }],
        options2: [{
            value: '信息与通信工程学院',
            label: '信息与通信工程学院'
        }, {
            value: '轮机学院',
            label: '轮机学院'
        }, {
            value: '艺术学院',
            label: '艺术学院'
        }, {
            value: '外语学院',
            label: '外语学院'
        }, {
            value: '邮轮游艇学院',
            label: '邮轮游艇学院'
        }, {
            value: '海事学院',
            label: '海事学院'
        }],
        pickerOptions: {
            shortcuts: [{
                text: '最近一周',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近一个月',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                    picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近三个月',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                    picker.$emit('pick', [start, end]);
                }
            }]
        },
        competitionTime: '',
        stage: [{
            competitionStageName: '预报名',
            competitionStageIntro: '平台预报名',
            competitionStageBeginTime: '2023-01-01 00:00:00',
            competitionStageEndTime: '2023-01-03 00:00:00',
        }, {
            competitionStageName: '初筛',
            competitionStageIntro: '校内选拔赛',
            competitionStageBeginTime: '2023-02-04 00:00:00',
            competitionStageEndTime: '2023-02-20 00:00:00',
        }, {
            competitionStageName: '报名',
            competitionStageIntro: '确定报名者名单',
            competitionStageBeginTime: '2023-03-07 00:00:00',
            competitionStageEndTime: '2023-03-09 00:00:00',
        }, {
            competitionStageName: '进行中',
            competitionStageIntro: '',
            competitionStageBeginTime: '2023-04-10 00:00:00',
            competitionStageEndTime: '2023-06-10 00:00:00',
        }, {
            competitionStageName: '结束',
            competitionStageIntro: '等待成绩',
            competitionStageBeginTime: '2023-06-11 00:00:00',
            competitionStageEndTime: '',
        }],
        competitionInformationList: [{
            infoTitle: '提示标题：大赛目标',
            infoContent: '提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。'
        }],
        /**
         * 阶段
         */
        stageDelBtn: [false, false, false, false, false, false],
        dialogVisible1: false,
        competitionStageName: '',
        competitionStageIntro: '',
        competitionStageTime: [],
        /**
         * 内容
         */
        contentDelBtn: [false, false, false, false, false, false, false, false],
        dialogVisible2: false,
        infoItemTitle: '',
        infoItemContent: '',
        // 上传附件
        fileList: [],
        dialogVisible3: false,
        resCompetitionId: ''
    }),
    methods: {
        setDelBtn0(index) {
            this.$set(this.stageDelBtn, index, true)
        },
        setDelBtn1(index) {
            this.$set(this.stageDelBtn, index, false)
        },
        setDelBtn2(index) {
            this.$set(this.contentDelBtn, index, true)
        },
        setDelBtn3(index) {
            this.$set(this.contentDelBtn, index, false)
        },
        delStage(index) {
            this.stage.splice(index, 1)
        },
        addJDitem() {
            if (this.competitionStageName == '' || this.competitionStageIntro == '' |
                this.competitionStageTime.length == 0) {
                ElementUI.Message.error('请输入完整信息')
                return
            }
            if (this.competitionStageName.length > 4 || this.competitionStageIntro.length > 10) {
                ElementUI.Message.error('数据过长')
                return
            }
            this.competitionStageTime[0] = this.$moment(this.competitionStageTime[0]).format('YYYY-MM-DD HH:mm:ss')
            this.competitionStageTime[1] = this.$moment(this.competitionStageTime[1]).format('YYYY-MM-DD HH:mm:ss')
            let stageItem = {
                competitionStageName: this.competitionStageName,
                competitionStageIntro: this.competitionStageIntro,
                competitionStageBeginTime: this.competitionStageTime[0],
                competitionStageEndTime: this.competitionStageTime[1],
            }
            this.stage.push(stageItem)
            this.dialogVisible1 = false
        },
        clearJDitem() {
            this.competitionStageName = ''
            this.competitionStageIntro = ''
            this.competitionStageTime = []
            this.dialogVisible1 = false
        },
        delContent(index) {
            this.competitionInformationList.splice(index, 1)
        },
        beforeAddContent() {
            if (this.infoItemContent == '') {
                ElementUI.Message.warning('请先在下方输入内容')
                return
            }
            this.dialogVisible2 = true
        },
        addContent() {
            let infoItem = {
                infoTitle: this.infoItemTitle,
                infoContent: this.infoItemContent
            }
            this.infoItemTitle = ''
            this.infoItemContent = ''
            this.competitionInformationList.push(infoItem)
            this.dialogVisible2 = false;
        },
        /**
         * 添加竞赛
         */
        async doAddCompetition() {
            const com = { ...this.competition }
            // TODO:判断各项非空
            
            // 负责人 当前用户id
            com.competitionUndertaker3Id = parseInt(localStorage.getItem('userId'))
            // 是否需要组队报名
            com.isTeamUp = com.isTeamUp ? 1 : 0
            // 组队 队伍人数限制上下限
            if (com.isTeamUp == 1) {
                com.signUpMinTeamMemberNum = this.teamMemberNum[0]
                com.signUpMaxTeamMemberNum = this.teamMemberNum[1]
            }
            // 预报名 是否开放
            com.competitionJoinedIsOpen = com.competitionJoinedIsOpen ? 1 : 0
            // 时间
            if (this.competitionTime) {
                com.competitionBeginTime = this.$moment(this.competitionTime[0]).format('YYYY-MM-DD HH:mm:ss')
                com.competitionEndTime = this.$moment(this.competitionTime[1]).format('YYYY-MM-DD HH:mm:ss')
            }
            // 步骤
            com.competitionStage = JSON.stringify(this.stage)
            // 内容
            com.competitionInformationList = JSON.stringify(this.competitionInformationList)
            // 提交数据库
            const { code, message, data } = await this.insertCompetition(com)
            if (code === 200) {
                this.resCompetitionId = data
                // 上传附件
                this.submitUpload()
                // 跳转详情页
                ElementUI.Message.success('发布成功！')
                setTimeout(() => {
                    this.$router.push({ path: 'competition_details', query: { competitionId: this.resCompetitionId } })
                }, 1500)
            }
        },
        ...mapActions('competition', ['insertCompetition']),
        /**
         * 上传文件
         * @param {*} file 
         */
        beforeAvatarUpload(file) {
            let pattern = /^.*(\.doc|\.docx|.xls|.xlsx|.pdf)$/
            const isJPG = pattern.test(file.name);
            const isLt5M = file.size / 1024 / 1024 / 1024 < 5;
            if (!isJPG) {
                this.$message.error('上传的文件只能是 Word、Excal和PDF !');
            }
            if (!isLt5M) {
                this.$message.error('文件大小不能超过 5MB!');
            }
            return isJPG && isLt5M;
        },
        ...mapActions('competition', ['uploadCompetitionFile']),
        handleUpload(raw) {
            this.fileList.push(raw.file);
        },
        async submitUpload() {
            // 获取文件到this.fileList
            this.$refs.upload.submit();
            if (this.fileList.length == 0) {
                return
            }
            // 添加文件
            var formData = new window.FormData();
            // 多个文件，需遍历
            this.fileList.forEach(function (file) {
                formData.append('file', file);
            })
            // 添加竞赛id 添加竞赛成功后的返回值中取
            formData.append("competitionId", this.resCompetitionId)
            // 设置类型
            formData.append("type", 0)
            // 上传
            const { code, message, data } = await this.uploadCompetitionFile(formData)
            if (code === 200) {
                ElementUI.Message.success(this.$UPLOADSUCCESS)
            }
            this.fileList = []
            this.$refs.upload.clearFiles();
        },
        cancelUpload() {
            this.fileList = []
            this.$refs.upload.clearFiles();
        },
        handleRemove(file, fileList) {
            this.fileList = fileList
        },
    }
}
</script>

<style lang="less" scoped>
.backstage-view3 {
    position: relative;
    // height: 900px;
    height: auto;

    top: 80px;
    left: 565px;
    width: 950px;
    box-shadow: 0px 0px 10px #4d4d4d2c;

    .backstage-model3 {
        position: relative;
        width: 100%;
        height: auto;


        .model-title {
            padding-left: 35px;
            font-size: 20px;
            height: 55px;
            line-height: 55px;
            font-weight: lighter;
        }

        .line {
            width: 100%;
            border-top: 1px dashed #90939957;
            margin-bottom: 20px;
        }

        .form-item {
            position: relative;
            display: flex;
            font-size: 14px;
            text-align: left;
            height: 65px;
            line-height: 65px;
            width: 100%;
            // font-weight: lighter;

            .item-title {
                position: relative;
                width: 130px;
                text-align: center;
            }

            .item-title-sp1 {
                position: relative;
                left: 35px;
            }

            .item-content {
                position: absolute;
                left: 130px;
                height: 100%;

                .input1 {
                    width: 300px;
                }

                .input2 {
                    width: 260px;
                }

                .input3 {
                    width: 260px;
                }

                .input4 {
                    width: 260px;
                }

                .input5 {
                    width: 400px;
                }

                .select1 {
                    width: 260px;
                }


                .item-title_inner1 {
                    width: 130px;
                    text-align: right;
                    margin-right: 30px;
                }

                .item-title_inner2 {
                    width: 130px;
                    text-align: right;
                    margin-right: 30px;
                }

            }

            .item-content-sp1 {
                position: absolute;
                left: 160px;

                .switch1 {
                    position: relative;
                    left: 10px;
                }
            }

            .item-content-sp2 {
                display: flex;

                .jd-item {
                    position: relative;
                    top: 12.5px;
                    width: 120px;
                    height: 65px;
                    line-height: 65px;
                    font-size: 12px;
                    border: 3px dashed #90939957;
                    border-left: 3px dashed #90939900;
                    border-right: 3px dashed #90939900;
                    color: #909399;
                    cursor: pointer;

                    .jd-1 {
                        position: absolute;
                        top: -20px;
                        left: 10px;
                    }

                    .jd-2 {
                        position: absolute;
                        top: 0px;
                        left: 10px;
                        width: 105px;
                    }

                    .jd-3 {
                        position: absolute;
                        top: 20px;
                        left: 10px;
                    }

                    .jd-del {
                        position: absolute;
                        top: -5px;
                        right: -5px;
                        width: 20px;
                        height: 20px;
                        font-size: 12px;
                        background-color: #ff5e5e;
                        color: #fff;
                        text-align: center;
                        line-height: 20px;
                        border-radius: 50%;
                        cursor: pointer;
                        // opacity: 0;
                    }

                }

                .jd-item:hover {
                    border: 3px dashed #409EFF;
                }



                .jd-add-btn {
                    position: relative;
                    top: 30px;
                    margin-left: 15px;
                    width: 30px;
                    height: 30px;
                    background-color: #409EFF;
                    border-radius: 50%;
                    color: #fff;
                    line-height: 30px;
                    text-align: center;
                    cursor: pointer;
                }

                .jd-add-btn2 {
                    position: relative;
                    top: 30px;
                    margin-left: 10px;
                    width: 50px;
                    height: 30px;
                    background-color: #409EFF;
                    border-radius: 20px;
                    color: #fff;
                    line-height: 30px;
                    text-align: center;
                    cursor: pointer;
                }

                .jd-add-btn3 {
                    position: relative;
                    top: 30px;
                    margin-left: 10px;
                    width: 90px;
                    height: 30px;
                    background-color: #409EFF;
                    border-radius: 20px;
                    color: #fff;
                    line-height: 30px;
                    text-align: center;
                    cursor: pointer;
                }

                .jd-add-btn2:hover {
                    background-color: #55bbff;
                }

                .jd-add-btn:hover {
                    background-color: #55bbff;
                }

                .jd-add-btn3:hover {
                    background-color: #55bbff;
                }
            }

            .item-content-sp3 {
                position: relative;
                top: 5px;
                width: 750px;

                .nr-item {
                    position: relative;
                    font-size: 12px;
                    margin-bottom: 20px;
                    border: 3px dashed #c7c7c7c7;

                    .nr-title {
                        position: relative;
                        left: -10px;
                        top: -10px;
                        font-size: 14px;
                        border: 2px dashed #b1b1b1;
                        background-color: #fff;
                        padding-left: 3px;
                        padding-right: 3px;
                    }

                    .nr-content {
                        position: relative;
                        top: -5px;
                        margin-left: 10px;
                        margin-right: 10px;
                        text-indent: 2em;
                    }

                    .cont-del {
                        position: absolute;
                        top: -10px;
                        right: -10px;
                        width: 22px;
                        height: 22px;
                        font-size: 12px;
                        background-color: #ff5e5e;
                        color: #fff;
                        text-align: center;
                        line-height: 22px;
                        border-radius: 50%;
                        cursor: pointer;
                        // opacity: 0;
                    }
                }

                .nr-item:hover {
                    border: 3px dashed #409EFF;
                }

                .nr-input {
                    position: relative;

                    .nr-add-btn {
                        position: relative;
                        // margin-top: 5px;
                        margin-bottom: 10px;
                        left: 0;
                    }
                }
            }

            .fj-add-btn {
                cursor: pointer;
                color: #3ea8f0;
            }

            .fj-add-btn:hover {
                color: #55bbff;
            }

            .slider {
                position: relative;
                left: 110px;
                width: 600px;
                margin-left: 10px;
                margin-right: 20px;
                text-align: center;
                display: flex;

                .slider-left {
                    width: 120px;
                    margin-right: 20px;
                    font-weight: lighter;
                }

                .slider-middle {
                    position: relative;
                    top: 20px;
                    width: 500px;
                    line-height: normal;
                }

                .slider-right {
                    width: 120px;
                    margin-left: 20px;
                    font-weight: lighter;
                }
            }


            ::v-deep .el-radio {
                margin-right: 10px;
            }

            .joined-is-open {
                position: absolute;
                right: 300px;
                font-size: 12px;
                height: 50px;
                line-height: 50px;
                color: #909399;
            }

            .switch2 {
                position: absolute;
                right: 250px;
                font-size: 12px;
                height: 50px;
                line-height: 50px;
            }

            .do-add-competition-btn {
                position: absolute;
                right: 40px;
                height: 50px;
                line-height: 50px;
                width: 160px;
                text-align: center;
                background-color: #3ac251;
                color: #fff;
                font-size: 16px;
                border-radius: 3px;
                cursor: pointer;
            }

            .do-add-competition-btn:hover {
                background-color: #45d45d;

            }

        }


        .fi1 {
            height: 80px;
            line-height: 80px;
        }

        .fi2 {
            height: 90px;
            line-height: 90px;
        }

        .fi3 {
            margin-top: 30px;
            margin-bottom: 30px;
            height: auto;
            line-height: normal;
        }

        .thisblack-bgc {
            background-color: #00000049;


            .jd-add-title {
                position: relative;
                font-size: 14px;
                margin-bottom: 25px;


                .title-content {
                    position: relative;
                    top: 0;
                    margin-left: 20px;
                    margin-right: 20px;
                }



                .jd-input {
                    width: 250px;
                    font-size: 12px;
                }


            }
        }

    }
}

::v-deep .el-dialog__body {
    padding: 10px 20px;
}

::v-deep .el-dialog__header {
    padding: 20px 20px 20px;
}

.add-file-btn {
    width: 30px;
    height: 30px;
    text-align: center;
    font-size: 20px;
    position: absolute;
    bottom: 20px;
    border: 1px dashed #409EFF;
}

</style>