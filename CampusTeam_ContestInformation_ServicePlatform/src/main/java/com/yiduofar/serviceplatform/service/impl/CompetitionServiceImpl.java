package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.constant.SystemConstant;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.*;
import com.yiduofar.serviceplatform.service.*;
import com.yiduofar.serviceplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * @author HuiBBao
 * @description 针对表【competition】的数据库操作Service实现
 * @createDate 2023-02-13 14:54:49
 */
@Service
@Slf4j
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {

    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    UserCompetitionMapper userCompetitionMapper;

    @Autowired
    TeamCompetitionMapper teamCompetitionMapper;

    @Autowired
    TeamMapper teamMapper;

    /**
     * 分页
     * 根据关键字模糊查询竞赛
     * @param queryVo
     * @return
     */
    @Override
    public ResponseResult selectCompetitionByKeyWord(QueryVo queryVo) {
        // 封装分页对象
        Page<Competition> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        // 根据关键字模糊查询
        List<Competition> records = competitionMapper.selectPage2(page,
                new QueryWrapper<>().like("competition_name", queryVo.getKeyword()));
        if (CollectionUtils.isEmpty(records)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        Long total = page.getTotal();
        return ResponseResult.success(new CompetitionPageTotal(records, total));
    }

    /**
     * 分页
     * 根据状查询竞赛
     * @param queryVo
     * @return
     */
    @Override
    public ResponseResult selectCompetitionByStatus(QueryVo queryVo) {
        // 封装分页对象
        Page<Competition> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        // 根据状态查询
        List<Competition> records = competitionMapper.selectPage2(page,
                new QueryWrapper<>().eq("competition_status", queryVo.getStatus()));
        if (CollectionUtils.isEmpty(records)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        Long total = page.getTotal();
        return ResponseResult.success(new CompetitionPageTotal(records, total));
    }

    /**
     * 查询轮播图Vo（竞赛封面）
     * @return
     */
    @Override
    public ResponseResult selectSlideShowVo() {
        List<SlideShowVo> list = competitionMapper.selectSlideShowVo();
        if (CollectionUtils.isEmpty(list)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        // 根据id倒叙排序
        list.sort((o1, o2) -> {
            Integer id1 = o1.getCompetitionId();
            Integer id2 = o2.getCompetitionId();
            return id2.compareTo(id1);
        });
        // 轮播图数量上限为8
        List<SlideShowVo> slideShowVos = list.subList(0, Math.min(list.size(), 8));
        return ResponseResult.success(slideShowVos);
    }

    /**
     * 分页
     * 根据用户id、关键字和状态查询竞赛
     * @param queryVo
     * @return
     */
    @Override
    public ResponseResult selectCompetitionByUserIdAndKeyWord(QueryVo queryVo) {
        // 分步骤，分别拿个人参加的竞赛id、队伍（作为队长）参加的竞赛id，再合并竞赛id模糊分页查询
        Set<Integer> ids = new HashSet<>();
        // 拿到用户个人参加的竞赛id
        QueryWrapper<UserCompetition> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", queryVo.getUserId()).eq("status", 1);
        List<UserCompetition> userCompetitions = userCompetitionMapper.selectList(wrapper);
        for (UserCompetition uc : userCompetitions) {
            ids.add(uc.getCompetitionId());
        }
        // 拿到队伍（作为队长）参加的竞赛id
        List<TeamCompetition> teamCompetitions = teamCompetitionMapper.selectTeamCompetitionList(queryVo.getUserId(), 1);
        for (TeamCompetition tc : teamCompetitions) {
            ids.add(tc.getCompetitionId());
        }
        // 如果ids为空，则直接返回
        if (CollectionUtils.isEmpty(ids)) {
            return ResponseResult.success(new CompetitionPageTotal2(new ArrayList<>(), 0L));
        }
        // 封装分页对象
        Page<Competition> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        competitionMapper.selectPageByIdAndKeyword(page, ids, queryVo.getStatus(), queryVo.getKeyword());
        List<Competition> records = page.getRecords();
        Long total = page.getTotal();
        // 若队伍信息不为空，将他们和竞赛绑定加入Vo2的列表
        if (!CollectionUtils.isEmpty(teamCompetitions)) {
            List<CompetitionVo2> records2 = new ArrayList<>();
            for (TeamCompetition tc : teamCompetitions) {
                for (Competition c : records) {
                    if (tc.getCompetitionId() == c.getCompetitionId()) {
                        // 绑定Vo对象
                        CompetitionVo2 competitionVo2 = new CompetitionVo2();
                        // 绑定队伍
                        Team team = teamMapper.selectById(tc.getTeamId());
                        competitionVo2.setTeamId(team.getTeamId());
                        competitionVo2.setTeamName(team.getTeamName());
                        competitionVo2.setTeamLogo(team.getTeamLogo());
                        // 绑定竞赛
                        competitionVo2.setCompetition(c);
                        // 加入list
                        records2.add(competitionVo2);
                    }
                }
            }
            // 加入剩余个人竞赛
            for (UserCompetition uc : userCompetitions) {
                for (Competition c : records) {
                    if (uc.getCompetitionId() == c.getCompetitionId()) {
                        records2.add(new CompetitionVo2(c));
                    }
                }
            }
            CompetitionPageTotal2 competitionPageTotal2 = new CompetitionPageTotal2(records2, total);
            return ResponseResult.success(competitionPageTotal2);
        } else {
            //  若没有以队伍形式参加的竞赛，也要以相同的数据形式返回
            List<CompetitionVo2> records3 = new ArrayList<>();
            for (Competition c : records) {
                records3.add(new CompetitionVo2(c));
            }
            CompetitionPageTotal2 competitionPageTotal2 = new CompetitionPageTotal2(records3, total);
            return ResponseResult.success(competitionPageTotal2);
        }
    }

    /**
     * 发布竞赛（新增）
     * @param competition
     * @return
     */
    @Override
    public ResponseResult insertCompetition(Competition competition) {
        competition.setCompetitionPublishDate(new Date());
        int count = competitionMapper.insert(competition);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success(competition.getCompetitionId());
    }

    /**
     * 根据发布者id查询竞赛
     * @param undertaker3Id
     * @param keyword
     * @return
     */
    @Override
    public ResponseResult selectCompetitionByUndertaker3Id(Integer undertaker3Id, String keyword) {
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_undertaker3_id", undertaker3Id).like("competition_name", keyword);
        List<Competition> competitions = competitionMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(competitions)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        // 根据id倒叙排序
        Collections.sort(competitions, (o1, o2) -> {
            Integer id1 = o1.getCompetitionId();
            Integer id2 = o2.getCompetitionId();
            return id2.compareTo(id1);
        });
        return ResponseResult.success(competitions);
    }

    /**
     * 编辑竞赛（更新）
     * @param competition
     * @return
     */
    @Override
    public ResponseResult updateCompetitionById(Competition competition) {
        int count = competitionMapper.updateById(competition);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.EDIT_ERROR);
        }
        return ResponseResult.success();
    }

    @Autowired
    CompetitionFileMapper competitionFileMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserCompetitionService userCompetitionService;

    @Autowired
    TeamCompetitionService teamCompetitionService;

    /**
     * 根据id查询竞赛Vo
     * @param competitionId
     * @return
     */
    @Override
    public ResponseResult selectCompetitionVoById(Integer competitionId) {
        Competition competition = competitionMapper.selectById(competitionId);
        if (Objects.isNull(competition)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        // 装填基本信息
        CompetitionVo competitionVo = new CompetitionVo(competition);
        // 根据发布者id查出用户信息
        User user = userMapper.selectById(competition.getCompetitionUndertaker3Id());
        // 根据竞赛id查出该竞赛的附件列表 附件文件类型 0
        QueryWrapper<CompetitionFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_id", competition.getCompetitionId()).eq("competition_file_type", 0);
        List<CompetitionFile> competitionFiles0 = competitionFileMapper.selectList(queryWrapper);
        // 根据竞赛id查出该竞赛的成绩列表 成绩文件类型 1
        QueryWrapper<CompetitionFile> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("competition_id", competition.getCompetitionId()).eq("competition_file_type", 1);
        List<CompetitionFile> competitionFiles1 = competitionFileMapper.selectList(queryWrapper2);
        if (competition.getIsTeamUp() == 0) {
            // 根据竞赛id查出参与用户列表并装填 0申请未通过 1参与
            QueryWrapper<UserCompetition> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("uc.competition_id", competition.getCompetitionId()).eq("uc.status", 1);
            List<User> users = userCompetitionMapper.selectUserListByCompetitionIdAndStatus(queryWrapper3);
            // 过滤密码
            for (User u : users) {
                u.setPassword(null);
            }
            competitionVo.setJoinedUserList(users);
        }
        if (competition.getIsTeamUp() == 1) {
            // 根据竞赛id查出参与队伍列表并装填
            QueryWrapper<UserCompetition> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("tc.competition_id", competitionId).eq("tc.status", 1);
            List<Team> teams = teamCompetitionMapper.selectTeamListByCompetitionIdAndStatus(queryWrapper4);
            competitionVo.setJoinedTeamList(teams);
        }
        // 装填
        competitionVo.setCompetitionUndertaker3(user);
        competitionVo.setCompetitionAttachmentList(competitionFiles0);
        competitionVo.setCompetitionResultList(competitionFiles1);
        return ResponseResult.success(competitionVo);
    }

    /**
     * 根据id查询竞赛
     * @param competitionId
     * @return
     */
    @Override
    public ResponseResult selectCompetitionById(Integer competitionId) {
        Competition competition = competitionMapper.selectById(competitionId);
        if (Objects.isNull(competition)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(competition);
    }

    /**
     * 下架竞赛（删除）
     * @param competitionId
     * @return
     */
    @Override
    public ResponseResult deleteCompetitionById(Integer competitionId) {
        Competition competition = competitionMapper.selectById(competitionId);
        // 删除竞赛
        int count = competitionMapper.deleteById(competitionId);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }


}





