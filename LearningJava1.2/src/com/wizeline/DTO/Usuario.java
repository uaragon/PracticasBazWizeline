package com.wizeline.DTO;

import com.wizeline.enums.AccountType;
import com.wizeline.enums.Country;

public class Usuario {
    private final AccountType accountType;
    private final Country country;

    public Usuario(AccountType accountType,Country country) {
        this.accountType = accountType;
        this.country = country;
    }
    public  AccountType getAccountType(){
        return accountType;
    }
    public  Country getCountry(){
        return  country;
    }

}
