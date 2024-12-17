package com.usersys.backend.service.impl.customer;

import com.usersys.backend.mapper.CustomerMapper;
import com.usersys.backend.pojo.Customer;
import com.usersys.backend.service.customer.UpdateCustomerService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomerServiceImpl implements UpdateCustomerService {

  @Autowired
  private CustomerMapper customerMapper;

  @Override
  public Map<String, String> updateCustomer(Map<String, String> data) {
    Map<String, String> map = new HashMap<>();
    int cno = Integer.parseInt(data.get("cno"));
    String avatar = data.get("avatar");
    String name = data.get("name");
    String gender = data.get("gender");
    String birth = data.get("birth");
    String likes = data.get("likes");
    String address = data.get("address");
    String number = data.get("number");
    String email = data.get("email");

    if(customerMapper.selectById(cno) == null) {
      Customer customer = new Customer(
          cno,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null
      );
      customerMapper.insert(customer);
    }
    Customer customer_new = new Customer(
        cno,
        avatar,
        name,
        gender,
        birth,
        likes,
        address,
        number,
        email
        );
    customerMapper.updateById(customer_new);
    map.put("error_message", "success");
    return map;

  }



}
