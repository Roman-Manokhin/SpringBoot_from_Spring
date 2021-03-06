package ru.rmanokhin.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rmanokhin.crud.DAO.UserSecurityDAO;
import ru.rmanokhin.crud.model.UserSecurity;

import java.util.List;

@Service
public class UserSecurityServiceImpl implements UserSecurityService{

    private final UserSecurityDAO userSecurityDAO;

    @Autowired
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public UserSecurityServiceImpl(UserSecurityDAO userSecurityDAO) {
        this.userSecurityDAO = userSecurityDAO;
    }

    @Override
    public UserSecurity getUserByLogin(String name) {
        return userSecurityDAO.findUserByName(name);
    }

    @Override
    public void addUser(UserSecurity userSecurity) {
        userSecurity.setUserPassword(getPasswordEncoder().encode(userSecurity.getUserPassword()));
        userSecurityDAO.save(userSecurity);
    }

    @Override
    public List<UserSecurity> getAllUsers() {
        return userSecurityDAO.findAll();
    }

    @Override
    public UserSecurity getUserById(long id) {
        return userSecurityDAO.getById(id);
    }

    @Override
    public void deleteUser(long id) {
        userSecurityDAO.deleteById(id);
    }

    @Override
    public void updateUser(UserSecurity userSecurity) {
        if (!userSecurity.getUserPassword().equals(getUserById(userSecurity.getId()).getUserPassword())) {
            userSecurity.setUserPassword(getPasswordEncoder().encode(userSecurity.getUserPassword()));
        }
        userSecurityDAO.save(userSecurity);
    }
}
