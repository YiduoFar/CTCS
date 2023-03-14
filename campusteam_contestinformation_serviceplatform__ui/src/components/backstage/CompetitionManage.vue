<template>
    <div class="backstage-view4 ">
        <!-- info -->
        <div class="backstage-model4 " v-if="change">
            <!-- 竞赛info -->
            <el-empty description="无数据" :image-size="120" v-if="competitionList.length == 0"
                style="position:absolute;left: 200px;top: 30px;"></el-empty>
            <div class="info-model-com-name ">
                <span @click="toCompetitionDetails()" class="com-name ts02s">
                    {{ competition.competitionName }}
                </span>
            </div>
            <div class="stage-title" v-if="competitionList.length != 0">当前阶段:
                <span style="color: red;font-weight: 500;">
                    *{{ stageNowName }}
                </span>
            </div>
            <div class="info-model-com-stages ">
                <div class="stage-item" v-for="item in stages">
                    <div class="stage-name">
                        {{ item.competitionStageName }}
                    </div>
                    <div class="stage-intro">
                        {{ item.competitionStageIntro }}
                    </div>
                    <div class="stage-time">
                        {{ item.competitionStageBeginTime.substring(5, 10) }}
                        {{ item.competitionStageEndTime == '' ? '' : '~' }}
                        {{ item.competitionStageEndTime.substring(5, 10) }}
                    </div>
                </div>
            </div>
            <!-- 预报名开关 -->
            <div class="competition-joined-is-open " v-if="competitionList.length != 0">
                <div class="is-open-text ">
                    竞赛预报名开关
                </div>
                <el-switch class="is-open-btn" v-model="competitionJoinedIsOpen" @change="joinedIsOpenChange()"
                    active-color="#13ce66" inactive-color="#909399">
                </el-switch>
            </div>
            <!-- 文件上传开关 -->
            <div class="competition-file-upload-is-open " v-if="competitionList.length != 0">
                <div class="is-open-text">
                    报名文件上传开关
                </div>
                <el-switch class="is-open-btn" v-model="competitionFileUploadIsOpen" @change="fileUploadIsOpenChange()"
                    active-color="#13ce66" inactive-color="#909399">
                </el-switch>
            </div>
            <!-- 详情编辑 -->
            <div class="competition-upload-cover-btn ts03s" @click="dialogVisible4 = true"
                v-if="competitionList.length != 0">上传封面图片</div>
            <div class="competition-edit-btn ts03s" @click="openEdit();" v-if="competitionList.length != 0">更多信息编辑</div>
            <div class="info-model-line"></div>
            <!-- 参与者 -->
            <div class="joined-mem-title ">
                参与{{ competition.isTeamUp == 0 ? '者' : '队伍' }}
                <i class="el-icon-user"></i>
            </div>
            <!-- list -->
            <div class="info-model-joined-mem-list ">
                <!-- 参与者 -->
                <el-empty description="无数据" :image-size="100" v-if="joinedMemlist.length == 0"></el-empty>
                <!-- user -->
                <div v-if="competition.isTeamUp == 0" class="joined-mem-item " v-for="item, index in joinedMemlist"
                    @mouseover="showDelBtn0(index)" @mouseleave="showDelBtn1(index)">
                    <el-tooltip class="item" effect="light" :content="'手机号 ' + item.phone" placement="right">
                        <img :src="item.userHead" alt="" class="joined-mem-avator">
                    </el-tooltip>
                    <div class="joined-mem-name " :title="item.realName + ' (' + item.userName + ') '">
                        {{ item.realName }}
                        ({{ item.userName }})
                    </div>
                    <!-- 剔除参与者 -->
                    <div class="del-user-from-joined-btn" title="剔除" v-if="showUserOrTeamDelBtn[index]"
                        @click="delUserOrTeamFromJoinedMemList(item, 0)">
                        <i class="el-icon-close"></i>
                    </div>
                </div>
                <!-- team -->
                <div v-if="competition.isTeamUp == 1" class="joined-mem-item " v-for="item, index in joinedMemlist"
                    @mouseover="showDelBtn0(index)" @mouseleave="showDelBtn1(index)">
                    <el-tooltip class="item" effect="light" :content="'队长：' + item.teamCaptionRealName" placement="right">
                        <img :src="item.teamLogo" alt="" class="joined-mem-avator">
                    </el-tooltip>
                    <div class="joined-mem-name " :title="item.teamName">
                        {{ item.teamName }}
                    </div>
                    <!-- 剔除参与者 -->
                    <div class="del-user-from-joined-btn" title="剔除" v-if="showUserOrTeamDelBtn[index]"
                        @click="delUserOrTeamFromJoinedMemList(item, 1)">
                        <i class="el-icon-close"></i>
                    </div>
                </div>
            </div>
            <!-- 申请列表 -->
            <div class="apply-list-btn" @click="dialogVisible3 = true" v-if="competitionList.length != 0">申请列表</div>
        </div>
        <!-- select -->
        <div class="backstage-model4 " v-if="change">
            <div class="select-model-title">
                选择竞赛
                <!-- 搜索框 -->
                <el-input class="select-model-search" v-model="input1" size="mini" placeholder="关键字"
                    prefix-icon="el-icon-search"></el-input>
            </div>
            <div class="select-model-list ">
                <!-- none -->
                <el-empty description="您未发布任何竞赛" :image-size="100" v-if="competitionList.length == 0"></el-empty>
                <div class="competition-item ts03s" v-for="item, index in competitionList"
                    @click="setCompetitionFromList(item, index); getJoinedMemList(); getApplyMemList()">
                    {{ item.competitionName }}
                </div>
                <div class="c-i-last" style="border: none;color: #b1b1b1;">您一共发布了 {{ competitionList.length }} 场竞赛</div>
            </div>
            <!-- 箭头 -->
            <div class="arrow ">
                <i class="el-icon-arrow-left"></i>
            </div>
        </div>
        <!-- file -->
        <div class="backstage-model4 " v-if="change">
            <!-- 附件 -->
            <div class="file-model-file0 ">
                <!-- title -->
                <div class="file0-title ">
                    附件管理&nbsp;
                    <i class="el-icon-folder"></i>
                </div>
                <!-- 添加附件 -->
                <div class="fj-add-btn ts02s" @click="dialogVisible = true" v-if="competitionList.length != 0">
                    <i class="el-icon-plus"></i>添加
                </div>
                <!-- list -->
                <div class="file0-list ">
                    <!-- none -->
                    <el-empty description="无文件" :image-size="100" v-if="file0List.length == 0"></el-empty>
                    <!--  -->
                    <div class="file0-item ts01s" v-for="file in file0List">
                        <div class="file0-name ">
                            <span class="name-span ts01s">
                                {{ file.competitionFileName }}
                            </span>
                        </div>
                        <div class="file0-time ">
                            {{ file.competitionFileAnnounceTime.substring(0, 10) }}
                        </div>
                        <div class="download-btn ">
                            <span class="download-btn-content ts02s" @click="downClick(file.competitionFileUrl)">
                                下载
                            </span>
                        </div>
                        <div class="del-btn ">
                            <span class="del-btn-content ts02s" @click="delClick(file, 0)">
                                删除
                            </span>
                        </div>
                    </div>
                    <div class="file-count" v-if="file0List.length != 0">
                        {{ file0List.length }}个附件
                    </div>
                </div>
                <!-- 批量下载 -->
                <div class="download-all-btn ts02s" v-if="file0List.length > 1" @click="downClickAll(0)">
                    批量下载({{ file0List.length }})
                </div>
            </div>
            <div class="file-model-line"></div>
            <!-- 报名文件 -->
            <div class="file-model-file2 ">
                <div class="file2-title ">
                    报名文件管理&nbsp;
                    <i class="el-icon-document-copy"></i>
                </div>
                <!-- list -->
                <div class="file2-list ">
                    <el-empty description="无文件" :image-size="100" v-if="file2List.length == 0"></el-empty>
                    <div class="file2-item ts01s" v-for="file, index in file2List">
                        <div class="file2-name ">
                            <span class="name-span ts01s">
                                {{ file.competitionFileName }}
                            </span>
                        </div>
                        <div class="file2-time ">
                            {{ file.competitionFileAnnounceTime.substring(0, 10) }}
                        </div>
                        <div class="download-btn ">
                            <span class="download-btn-content ts02s" @click="downClick(file.competitionFileUrl)">
                                下载
                            </span>
                        </div>
                        <div class="del-btn ">
                            <span class="del-btn-content ts02s" @click="delClick(file, 2)">
                                删除
                            </span>
                        </div>
                    </div>
                    <div class="file-count" v-if="file2List.length != 0">
                        {{ file2List.length }}份报名资料
                    </div>
                </div>
                <!-- 批量下载 -->
                <div class="download-all-btn ts02s" v-if="file2List.length > 1" @click="downClickAll(2)">
                    批量下载({{ file2List.length }})
                </div>
            </div>
        </div>
        <!-- result -->
        <div class="backstage-model4 " v-if="change">
            <div class="file1-title">
                成绩&nbsp;
                <i class="el-icon-cherry"></i>
            </div>
            <!-- 添加成绩 -->
            <div class="result-file-add-btn ts02s" @click="dialogVisible2 = true" v-if="competitionList.length != 0">
                <i class="el-icon-plus"></i>发布
            </div>
            <!-- list -->
            <div class="file1-list ">
                <div class="file1-item ts01s" v-for="file, index in file1List">
                    <div class="file1-name">
                        <span class="name-span ts01s">
                            {{ file.competitionFileName }}
                        </span>
                    </div>
                    <div class="file1-time ">
                        {{ file.competitionFileAnnounceTime }}
                    </div>
                    <div class="download-btn ">
                        <span class="download-btn-content ts02s" @click="downClick(file.competitionFileUrl)">
                            下载
                        </span>
                    </div>
                    <div class="del-btn ">
                        <span class="del-btn-content ts02s" @click="delClick(file, 1)">
                            删除
                        </span>
                    </div>
                </div>
                <div class="file-count" v-if="file1List.length == 0">
                    （暂无成绩）
                </div>
                <div class="file-count" v-if="file1List.length != 0">
                    {{ file1List.length }}份成绩
                </div>
            </div>
        </div>
        <!-- 上传附件 -->
        <el-dialog title="附件上传" :visible.sync="dialogVisible" width="30%" :modal="false" class="thisblack-bgc">

            <el-upload class="upload-demo" :before-upload="beforeAvatarUpload" ref="upload" action="#"
                :http-request="handleUpload" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
                <div slot="trigger" size="small" type="primary" class="add-file-btn">+</div>
                <div slot="tip" class="el-upload__tip">只能上传word、excal、pdf文件，且不超过5mb</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" round type="primary" @click="doUpLoad(0); dialogVisible = false">确
                    定</el-button>
                <el-button size="small" round @click="cancelUpload(0); dialogVisible = false">取 消</el-button>
            </span>
        </el-dialog>
        <!-- 上传成绩 -->
        <el-dialog title="成绩上传" :visible.sync="dialogVisible2" width="30%" :modal="false" class="thisblack-bgc">
            <el-upload class="upload-demo" :before-upload="beforeAvatarUpload" ref="upload" action="#"
                :http-request="handleUpload" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
                <div slot="trigger" size="small" type="primary" class="add-file-btn">+</div>
                <div slot="tip" class="el-upload__tip">只能上传word、excal、pdf文件，且不超过5mb</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" round type="primary" @click="doUpLoad(1); dialogVisible2 = false">确
                    定</el-button>
                <el-button size="small" round @click="cancelUpload(); dialogVisible2 = false">取 消</el-button>
            </span>
        </el-dialog>
        <!-- 上传封面图片 -->
        <el-dialog title="上传封面图片" :visible.sync="dialogVisible4" width="30%" :modal="false" class="thisblack-bgc">
            <el-upload class="upload-demo" :before-upload="beforeAvatarUploadImg" ref="upload" action="#"
                :http-request="handleUploadImg" :on-remove="handleRemoveImg" :limit="1" :on-exceed="handleExceedImg"
                :file-list="fileList2" :auto-upload="false">
                <div slot="trigger" size="small" type="primary" class="add-file-btn">+</div>
                <div slot="tip" class="el-upload__tip">只能上传 .jpg 文件，且不超过2mb</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" round type="primary" @click="doUpLoadImg(); dialogVisible4 = false">确
                    定</el-button>
                <el-button size="small" round @click="cancelUploadImg(); dialogVisible4 = false">取 消</el-button>
            </span>
        </el-dialog>
        <!-- 申请列表 -->
        <el-dialog title="申请列表" :visible.sync="dialogVisible3" width="30%" :modal="false" class="thisblack-bgc">
            <div class="competition-apply-list ">
                <!-- none -->
                <el-empty :image-size="100" description="暂无申请" v-if="applyMemlist.length == 0"></el-empty>
                <!-- list -->
                <div class="apply-item " v-for="item in applyMemlist" v-if="competition.isTeamUp == 0">
                    <img :src="item.userHead" alt="" class="apply-mem-head">
                    <div class="apply-mem-name ">
                        <span class="name-content">
                            {{ item.userName }}
                        </span>
                    </div>
                    <div class="apply-mem-real-name ">真实姓名：{{ item.realName }}</div>
                    <div class="apply-mem-from " :title="item.college">{{ item.specialty }}{{ item.clazz }}</div>
                    <div class="apply-btn" @click="applyJoin(item, item.id)">同意</div>
                    <div class="rejust-btn" @click="rejectJoin(item, item.id)">婉拒</div>
                </div>
                <!-- list -->
                <div class="apply-item " v-for="item in applyMemlist" v-if="competition.isTeamUp == 1">
                    <img :src="item.teamLogo" alt="" class="apply-mem-head amn-bgc">
                    <div class="apply-mem-name">
                        <span class="name-content">
                            {{ item.teamName }}
                        </span>
                    </div>
                    <div class="apply-mem-real-name ">队长：{{ item.teamCaptionRealName }}</div>
                    <div class="apply-mem-from ">{{ item.teamMemberNum }}/{{ item.teamMemberNumMax }}</div>
                    <div class="apply-btn" @click="applyJoin(item, item.teamId)">同意</div>
                    <div class="rejust-btn" @click="rejectJoin(item, item.teamId)">婉拒</div>
                </div>
            </div>
        </el-dialog>
        <CompetitionEdit v-if="!change" v-bind:competitionId="competition.competitionId"></CompetitionEdit>
    </div>
</template>

<script>
import ElementUI from 'element-ui'
import { saveAs } from 'file-saver'
import JSZip from 'jszip'
import { mapActions } from 'vuex'
import CompetitionEdit from './CompetitionEdit.vue'
export default {
    data: () => ({
        // 选择竞赛
        competitionList: [],
        input1: '',
        // 信息模块
        competition: {
            competitionId: '',
            competitionStage: '',
            competitionJoinedIsOpen: '',
            competitionFileUploadIsOpen: '',
            competitionBeginTime: '',
            competitionEndTime: ''
        },
        competitionJoinedIsOpen: false,
        competitionFileUploadIsOpen: false,
        stages: [],
        stageNow: 0,
        stageNowName: '',
        // 参与者
        joinedMemlist: [],
        showUserOrTeamDelBtn: [],
        // 申请列表
        applyMemlist: [],
        dialogVisible3: false,
        // 文件模块
        file0List: [],
        file2List: [],
        // 成绩模块
        file1List: [],
        // 上传
        fileList: [],
        dialogVisible: false,
        dialogVisible2: false,
        // 编辑
        change: true,
        // 上传封面图片
        dialogVisible4: false,
        fileList2: []
    }),
    mounted() {
    },
    // created初始化数据
    created() {
        this.init()
    },
    // updated写css
    updated() {
        // 不生效
        document.getElementsByClassName('stage-item')[this.stageNow].classList.add('stage-item-active')
    },
    watch: {
        'input1'() {
            this.searchCompetition()
        },
    },
    components: {
        CompetitionEdit
    },
    methods: {
        // 初始化数据
        async init() {
            // 竞赛列表
            await this.searchCompetition()
            // css
            if (this.competitionList.length == 0) {
                setTimeout(() => {
                    ElementUI.Message.warning('您咱未发布任何竞赛')
                }, 1000)
                return
            }
            // 默认列表第一个竞赛高亮
            document.getElementsByClassName('competition-item')[0].classList.add('competition-item-active')
            // 选择最近发布的一个竞赛
            this.competition = { ...this.competitionList[0] }
            // 参与者列表
            await this.getJoinedMemList()
            // 申请者列表
            await this.getApplyMemList()
            // 初始化步骤
            this.stages = [...JSON.parse(this.competition.competitionStage)]
            this.stageNow = this.competition.competitionStageNow // 提示 Error in mounted hook (Promise/async): "ReferenceError: stageNow is not defined
            // console.log(this.stageNow); // 打印1
            this.stageNowName = this.stages[this.stageNow].competitionStageName  // 报错
            // console.log(this.stageNowName);

            // 赋值到变量 成功
            // let sn = this.competition.competitionStageNow
            // this.stageNowName = this.stages[sn].competitionStageName
            // console.log(this.stageNowName); // 打印正常

            // vue2 大坑 数据响应式 必须在data里写上属性名

            // 初始化步骤删除按钮显示数组
            this.showUserOrTeamDelBtn = Array(this.joinedMemlist.length).fill(false)

            // 初始化竞赛两个开关
            this.competitionJoinedIsOpen = this.competition.competitionJoinedIsOpen == 0 ? false : true
            this.competitionFileUploadIsOpen = this.competition.competitionFileUploadIsOpen == 0 ? false : true
            // 初始化时间
            this.competition.competitionBeginTime = this.$moment(this.competition.competitionBeginTime).format('YYYY-MM-DD HH:mm:ss')
            this.competition.competitionEndTime = this.$moment(this.competition.competitionEndTime).format('YYYY-MM-DD HH:mm:ss')
            // 文件列表
            await this.getFile0List() // 附件
            await this.getFile2List() // 报名文件
            await this.getFile1List() // 成绩
        },
        ...mapActions('competition', ['getCompetitionFileByCompetitionIdAndType']),
        // 得到附件
        async getFile0List() {
            // 附件列表 0
            const { code, message, data } = await this.getCompetitionFileByCompetitionIdAndType({
                competitionId: this.competition.competitionId,
                competitionFileType: 0
            })
            if (code === 200) {
                this.file0List = [...data]
                // 格式化时间
                this.file0List.forEach(item => {
                    item.competitionFileAnnounceTime = this.$moment(item.competitionFileAnnounceTime).format('YYYY-MM-DD HH:mm:ss')
                })
            } else {
                this.file0List = []
            }
        },
        // 得到报名文件
        async getFile2List() {
            // 报名文件列表 2
            const { code, message, data } = await this.getCompetitionFileByCompetitionIdAndType({
                competitionId: this.competition.competitionId,
                competitionFileType: 2
            })
            if (code === 200) {
                this.file2List = [...data]
                // 格式化时间
                this.file2List.forEach(item => {
                    item.competitionFileAnnounceTime = this.$moment(item.competitionFileAnnounceTime).format('YYYY-MM-DD HH:mm:ss')
                })
            } else {
                this.file2List = []
            }
        },
        // 得到成绩
        async getFile1List() {
            // 成绩列表 1
            const { code, message, data } = await this.getCompetitionFileByCompetitionIdAndType({
                competitionId: this.competition.competitionId,
                competitionFileType: 1
            })
            if (code === 200) {
                this.file1List = [...data]
                // 格式化时间
                this.file1List.forEach(item => {
                    item.competitionFileAnnounceTime = this.$moment(item.competitionFileAnnounceTime).format('YYYY-MM-DD HH:mm:ss')
                })
            } else {
                this.file1List = []
            }
        },
        /**
         * 下载
         */
        downClick(href) {
            if (String(href).includes('.pdf')) {
                window.open(href, '_blank');
            } else {
                window.open(href, '_self');
            }
        },
        /**
         * 批量下载
         * @param {*} href 
         */
        download(href) {
            return new Promise((resolve, reject) => {
                this.$axios({
                    method: 'get',
                    url: href,
                    responseType: 'arraybuffer'
                }).then(data => {
                    resolve(data.data)
                }).catch(error => {
                    reject(error.toString())
                })
            })
        },
        downClickAll(type) {
            const zip = new JSZip()
            const promises = []
            if (type == 0) {
                //companyAttachmentsList是我自己的附件url地址数组 你可以改成你的数组
                this.file0List.forEach(item => {
                    //item.downloadLink.split('?')[0] 处理companyAttachmentsList里的url地址 去除？号和后面的字符串
                    const promise = this.download(item.competitionFileUrl).then(data => { // 下载文件, 并存成ArrayBuffer对象
                        //item.fileName companyAttachmentsList里的文件名
                        zip.file(item.competitionFileName, data, { binary: true }) // 逐个添加文件
                        // cache[item.fileName] = data //可要可不要 用来打印查检查添加了那些文件
                    })
                    promises.push(promise) //加到promises队列里
                })
                Promise.all(promises).then(() => { //异步队列全部完成时 执行下面代码
                    zip.generateAsync({ type: "blob" }).then(content => { // 生成二进制流
                        saveAs(content, "附件打包.zip") // 利用file-saver保存文件
                    })
                })
            }
            if (type == 2) {
                //companyAttachmentsList是我自己的附件url地址数组 你可以改成你的数组
                this.file2List.forEach(item => {
                    //item.downloadLink.split('?')[0] 处理companyAttachmentsList里的url地址 去除？号和后面的字符串
                    const promise = this.download(item.competitionFileUrl).then(data => { // 下载文件, 并存成ArrayBuffer对象
                        //item.fileName companyAttachmentsList里的文件名
                        zip.file(item.competitionFileName, data, { binary: true }) // 逐个添加文件
                        // cache[item.fileName] = data //可要可不要 用来打印查检查添加了那些文件
                    })
                    promises.push(promise) //加到promises队列里
                })
                Promise.all(promises).then(() => { //异步队列全部完成时 执行下面代码
                    zip.generateAsync({ type: "blob" }).then(content => { // 生成二进制流
                        saveAs(content, "报名文件打包.zip") // 利用file-saver保存文件
                    })
                })
            }
        },
        /**
         * 删除文件
         * @param {*} file 
         */
        ...mapActions('competition', ['deleteCompetitionFileById']),
        async delClick(file, type) {
            let competitionFileName = file.competitionFileName
            let _this = this
            this.$confirm(competitionFileName, '确定删除该文件？', {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }).then(async () => {
                const { code, message, data } = await _this.deleteCompetitionFileById(file)
                if (code === 200) {
                    ElementUI.Message.success('成功删除文件')
                    if (type == 0) {
                        this.file0List = this.file0List.filter(item => item != file)
                    } else if (type == 1) {
                        this.file1List = this.file1List.filter(item => item != file)
                    } else if (type == 2) {
                        this.file2List = this.file2List.filter(item => item != file)
                    }
                }
            })
        },
        /**
         * 取competitionList数据
         */
        ...mapActions('competition', ['selectCompetitionByUndertaker3Id']),
        async searchCompetition() {
            let userId = localStorage.getItem('userId')
            const queryVo = {
                userId: userId,
                keyword: this.input1
            }
            const { code, message, data } = await this.selectCompetitionByUndertaker3Id(queryVo)
            if (code === 200) {
                this.competitionList = [...data]
                // 时间处理
                this.competitionList.forEach(item => {
                    item.competitionPublishDate = this.$moment(item.competitionPublishDate).format('YYYY-MM-DD HH:mm:ss');
                })
            }
        },
        ...mapActions('user_competition', ['selectUserListByCompetitionIdAndStatus']),
        ...mapActions('team_competition', ['selectTeamListByCompetitionIdAndStatus']),
        /**
         * 取参与者列表
         */
        async getJoinedMemList() {
            const queryVo = {
                competitionId: this.competition.competitionId,
                status: 1 // 1已确认参加 0已申请未确认
            }
            if (this.competition.isTeamUp == 0) {
                const { code, messge, data } = await this.selectUserListByCompetitionIdAndStatus(queryVo)
                if (code === 200) {
                    this.joinedMemlist = [...data]
                } else {
                    this.joinedMemlist = []
                }
            }
            if (this.competition.isTeamUp == 1) {
                const { code, messge, data } = await this.selectTeamListByCompetitionIdAndStatus(queryVo)
                if (code === 200) {
                    this.joinedMemlist = [...data]
                } else {
                    this.joinedMemlist = []
                }
            }
            // 初始化删除按钮显示数组
            this.showUserOrTeamDelBtn = Array(this.joinedMemlist.length).fill(false)
        },
        /**
         * 取申请者列表
         */
        async getApplyMemList() {
            const queryVo = {
                competitionId: this.competition.competitionId,
                status: 0 // 1已确认参加 0已申请未确认
            }
            if (this.competition.isTeamUp == 0) {
                const { code, messge, data } = await this.selectUserListByCompetitionIdAndStatus(queryVo)
                if (code === 200) {
                    this.applyMemlist = [...data]
                } else {
                    this.applyMemlist = []
                }
            }
            if (this.competition.isTeamUp == 1) {
                const { code, messge, data } = await this.selectTeamListByCompetitionIdAndStatus(queryVo)
                if (code === 200) {
                    this.applyMemlist = [...data]
                } else {
                    this.applyMemlist = []
                }
            }
        },
        /**
         * 剔除、按钮显示
         * @param {*} index 
         */
        showDelBtn0(index) {
            this.$set(this.showUserOrTeamDelBtn, index, true)
        },
        showDelBtn1(index) {
            this.$set(this.showUserOrTeamDelBtn, index, false)
        },
        ...mapActions('user_competition', ['deleteUserCompetition']),
        ...mapActions('team_competition', ['deleteTeamCompetition']),
        async delUserOrTeamFromJoinedMemList(item, type) {
            let _this = this
            // 剔除用户
            if (type == 0) {
                this.$confirm(item.userName, '确定剔除该参赛者/队伍？', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(async () => {
                    const userCompetitionDeleteVo = {
                        userId: item.id,
                        competitionId: this.competition.competitionId,
                        status: 1,
                        type: 3
                    }
                    const { code, message, data } = await _this.deleteUserCompetition(userCompetitionDeleteVo)
                    if (code === 200) {
                        ElementUI.Message.success('剔除成功')
                        _this.joinedMemlist = _this.joinedMemlist.filter(i => i != item)
                    }
                })
            }
            // 剔除队伍
            if (type == 1) {
                this.$confirm(item.teamName, '确定剔除该参赛队伍？', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(async () => {
                    const teamCompetitionDeleteVo = {
                        teamId: item.teamId,
                        competitionId: this.competition.competitionId,
                        status: 1,
                        type: 3
                    }
                    const { code, message, data } = await _this.deleteTeamCompetition(teamCompetitionDeleteVo)
                    if (code === 200) {
                        ElementUI.Message.success('剔除成功')
                        _this.joinedMemlist = _this.joinedMemlist.filter(i => i != item)
                    }
                })
            }
        },
        // 选择当前竞赛
        async setCompetitionFromList(item, index) {
            this.competition = { ...item }
            // 初始化竞赛两个开关
            this.competitionJoinedIsOpen = this.competition.competitionJoinedIsOpen == 0 ? false : true
            this.competitionFileUploadIsOpen = this.competition.competitionFileUploadIsOpen == 0 ? false : true
            // 初始化时间
            this.competition.competitionBeginTime = this.$moment(this.competition.competitionBeginTime).format('YYYY-MM-DD HH:mm:ss')
            this.competition.competitionEndTime = this.$moment(this.competition.competitionEndTime).format('YYYY-MM-DD HH:mm:ss')
            // 步骤
            this.stages = [...JSON.parse(this.competition.competitionStage)]
            let stageNow = this.competition.competitionStageNow
            this.stageNowName = this.stages[stageNow].competitionStageName
            // 文件列表
            await this.getFile0List() // 附件
            await this.getFile2List() // 报名文件
            await this.getFile1List() // 成绩
            // 步骤css
            let arr0 = document.getElementsByClassName('stage-item')
            for (let i = 0; i < arr0.length; i++) {
                if (i == stageNow) {
                    arr0[i].classList.add('stage-item-active')
                    arr0[i].classList.remove('stage-item-nomal')
                } else {
                    arr0[i].classList.add('stage-item-nomal')
                    arr0[i].classList.remove('stage-item-active')
                }
            }
            // 选择竞赛css
            let arr = document.getElementsByClassName('competition-item')
            for (let i = 0; i < arr.length; i++) {
                if (i == index) {
                    arr[i].classList.add('competition-item-active')
                    arr[i].classList.remove('competition-item-nomal')
                } else {
                    arr[i].classList.add('competition-item-nomal')
                    arr[i].classList.remove('competition-item-active')
                }
            }
        },
        /**
         * 两个开关 编辑
         */
        ...mapActions('competition', ['updateCompetitionById']),
        // 预报名开关
        joinedIsOpenChange() {
            let text = this.competitionJoinedIsOpen ? '打开' : '关闭'
            this.$confirm('确定' + text + '预报名?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async() => {
                this.competition.competitionJoinedIsOpen = this.competitionJoinedIsOpen ? 1 : 0
                const { code, message, data } = await this.updateCompetitionById(this.competition)
                if (code === 200) {
                    // this.competitionJoinedIsOpen = !this.competitionJoinedIsOpen
                    ElementUI.Message.success('成功' + text + '预报名')
                }
            }).catch(() => {
                this.competitionJoinedIsOpen = !this.competitionJoinedIsOpen
            });
        },
        // 文件上传开关
        fileUploadIsOpenChange() {
            let text = this.competitionFileUploadIsOpen ? '打开' : '关闭'
            this.$confirm('确定' + text + '文件上传?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                this.competition.competitionFileUploadIsOpen = this.competitionFileUploadIsOpen ? 1 : 0
                const { code, message, data } = await this.updateCompetitionById(this.competition)
                if (code === 200) {
                    // this.competitionFileUploadIsOpen = !this.competitionFileUploadIsOpen
                    ElementUI.Message.success('成功' + text + '文件上传')
                }
            }).catch(() => {
                this.competitionFileUploadIsOpen = !this.competitionFileUploadIsOpen
            });
        },
        /**
         * 上传文件
         * @param {*} file 
         */
        // 上传前检查
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
        // 上传
        async doUpLoad(type) {
            // 获取文件到this.fileList
            this.$refs.upload.submit();
            if (this.fileList.length == 0) {
                return
            }
            // 添加文件
            var formData = new window.FormData();
            this.fileList.forEach(function (file) {
                formData.append('file', file); // 因为要上传多个文件，所以需要遍历一下才行
                //不要直接使用我们的文件数组进行上传，你会发现传给后台的是两个Object
            })
            // 添加竞赛id
            formData.append("competitionId", this.competition.competitionId)
            // 设置类型
            formData.append("type", type)
            // 上传
            const { code, message, data } = await this.uploadCompetitionFile(formData)
            if (code === 200) {
                ElementUI.Message.success(this.$UPLOADSUCCESS)
                // 添加到数组
                let res = [...data]
                if (type == 0) {
                    res.forEach(item => {
                        this.file0List.push(item)
                    })
                }
                if (type == 1) {
                    res.forEach(item => {
                        this.file1List.push(item)
                    })
                }
            }
            this.fileList = []
            this.$refs.upload.clearFiles();
        },
        cancelUpload() {
            this.fileList = []
            this.$refs.upload.clearFiles();
        },
        handFileSuccess(file) {
            this.fileList = []
            this.$refs.upload.clearFiles();
        },
        handleRemove(file, fileList) {
            this.fileList = fileList
        },
        /**
         * 上传封面图片
         * @param {*} file 
         */
        // 上传前检查
        beforeAvatarUploadImg(file) {
            let pattern = /^.*(\.jpg)$/
            const isJPG = pattern.test(file.name);
            const isLt2M = file.size / 1024 / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error('封面图片只限 jpg 格式');
            }
            if (!isLt2M) {
                this.$message.error('文件大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        ...mapActions('competition', ['uploadCompetitionCover']),
        handleUploadImg(raw) {
            this.fileList2.push(raw.file);
        },
        // 上传
        async doUpLoadImg() {
            // 获取文件到this.fileList
            this.$refs.upload.submit();
            if (this.fileList2.length == 0) {
                return
            }
            // 添加文件
            var formData = new window.FormData();
            this.fileList2.forEach(function (file) {
                formData.append('file', file);
            })
            // 添加竞赛id
            formData.append("competitionId", this.competition.competitionId)
            // 上传
            const { code, message, data } = await this.uploadCompetitionCover(formData)
            if (code === 200) {
                ElementUI.Message.success(this.$UPLOADSUCCESS)
            }
            this.fileList2 = []
            this.$refs.upload.clearFiles();
        },
        cancelUploadImg() {
            this.fileList2 = []
            this.$refs.upload.clearFiles();
        },
        handleRemoveImg(file, fileList) {
            this.fileList2 = fileList
        },
        handleExceedImg(files, fileList) {
            this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        /**
         * 同意申请
         */
        ...mapActions('user_competition', ['updateUserCompetitionStatusById']),
        ...mapActions('team_competition', ['updateTeamCompetitionStatusById']),
        // 同意
        async applyJoin(item, id) {
            // 同意个人申请
            if (this.competition.isTeamUp == 0) {
                const userCompetition = {
                    userId: id,
                    competitionId: this.competition.competitionId,
                    status: 1
                }
                const { code, message, data } = await this.updateUserCompetitionStatusById(userCompetition)
                if (code === 200) {
                    this.applyMemlist = this.applyMemlist.filter(e => e != item)
                    this.joinedMemlist.push(item)
                }
            } else {
                // 同意组队申请
                const teamCompetition = {
                    teamId: id,
                    competitionId: this.competition.competitionId,
                    status: 1
                }
                const { code, message, data } = await this.updateTeamCompetitionStatusById(teamCompetition)
                if (code === 200) {
                    this.applyMemlist = this.applyMemlist.filter(e => e != item)
                    this.joinedMemlist.push(item)
                }
            }
        },
        /**
         * 拒绝申请
         */
        ...mapActions('user_competition', ['deleteUserCompetition']),
        ...mapActions('team_competition', ['deleteTeamCompetition']),
        // 拒绝
        async rejectJoin(item, id) {
            // 拒绝个人申请
            if (this.competition.isTeamUp == 0) {
                const userCompetitionDeleteVo = {
                    userId: id,
                    competitionId: this.competition.competitionId,
                    status: 0,
                    type: 2 // type 操作类型 剔除3 婉拒2 退出1
                }
                const { code, message, data } = await this.deleteUserCompetition(userCompetitionDeleteVo)
                if (code === 200) {
                    this.applyMemlist = this.applyMemlist.filter(e => e != item)
                }
            } else {
                // 拒绝组队申请
                const teamCompetitionDeleteVo = {
                    teamId: id,
                    competitionId: this.competition.competitionId,
                    status: 0,
                    type: 2 // type 操作类型 剔除3 婉拒2 退出1
                }
                const { code, message, data } = await this.deleteTeamCompetition(teamCompetitionDeleteVo)
                if (code === 200) {
                    this.applyMemlist = this.applyMemlist.filter(e => e != item)
                }
            }
        },
        toCompetitionDetails() {
            this.$router.push({ path: '/competition_details', query: { competitionId: this.competition.competitionId } })
        },
        openEdit() {
            this.$parent.editStatus(false)
            this.change = false
        },
        closeEdit() {
            this.$parent.editStatus(true)
            this.change = true
            location.reload()
        },
        closeEdit2() {
            this.$parent.editStatus(true)
            this.change = true
            location.reload()
        }
    }
}
</script>

<style lang="less" scoped>
.backstage-view4 {
    position: absolute;
    top: 80px;
    left: 565px;
    width: 1020px;

    .backstage-model4 {
        position: relative;
        width: 100%;
    }

    .backstage-model4:nth-child(1) {
        height: 300px;
        width: 720px;
        margin-bottom: 12px;
        box-shadow: 0px 0px 10px #8f8f8f5b;

        .info-model-com-name {
            font-size: 18px;
            font-weight: lighter;
            width: 500px;
            padding-left: 20px;
            padding-top: 10px;
            padding-bottom: 10px;
            // color: #008dce;

            // 省略效果
            overflow: hidden;
            display: -webkit-box;
            /*第几行裁剪*/
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;

            .com-name {
                cursor: pointer;
            }

            .com-name:hover {
                color: #238bbb;
            }
        }

        .stage-title {
            position: relative;
            top: 10px;
            font-size: 13px;
            left: 20px;
            font-weight: lighter;
        }

        .info-model-com-stages {
            position: relative;
            left: 20px;
            top: 20px;
            width: 465px;
            height: 75px;
            display: flex;

            .stage-item {
                position: relative;
                width: 93px;
                height: 100%;
                font-size: 12px;
                border: 1px dashed #68686886;
                color: #909399;
                // font-weight: lighter;



                .stage-name {
                    position: absolute;
                    top: 5px;
                    left: 5px;
                }

                .stage-intro {
                    position: absolute;
                    top: 23px;
                    left: 5px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 2;
                    -webkit-box-orient: vertical;
                }

                .stage-time {
                    position: absolute;
                    bottom: 5px;
                    left: 5px;
                }
            }

            .stage-item-active {
                border: 1px solid #28a2db;
                color: #353535;
                background-color: #d5f2ff2d;
            }

            .stage-item-nomal {
                border: 1px dashed #68686886;
                color: #909399;
                background-color: none;
            }
        }

        .competition-joined-is-open {
            position: absolute;
            width: 200px;
            left: 35px;
            height: 30px;
            line-height: 30px;
            font-size: 13px;
            top: 190px;
            display: flex;
            // font-weight: lighter;
            color: #686868;

            .is-open-text {
                width: 110px;
                text-align: right;
            }

            .is-open-btn {
                margin-left: 15px;
                top: 4.5px;
            }
        }

        .competition-file-upload-is-open {
            position: absolute;
            width: 200px;
            left: 35px;
            height: 30px;
            line-height: 30px;
            font-size: 13px;
            top: 230px;
            display: flex;
            // font-weight: lighter;
            color: #686868;

            .is-open-text {
                width: 110px;
                text-align: right;
            }

            .is-open-btn {
                margin-left: 15px;
                top: 4.5px;
            }
        }

        .competition-edit-btn {
            position: absolute;
            left: 355px;
            top: 230px;
            width: 120px;
            height: 40px;
            background-color: #28a2db;
            color: #fff;
            text-align: center;
            line-height: 40px;
            font-size: 14px;
            cursor: pointer;
            border-radius: 2px;
        }

        .competition-upload-cover-btn {
            position: absolute;
            left: 400px;
            top: 193px;
            font-size: 12px;
            color: #158fc7;
            cursor: pointer;
        }

        .competition-edit-btn:hover {
            background-color: #33aee7;
        }

        .competition-upload-cover-btn:hover {
            color: #46a3ce;
        }


        .joined-mem-title {
            position: absolute;
            right: 5px;
            top: 10px;
            width: 185px;
            font-size: 17px;
            font-weight: lighter;
            color: #909399;
        }

        .info-model-joined-mem-list {
            position: absolute;
            right: 0px;
            top: 50px;
            height: 220px;
            width: 195px;
            overflow-y: scroll;
            overflow-x: hidden;

            .joined-mem-item {
                position: relative;
                height: 30px;
                line-height: 30px;
                width: 100%;
                margin-bottom: 8px;

                .joined-mem-avator {
                    width: 30px;
                }

                .joined-mem-name {
                    position: absolute;
                    left: 40px;
                    top: 0;
                    font-size: 13px;
                    width: 140px;
                    cursor: pointer;


                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .del-user-from-joined-btn {
                    position: absolute;
                    top: 7px;
                    left: 170px;
                    width: 15px;
                    height: 15px;
                    line-height: 15px;
                    font-size: 12px;
                    color: #fff;
                    background-color: #bd3333;
                    text-align: center;
                    border-radius: 3px;
                    cursor: pointer;
                }
            }
        }

        .apply-list-btn {
            position: absolute;
            bottom: 7px;
            right: 10px;
            font-size: 12px;
            color: #158fc7;
            cursor: pointer;
        }

        .apply-list-btn:hover {
            color: #28a2db;
        }

        .info-model-line {
            position: absolute;
            top: 40px;
            right: 210px;
            height: 245px;
            border-left: 1px dashed #68686879;
        }
    }

    .backstage-model4:nth-child(2) {
        position: absolute;
        right: 0;
        top: 0;
        height: 300px;
        width: 290px;
        box-shadow: 0px 0px 10px #8d8d8d2c;

        // border: 1px dashed #00000086;
        .select-model-title {
            font-size: 17px;
            padding: 10px 15px 13px 15px;
            font-weight: lighter;
            color: #686868;
        }

        .select-model-search {
            position: absolute;
            right: 10px;
            top: 7.5px;
            width: 90px;
        }

        .select-model-line {
            border-top: 1px dashed #a1a1a13a;
        }

        .select-model-list {
            position: relative;
            left: 4%;
            width: 93%;
            top: 5px;
            height: 235px;
            overflow-y: scroll;
            overflow-x: hidden;

            .competition-item {
                position: relative;
                width: 94%;
                height: 30px;
                line-height: 30px;
                margin-bottom: 8px;
                font-size: 12px;
                padding-left: 10px;
                border: 1px dashed #adadad98;
                cursor: pointer;

                // 省略效果
                overflow: hidden;
                display: -webkit-box;
                /*第几行裁剪*/
                -webkit-line-clamp: 1;
                -webkit-box-orient: vertical;
            }

            .competition-item:hover {
                border: 1px dashed #28a2db;
                background-color: #7bd5ff2d;
            }

            .competition-item-nomal {
                border: 1px dashed #adadad98;
                background-color: none;
            }

            .competition-item-active {
                border: 1px dashed #28a2db;
                background-color: #7bd5ff2d;
            }

            .c-i-last {
                position: relative;
                width: 94%;
                height: 30px;
                line-height: 30px;
                margin-bottom: 8px;
                font-size: 12px;
                padding-left: 10px;
                border: 1px dashed #adadad98;
            }

            .c-i-last:hover {
                border: none;
                background-color: none;
            }
        }

        .arrow {
            position: absolute;
            top: 115px;
            left: -15px;
            width: 20px;
            height: 50px;
            line-height: 50px;
            text-align: center;
            background-color: #fff;
        }
    }

    .backstage-model4:nth-child(3) {
        height: 400px;
        box-shadow: 0px 0px 10px #8d8d8d2c;
        margin-bottom: 12px;
        display: flex;

        // 附件
        .file-model-file0 {
            position: relative;
            width: 49.9%;
            left: 0;
            height: 100%;

            .file0-title {
                padding-left: 20px;
                font-size: 20px;
                height: 55px;
                line-height: 55px;
                font-weight: lighter;
            }

            .fj-add-btn {
                position: absolute;
                top: 17px;
                left: 130px;
                margin-left: 20px;
                font-size: 12px;
                width: 50px;
                height: 22px;
                line-height: 22px;
                text-align: center;
                border-radius: 10px;
                background-color: #67a7c5;
                cursor: pointer;
                color: #fff;
            }

            .fj-add-btn:hover {
                background-color: #2e94c4;

            }



            .file0-list {
                position: relative;
                left: 2%;
                width: 96%;
                height: 305px;
                overflow-y: scroll;
                overflow-x: hidden;

                .file0-item {
                    position: relative;
                    width: 96%;
                    left: 2%;
                    height: 40px;
                    line-height: 40px;
                    margin-bottom: 10px;
                    border: 1px dashed #68686800;
                    font-size: 12px;

                    .file0-name {
                        position: absolute;
                        left: 10px;
                        width: 245px;
                        // 省略效果
                        overflow: hidden;
                        display: -webkit-box;
                        /*第几行裁剪*/
                        -webkit-line-clamp: 1;
                        -webkit-box-orient: vertical;

                        .name-span:hover {
                            cursor: pointer;
                            color: #3194c2;
                        }
                    }

                    .file0-time {
                        position: absolute;
                        left: 270px;
                        width: 80px;
                        text-align: center;
                        color: #909399;
                    }

                    .download-btn {
                        position: absolute;
                        left: 380px;
                        text-align: center;
                        width: 40px;
                    }

                    .download-btn-content {
                        cursor: pointer;
                        color: #0b72a1;
                    }

                    .download-btn-content:hover {
                        color: #39a7da;
                    }

                    .del-btn {
                        position: absolute;
                        left: 420px;
                        text-align: center;
                        width: 40px;
                    }

                    .del-btn-content {
                        cursor: pointer;
                        color: #0b72a1;
                    }

                    .del-btn-content:hover {
                        color: #cc3429;
                    }
                }

                .file0-item:hover {
                    border: 1px dashed #68686879;
                }

                .file-count {
                    position: relative;
                    font-size: 12px;
                    color: #909399;
                    left: 22px;
                }
            }

            .download-all-btn {
                position: absolute;
                bottom: 15px;
                left: 35px;
                font-size: 12px;
                cursor: pointer;
                color: #0b72a1;
            }

            .download-all-btn:hover {
                color: #3ca4d4;
            }

            .upload-fj-btn {
                position: absolute;
                bottom: 15px;
                left: 120px;
                font-size: 12px;
                cursor: pointer;
                color: #0b72a1;
            }

            .upload-fj-btn:hover {
                color: #3ca4d4;
            }
        }

        .file-model-line {
            position: relative;
            top: 40px;
            // right: 210px;
            height: 320px;
            border-left: 1px dashed #68686879;
        }

        // 报名文件
        .file-model-file2 {
            position: relative;
            width: 49.9%;
            right: 0;
            height: 100%;

            .file2-title {
                padding-left: 30px;
                font-size: 20px;
                height: 55px;
                line-height: 55px;
                font-weight: lighter;
            }

            .file2-list {
                position: relative;
                left: 2%;
                width: 96%;
                height: 305px;
                overflow-y: scroll;
                overflow-x: hidden;

                .file2-item {
                    position: relative;
                    width: 96%;
                    left: 2%;
                    height: 40px;
                    line-height: 40px;
                    margin-bottom: 10px;
                    border: 1px dashed #68686800;
                    font-size: 12px;

                    .file2-name {
                        position: absolute;
                        left: 10px;
                        width: 245px;
                        // 省略效果
                        overflow: hidden;
                        display: -webkit-box;
                        /*第几行裁剪*/
                        -webkit-line-clamp: 1;
                        -webkit-box-orient: vertical;

                        .name-span:hover {
                            cursor: pointer;
                            color: #3194c2;
                        }
                    }

                    .file2-time {
                        position: absolute;
                        left: 270px;
                        width: 80px;
                        text-align: center;
                        color: #909399;
                    }

                    .download-btn {
                        position: absolute;
                        left: 380px;
                        text-align: center;
                        width: 40px;
                    }

                    .download-btn-content {
                        cursor: pointer;
                        color: #0b72a1;
                    }

                    .download-btn-content:hover {
                        color: #39a7da;
                    }

                    .del-btn {
                        position: absolute;
                        left: 420px;
                        text-align: center;
                        width: 40px;
                    }

                    .del-btn-content {
                        cursor: pointer;
                        color: #0b72a1;
                    }

                    .del-btn-content:hover {
                        color: #cc3429;
                    }
                }

                .file2-item:hover {
                    border: 1px dashed #68686879;
                }

                .file-count {
                    position: relative;
                    font-size: 12px;
                    color: #909399;
                    left: 22px;
                }
            }

            .download-all-btn {
                position: absolute;
                bottom: 15px;
                left: 35px;
                font-size: 12px;
                cursor: pointer;
                color: #0b72a1;
            }

            .download-all-btn:hover {
                color: #3ca4d4;
            }
        }
    }

    .backstage-model4:nth-child(4) {
        min-height: 60px;
        box-shadow: 0px 0px 10px #8d8d8d2c;
        position: relative;
        margin-bottom: 30px;
        padding-bottom: 20px;

        .file1-title {
            padding-left: 25px;
            font-size: 20px;
            height: 55px;
            line-height: 55px;
            font-weight: lighter;
        }

        .result-file-add-btn {
            position: absolute;
            top: 17px;
            left: 95px;
            margin-left: 20px;
            font-size: 12px;
            width: 50px;
            height: 22px;
            line-height: 22px;
            text-align: center;
            border-radius: 10px;
            background-color: #67a7c5;
            color: #fff;
            cursor: pointer;
        }

        .result-file-add-btn:hover {
            background-color: #2e94c4;
        }


        .file1-list {
            position: relative;
            left: 2%;
            width: 96%;
            // height: 305px;
            overflow-y: scroll;
            overflow-x: hidden;

            .file1-item {
                position: relative;
                width: 98%;
                left: 1%;
                height: 40px;
                line-height: 40px;
                margin-bottom: 10px;
                border: 1px dashed #68686879;
                font-size: 12px;

                .file1-name {
                    position: absolute;
                    left: 15px;
                    width: 500px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;

                    .name-span:hover {
                        cursor: pointer;
                        color: #0da045;
                    }
                }

                .file1-time {
                    position: absolute;
                    right: 270px;
                    width: 130px;
                    text-align: center;
                    color: #909399;
                }

                .download-btn {
                    position: absolute;
                    right: 70px;
                    text-align: center;
                    width: 40px;
                }

                .download-btn-content {
                    cursor: pointer;
                    color: #0b72a1;
                }

                .download-btn-content:hover {
                    color: #39a7da;
                }

                .del-btn {
                    position: absolute;
                    right: 20px;
                    text-align: center;
                    width: 40px;
                }

                .del-btn-content {
                    cursor: pointer;
                    color: #0b72a1;
                }

                .del-btn-content:hover {
                    color: #cc3429;
                }
            }


            .file-count {
                position: relative;
                font-size: 12px;
                color: #909399;
                left: 15px;
            }
        }
    }

    .thisblack-bgc {
        background-color: #00000049;
    }

    ::v-deep .el-dialog__body {
        padding: 10px 20px;
    }

    ::v-deep .el-dialog__header {
        padding: 20px 20px 0px;
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

    .competition-apply-list {
        position: relative;
        max-height: 500px;
        min-height: 100px;
        padding-bottom: 30px;

        .apply-item {
            position: relative;
            height: 40px;
            line-height: 40px;
            border: 3px dashed #90939954;
            margin-bottom: 10px;

            .apply-mem-head {
                width: 40px;
            }

            .apply-mem-name {
                position: absolute;
                left: 60px;
                top: 0;
                width: 130px;

                .name-content {
                    cursor: pointer;
                }

                .name-content:hover {
                    color: #0b72a1;
                }
            }

            .amn-bgc {
                background-color: #96969646;
            }

            .apply-mem-real-name {
                position: absolute;
                left: 200px;
                top: 0;
                width: 140px;
                color: #909399;
            }

            .apply-mem-from {
                position: absolute;
                left: 350px;
                top: 0;
                width: 140px;
                color: #909399;
            }

            .apply-btn {
                position: absolute;
                right: 60px;
                top: 0;
                color: #0b72a1;
                cursor: pointer;
            }

            .apply-btn:hover {
                color: #2d9dd1;
            }

            .rejust-btn {
                position: absolute;
                right: 20px;
                top: 0;
                color: #0b72a1;
                cursor: pointer;
            }

            .rejust-btn:hover {
                color: #a1150b;
            }
        }

        .apply-item:hover {
            border: 3px dashed #2b5fc76b;
        }
    }
}

/* 定义滚动条样式 */
::-webkit-scrollbar {
    width: 4px;
    height: 6px;
    background-color: rgba(240, 240, 240, 0);
}

/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track {
    box-shadow: inset 0 0 0px rgba(240, 240, 240, 0);
    border-radius: 10px;
    background-color: rgba(240, 240, 240, 0);
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
    border-radius: 10px;
    box-shadow: inset 0 0 0px rgba(240, 240, 240, 0.185);
    background-color: rgba(158, 158, 158, 0.089);
}
</style>