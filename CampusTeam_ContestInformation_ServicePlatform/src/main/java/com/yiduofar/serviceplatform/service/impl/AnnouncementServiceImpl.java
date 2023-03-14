package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.Announcement;
import com.yiduofar.serviceplatform.domain.Message;
import com.yiduofar.serviceplatform.domain.News;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.service.AnnouncementService;
import com.yiduofar.serviceplatform.mapper.AnnouncementMapper;
import com.yiduofar.serviceplatform.socketio.NettySocketEventHandler;
import com.yiduofar.serviceplatform.vo.AnnouncementPageTotal;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.toNewsOrAnnouncementUrl;

/**
* @author HuiBBao
* @description 针对表【announcement】的数据库操作Service实现
* @createDate 2023-03-10 22:29:42
*/
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
    implements AnnouncementService{

    @Autowired
    AnnouncementMapper announcementMapper;

    /**
     * 获取所有公告
     * @return
     */
    @Override
    public ResponseResult selectAll() {
        List<Announcement> announcements = announcementMapper.selectList(new QueryWrapper<Announcement>()
                .orderByDesc("announcement_publish_time"));
        if (CollectionUtils.isEmpty(announcements)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(announcements);
    }

    @Override
    public ResponseResult selectPage(QueryVo queryVo) {
        // 封装分页对象
        Page page = new Page(queryVo.getCurrent(), queryVo.getSize());
        // 分页查询
        List<Announcement> announcements = announcementMapper.selectPage2(page);
        if (CollectionUtils.isEmpty(announcements)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(new AnnouncementPageTotal(announcements, page.getTotal()));
    }


    /**
     * 根据id查询公告
     * @param announcementId
     * @return
     */
    @Override
    public ResponseResult selectById(Integer announcementId) {
        Announcement announcement = announcementMapper.selectById(announcementId);
        if (Objects.isNull(announcement)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(announcement);
    }

    @Override
    public ResponseResult insertOne(Announcement announcement) {
        announcement.setAnnouncementPublishTime(new Date());
        int insert = announcementMapper.insert(announcement);
        if (Objects.isNull(insert)) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        Message message = new Message();
        message.setMessageType(4);
        message.setMessageObjectType(3);
        message.setMessageTime(new Date());
        message.setMessageContent(announcement.getAnnouncementTitle());
        message.setMessageUrl(toNewsOrAnnouncementUrl(announcement.getAnnouncementId(), "announcement"));
        // socketio 广播公告
        NettySocketEventHandler.sendBroadcast(message);
        return ResponseResult.success(announcement.getAnnouncementId());
    }

    /**
     * 删除公告
     * @param announcement
     * @return
     */
    @Override
    public ResponseResult deleteOne(Announcement announcement) {
        int delete = announcementMapper.deleteById(announcement.getAnnouncementId());
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }
}




