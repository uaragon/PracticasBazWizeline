package com.wizeline.DTO;

import com.wizeline.DAO.Builder;
import com.wizeline.enums.AccountType;
import com.wizeline.enums.Country;

public class User {

    public void constructUSer(Builder builder){
        builder.setAccountTipe(AccountType.AHORRO);
        builder.setCountry(Country.MX);
    }

}
