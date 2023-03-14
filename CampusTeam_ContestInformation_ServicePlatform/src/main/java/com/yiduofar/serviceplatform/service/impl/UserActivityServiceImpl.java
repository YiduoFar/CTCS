package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.ActivityMapper;
import com.yiduofar.serviceplatform.mapper.UserMapper;
import com.yiduofar.serviceplatform.service.MessageService;
import com.yiduofar.serviceplatform.service.UserActivityService;
import com.yiduofar.serviceplatform.mapper.UserActivityMapper;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import com.yiduofar.serviceplatform.vo.UserActivityDeleteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageTip.*;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_ACTIVITY;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_BACKSTAGE;

/**
 * @author HuiBBao
 * @description 针对表【user_activity】的数据库操作Service实现
 * @createDate 2023-02-27 00:27:36
 */
@Service
public class UserActivityServiceImpl extends ServiceImpl<UserActivityMapper, UserActivity> implements UserActivityService {

    @Autowired
    UserActivityMapper userActivityMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    MessageService messageService;

    @Autowired
    UserMapper userMapper;

    /**
     * 删除一条用户活动关系
     * userActivityDeleteVo.type为操作类型 剔除3 婉拒2 退出1
     *
     * @param userActivityDeleteVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteUserActivity(UserActivityDeleteVo userActivityDeleteVo) {
        // 删除数据库记录
        int delete = userActivityMapper.delete(new QueryWrapper<UserActivity>()
                .eq("user_id", userActivityDeleteVo.getUserId())
                .eq("activity_id", userActivityDeleteVo.getActivityId())
                .eq("status", userActivityDeleteVo.getStatus()));
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        Activity activity = activityMapper.selectById(userActivityDeleteVo.getActivityId());
        // 参与数量-1
        if (activity.getActivityJoinedNum() > 0) {
            UpdateWrapper<Activity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.setSql("activity_joined_num = activity_joined_num - 1")
                    .eq("activity_id", activity.getActivityId());
            int update = activityMapper.update(activity, updateWrapper);
            if (update != 1) {
                throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
            }
        }
        // 发消息
        String content = USER_ACTIVITY_DELETE_TIP[userActivityDeleteVo.getType() - 1] + activity.getActivityName();
        messageService.sendMessageToUser(userActivityDeleteVo.getUserId(), content, TO_BACKSTAGE + 5, 0);
        return ResponseResult.success();
    }

    /**
     * 查询一条用户活动关系
     *
     * @param userActivity
     * @return
     */
    @Override
    public ResponseResult selectOne(UserActivity userActivity) {
        UserActivity res = userActivityMapper.selectOne(new QueryWrapper<UserActivity>()
                .eq("user_id", userActivity.getUserId())
                .eq("activity_id", userActivity.getActivityId()));
        // 不检查直接返回 交给前端判断申请状态和已参赛状态
        return ResponseResult.success(res);
    }

    /**
     * 新增用户活动关系（申请）
     *
     * @param userActivity
     * @return
     */
    @Override
    public ResponseResult insertOne(UserActivity userActivity) {
        User user = userMapper.selectById(userActivity.getUserId());
        Activity activity = activityMapper.selectById(userActivity.getActivityId());
        // 已经结束
        if (activity.getActivityStatus() == 0) {
            throw new GlobalException(ResponseResultEnum.ACTIVITY_END_ERROR);
        }
        // 自己不能加入
        if (user.getId() == activity.getActivityInitiatorId()) {
            throw new GlobalException(ResponseResultEnum.USER_INITIATOR_JOIN_ERROR);
        }
        // 受众检查
        if (activity.getActivityAudience() == 1) {
            // 学院
            if (!activity.getActivityAudienceName().equals(user.getCollege())) {
                throw new GlobalException(ResponseResultEnum.USER_AUDIENCE_ERROR);
            }
        }
        if (activity.getActivityAudience() == 2) {
            // 专业
            if (!activity.getActivityAudienceName().equals(user.getSpecialty())) {
                throw new GlobalException(ResponseResultEnum.USER_SPECIALTY_ERROR);
            }
        }
        // 申请
        int count = userActivityMapper.insert(userActivity);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        if (!Objects.isNull(user) && !Objects.isNull(activity)) {
            // 给自己发消息 通知 type 0
            String content = "提交了待处理的申请: " + activity.getActivityName();
            messageService.sendMessageToUser(user.getId(), content, TO_ACTIVITY + activity.getActivityId(), 0);
            // 给负责的老师发消息 申请 type 1
            String content2 = user.getRealName() + " 申请参加" + activity.getActivityName();
            messageService.sendMessageToUser(activity.getActivityInitiatorId(), content2, TO_BACKSTAGE + 5, 1);
        }
        return ResponseResult.success();
    }

    /**
     * 根据活动id、状态查询活动的 参与者 或 申请者列表（个人）
     *
     * @param activityId
     * @param status
     * @return
     */
    @Override
    public ResponseResult selectUserListByActivityIdAndStatus(Integer activityId, Integer status) {
        List<User> users = userActivityMapper.selectUserListByActivityIdAndStatus(new QueryWrapper<>()
                .eq("ua.activity_id", activityId)
                .eq("ua.status", status));
        if (CollectionUtils.isEmpty(users)) {
            throw new GlobalException(ResponseResultEnum.NULL_USER_LIST_ERROR);
        }
        return ResponseResult.success(users);
    }

    /**
     * 更新用户活动关系（同意申请）
     *
     * @param userActivity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateStatusById(UserActivity userActivity) {
        if (userActivity.getStatus() != 1) {
            throw new GlobalException(ResponseResultEnum.ERROR);
        }
        Activity activity = activityMapper.selectById(userActivity.getActivityId());
        // 增加参与数量
        UpdateWrapper<Activity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("activity_joined_num = activity_joined_num + 1")
                .eq("activity_id", activity.getActivityId());
        int update = activityMapper.update(activity, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 更新数据库
        int count = userActivityMapper.updateStatusById(userActivity);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 给用户发送消息
        String content = "申请通过: " + activity.getActivityName();
        messageService.sendMessageToUser(userActivity.getUserId(), content, TO_BACKSTAGE + 5, 0);
        return ResponseResult.success();
    }
}




