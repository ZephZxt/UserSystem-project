package com.usersys.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usersys.backend.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

}
