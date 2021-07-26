package coin.gaming.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(title = "OneTouch Request",
        description = "OneTouch Request getting redirect game URL.")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedirectRequest {
    @Schema(required = true)
    private String user;

    @Schema(required = true)
    private String token;

    @Schema(required = true)
    private String platform;

    @Schema(required = true)
    @JsonProperty("operator_id")
    private int operatorId;

    @Schema(required = true)
    @JsonProperty("game_id")
    private int gameId;

    @Schema(required = true)
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("sub_partner_id")
    private String subPartnerId;

    @JsonProperty("lobby_url")
    private String lobbyUrl;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("display_unit")
    private String displayUnit;

    @JsonProperty("deposit_url")
    private String depositUrl;

    @JsonProperty("country")
    private String country;
}
