package com.yiduofar.serviceplatform.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.*;
import com.yiduofar.serviceplatform.service.ActivityService;
import com.yiduofar.serviceplatform.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author HuiBBao
 * @description 针对表【activity】的数据库操作Service实现
 * @createDate 2023-02-27 00:18:57
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    UserActivityMapper userActivityMapper;

    @Autowired
    TeamActivityMapper teamActivityMapper;

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ActivityFileMapper activityFileMapper;

    /**
     * 分页
     * 根据用户id、关键字和状态查询活动
     *
     * @param queryVo
     * @return
     */
    @Override
    public ResponseResult selectMyActivityByKeyWordAndStatus(QueryVo queryVo) {
        // 分步骤，分别拿个人参加的活动id、队伍（作为队长）参加的活动id，再合并活动id模糊分页查询
        Set<Integer> ids = new HashSet<>();
        // 拿到用户个人参加的活动id
        List<UserActivity> userActivities = userActivityMapper.selectList(new QueryWrapper<UserActivity>()
                .eq("user_id", queryVo.getUserId()).eq("status", 1));
        for (UserActivity userActivity : userActivities) {
            ids.add(userActivity.getActivityId());
        }
        // 拿到队伍（作为队长）参加的活动id
        List<TeamActivity> teamActivities = teamActivityMapper.selectTeamActivityList(queryVo.getUserId(), 1);
        for (TeamActivity teamActivity : teamActivities) {
            ids.add(teamActivity.getActivityId());
        }
        // 如果ids为空，则直接返回
        if (CollectionUtils.isEmpty(ids)) {
            return ResponseResult.success(new ActivityMineVoPageTotal(new ArrayList<>(), 0L));
        }
        System.out.println(ids);
        // 封装分页对象
        Page<Activity> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());

        System.out.println(queryVo);

        activityMapper.selectMyActivityByKeyWordAndStatus(page, ids, queryVo.getStatus(), queryVo.getKeyword());
        // 拿到分页数据 records
        List<Activity> records = page.getRecords();

        System.out.println("records:" + records);
        System.out.println("total:" + page.getTotal());

        Long total = page.getTotal();
        // 若队伍信息不为空，将他们和活动信息绑定为Vo组成Vo列表返回
        if (!CollectionUtils.isEmpty(teamActivities)) {
            // 组装列表records2
            List<ActivityMineVo> records2 = new ArrayList<>();
            for (TeamActivity teamActivity : teamActivities) {
                for (Activity activity : records) {
                    if (teamActivity.getActivityId() == activity.getActivityId()) {
                        Team team = teamMapper.selectById(teamActivity.getTeamId());
                        // 绑定Vo对象 加入list
                        records2.add(new ActivityMineVo(activity, team.getTeamId(), team.getTeamName(), team.getTeamLogo()));
                    }
                }
            }
            // 加入剩余个人活动
            for (UserActivity userActivity : userActivities) {
                for (Activity activity : records) {
                    if (userActivity.getActivityId() == activity.getActivityId()) {
                        records2.add(new ActivityMineVo(activity));
                    }
                }
            }
            ActivityMineVoPageTotal activityMineVoPageTotal = new ActivityMineVoPageTotal(records2, total);
            return ResponseResult.success(activityMineVoPageTotal);
        } else {
            //  若没有以队伍形式参加的活动，也要以相同的数据形式返回
            List<ActivityMineVo> records3 = new ArrayList<>();
            for (Activity activity : records) {
                records3.add(new ActivityMineVo(activity));
            }
            ActivityMineVoPageTotal activityMineVoPageTotal = new ActivityMineVoPageTotal(records3, total);
            return ResponseResult.success(activityMineVoPageTotal);
        }
    }

    /**
     * 根据发布者id查询活动
     *
     * @param queryVo
     * @return
     */
    @Override
    public ResponseResult selectActivityByInitiatorId(QueryVo queryVo) {
        // 封装分页对象
        Page<Activity> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        List<Activity> activities = activityMapper.selectPage2(page, new QueryWrapper<>()
                .eq("activity_initiator_id", queryVo.getUserId())
                .like("activity_name", queryVo.getKeyword()));
        // 判空
        if (CollectionUtils.isEmpty(activities)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(new ActivityPageTotal(activities, page.getTotal()));
    }

    /**
     * 根据id删除活动
     *
     * @param activityId
     * @return
     */
    @Override
    public ResponseResult deleteActivityById(Integer activityId) {
        int delete = activityMapper.deleteById(activityId);
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }


    /**
     * 新增活动
     *
     * @param activity
     * @return
     */
    @Override
    public ResponseResult insertActivity(Activity activity) {
        activity.setActivityPublishDate(new Date());
        int insert = activityMapper.insert(activity);
        if (insert != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success(activity.getActivityId());
    }

    /**
     * 根据关键字查询活动
     * @param queryVo
     * @return
     */
    @Override
    public ResponseResult selectActivityByKeyword(QueryVo queryVo) {
        // 封装分页对象
        Page<Activity> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        // 根据关键字模糊查询
        List<Activity> records = activityMapper.selectPage2(page,
                new QueryWrapper<>().like("activity_name", queryVo.getKeyword()));
        if (CollectionUtils.isEmpty(records)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        // 封装Vo，带上发布者信息
        List<ActivityInitiatorVo> pageList = new ArrayList<>();
        for (Activity activity : records) {
            ActivityInitiatorVo activityInitiatorVo = new ActivityInitiatorVo();
            activityInitiatorVo.setActivity(activity);
            activityInitiatorVo.setInitiator(userMapper.selectById(activity.getActivityInitiatorId()));
            pageList.add(activityInitiatorVo);
        }
        // 总数
        Long total = page.getTotal();
        return ResponseResult.success(new ActivityInitiatorPageTotal(pageList, total));
    }

    /**
     * 查询最新的5个活动
     *
     * @param
     * @return
     */
    @Override
    public ResponseResult selectLatestActivity() {
        List<Activity> activities = activityMapper.selectLatestActivity();
        if (CollectionUtils.isEmpty(activities)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        List<ActivityInitiatorVo> latestActivity = new ArrayList<>();
        for (Activity activity : activities) {
            ActivityInitiatorVo activityInitiatorVo = new ActivityInitiatorVo();
            activityInitiatorVo.setActivity(activity);
            activityInitiatorVo.setInitiator(userMapper.selectById(activity.getActivityInitiatorId()));
            latestActivity.add(activityInitiatorVo);
        }
        return ResponseResult.success(latestActivity);
    }

    /**
     * 查询ActivityDetailVo
     * @param activityId
     * @return
     */
    @Override
    public ResponseResult selectActivityDetailVoById(Integer activityId) {
        Activity activity = activityMapper.selectById(activityId);
        ActivityDetailVo activityDetailVo = new ActivityDetailVo();
        activityDetailVo.setActivity(activity);
        // 查发布者
        User user = userMapper.selectById(activity.getActivityInitiatorId());
        user.setPassword(null);
        activityDetailVo.setInitiator(user);
        // 查文件列表
        List<ActivityFile> activityFiles = activityFileMapper.selectList(new QueryWrapper<ActivityFile>().eq("activity_id", activityId));
        activityDetailVo.setActivityFileList(activityFiles);
        if (activity.getIsTeamUp() == 0) {
            // 查参与者列表
            List<User> users = userActivityMapper.selectUserListByActivityIdAndStatus(new QueryWrapper<>()
                    .eq("ua.activity_id", activityId)
                    .eq("ua.status", 1));
            // 过滤密码
            for (User u : users) {
                u.setPassword(null);
            }
            activityDetailVo.setJoinedUserList(users);
        }
        if (activity.getIsTeamUp() == 1) {
            // 查参与队伍列表
            List<Team> teams = teamActivityMapper.selectTeamListByActivityIdAndStatus(new QueryWrapper<>()
                    .eq("ta.activity_id", activityId)
                    .eq("ta.status", 1));
            activityDetailVo.setJoinedTeamList(teams);
        }
        return ResponseResult.success(activityDetailVo);
    }

    /**
     * 根据id查询活动
     * @param activityId
     * @return
     */
    @Override
    public ResponseResult selectActivityById(Integer activityId) {
        Activity activity = activityMapper.selectById(activityId);
        if (Objects.isNull(activity)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(activity);
    }

    /**
     * 编辑活动（更新）
     * @param activity
     * @return
     */
    @Override
    public ResponseResult updateActivityById(Activity activity) {
        int count = activityMapper.updateById(activity);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.EDIT_ERROR);
        }
        return ResponseResult.success();
    }

}




