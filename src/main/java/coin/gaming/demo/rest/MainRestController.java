package coin.gaming.demo.rest;

import coin.gaming.demo.AppProperties;
import coin.gaming.demo.Constants;
import coin.gaming.demo.common.Utils;
import coin.gaming.demo.model.RedirectRequest;
import coin.gaming.demo.service.OneTouchService;
import coin.gaming.demo.service.RetryTemplateService;
import coin.gaming.demo.service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.awt.print.Book;
import java.util.UUID;

@CrossOrigin(
    origins = {"*"},
    allowedHeaders = {"*"},
    methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE,
        RequestMethod.OPTIONS, RequestMethod.HEAD
    }
)
@RestController
@RequestMapping(Constants.ENDPOINT_URL_ROOT)
@Log4j2
@RequiredArgsConstructor
@Tag(name = "MainRestController", description = "Main REST Controller.")
public class MainRestController {

    // Request Mapping path for current RestController:
    public static final String ENDPOINT_URL_PATH_GLOBAL = Constants.ENDPOINT_URL_ROOT;

    // Request Mapping paths current RestController...
    public static final String ENDPOINT_URL_REDIRECT = "game";
    public static final String ENDPOINT_URL_TRANSACTION_BET = "transaction/bet";

    // beans
    private final RetryTemplateService retryTemplateService;
    private final AppProperties appProperties;
    private final SignService signService;
    private final OneTouchService oneTouchService;


    @Operation(summary = "Read CoinGaming endpoint and redirecting to a url received in a Response.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "?",
            content = {
                @Content(mediaType = "application/json",
                         schema = @Schema(implementation = Book.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request.",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "API Url not found.",
            content = @Content)
    })
    @PostMapping(
        value = MainRestController.ENDPOINT_URL_REDIRECT,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public RedirectView redirectToCoinGamingUrl() throws Exception {
        final var msg = "{} Redirect endpoint";
        LOG.debug(msg, "START");

        var operatorId = 10;
        var gameId = 70000;
        var currency = "EUR";

        RedirectRequest request = RedirectRequest.builder()
                                                 .user("john12345")
                                                 .token(UUID.randomUUID().toString())
                                                 .platform("GPL_DESKTOP")
                                                 .operatorId(operatorId)
                                                 .gameId(gameId)
                                                 .currency(currency)
                                                 .build();
        final var stringRequest = Utils.asJsonString(request);
        final var xSignature = signService.getSignature(stringRequest);

        var response = retryTemplateService.retry(
            appProperties.getRetryGameUrlMaxAttempts(),
            appProperties.getRetryGameUrlTimeout(),
            context -> oneTouchService.invokeGetGameUrl(xSignature, stringRequest)
        );

        var redirectUrl = response.getUrl();
        var redirectView = new RedirectView(redirectUrl);

        LOG.debug(msg, "END");
        return redirectView;
    }
}
