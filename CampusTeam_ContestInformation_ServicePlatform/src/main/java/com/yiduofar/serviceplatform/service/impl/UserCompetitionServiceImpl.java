package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.*;
import com.yiduofar.serviceplatform.service.MessageService;
import com.yiduofar.serviceplatform.service.UserCompetitionService;
import com.yiduofar.serviceplatform.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageTip.USER_COMPETITION_DELETE_TIP;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_BACKSTAGE;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_COMPETITION;

/**
 * @author HuiBBao
 * @description 针对表【user_competition】的数据库操作Service实现
 * @createDate 2023-02-14 15:30:09
 */
@Service
public class UserCompetitionServiceImpl extends ServiceImpl<UserCompetitionMapper, UserCompetition>
        implements UserCompetitionService {

    @Autowired
    UserCompetitionMapper userCompetitionMapper;

    @Autowired
    MessageService messageService;

    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TeamCompetitionMapper teamCompetitionMapper;

    @Autowired
    TeamActivityMapper teamActivityMapper;

    /**
     * 删除一条用户竞赛关系
     * userCompetitionDeleteVo.type为操作类型 剔除3 婉拒2 退出1
     *
     * @param userCompetitionDeleteVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteUserCompetition(UserCompetitionDeleteVo userCompetitionDeleteVo) {
        QueryWrapper<UserCompetition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userCompetitionDeleteVo.getUserId())
                .eq("competition_id", userCompetitionDeleteVo.getCompetitionId())
                .eq("status", userCompetitionDeleteVo.getStatus());
        int delete = userCompetitionMapper.delete(queryWrapper);
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        Competition competition = competitionMapper.selectById(userCompetitionDeleteVo.getCompetitionId());
        // 报名人数减1
        if (competition.getCompetitionJoinedNum() > 0) {
            UpdateWrapper<Competition> updateWrapper = new UpdateWrapper<>();
            updateWrapper.setSql("competition_joined_num = competition_joined_num - 1")
                    .eq("competition_id", competition.getCompetitionId());
            int update = competitionMapper.update(competition, updateWrapper);
            if (update != 1) {
                throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
            }
        }
        // 发消息
        String content = USER_COMPETITION_DELETE_TIP[userCompetitionDeleteVo.getType() - 1] + competition.getCompetitionName();
        messageService.sendMessageToUser(userCompetitionDeleteVo.getUserId(), content, TO_BACKSTAGE + 2, 0);
        return ResponseResult.success();
    }

    /**
     * 根据竞赛id、状态查询竞赛的 参与者 或 申请者列表（个人）
     *
     * @param competitionId
     * @param status
     * @return
     */
    @Override
    public ResponseResult selectUserListByCompetitionIdAndStatus(Integer competitionId, Integer status) {
        QueryWrapper<UserCompetition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uc.competition_id", competitionId)
                .eq("uc.status", status);
        List<User> users = userCompetitionMapper.selectUserListByCompetitionIdAndStatus(queryWrapper);
        if (CollectionUtils.isEmpty(users)) {
            throw new GlobalException(ResponseResultEnum.NULL_USER_LIST_ERROR);
        }
        return ResponseResult.success(users);
    }

    /**
     * 更新用户竞赛关系（同意申请）
     *
     * @param userCompetition
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateStatusById(UserCompetition userCompetition) {
        if (userCompetition.getStatus() != 1) {
            throw new GlobalException(ResponseResultEnum.ERROR);
        }
        Competition competition = competitionMapper.selectById(userCompetition.getCompetitionId());
        // 增加参与数量
        UpdateWrapper<Competition> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("competition_joined_num = competition_joined_num + 1")
                .eq("competition_id", competition.getCompetitionId());
        int update = competitionMapper.update(competition, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 更新数据库
        int count = userCompetitionMapper.updateStatusById(userCompetition);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 给用户发送消息
        String content = "申请通过: " + competition.getCompetitionName();
        messageService.sendMessageToUser(userCompetition.getUserId(), content, TO_BACKSTAGE + 2, 0);
        return ResponseResult.success();
    }

    /**
     * 新增用户竞赛关系（申请）
     *
     * @param userCompetition
     * @return
     */
    @Override
    public ResponseResult insertOne(UserCompetition userCompetition) {
        User user = userMapper.selectById(userCompetition.getUserId());
        Competition competition = competitionMapper.selectById(userCompetition.getCompetitionId());
        // 已经结束
        if (competition.getCompetitionStatus() == 0) {
            throw new GlobalException(ResponseResultEnum.COMPETITION_END_ERROR);
        }
        // 自己不能加入
        if (user.getId() == competition.getCompetitionUndertaker3Id()) {
            throw new GlobalException(ResponseResultEnum.USER_INITIATOR_JOIN_ERROR);
        }
        // 受众
        if (competition.getCompetitionAudience() == 1) {
            if (!competition.getCompetitionAudienceName().equals(user.getCollege())) {
                throw new GlobalException(ResponseResultEnum.USER_AUDIENCE_ERROR);
            }
        }
        if (competition.getCompetitionAudience() == 2) {
            if (!competition.getCompetitionAudienceName().equals(user.getSpecialty())) {
                throw new GlobalException(ResponseResultEnum.USER_SPECIALTY_ERROR);
            }
        }
        // 申请
        int count = userCompetitionMapper.insert(userCompetition);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        if (!Objects.isNull(user) && !Objects.isNull(competition)) {
            // 给自己发消息 通知 type 0
            String content = "提交了待处理的申请: " + competition.getCompetitionName();
            messageService.sendMessageToUser(user.getId(), content, TO_COMPETITION + competition.getCompetitionId(), 0);
            // 给负责的老师发消息 申请 type 1
            String content2 = user.getRealName() + " 申请参加" + competition.getCompetitionName();
            messageService.sendMessageToUser(competition.getCompetitionUndertaker3Id(), content2, TO_BACKSTAGE + 4, 1);
        }
        return ResponseResult.success();
    }

    /**
     * 查询一条用户竞赛关系
     *
     * @param userCompetition
     * @return
     */
    @Override
    public ResponseResult selectOne(UserCompetition userCompetition) {
        QueryWrapper<UserCompetition> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userCompetition.getUserId())
                .eq("competition_id", userCompetition.getCompetitionId());
        UserCompetition object = userCompetitionMapper.selectOne(wrapper);
        // 不检查直接返回 交给前端判断申请状态和已参赛状态
        return ResponseResult.success(object);
    }


}




