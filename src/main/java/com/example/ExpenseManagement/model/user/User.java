package com.example.ExpenseManagement.model.user;

import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.model.Role;
import com.example.ExpenseManagement.model.Category;
import com.example.ExpenseManagement.service.CategoryService;
import com.example.ExpenseManagement.validations.EmailValidator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private Set<Category> categories = new LinkedHashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> getRole().name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Category> getCategories () {
        return categories;
    }

    public void setCategories (Set<Category> categories) {
        this.categories = categories;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        if ( !EmailValidator.isValidEmail(email) ) {
            throw new RuntimeException("This email is invalid!");
        }
        this.email = email;
    }
}
