package coin.gaming.demo.rest;

import coin.gaming.demo.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.awt.print.Book;

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
@Tag(name = "MainRestController", description = "Main REST Controller.")
public class MainRestController {

    // Request Mapping path for current RestController:
    public static final String ENDPOINT_URL_PATH_GLOBAL = Constants.ENDPOINT_URL_ROOT;

    // Request Mapping paths current RestController...
    public static final String ENDPOINT_URL_REDIRECT = "redirect";
    public static final String ENDPOINT_URL_TRANSACTION_BET = "transaction/bet";

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
    /* 
    public Book findById(@Parameter(description = "id of book to be searched") 
      @PathVariable long id) {
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException());
    }
    */
    @PostMapping(MainRestController.ENDPOINT_URL_REDIRECT)
    public RedirectView redirectToCoinGamingUrl() {
        final var msg = "{} Redirect endpoint";
        LOG.debug(msg, "START");

        var redirectUrl = "http://yahoo.com";
        var redirectView = new RedirectView(redirectUrl);

        LOG.debug(msg, "END");
        return redirectView;
    }
}
