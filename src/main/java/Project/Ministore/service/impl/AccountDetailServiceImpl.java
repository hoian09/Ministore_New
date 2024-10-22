package Project.Ministore.service.impl;
import Project.Ministore.Config.MyAccountDetails;
import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class AccountDetailServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountEntity user = accountRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Người dùng không tìm thấy");
        }
        return new MyAccountDetails(user);
    }
    }

