//package Project.Ministore.service;
//
//import Project.Ministore.Config.MyAccountDetails;
//import Project.Ministore.Entity.AccountEntity;
//import Project.Ministore.repository.AccountRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//
//public class AccountDetailServiceImpl implements UserDetailsService {
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username)
//            throws UsernameNotFoundException {
//        AccountEntity account =accountRepository.findByUsername(username);
//        if (account == null) {
//            throw new UsernameNotFoundException("Could not find user");
//        }
//
//        return new MyAccountDetails(account);
//    }
//}
