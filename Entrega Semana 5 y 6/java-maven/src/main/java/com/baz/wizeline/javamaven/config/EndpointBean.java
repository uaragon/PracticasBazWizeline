package com.baz.wizeline.javamaven.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties(prefix = "consumers")
public class EndpointBean {

    private String login;
    private String createUser;
    private String createUsers;
    private String userAccount;
    private String accounts;
    private String accountsGroupByType;

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public String getCreateUser() {

        return createUser;
    }

    public void setCreateUser(String createUser) {

        this.createUser = createUser;
    }

    public String getCreateUsers() {

        return createUsers;
    }

    public void setCreateUsers(String createUsers) {

        this.createUsers = createUsers;
    }

    public String getUserAccount() {

        return userAccount;
    }

    public void setUserAccount(String userAccount) {

        this.userAccount = userAccount;
    }

    public String getAccounts() {

        return accounts;
    }

    public void setAccounts(String accounts) {

        this.accounts = accounts;
    }

    public String getAccountsGroupByType() {

        return accountsGroupByType;
    }

    public void setAccountsGroupByType(String accountsGroupByType) {

        this.accountsGroupByType = accountsGroupByType;
    }

}