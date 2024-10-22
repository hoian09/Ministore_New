package Project.Ministore.service.impl;
import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.repository.AccountRepository;
import Project.Ministore.service.AccountService;
import Project.Ministore.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountEntity saveUser(AccountEntity user) {
        user.setRole("ROLE_USER");
        user.setEnable(true);
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        AccountEntity saveUser = accountRepository.save(user);
        return saveUser;
    }

    @Override
    public AccountEntity getUserByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public List<AccountEntity> getUsers(String role) {
        return accountRepository.findByRole(role);
    }

    @Override
    public Boolean updateAccountStatus(Integer id, Boolean enable) {
        Optional<AccountEntity> findByUser = accountRepository.findById(id);
        if (findByUser.isPresent()){
            AccountEntity user = findByUser.get();
            user.setEnable(enable);
            accountRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void increaseFailedAttempt(AccountEntity user) {
        int attempt = user.getFailed_attempt() + 1;
        user.setFailed_attempt(attempt);
        accountRepository.save(user);
    }

    @Override
    public void userAccountLock(AccountEntity user) {
        user.setAccount_nonlocked(false);
        user.setLock_time(new Date());
        accountRepository.save(user);
    }

    @Override
    public boolean unlockAccountTimeExpired(AccountEntity user) {
        long lock_time = user.getLock_time().getTime();
        long unlock_time = lock_time + AppConstant.UNLOCK_DURATION_TIME;
        long current_time = System.currentTimeMillis();
        if (unlock_time < current_time ){
            user.setAccount_nonlocked(true);
            user.setFailed_attempt(0);
            user.setLock_time(null);
            accountRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void resetAttempt(int accountId) {

    }

    @Override
    public void updateUserResetToken(String email, String resetToken) {
        AccountEntity findByEmail = accountRepository.findByEmail(email);
        findByEmail.setResetToken(resetToken);
        accountRepository.save(findByEmail);
    }

    @Override
    public AccountEntity getUserByToken(String token) {
        return accountRepository.findByResetToken(token);
    }

    @Override
    public AccountEntity updateUser(AccountEntity user) {
        return accountRepository.save(user);
    }

}

