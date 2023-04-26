package vin.pth.security.core.util;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import vin.pth.security.core.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
public class UserAuthUtil {

  private UserAuthUtil() {
  }

  public static Authentication toAuth(UserAuthInfo userAuthInfo) {
    Assert.notNull(userAuthInfo, "userAuthInfo is null!");
    return new Authentication() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuthInfo.getAuthorities();
      }

      @Override
      public Object getCredentials() {
        return userAuthInfo.getPassword();
      }

      @Override
      public Object getDetails() {
        return userAuthInfo;
      }

      @Override
      public Object getPrincipal() {
        return userAuthInfo.getUsername();
      }

      @Override
      public boolean isAuthenticated() {
        return true;
      }

      @Override
      public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // Do nothing.
      }

      @Override
      public String getName() {
        return userAuthInfo.getUsername();
      }
    };

  }
}
