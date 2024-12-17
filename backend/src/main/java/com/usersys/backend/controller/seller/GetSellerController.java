package com.usersys.backend.controller.seller;

import com.usersys.backend.pojo.Seller;
import com.usersys.backend.service.seller.GetSellerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetSellerController {

  @Autowired
  private GetSellerService getSellerService;

  @GetMapping("/seller/get/")
  public List<Seller> getSeller() {
    return getSellerService.getSeller();
  }

}
