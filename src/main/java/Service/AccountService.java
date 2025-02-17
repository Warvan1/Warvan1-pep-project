package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if(account.getUsername() == null || account.getUsername() == "" || account.password.length() < 4){
            return null;
        }
        if(accountDAO.getAccountWithUsername(account.getUsername()) != null){
            return null;
        }
        return accountDAO.insertNewAccount(account);
    }
    
    public Account login(Account account){
        return accountDAO.getAccountWithUsernameAndPassword(account);
    }
}
