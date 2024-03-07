package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account)
    {
        return accountDAO.registerUser(account);
    }

    public Account loginAccount(Account account)
    {
        return accountDAO.loginUser(account);
    }
    
}
