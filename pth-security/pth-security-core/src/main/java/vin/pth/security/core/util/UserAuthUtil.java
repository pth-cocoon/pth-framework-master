package vin.pth.security.core.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import vin.pth.security.core.pojo.UserAuthInfo;

import java.util.Collection;

/**
 * @author Cocoon
 */
public class UserAuthUtil {

  private UserAuthUtil() {
  }

  public static Authentication toAuth(UserAuthInfo userAuthInfo) {
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
