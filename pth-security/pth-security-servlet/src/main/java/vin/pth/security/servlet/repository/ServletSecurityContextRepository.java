package vin.pth.security.servlet.repository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import vin.pth.security.core.consts.SecurityConst;
import vin.pth.security.core.pojo.UserAuthInfo;
import vin.pth.security.core.util.UserAuthUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Cocoon
 */
public class ServletSecurityContextRepository implements SecurityContextRepository {

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserAuthInfo userAuthInfo = (UserAuthInfo) session.getAttribute(SecurityConst.USER_INFO_KEY);

        if (userAuthInfo == null) {
            session.removeAttribute(SecurityConst.USER_INFO_KEY);
        } else {
            Authentication authentication = UserAuthUtil.toAuth(userAuthInfo);
            context.setAuthentication(authentication);
            session.setAttribute(SecurityConst.USER_INFO_KEY, userAuthInfo);
        }


    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        return context != null;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpSession session = requestResponseHolder.getRequest().getSession();
        UserAuthInfo userAuthInfo = (UserAuthInfo) session.getAttribute(SecurityConst.USER_INFO_KEY);
        return new SecurityContextImpl(UserAuthUtil.toAuth(userAuthInfo));
    }


}
