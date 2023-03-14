package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author HuiBBao
 * @create 2023/2/12 17:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserHeadFileVo {

    String id;

    MultipartFile file;

}
