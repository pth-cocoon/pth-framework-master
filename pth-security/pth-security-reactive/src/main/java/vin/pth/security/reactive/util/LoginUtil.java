package vin.pth.security.reactive.util;

import org.springframework.web.server.WebSession;
import vin.pth.security.core.consts.SecurityConst;
import vin.pth.security.core.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
public class LoginUtil {

    private LoginUtil() {

    }


    public static void login(UserAuthInfo userAuthInfo, WebSession session) {
        session.getAttributes().put(SecurityConst.USER_INFO_KEY, userAuthInfo);
    }

    public static UserAuthInfo getAuthInfo(WebSession session) {
        return (UserAuthInfo) session.getAttributes().get(SecurityConst.USER_INFO_KEY);
    }
}
