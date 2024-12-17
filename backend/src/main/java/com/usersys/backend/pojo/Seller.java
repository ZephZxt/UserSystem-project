package com.usersys.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
  @TableId(type = IdType.INPUT)
  private Integer sno;
  private String avatar;
  private String name;
  private String gender;
  private String birth;
  private String email;
  private String number;
  private String description;

}
