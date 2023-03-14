package com.yiduofar.serviceplatform.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yiduofar.serviceplatform.domain.Competition;
import com.yiduofar.serviceplatform.domain.CompetitionFile;
import com.yiduofar.serviceplatform.domain.Team;
import com.yiduofar.serviceplatform.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/2/22 1:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompetitionVo extends Competition {

    /**
     * 负责人
     */
    private User competitionUndertaker3;

    /**
     * 附件
     */
    private List<CompetitionFile> competitionAttachmentList;

    /**
     * 成绩
     */
    private List<CompetitionFile> competitionResultList;

    /**
     * 参赛者
     */
    private List<User> joinedUserList;

    /**
     * 参赛队伍
     */
    private List<Team> joinedTeamList;

    public CompetitionVo(Competition competition){
        this.setCompetitionId(competition.getCompetitionId());
        this.setCompetitionName(competition.getCompetitionName());
        this.setCompetitionJoinedNum(competition.getCompetitionJoinedNum());
        this.setCompetitionType(competition.getCompetitionType());
        this.setIsTeamUp(competition.getIsTeamUp());
        this.setSignUpMinTeamMemberNum(competition.getSignUpMinTeamMemberNum());
        this.setSignUpMaxTeamMemberNum(competition.getSignUpMaxTeamMemberNum());
        this.setCompetitionAudience(competition.getCompetitionAudience());
        this.setCompetitionAudienceName(competition.getCompetitionAudienceName());
        this.setCompetitionStageNow(competition.getCompetitionStageNow());
        this.setCompetitionStage(competition.getCompetitionStage());
        this.setCompetitionUndertaker(competition.getCompetitionUndertaker());
        this.setCompetitionUndertaker2(competition.getCompetitionUndertaker2());
        this.setCompetitionUndertaker3Id(competition.getCompetitionUndertaker3Id());
        this.setCompetitionAdvisorList(competition.getCompetitionAdvisorList());
        this.setCompetitionInformationList(competition.getCompetitionInformationList());
        this.setCompetitionBeginTime(competition.getCompetitionBeginTime());
        this.setCompetitionEndTime(competition.getCompetitionEndTime());
        this.setCompetitionExtraPoints(competition.getCompetitionExtraPoints());
        this.setCompetitionStatus(competition.getCompetitionStatus());
        this.setCompetitionFileUploadIsOpen(competition.getCompetitionFileUploadIsOpen());
        this.setCompetitionJoinedIsOpen(competition.getCompetitionJoinedIsOpen());
        this.setCompetitionPublishDate(competition.getCompetitionPublishDate());
        this.setCompetitionCover(competition.getCompetitionCover());
    }


}
