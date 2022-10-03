package com.wizeline.DAO;

import com.wizeline.DTO.Usuario;
import com.wizeline.enums.AccountType;
import com.wizeline.enums.Country;

public class UserBuilder implements Builder {
    private AccountType accountType;
    private Country country;


    public void setAccountTipe(AccountType accountType) {
        this.accountType = accountType;

    }

    public void setCountry(Country country) {
        this.country = country;

    }

    public Usuario getResult() {
        return new Usuario(accountType, country);
    }


}
