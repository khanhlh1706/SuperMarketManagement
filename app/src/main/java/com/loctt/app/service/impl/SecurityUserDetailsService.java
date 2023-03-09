/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.Employee;
import com.loctt.app.model.User;
import com.loctt.app.model.UserDetailsPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    public SecurityUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    public SecurityUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User customer = userService.findByUsername(username);
        Employee employee = employeeService.findByUsername(username);
        if (customer != null) {
            UserDetailsPrincipal user = new UserDetailsPrincipal(customer);
            user.setRole("USER");
            return user;
        }
        if (employee != null) {
            UserDetailsPrincipal user = new UserDetailsPrincipal(employee);
            String role = employee.getRole().getRoleName();
            user.setRole(role);
            System.out.println(user.getAuthorities().toString());
            return user;
        } else {
            throw new UsernameNotFoundException("User not found: " + customer);
        }
    }

}
