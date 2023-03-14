package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.*;
import com.yiduofar.serviceplatform.service.MessageService;
import com.yiduofar.serviceplatform.service.SystemService;
import com.yiduofar.serviceplatform.socketio.MessageInfoStructure;
import com.yiduofar.serviceplatform.socketio.NettySocketEventHandler;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import com.yiduofar.serviceplatform.vo.SystemDomainVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_ARTICLE;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_BACKSTAGE;

/**
 * @author HuiBBao
 * @create 2023/3/10 11:16
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleCommentMapper articleCommentMapper;

    @Autowired
    MessageService messageService;

    /**
     * 全平台删除
     *
     * @param systemDomainVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult manageDelete(SystemDomainVo systemDomainVo) {
        // 封禁用户
        if (systemDomainVo.getClazzType().equals("user")) {
            User user = userMapper.selectById(systemDomainVo.getId());
            int update = userMapper.update(user, new UpdateWrapper<User>()
                    .setSql("status = 2")
                    .eq("id", user.getId()));
            if (update != 1) {
                throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
            }
            // 发消息
            messageService.sendMessageToUser(user.getId(), "账号被封禁，请联系管理员", "", 0);
            // 强制下线
            NettySocketEventHandler.sendToUser("login:" + user.getPhone(), new MessageInfoStructure("logout_event", "封禁"));
            return ResponseResult.success();
        }
        // 其余实体
        int delete = 0;
        switch (systemDomainVo.getClazzType()) {
            case "competition": {
                Competition competition = competitionMapper.selectById(systemDomainVo.getId());
                delete = competitionMapper.deleteById(competition.getCompetitionId());
                messageService.sendMessageToUser(competition.getCompetitionUndertaker3Id(),
                        "您的竞赛: " + competition.getCompetitionName() + "含有非法内容，已被平台下架",
                        TO_BACKSTAGE + 2, 0);
                break;
            }
            case "activity": {
                Activity activity = activityMapper.selectById(systemDomainVo.getId());
                delete = activityMapper.deleteById(activity.getActivityId());
                messageService.sendMessageToUser(activity.getActivityInitiatorId(),
                        "您的活动: " + activity.getActivityName() + "含有非法内容，已被平台下架",
                        TO_BACKSTAGE + 5, 0);
                break;
            }
            case "team": {
                Team team = teamMapper.selectById(systemDomainVo.getId());
                delete = teamMapper.deleteById(team.getTeamId());
                messageService.sendMessageToUser(team.getTeamCaptionId(),
                        "您的队伍: " + team.getTeamName() + "含有非法行为，已被平台下架",
                        TO_BACKSTAGE + 7, 0);
                break;
            }
            case "article": {
                Article article = articleMapper.selectById(systemDomainVo.getId());
                delete = articleMapper.deleteById(article.getArticleId());
                messageService.sendMessageToUser(article.getArticleAuthorId(),
                        "您的帖子: " + article.getArticleTitle() + "含有非法内容，已被平台下架",
                        TO_BACKSTAGE + 9, 0);
                break;
            }
            case "comment": {
                ArticleComment comment = articleCommentMapper.selectById(systemDomainVo.getId());
                Article article = articleMapper.selectById(comment.getToArticleId());
                delete = articleCommentMapper.deleteById(comment.getCommentId());
                messageService.sendMessageToUser(comment.getFromUserId(),
                        "您在帖子: " + article.getArticleTitle() + "的评论含敏感内容，已被平台删除",
                        TO_ARTICLE + article.getArticleId(), 0);
                break;
            }
        }
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 全平台查询
     *
     * @param systemDomainVo
     * @return
     */
    @Override
    public ResponseResult manageSelect(SystemDomainVo systemDomainVo) {
        List<SystemDomainVo> list = new ArrayList<>();
        switch (systemDomainVo.getClazzType()) {
            case "user": {
                List<User> users = userMapper.selectList(new QueryWrapper<User>()
                        .like("user_name", systemDomainVo.getKeyword()));
                for (User user : users) {
                    SystemDomainVo systemDomainVo2 = new SystemDomainVo(user.getId(), user.getUserName(), user.getIntro(), "user");
                    systemDomainVo2.setUserStatus(user.getStatus());
                    list.add(systemDomainVo2);
                }
                break;
            }
            case "team": {
                List<Team> teams = teamMapper.selectList(new QueryWrapper<Team>()
                        .like("team_name", systemDomainVo.getKeyword()));
                for (Team team : teams) {
                    list.add(new SystemDomainVo(team.getTeamId(), team.getTeamName(), team.getTeamIntro(), "team"));
                }
                break;
            }
            case "competition": {
                List<Competition> competitions = competitionMapper.selectList(new QueryWrapper<Competition>()
                        .like("competition_name", systemDomainVo.getKeyword()));
                for (Competition competition : competitions) {
                    list.add(new SystemDomainVo(competition.getCompetitionId(), competition.getCompetitionName(), competition.getCompetitionPublishDate().toString(), "competition"));
                }
                break;
            }
            case "activity": {
                List<Activity> activities = activityMapper.selectList(new QueryWrapper<Activity>()
                        .like("activity_name", systemDomainVo.getKeyword()));
                for (Activity activity : activities) {
                    list.add(new SystemDomainVo(activity.getActivityId(), activity.getActivityName(), activity.getActivityIntro(), "activity"));
                }
                break;
            }
            case "article": {
                List<Article> articles = articleMapper.selectList(new QueryWrapper<Article>()
                        .like("article_title", systemDomainVo.getKeyword()));
                for (Article article : articles) {
                    list.add(new SystemDomainVo(article.getArticleId(), article.getArticleTitle(), article.getArticleDigest(), "article"));
                }
                break;
            }
            case "comment": {
                List<ArticleComment> comments = articleCommentMapper.selectList(new QueryWrapper<ArticleComment>()
                        .like("comment_content", systemDomainVo.getKeyword()));
                for (ArticleComment comment : comments) {
                    SystemDomainVo systemDomainVo2 = new SystemDomainVo(comment.getCommentId(), comment.getCommentContent(), "", "comment");
                    systemDomainVo2.setToArticleId(comment.getToArticleId());
                    list.add(systemDomainVo2);
                }
                break;
            }
        }
        return ResponseResult.success(list);
    }


}
