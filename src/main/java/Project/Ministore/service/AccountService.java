package Project.Ministore.service;


import Project.Ministore.Entity.AccountEntity;

import java.util.List;

public interface AccountService {
    AccountEntity saveUser(AccountEntity user);
    public AccountEntity getUserByEmail(String email);
    List<AccountEntity> getUsers(String role);
    public Boolean updateAccountStatus(Integer id, Boolean enable);
    public void increaseFailedAttempt(AccountEntity user);
    public void userAccountLock(AccountEntity user);
    public boolean unlockAccountTimeExpired(AccountEntity user);
    public void resetAttempt(int accountId);
    public void updateUserResetToken(String email, String resetToken);
    public AccountEntity getUserByToken(String token);
    public AccountEntity updateUser(AccountEntity user);
}
