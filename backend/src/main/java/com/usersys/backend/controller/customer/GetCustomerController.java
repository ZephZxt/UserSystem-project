package com.usersys.backend.controller.customer;

import com.usersys.backend.pojo.Customer;
import com.usersys.backend.service.customer.GetCustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCustomerController {

  @Autowired
  private GetCustomerService getCustomerService;

  @GetMapping("/customer/get/")
  public List<Customer> getCustomer() {
    return getCustomerService.getCustomer();
  }

}
