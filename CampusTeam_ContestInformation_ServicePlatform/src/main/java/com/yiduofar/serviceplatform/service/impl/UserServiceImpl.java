package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.constant.SystemConstant;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.*;
import com.yiduofar.serviceplatform.service.UserService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import com.yiduofar.serviceplatform.vo.SystemDomainVo;
import com.yiduofar.serviceplatform.vo.UserDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author HuiBBao
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-02-07 13:32:23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserTeamMapper userTeamMapper;

    @Autowired
    UserCompetitionMapper userCompetitionMapper;

    @Autowired
    UserActivityMapper userActivityMapper;

    @Autowired
    ArticleMapper articleMapper;

    /**
     * 根据手机号查询用户
     *
     * @param phone
     * @return
     */
    @Override
    public ResponseResult selectOneByPhone(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new GlobalException(ResponseResultEnum.NULL_USER_ERROR);
        }
        user.setPassword(null);
        return ResponseResult.success(user);
    }

    /**
     * 根据id编辑用户信息
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult updateOneById(User user) {
        int count = userMapper.updateByOneById(user);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 根据用户id查询用户名
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult selectUserNameById(Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new GlobalException(ResponseResultEnum.NULL_USER_ERROR);
        }
        return ResponseResult.success(user.getUserName());
    }

    /**
     * 根据用户id上传头像图片
     *
     * @param file
     * @param id
     * @return
     */
    @Override
    public ResponseResult uploadHeadImgById(MultipartFile file, Integer id) throws IOException {
        /**
         * 上传图片
         */
        if (file == null) {
            throw new GlobalException(ResponseResultEnum.FILE_ERROR);
        }
        // 存储路径
        String filePath = SystemConstant.USERHEAD_UPLOAD_PATH_REAL;
        File image = new File(filePath + id + SystemConstant.USERHEAD_SUFFIX);
        if (!image.getParentFile().exists()) {
            image.getParentFile().mkdirs();
        }
        file.transferTo(image);
        /**
         * 存储数据库
         */
        String filePath2 = SystemConstant.UPLOAD_PATH_WEB + id + SystemConstant.USERHEAD_SUFFIX;
        int count = userMapper.updateUserHeadById(id, filePath2);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success(filePath2);
    }

    /**
     * 查询用户详情Vo
     */
    @Override
    public ResponseResult selectUserDetailVo(Integer userId) {
        UserDetailVo userDetailVo = new UserDetailVo();
        // 用户
        User user = userMapper.selectById(userId);
        userDetailVo.setUser(user);
        // 队伍
        List<Team> teamList = userTeamMapper.selectTeamListByUserId(new QueryWrapper<UserTeam>()
                .eq("ut.user_id", userId)
                .eq("ut.status", 1));
        userDetailVo.setTeamList(teamList);
        // 竞赛
        List<Competition> competitionList = userCompetitionMapper.selectCompetitionListByUserIdAndStatus(userId, 1);
        userDetailVo.setCompetitionList(competitionList);
        // 活动
        List<Activity> activityList = userActivityMapper.selectActivityListByUserIdAndStatus(userId, 1);
        userDetailVo.setActivityList(activityList);
        // 帖子
        List<Article> articleList = articleMapper.selectList(new QueryWrapper<Article>()
                .eq("article_author_id", userId));
        userDetailVo.setArticleList(articleList);

        return ResponseResult.success(userDetailVo);
    }

    /**
     * 解封用户
     *
     * @return
     */
    @Override
    public ResponseResult unseal(SystemDomainVo systemDomainVo) {
        User user = userMapper.selectById(systemDomainVo.getId());
        int update = userMapper.update(user, new UpdateWrapper<User>()
                .setSql("status = 1")
                .eq("id", systemDomainVo.getId()));
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

}




