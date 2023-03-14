package com.yiduofar.serviceplatform.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.*;
import com.yiduofar.serviceplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HuiBBao
 * @create 2023/2/24 15:23
 */
@Component
@Slf4j
public class ScheduleTask {

    /**
     * 积分排行的领域对象数组
     */
    public final static List<UserPointVo> userPointVoList = new ArrayList<>();

    /**
     * 优秀个人的领域对象数组
     */
    public final static List<ExcellentPlayerVo> excellentPlayerVoList = new ArrayList<>();

    /**
     * 优秀队伍的领域对象数组
     */
    public final static List<ExcellentTeamVo> excellentTeamVoList = new ArrayList<>();


    @Autowired
    UserMapper userMapper;

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    UserCompetitionMapper userCompetitionMapper;

    @Autowired
    TeamCompetitionMapper teamCompetitionMapper;

    @Autowired
    TeamActivityMapper teamActivityMapper;

    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    UserActivityMapper userActivityMapper;

//    @Scheduled(cron = "0/10 * * * * ?")//每10秒执行一次

    /**
     * 积分排行榜
     * 每天凌晨执行一次计算
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void countPoint() {
        // 先清空
        userPointVoList.clear();
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        for (User user : users) {
            UserPointVo userPointVo = new UserPointVo();
            // 基本信息
            userPointVo.setUserHead(user.getUserHead());
            userPointVo.setUserName(user.getUserName());
            // 计算积分 (参赛次数 * 100)
            Integer count = userCompetitionMapper.selectCount(new QueryWrapper<UserCompetition>()
                    .eq("user_id", user.getId())
                    .eq("status", 1));
            userPointVo.setPoint(count * 100);
            userPointVoList.add(userPointVo);
        }
        // 根据积分倒叙排序
        Collections.sort(userPointVoList, (o1, o2) -> {
            Integer id1 = o1.getPoint();
            Integer id2 = o2.getPoint();
            return id2.compareTo(id1);
        });
    }

    /**
     * 计算优秀个人（暂定1.0根据参赛数量多至少排序）
     * 每天凌晨执行一次计算
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void countExcellentPlayer() {
        excellentPlayerVoList.clear();
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        for (User user : users) {
            Integer count = userCompetitionMapper.selectCount(new QueryWrapper<UserCompetition>()
                    .eq("user_id", user.getId())
                    .eq("status", 1));
            ExcellentPlayerVo excellentPlayerVo = new ExcellentPlayerVo(user.getUserHead(), user.getUserName(), count);
            excellentPlayerVoList.add(excellentPlayerVo);
        }
        // 根据参赛数量倒叙排序
        Collections.sort(excellentPlayerVoList, (o1, o2) -> {
            Integer id1 = o1.getJoinedNum();
            Integer id2 = o2.getJoinedNum();
            return id2.compareTo(id1);
        });
        excellentPlayerVoList.subList(0, Math.min(excellentPlayerVoList.size(), 12));
    }

    /**
     * 计算优秀队伍（暂定根据创建时间长短（30%）和参加竞赛（50%）和参加活动（20%）的比例计算）
     * 每天凌晨执行一次计算
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void countExcellentTeam() {
        excellentTeamVoList.clear();
        List<Team> teams = teamMapper.selectList(new QueryWrapper<>());
        Date date = new Date();
        // 计算优秀队伍分数
        teams.sort((o1, o2) -> {
            long time1 = Math.abs(o1.getTeamCreateTime().getTime() - date.getTime());
            long time2 = Math.abs(o2.getTeamCreateTime().getTime() - date.getTime());
            Integer c1 = teamCompetitionMapper.selectCount(new QueryWrapper<TeamCompetition>()
                    .eq("team_id", o1.getTeamId())
                    .eq("status", 1));
            Integer c2 = teamCompetitionMapper.selectCount(new QueryWrapper<TeamCompetition>()
                    .eq("team_id", o2.getTeamId())
                    .eq("status", 1));
            Integer a1 = teamActivityMapper.selectCount(new QueryWrapper<TeamActivity>()
                    .eq("team_id", o1.getTeamId())
                    .eq("status", 1));
            Integer a2 = teamActivityMapper.selectCount(new QueryWrapper<TeamActivity>()
                    .eq("team_id", o2.getTeamId())
                    .eq("status", 1));
            Double point1 = time1 * 0.3 + c1 * 0.5 + a1 * 0.2;
            Double point2 = time2 * 0.3 + c2 * 0.5 + a2 * 0.2;
            return point2.compareTo(point1);
        });
        // 装填
        for (Team team : teams) {
            Integer count = teamCompetitionMapper.selectCount(new QueryWrapper<TeamCompetition>()
                    .eq("team_id", team.getTeamId())
                    .eq("status", 1));
            excellentTeamVoList.add(new ExcellentTeamVo(team, count));
        }
        excellentTeamVoList.subList(0, Math.min(excellentTeamVoList.size(), 20));
    }

    /**
     * 计算队伍星级（暂定根据创建时间长短（30%）和参加竞赛（50%）和参加活动（20%）的比例计算）
     * 每天凌晨执行一次计算
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateTeamStar() {
        List<Team> teams = teamMapper.selectList(new QueryWrapper<>());
        Date date = Calendar.getInstance().getTime();
        for (Team team : teams) {
            long time = Math.abs(date.getTime() - team.getTeamCreateTime().getTime());
            Integer c = teamCompetitionMapper.selectCount(new QueryWrapper<TeamCompetition>()
                    .eq("team_id", team.getTeamId())
                    .eq("status", 1));
            Integer a = teamActivityMapper.selectCount(new QueryWrapper<TeamActivity>()
                    .eq("team_id", team.getTeamId())
                    .eq("status", 1));
            double star = 0;
            // 100天以上
            if (time > 8642634800L) {
                star += 2;
            } else {
                star += ((double)time / 8642634800L) * 2;
            }
            // 3场竞赛以上
            if (c >= 3) {
                star += 2;
            } else {
                star += ((double)c / 3) * 2;
            }
            // 5场活动以上
            if (a >= 5) {
                star += 1;
            } else {
                star += ((double)a / 5);
            }
            BigDecimal bigDecimal = new BigDecimal(star).setScale(2, RoundingMode.HALF_UP);
            star = bigDecimal.doubleValue();
            // 更新数据库
            team.setTeamStar(star);
            int update = teamMapper.updateById(team);
            if (update != 1) {
                throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
            }
        }
        log.info("完成了队伍星级的更新");
    }

    /**
     * 每隔一小时计算并更新竞赛当前阶段
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateCompetitionCurrentStage() {
        // 当前时间
        Calendar instance = Calendar.getInstance();
        Date date = instance.getTime();
        // 竞赛列表
        List<Competition> competitions = competitionMapper.selectList(new QueryWrapper<>());
        for (Competition competition : competitions) {
            // 反序列化JSON
            List<CompetitionStage> stages = JSON.parseArray(competition.getCompetitionStage(), CompetitionStage.class);
            if (CollectionUtils.isEmpty(stages)) {
                continue;
            }
            int currentIndex = 0;
            for (int i = 0; i < stages.size(); i++) {
                // 当前时间在开始时间之后（考虑到某些步骤不存在EndTime）
                if (date.after(stages.get(i).competitionStageBeginTime)) {
                    currentIndex = i;
                } else {
                    break;
                }
            }
            // 更新数据库的当前步骤字段
            competition.setCompetitionStageNow(currentIndex);
            // 如果currentIndex以及是最后阶段，调整status为0，即该竞赛已结束
            if (currentIndex == stages.size() - 1) {
                competition.setCompetitionStatus(0);
            }
            int update = competitionMapper.updateById(competition);
            if (update != 1) {
                throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
            }
            // TODO: 推送到每参与者的消息盒子
        }
    }

    /**
     * 每隔一小时计算并更新活动当前阶段
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateActivityCurrentStage() {
        // 当前时间
        Calendar instance = Calendar.getInstance();
        Date date = instance.getTime();
        // 竞赛列表
        List<Activity> activities = activityMapper.selectList(new QueryWrapper<>());
        for (Activity activity : activities) {
            // 反序列化JSON
            List<ActivityStage> stages = JSON.parseArray(activity.getActivityStage(), ActivityStage.class);
            if (CollectionUtils.isEmpty(stages)) {
                continue;
            }
            int currentIndex = 0;
            for (int i = 0; i < stages.size(); i++) {
                // 当前时间在开始时间之后（考虑到某些步骤不存在EndTime）
                if (date.after(stages.get(i).activityStageBeginTime)) {
                    currentIndex = i;
                } else {
                    break;
                }
            }
            // 更新数据库的当前步骤字段
            activity.setActivityStageNow(currentIndex);
            // 如果currentIndex以及是最后阶段，调整status为0，即该活动已结束
            if (currentIndex == stages.size() - 1) {
                activity.setActivityStatus(0);
            }
            int update = activityMapper.updateById(activity);
            if (update != 1) {
                throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
            }
            // TODO: 推送到每参与者的消息盒子
        }
    }


}
