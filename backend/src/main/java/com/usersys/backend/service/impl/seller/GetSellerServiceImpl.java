package com.usersys.backend.service.impl.seller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.usersys.backend.mapper.SellerMapper;
import com.usersys.backend.pojo.Seller;
import com.usersys.backend.pojo.User;
import com.usersys.backend.service.seller.GetSellerService;
import com.usersys.backend.utils.UserUtil;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetSellerServiceImpl implements GetSellerService {

  @Autowired
  private SellerMapper sellerMapper;

  @Override
  public List<Seller> getSeller() {
    User user = UserUtil.getUser();
    int id = user.getId();
    if(Objects.equals(user.getRole(), "Admin")) {
      return sellerMapper.selectList(null);
    }
    else {
      QueryWrapper<Seller> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("sno", id);
      return sellerMapper.selectList(queryWrapper);
    }
  }
}
