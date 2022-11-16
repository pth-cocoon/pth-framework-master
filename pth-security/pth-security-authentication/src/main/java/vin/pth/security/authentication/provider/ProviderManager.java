package vin.pth.security.authentication.provider;

import java.util.Collection;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vin.pth.security.core.exception.AuthenticationException;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
@SuppressWarnings("all")
@Component
@RequiredArgsConstructor
public class ProviderManager {

  private final Collection<AuthenticationProvider> providers;

  public UserAuthInfo authenticate(Map<String, Object> o) {
    for (AuthenticationProvider provider : providers) {
      var params = provider.supports(o);
      if (params != null) {
        return provider.authenticate(params);
      }
    }
    throw new AuthenticationException(1, "暂无支持的Provider");
  }

}
