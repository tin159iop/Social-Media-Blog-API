package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    
    }

    public Account register(Account account) {
        if (account.getUsername().length() == 0) {
            return null;
        }
        if (account.getPassword().length() < 4) {
            return null;
        }

        return this.accountDAO.register(account);
    }

    public Account login(Account account) {
        if (account.getUsername().length() == 0) {
            return null;
        }
        if (account.getPassword().length() < 4) {
            return null;
        }

        return this.accountDAO.login(account);
    }
}
