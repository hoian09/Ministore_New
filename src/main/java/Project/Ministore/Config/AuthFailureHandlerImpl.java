package Project.Ministore.Config;

import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.repository.AccountRepository;
import Project.Ministore.service.AccountService;
import Project.Ministore.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("username");
        AccountEntity user = accountRepository.findByEmail(email);
        if (user != null) {
            if (user.getEnable()) {
                if (user.getAccount_nonlocked()) {
                    if (user.getFailed_attempt() < AppConstant.ATTEMPT_TIME) {
                        accountService.increaseFailedAttempt(user);
                    } else {
                        accountService.userAccountLock(user);
                        exception = new LockedException("Tài khoản của bạn đã bị khóa!! lần thử thứ 3 thất bại");
                    }
                } else {

                    if (accountService.unlockAccountTimeExpired(user)) {
                        exception = new LockedException("Tài khoản của bạn đã được mở khóa !! Vui lòng thử đăng nhập");
                    } else {
                        exception = new LockedException("Tài khoản của bạn đã bị khóa!! Thỉnh thoảng hãy thử lại");
                    }
                }
            } else {
                exception = new LockedException("Tài khoản của bạn không hoạt động");
            }
        } else {
            exception = new LockedException("Email và mật khẩu không hợp lệ");
        }
        request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
