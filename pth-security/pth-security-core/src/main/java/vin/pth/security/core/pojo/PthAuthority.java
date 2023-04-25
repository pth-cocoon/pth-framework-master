package vin.pth.security.core.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @author Cocoon
 */
public record PthAuthority(String method, String uri) implements GrantedAuthority, Serializable {

  @JsonIgnore
  @Override
  public String getAuthority() {
    return uri + "@" + method;
  }
}
