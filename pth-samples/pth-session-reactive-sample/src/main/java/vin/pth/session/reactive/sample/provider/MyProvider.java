package vin.pth.session.reactive.sample.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import vin.pth.security.authentication.provider.AuthenticationProvider;
import vin.pth.security.core.model.Authority;
import vin.pth.security.core.model.UserAuthInfo;
import vin.pth.session.reactive.sample.pojo.UsernamePasswordDTO;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
@Component
public class MyProvider implements AuthenticationProvider<UsernamePasswordDTO> {

  private final static String PASSWORD_KEY = "password";
  private final static String USERNAME_KEY = "username";

  @Override
  public UserAuthInfo authenticate(UsernamePasswordDTO param) {
    UserAuthInfo userAuthInfo = new UserAuthInfo("123");
    userAuthInfo.setAuthorities(init());
    return userAuthInfo;
  }

  private List<Authority> init() {
    List<Authority> authorities = new ArrayList<>();
    authorities.add(new Authority(HttpMethod.GET, "/login"));
    authorities.add(new Authority(HttpMethod.GET, "/userInfo/**"));
    return authorities;
  }

  @Override
  public UsernamePasswordDTO supports(@NonNull Map<String, Object> param) {

    if (param.containsKey(USERNAME_KEY) && param.containsKey(PASSWORD_KEY)) {
      UsernamePasswordDTO usernamePasswordDTO = new UsernamePasswordDTO();
      usernamePasswordDTO.setUsername(param.get("username").toString());
      usernamePasswordDTO.setPassword(param.get("password").toString());
      return usernamePasswordDTO;
    } else {
      return null;
    }
  }
}
