package com.usersys.backend.service.impl.seller;

import com.usersys.backend.mapper.SellerMapper;
import com.usersys.backend.pojo.Seller;
import com.usersys.backend.service.seller.UpdateSellerService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateSellerServiceImpl implements UpdateSellerService {

  @Autowired
  private SellerMapper sellerMapper;

  @Override
  public Map<String, String> updateSeller(Map<String, String> data) {

    Map<String, String> map  = new HashMap<>();
    String name = data.get("name");
    String avatar = data.get("avatar");
    String gender = data.get("gender");
    String birth = data.get("birth");
    String email = data.get("email");
    String number = data.get("number");
    String description = data.get("description");

    int sno = Integer.parseInt(data.get("sno"));
    if(sellerMapper.selectById(sno) == null) {
      Seller seller = new Seller(
          sno,
          null,
          null,
          null,
          null,
          null,
          null,
          null
      );
      sellerMapper.insert(seller);
    }

    Seller seller_new = new Seller(
        sno,
        avatar,
        name,
        gender,
        birth,
        email,
        number,
        description
    );
    sellerMapper.updateById(seller_new);
    map.put("error_message", "success");
    return map;
  }
}
