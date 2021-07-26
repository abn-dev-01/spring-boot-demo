package coin.gaming.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "OneTouch Respone.",
        description = "Returns the Landing URL of the chosen game that can be embedded into the website")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedirectResponse {
    private String url;
}
