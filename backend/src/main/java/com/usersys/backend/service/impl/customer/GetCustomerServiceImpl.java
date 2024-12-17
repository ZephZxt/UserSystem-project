package com.usersys.backend.service.impl.customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.usersys.backend.mapper.CustomerMapper;
import com.usersys.backend.pojo.Customer;
import com.usersys.backend.pojo.User;
import com.usersys.backend.service.customer.GetCustomerService;
import com.usersys.backend.utils.UserUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerServiceImpl implements GetCustomerService {

  @Autowired
  private CustomerMapper customerMapper;

  @Override
  public List<Customer> getCustomer() {
    User user = UserUtil.getUser();
    if(user.getRole().equals("Admin")) {
      return customerMapper.selectList(null);
    }
    else {
      QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("cno", user.getId());
      return customerMapper.selectList(queryWrapper);
    }
  }
}
