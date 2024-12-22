package com.example.ExpenseManagement.model;

public interface UserBuilder {
    public void buildUsername(String username);
    public void buildPassword(String password);
    public void buildRole(Role role);
    public User buildUser();
}
