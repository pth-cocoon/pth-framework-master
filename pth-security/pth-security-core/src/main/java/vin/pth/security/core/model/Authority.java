package vin.pth.security.core.model;

import java.io.Serializable;
import org.springframework.http.HttpMethod;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
public record Authority(HttpMethod method, String uri) implements Serializable {

}
