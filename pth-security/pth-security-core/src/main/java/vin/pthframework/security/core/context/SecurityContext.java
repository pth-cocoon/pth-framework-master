package vin.pthframework.security.core.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * Base implementation of {@link SecurityContext}.
 * <p>
 * Used by default by {@link SecurityContextHolder} strategies.
 *
 * @author Ben Alex
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SecurityContext {


  private UserAuthInfo authInfo;


}
