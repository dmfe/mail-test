package org.home.rest;

import lombok.extern.slf4j.Slf4j;
import org.home.model.entity.SMTPSettings;
import org.home.model.entity.TestResponse;
import org.home.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/email/v1/api", produces = APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
@Slf4j
public class EmailController {

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/send", method = POST)
    public ResponseEntity<String> sendMail() {
        String result = "Email was sent";
        return new ResponseEntity<>(makeOperationResponseJson(result), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/test", method = GET)
    public ResponseEntity<TestResponse> testMailServer(
            @RequestParam(value = "server") String server,
            @RequestParam(value = "port", required = false, defaultValue = "25") Integer port,
            @RequestParam(value = "auth", defaultValue = "false") Boolean auth,
            @RequestParam(value = "ssl", defaultValue = "false") Boolean ssl,
            @RequestParam(value = "user", required = false) String user,
            @RequestParam(value = "password", required = false) String pwd
    ) {
        SMTPSettings settings = SMTPSettings.builder()
                .server(server)
                .port(port)
                .auth(auth)
                .sslSecurity(ssl)
                .userName(user)
                .password(pwd).build();

        TestResponse result = emailService.testSMTPServer(settings);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private String makeOperationResponseJson(String result) {
        return String.join("",
                "{",
                "  \"result\": \"" + result + "\"",
                "}");
    }

    private String makeOperationResponseJson(String result, boolean resultIsObject)  {
        if (resultIsObject) {
            return String.join("",
                    "{",
                    "  \"result\": " + result,
                    "}");
        } else {
            return makeOperationResponseJson(result);
        }
    }
}