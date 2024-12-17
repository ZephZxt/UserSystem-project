package com.usersys.backend.controller.seller;

import com.usersys.backend.service.seller.UpdateSellerService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateSellerController {

  @Autowired
  private UpdateSellerService updateSellerService;

  @PostMapping("/seller/update/")
  public Map<String, String> updateSeller(@RequestParam Map<String, String> data) {
    return updateSellerService.updateSeller(data);
  }

}
