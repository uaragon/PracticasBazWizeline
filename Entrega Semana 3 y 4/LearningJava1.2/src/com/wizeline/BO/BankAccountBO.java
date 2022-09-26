package com.wizeline.BO;

import com.wizeline.DTO.BankAccountDTO;

import java.util.List;

public interface BankAccountBO {
    List<BankAccountDTO> getAccounts();

    // BankAccountDTO getAccountDetails(String user);
    List<BankAccountDTO> getAccount();
    BankAccountDTO getAccountDetails(String user,String lastUsage);
}
