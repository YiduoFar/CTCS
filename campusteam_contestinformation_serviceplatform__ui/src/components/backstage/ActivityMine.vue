<template>
    <div class="backstage-view2 ">
        <!-- 进行中 -->
        <div class="backstage-model2" v-if="change">
            <div class="model-title">
                我发布的活动&nbsp;
                <i class="el-icon-wind-power"></i>
                <!-- 搜索我的活动 -->
                <el-input class="model-search" size="mini" placeholder="搜索我的活动" prefix-icon="el-icon-search"
                    v-model="queryVo1.keyword" @keyup.enter.native="search1"></el-input>
            </div>
            <!-- 分割线 -->
            <div class="line"></div>
            <!-- 进行中 活动列表 -->
            <div class="activity-list ">
                <!-- 无数据时 显示 -->
                <el-empty description="没有数据" v-if="myActivityList1.length == 0"></el-empty>
                <!-- 活动item -->
                <div class="activity-item " v-for="item in myActivityList1">
                    <!-- 活动名称 -->
                    <div class="item-name " :title="item.activityName"
                        @click="$router.push({ path: '/activity_details', query: { activityId: item.activityId } })"
                        v-if="item.isTeamUp == 1">
                        {{ item.activityName }}
                    </div>
                    <!-- 无队伍名称的活动名称 宽度更大 -->
                    <div class="item-name2 " :title="item.activityName"
                        @click="$router.push({ path: '/activity_details', query: { activityId: item.activityId } })"
                        v-if="item.isTeamUp == 0">
                        {{ item.activityName }}
                    </div>
                    <div>
                        <!-- 个人活动标标 -->
                        <span class="item-isTeamUp" style="background-color: #1baec2;" v-if="item.isTeamUp == 0">{{
                            isTeamUp[0]
                        }}</span>
                        <!-- 团队活动标标 -->
                        <span class="item-isTeamUp " style="background-color: #22a74e;" v-if="item.isTeamUp == 1">{{
                            isTeamUp[1]
                        }}</span>
                        <!-- 活动类型标标 -->
                        <span class="item-isTeamUp " style="background-color: #e79806c7;">{{
                            activityType[item.activityType]
                        }}</span>
                    </div>
                    <!-- 当前进行的步骤 -->
                    <div class="item-stage " v-if="item.activityStage">
                        {{
                            JSON.parse(item.activityStage)[item.activityStageNow].activityStageName
                        }}
                    </div>
                    <!-- 活动开始时间 -->
                    <div class="item-time " v-if="item.activityBeginTime">{{
                        item.activityBeginTime.substring(0, 10) }}</div>
                    <!-- 申请列表 -->
                    <div class="item-joined-mem-list-btn">
                        <span class="btn-content ts02s"
                            @click="activity = { ...item }; getApplyMemList(); dialogVisible2 = true">
                            申请列表
                        </span>
                    </div>
                    <!-- 编辑活动 -->
                    <div class="item-edit-btn ">
                        <i class="el-icon-edit-outline btn-content" @click="activity = { ...item }; openEdit();"
                            title="编辑活动"></i>
                    </div>
                    <!-- 更多 弹出框 -->
                    <el-popover placement="right" width="50" trigger="hover" class="item-more-btn">
                        <!-- 上传文件 -->
                        <div class="more-i-i ts05s" @click="dialogVisible = true; activityId = item.activityId">
                            <i class="el-icon-picture-outline"></i>
                            上传封面
                        </div>
                        <!-- 上传文件 -->
                        <div class="more-i-i ts05s" @click="dialogVisible1 = true; activityId = item.activityId">
                            <i class="el-icon-link"></i>
                            上传资料
                        </div>
                        <!-- 退出活动 -->
                        <div class="more-i-i ts05s" @click="doDeleteActivity(item)">
                            <i class="el-icon-close"></i>
                            删除活动
                        </div>
                        <!-- ...图标 -->
                        <i class="el-icon-more" slot="reference" style="color: #909399;cursor: pointer;"></i>
                    </el-popover>
                </div>
            </div>
            <!-- 分页组件 -->
            <div class="activity-page ">
                <el-pagination layout="prev, next" :total="totalPage1" class="page-el-pagination"
                    @current-change="currentChange1">
                </el-pagination>
            </div>
        </div>
        <!-- 已结束 -->
        <div class="backstage-model2" v-if="change">
            <div class="model-title">
                我参加的活动&nbsp;
                <i class="el-icon-hot-water"></i>
                <!-- 搜索我的活动 -->
                <el-input class="model-search" size="mini" placeholder="搜索我的活动" prefix-icon="el-icon-search"
                    v-model="queryVo2.keyword" @keyup.enter.native="search2"></el-input>
            </div>
            <!-- 分割线 -->
            <div class="line"></div>
            <!-- 已结束 活动列表 -->
            <div class="activity-list ">
                <!-- 无数据时显示 -->
                <el-empty description="没有数据" v-if="myActivityList2.length == 0"></el-empty>
                <!-- 活动item -->
                <div class="activity-item " v-for="item in myActivityList2">
                    <!-- 活动名称 -->
                    <div class="item-name " :title="item.activity.activityName"
                        @click="$router.push({ path: '/activity_details', query: { activityId: item.activity.activityId } })"
                        v-if="item.activity.isTeamUp == 1">
                        {{ item.activity.activityName }}
                    </div>
                    <!-- 无队伍名称的活动名称 宽度更大 -->
                    <div class="item-name2 " :title="item.activity.activityName"
                        @click="$router.push({ path: '/activity_details', query: { activityId: item.activity.activityId } })"
                        v-if="item.activity.isTeamUp == 0">
                        {{ item.activity.activityName }}
                    </div>
                    <div>
                        <!-- 个人活动标标 -->
                        <span class="item-isTeamUp" style="background-color: #1baec2;" v-if="item.activity.isTeamUp == 0">
                            {{ isTeamUp[item.activity.isTeamUp] }}
                        </span>
                        <!-- 团队活动标标 -->
                        <span class="item-isTeamUp " style="background-color: #22a74e;"
                            v-if="item.activity.isTeamUp == 1">{{ isTeamUp[item.activity.isTeamUp] }}
                        </span>
                        <!-- 队伍附加信息 -->
                        <span class="item-isTeamUp-team-info " v-if="item.activity.isTeamUp == 1">
                            <!-- 队伍Logo -->
                            <img :src="item.teamLogo" alt="" class="team-logo">
                            <!-- 队伍名称 -->
                            <span class="team-name">
                                {{ item.teamName }}
                            </span>
                        </span>
                    </div>
                    <!-- 步骤 -->
                    <div class="item-stage " v-if="item.activity.activityStage">
                        {{
                            JSON.parse(item.activity.activityStage)[item.activity.activityStageNow].activityStageName
                        }}
                    </div>
                    <!-- 活动类型 -->
                    <div class="item-type ">{{
                        activityType[item.activity.activityType] }}活动</div>
                    <!-- 活动开始时间 -->
                    <div class="item-time " v-if="item.activity.activityBeginTime">{{
                        item.activity.activityBeginTime.substring(0, 10) }}</div>
                    <!-- 更多 弹出框 -->
                    <el-popover placement="right" width="50" trigger="click" class="item-more-btn">
                        <!-- 退出活动 -->
                        <div class="more-i-i2 ts05s" @click="doQuitActivity(item.activity.activityId, item.teamId)">
                            <i class="el-icon-right"></i>
                            退出活动
                        </div>
                        <!-- ...图标 -->
                        <i class="el-icon-more" slot="reference" style="color: #909399;cursor: pointer;"></i>
                    </el-popover>
                </div>
            </div>
            <!-- 分页 -->
            <div class="activity-page ">
                <el-pagination layout="prev, next" :total="totalPage2" class="page-el-pagination"
                    @current-change="currentChange2">
                </el-pagination>
            </div>
        </div>
        <!-- 上传封面的dialog -->
        <el-dialog title="活动封面上传" :visible.sync="dialogVisible" width="30%" :modal="false" class="thisblack-bgc">
            <el-upload class="upload-demo" :before-upload="beforeUploadCover" ref="upload" action="#"
                :http-request="handleUploadCover" :on-remove="handleRemoveCover" :file-list="coverFile" :auto-upload="false"
                :limit="1" :on-exceed="handleExceedImg">
                <div slot="trigger" size="small" type="primary" class="add-file-btn">+</div>
                <div slot="tip" class="el-upload__tip">只能上传 jpg 格式图片，且不超过2mb</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" round type="primary" @click="submitUploadCover(); dialogVisible = false">确
                    定</el-button>
                <el-button size="small" round @click="cancelUploadCover(); dialogVisible = false">取 消</el-button>
            </span>
        </el-dialog>
        <!-- 上传文件的dialog -->
        <el-dialog title="活动文件上传" :visible.sync="dialogVisible1" width="30%" :modal="false" class="thisblack-bgc">
            <el-upload class="upload-demo" :before-upload="beforeAvatarUpload" ref="upload" action="#"
                :http-request="handleUpload" :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
                <div slot="trigger" size="small" type="primary" class="add-file-btn">+</div>
                <div slot="tip" class="el-upload__tip">只能上传word、excal文件，且不超过5mb</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" round type="primary" @click="submitUpload(); dialogVisible1 = false">确
                    定</el-button>
                <el-button size="small" round @click="cancelUpload(); dialogVisible1 = false">取 消</el-button>
            </span>
        </el-dialog>
        <!-- 申请列表 -->
        <el-dialog title="申请列表" :visible.sync="dialogVisible2" width="30%" :modal="false" class="thisblack-bgc">
            <div class="activity-apply-list ">
                <!-- none -->
                <el-empty :image-size="100" description="暂无申请" v-if="applyMemlist.length == 0"></el-empty>
                <!-- list -->
                <div class="apply-item " v-for="item in applyMemlist" v-if="activity.isTeamUp == 0">
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
                <div class="apply-item " v-for="item in applyMemlist" v-if="activity.isTeamUp == 1">
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
                <div class="apply-item-count" v-if="applyMemlist.length != 0">共收到 {{ applyMemlist.length }} 条申请</div>
            </div>
        </el-dialog>
        <ActivityEdit v-if="!change" v-bind:activityId="activity.activityId"></ActivityEdit>
    </div>
</template>

<script>
import { mapActions } from 'vuex';
import ElementUI from 'element-ui';
import ActivityEdit from './ActivityEdit.vue';
export default {
    data: () => ({
        /**
         * 用户发布的活动list
         */
        myActivityList1: [],
        /**
         * 用户发布的分页总页数
         */
        totalPage1: 0,
        /**
         * 用户发布的查询Vo
         */
        queryVo1: {
            current: 1,
            size: 5,
            keyword: '',
            userId: '',
            status: ''
        },
        /**
         * 用户参加的活动list
         */
        myActivityList2: [],
        /**
         * 用户参加的分页总页数
         */
        totalPage2: 0,
        /**
         * 用户参加的查询Vo
         */
        queryVo2: {
            current: 1,
            size: 5,
            keyword: '',
            userId: '',
            status: '',
        },
        /**
         * 活动类型
         */
        activityType: ['团建', '聚会', '运动', '社团', '户外', '游戏'],
        /**
         * 是否是组队活动
         */
        isTeamUp: ['个人活动', '团队活动'],
        /**
         * 
         */
        activityId: '',
        fileList: [],
        dialogVisible1: false,
        /**
         * 上传封面
         */
        dialogVisible: false,
        coverFile: [],
        /**
         * 申请列表
         */
        dialogVisible2: false,
        applyMemlist: [],
        /**
         * 活动
         */
        activity: {},
        /**
         * 编辑
         */
        change: true,
    }),
    methods: {
        ...mapActions('activity', ['selectActivityByInitiatorId']),
        ...mapActions('activity', ['selectMyActivityByKeyWordAndStatus']),
        ...mapActions('user_activity', ['deleteUserActivity']),
        ...mapActions('team_activity', ['deleteTeamActivity']),
        ...mapActions('activity', ['uploadActivityFile']),
        ...mapActions('activity', ['uploadActivityCover']),
        ...mapActions('activity', ['deleteActivityById']),
        /**
         * 用户发布的活动
         */
        async doGetActivity1() {
            await this.search1()
        },
        /**
         * 搜索用户发布的活动
         */
        async search1() {
            this.queryVo1.userId = localStorage.getItem('userId')
            const { code, message, data } = await this.selectActivityByInitiatorId(this.queryVo1);
            if (code === 200) {
                // 分页数据
                this.myActivityList1 = [...data.page]
                // 计算总页数
                let size = this.queryVo1.size;
                let total = data.total
                this.totalPage1 = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            } else {
                this.myActivityList1 = []
            }
        },
        /**
         * 用户参加的活动
         */
        async doGetActivity2() {
            await this.search2()
        },
        /**
         * 搜索用户参加的活动
         */
        async search2() {
            this.queryVo2.userId = localStorage.getItem('userId')
            const { code, message, data } = await this.selectMyActivityByKeyWordAndStatus(this.queryVo2);
            if (code === 200) {
                // 分页数据
                this.myActivityList2 = [...data.page]
                // 计算总页数
                let size = this.queryVo2.size;
                let total = data.total
                this.totalPage2 = total % size != 0 ? (parseInt(total / size) + 1) * 10 : (parseInt(total / size)) * 10;
            }
        },
        /**
         * 用户发布的活动列表分页
         */
        async currentChange1(e) {
            this.queryVo1.current = e
            this.search1()
        },
        /**
         * 用户参加的活动列表分页
         */
        async currentChange2(e) {
            this.queryVo2.current = e
            this.search2()
        },
        /**
         * 删除活动
         */
        async doDeleteActivity(item) {
            this.$confirm(item.activityName, '确定删除该活动？', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                var formData = new window.FormData()
                formData.append('activityId', item.activityId)
                const { code, message, data } = await this.deleteActivityById(formData)
                if (code === 200) {
                    ElementUI.Message.success('删除成功')
                    setTimeout(() => {
                        location.reload()
                    }, 1000)
                }
            })
        },
        /**
         * 退出活动
         */
        doQuitActivity(activityId, teamId) {
            const _this = this
            this.$confirm('确定退出该活动?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                if (teamId) {
                    let teamActivityDeleteVo = {
                        teamId: teamId,
                        activityId: activityId,
                        status: 1, // 0申请 1确认关系
                        type: 1 // type 操作类型 剔除3 婉拒2 退出1
                    }
                    const { code, message, data } = await _this.deleteTeamActivity(teamActivityDeleteVo);
                    if (code === 200) {
                        this.$message({
                            type: 'success',
                            message: '成功退出该活动'
                        });
                    } else {
                        this.$message({
                            type: 'error',
                            message: '系统错误'
                        });
                    }
                } else {
                    // 拿userId
                    let id = localStorage.getItem('userId')
                    let userActivityDeleteVo = {
                        userId: id,
                        activityId: activityId,
                        status: 1, // 0申请 1确认关系
                        type: 1 // type 操作类型 剔除3 婉拒2 退出1
                    }
                    const { code, message, data } = await _this.deleteUserActivity(userActivityDeleteVo);
                    if (code === 200) {
                        this.$message({
                            type: 'success',
                            message: '成功退出该活动'
                        });
                    } else {
                        this.$message({
                            type: 'error',
                            message: '系统错误'
                        });
                    }
                }
                setTimeout(() => {
                    location.reload()
                }, 1000)
            })
        },
        /**
         * 上传文件
         * @param {*} file 
         */
        beforeAvatarUpload(file) {
            let pattern = /^.*(\.doc|\.docx|.xls|.xlsx)$/
            const isJPG = pattern.test(file.name);
            const isLt5M = file.size / 1024 / 1024 / 1024 < 5;
            if (!isJPG) {
                this.$message.error('上传的文件只能是 word 或 excal !');
            }
            if (!isLt5M) {
                this.$message.error('文件大小不能超过 5MB!');
            }
            return isJPG && isLt5M;
        },
        handleUpload(raw) {
            this.fileList.push(raw.file);
        },
        async submitUpload() {
            // 获取文件到this.fileList
            this.$refs.upload.submit();
            if (this.fileList.length == 0) {
                setTimeout(() => {
                    this.$message.warning('点击太快，没看清楚(^_^)9');
                }, 500)
                return
            }
            // 添加文件
            var formData = new window.FormData();
            this.fileList.forEach(function (file) {
                formData.append('file', file);
            })
            // 添加活动id
            formData.append("activityId", this.activityId)
            // 上传
            const { code, message, data } = await this.uploadActivityFile(formData)
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
        /**
         * 上传封面
         * @param {*} file 
         */
        beforeUploadCover(file) {
            let pattern = /^.*(\.jpg)$/
            const isJPG = pattern.test(file.name);
            const isLt5M = file.size / 1024 / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error('封面图片只限 jpg 格式');
            }
            if (!isLt5M) {
                this.$message.error('文件大小不能超过 2MB!');
            }
            return isJPG && isLt5M;
        },
        handleUploadCover(raw) {
            this.coverFile.push(raw.file);
        },
        async submitUploadCover() {
            // 获取文件到this.coverFile
            this.$refs.upload.submit();
            if (this.coverFile.length == 0) {
                setTimeout(() => {
                    this.$message.warning('点击太快，没看清楚(^_^)9');
                }, 500)
                return
            }
            // 添加文件
            var formData = new window.FormData();
            this.coverFile.forEach(function (file) {
                formData.append('file', file);
            })
            // 添加活动id
            formData.append("activityId", this.activityId)
            // 上传
            const { code, message, data } = await this.uploadActivityCover(formData)
            if (code === 200) {
                ElementUI.Message.success(this.$UPLOADSUCCESS)
            }
            this.coverFile = []
            this.$refs.upload.clearFiles();
        },
        cancelUploadCover() {
            this.coverFile = []
            this.$refs.upload.clearFiles();
        },
        handleRemoveCover(file, fileList) {
            this.coverFile = fileList
        },
        handleExceedImg(files, fileList) {
            this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        ...mapActions('user_activity', ['selectUserListByActivityIdAndStatus']),
        ...mapActions('team_activity', ['selectTeamListByActivityIdAndStatus']),
        /**
         * 取申请者列表
         */
        async getApplyMemList() {
            const queryVo = {
                activityId: this.activity.activityId,
                status: 0 // 1已确认参加 0已申请未确认
            }
            if (this.activity.isTeamUp == 0) {
                const { code, messge, data } = await this.selectUserListByActivityIdAndStatus(queryVo)
                if (code === 200) {
                    this.applyMemlist = [...data]
                } else {
                    this.applyMemlist = []
                }
            }
            if (this.activity.isTeamUp == 1) {
                const { code, messge, data } = await this.selectTeamListByActivityIdAndStatus(queryVo)
                if (code === 200) {
                    this.applyMemlist = [...data]
                } else {
                    this.applyMemlist = []
                }
            }
        },
        /**
         * 同意申请
         */
        ...mapActions('user_activity', ['updateUserActivityStatusById']),
        ...mapActions('team_activity', ['updateTeamActivityStatusById']),
        // 同意
        async applyJoin(item, id) {
            // 同意个人申请
            if (this.activity.isTeamUp == 0) {
                const userActivity = {
                    userId: id,
                    activityId: this.activity.activityId,
                    status: 1
                }
                const { code, message, data } = await this.updateUserActivityStatusById(userActivity)
                if (code === 200) {
                    this.applyMemlist = this.applyMemlist.filter(e => e != item)
                }
            } else {
                // 同意组队申请
                const teamActivity = {
                    teamId: id,
                    activityId: this.activity.activityId,
                    status: 1
                }
                const { code, message, data } = await this.updateTeamActivityStatusById(teamActivity)
                if (code === 200) {
                    this.applyMemlist = this.applyMemlist.filter(e => e != item)
                }
            }
        },
        /**
         * 拒绝申请
         */
        ...mapActions('user_activity', ['deleteUserActivity']),
        ...mapActions('team_activity', ['deleteTeamActivity']),
        // 拒绝
        async rejectJoin(item, id) {
            // 拒绝个人申请
            if (this.activity.isTeamUp == 0) {
                const userActivityDeleteVo = {
                    userId: id,
                    activityId: this.activity.activityId,
                    status: 0,
                    type: 2 // type 操作类型 剔除3 婉拒2 退出1
                }
                const { code, message, data } = await this.deleteUserActivity(userActivityDeleteVo)
                if (code === 200) {
                    this.applyMemlist = this.applyMemlist.filter(e => e != item)
                }
            } else {
                // 拒绝组队申请
                const teamActivityDeleteVo = {
                    teamId: id,
                    activityId: this.activity.activityId,
                    status: 0,
                    type: 2 // type 操作类型 剔除3 婉拒2 退出1
                }
                const { code, message, data } = await this.deleteTeamActivity(teamActivityDeleteVo)
                if (code === 200) {
                    this.applyMemlist = this.applyMemlist.filter(e => e != item)
                }
            }
        },
        openEdit() {
            this.$parent.editStatus2(false)
            this.change = false
        },
        closeEdit() {
            this.$parent.editStatus2(true)
            this.change = true
            // location.reload()
        },
        closeEdit2() {
            this.$parent.editStatus2(true)
            this.change = true
            location.reload()
        }
    },
    mounted() {
        this.doGetActivity1()
        this.doGetActivity2()
    },
    watch: {
        'queryVo1.keyword'() {
            this.search1()
        },
        'queryVo2.keyword'() {
            this.search2()
        },
    },
    components: {
        ActivityEdit
    }
}
</script>

<style lang="less" scoped>
.backstage-view2 {
    position: absolute;
    top: 80px;
    left: 565px;
    width: 950px;

    .backstage-model2 {
        position: relative;
        width: 100%;
        box-shadow: 0px 0px 10px #4d4d4d2c;

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
        }

        .model-search {
            position: absolute;
            right: 15px;
            width: 200px;
        }

        .activity-page {
            position: relative;
            bottom: 0;
            text-align: right;
        }
    }

    .backstage-model2:nth-child(1) {
        height: 410px;
        margin-bottom: 15px;

        .activity-list {
            width: 100%;
            height: 310px;
            overflow: hidden;

            .activity-item {
                width: 100%;
                height: 60px;
                overflow: hidden;
                display: flex;
                font-size: 14px;
                line-height: 60px;
                border-bottom: 1px dashed #99999957;

                .item-name {
                    position: relative;
                    margin-left: 35px;
                    margin-right: 5px;
                    max-width: 260px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .item-name2 {
                    position: relative;
                    margin-left: 35px;
                    margin-right: 5px;
                    max-width: 340px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;

                }

                .item-isTeamUp {
                    position: relative;
                    color: #fff;
                    padding-top: 3px;
                    padding-bottom: 3px;
                    padding-left: 7px;
                    padding-right: 7px;
                    border-radius: 5px;
                    font-size: 12px;
                    margin-left: 10px;
                }

                .item-isTeamUp-team-info {
                    position: relative;
                    color: #fff;
                    background-color: #d38033ad;
                    padding-top: 3px;
                    padding-bottom: 3px;
                    padding-left: 10px;
                    padding-right: 20px;
                    border-radius: 5px;
                    margin-left: 10px;
                    font-size: 12px;

                    .team-logo {
                        position: absolute;
                        left: 5px;
                        top: 0;
                        width: 20px;
                        height: 20px;
                    }

                    .team-name {
                        position: relative;
                        left: 15px;
                        top: 0;
                    }
                }

                .item-stage {
                    color: #eb4d41;
                    width: 120px;
                    text-align: center;
                    position: absolute;
                    left: 500px;
                }

                // .item-type {
                //     width: 130px;
                //     position: absolute;
                //     left: 620px;
                //     text-align: center;
                //     font-weight: lighter;
                // }

                .item-time {
                    width: 130px;
                    position: absolute;
                    left: 620px;
                    text-align: center;
                    font-weight: lighter;
                }

                .item-joined-mem-list-btn {
                    width: 130px;
                    position: absolute;
                    left: 740px;
                    text-align: center;
                    font-weight: lighter;

                    .btn-content {
                        color: #0a75b3;
                        cursor: pointer;
                    }

                    .btn-content:hover {
                        color: #3db7e7;
                    }
                }

                .item-edit-btn {
                    width: 30px;
                    position: absolute;
                    left: 840px;
                    text-align: center;
                    font-weight: lighter;
                    font-size: 16px;
                    margin-top: 1px;

                    .btn-content {
                        cursor: pointer;

                    }
                }


                .item-more-btn {
                    position: absolute;
                    left: 900px;
                }

            }
        }
    }

    .backstage-model2:nth-child(2) {
        height: 425px;

        .activity-list {
            width: 100%;
            height: 310px;
            overflow: hidden;

            .activity-item {
                width: 100%;
                height: 60px;
                overflow: hidden;
                display: flex;
                font-size: 14px;
                line-height: 60px;
                border-bottom: 1px dashed #99999957;

                .item-name {
                    position: relative;
                    margin-left: 35px;
                    margin-right: 5px;
                    max-width: 260px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .item-name2 {
                    position: relative;
                    margin-left: 35px;
                    margin-right: 5px;
                    max-width: 340px;
                    // 省略效果
                    overflow: hidden;
                    display: -webkit-box;
                    /*第几行裁剪*/
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }

                .item-isTeamUp {
                    color: #fff;
                    padding-top: 3px;
                    padding-bottom: 3px;
                    padding-left: 7px;
                    padding-right: 7px;
                    border-radius: 5px;
                    font-size: 12px;
                    margin-left: 10px;
                }

                .item-isTeamUp-team-info {
                    position: relative;
                    color: #fff;
                    background-color: #d38033ad;
                    padding-top: 3px;
                    padding-bottom: 3px;
                    padding-left: 10px;
                    padding-right: 20px;
                    border-radius: 5px;
                    margin-left: 10px;
                    font-size: 12px;

                    .team-logo {
                        position: absolute;
                        left: 5px;
                        top: 0;
                        width: 20px;
                        height: 20px;
                    }

                    .team-name {
                        position: relative;
                        left: 15px;
                        top: 0;
                    }
                }

                .item-stage {
                    color: #a8a8a8;
                    width: 120px;
                    text-align: center;
                    position: absolute;
                    left: 500px;
                }

                .item-type {
                    width: 130px;
                    position: absolute;
                    left: 620px;
                    text-align: center;
                    font-weight: lighter;
                }

                .item-time {
                    width: 130px;
                    position: absolute;
                    left: 750px;
                    text-align: center;
                    font-weight: lighter;
                }

                // .item-joined-mem-list-btn {
                //     width: 130px;
                //     position: absolute;
                //     left: 750px;
                //     text-align: center;
                //     font-weight: lighter;
                //     .btn-content{
                //         color: #0a75b3;
                //         cursor: pointer;
                //     }
                //     .btn-content:hover{
                //         color: #3db7e7;
                //     }
                // }

                .item-more-btn {
                    position: absolute;
                    left: 900px;
                }

            }
        }
    }

    .thisblack-bgc {
        background-color: #00000049;
    }

    .activity-apply-list {
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

        .apply-item-count {
            font-size: 12px;
            color: #909399;
        }
    }
}

::v-deep .el-button {
    font-size: 14px;
    font-weight: lighter;
    width: 70px;
}

::v-deep .el-dialog__body {
    padding: 10px 20px;
}

::v-deep .el-dialog__header {
    padding: 20px 20px 10px;
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

<style lang="less">
/* scope不生效 */
.el-popover {
    min-width: 80px;
    padding: 8px;

    .more-i-i,
    .more-i-i2 {
        height: 25px;
        line-height: 25px;
        text-align: center;
        cursor: pointer;
    }

    .more-i-i:nth-child(1) {
        margin-bottom: 5px;
    }

    .more-i-i:nth-child(2) {
        margin-bottom: 5px;
    }

    .more-i-i:nth-child(1):hover {
        color: #1fc779;
    }

    .more-i-i:nth-child(2):hover {
        color: #1f6dc7;
    }

    .more-i-i:nth-child(3):hover {
        color: #c71f1f;
    }

    .more-i-i2:hover {
        color: #18c063;
    }
}
</style>