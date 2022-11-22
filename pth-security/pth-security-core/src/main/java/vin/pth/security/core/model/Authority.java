package vin.pth.security.core.model;

import java.io.Serializable;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
@Validated
public record Authority(HttpMethod method, String uri) implements Serializable {

}
