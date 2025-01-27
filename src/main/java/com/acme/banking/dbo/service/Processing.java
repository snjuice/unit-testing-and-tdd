package com.acme.banking.dbo.service;

import com.acme.banking.dbo.dao.AccountRepository;
import com.acme.banking.dbo.dao.ClientRepository;
import com.acme.banking.dbo.domain.Account;
import com.acme.banking.dbo.domain.Cash;
import com.acme.banking.dbo.domain.Client;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class Processing {

    private AccountRepository accounts;
    private ClientRepository clients;
    private Cash cash;

    // This can't be overriden by DI
//    private AccountRepository accounts = new AccountRepository();
    // How should we implement Processing, so we can replace real processing
    // with a mock one

    /*
     *  Dependency INJECTION
     */
    public Processing(AccountRepository accounts, ClientRepository clients, Cash cash) {

        // Guard clause
        if (accounts == null) throw new IllegalArgumentException("AccountRepository should not be Null");
        if (clients == null) throw new IllegalArgumentException("ClientRepository should not be Null");
        if (cash == null) throw new IllegalArgumentException("Cash should not be Null");

        this.accounts = accounts;
        this.clients = clients;
        this.cash = cash;

    }

    public Client createClient(String name) {
        int id = clients.initClientId();
        Client client = new Client(id, name);
        clients.save(client);
        return client;

        //return null; //TODO
    }

    public Collection<Account> getAccountsByClientId(int clientId) {
        ///...
        return accounts.getAccountsByClientId(clientId);
        ///...
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        //TODO

        Account from = accounts.getAccountById(fromAccountId);
        Account to = accounts.getAccountById(toAccountId);

        from.setAmount(from.getAmount() - amount);
        to.setAmount(to.getAmount() + amount);

        accounts.save(from);
        accounts.save(to);
    }

    public void cash(double amount, int fromAccountId) {
        //Cash.log(amount, fromAccountId) // refactoring
        cash.log(amount, fromAccountId);
    }
}
