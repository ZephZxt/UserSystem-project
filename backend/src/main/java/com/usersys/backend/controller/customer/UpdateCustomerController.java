package com.usersys.backend.controller.customer;

import com.usersys.backend.service.customer.UpdateCustomerService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateCustomerController {

  @Autowired
  private UpdateCustomerService updateCustomerService;

  @PostMapping("/customer/update/")
  public Map<String, String> updateCustomer(@RequestParam Map<String, String> data) {
    return updateCustomerService.updateCustomer(data);
  }

}
