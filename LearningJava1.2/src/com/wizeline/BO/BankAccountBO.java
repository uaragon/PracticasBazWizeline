package com.wizeline.BO;

import com.wizeline.DTO.BankAccountDTO;

import java.util.List;

public interface BankAccountBO {
    List<BankAccountDTO> getAccounts();
    List<BankAccountDTO> getAccount();
    BankAccountDTO getAccountDetails(String user,String lastUsage);
}
