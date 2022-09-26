package com.wizeline.DAO;

import com.wizeline.BO.BankAccountBO;
import com.wizeline.DTO.BankAccountDTO;
import com.wizeline.enums.Country;

import java.util.ArrayList;
import java.util.List;

import static com.wizeline.utils.Utils.*;

public class BankAccountBOImpl implements BankAccountBO {
    @Override
    public List<BankAccountDTO> getAccounts() {
        // Definicion de lista con la información de las cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(buildBankAccount("user3@wizeline.com", true, Country.MX, "08-09-2021"));
        accountDTOList.add(buildBankAccount("user1@wizeline.com", false, Country.FR, "07-09-2021"));
        accountDTOList.add(buildBankAccount("user2@wizeline.com", false, Country.US, "16-09-2021"));
        return accountDTOList;
    }

    @Override
    public List<BankAccountDTO> getAccount() {
        return null;
    }

    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        return buildBankAccount(user, true, Country.MX, lastUsage);
    }

    // Creación de tipo de dato BankAccount con la ayuda de la clase Utils.java
    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, String lastUsage) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        //bankAccountDTO.setAccountNumber(randomAcountNumber());
        bankAccountDTO.setAccountName("Dummy Account ".concat(randomInt()));
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(randomBalance());
        bankAccountDTO.setAccountType(pickRandomAccountType());
        bankAccountDTO.setCountry(getCountry(country));
        bankAccountDTO.setLastUsage(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }
}