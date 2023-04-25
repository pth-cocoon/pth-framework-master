package vin.pth.security.servlet.util;

import vin.pth.security.core.consts.SecurityConst;
import vin.pth.security.core.pojo.UserAuthInfo;

import javax.servlet.http.HttpSession;

/**
 * @author Cocoon
 */
public class LoginUtil {

    private LoginUtil() {
    }


    public static void login(UserAuthInfo userAuthInfo, HttpSession session) {
        session.setAttribute(SecurityConst.USER_INFO_KEY, userAuthInfo);
    }

    public static UserAuthInfo getAuthInfo(HttpSession session) {
        return (UserAuthInfo) session.getAttribute(SecurityConst.USER_INFO_KEY);
    }
}
