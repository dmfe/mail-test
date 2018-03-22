package org.home.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
public class SMTPSettings {

    private String server;
    @Builder.Default
    private Integer port = 25;
    private String userName;
    private String password;
    @Builder.Default
    private Boolean auth = false;
    @Builder.Default
    private Boolean sslSecurity = false;

    @Builder
    public SMTPSettings(String server, Integer port, String userName, String password,
                        Boolean auth, Boolean sslSecurity) {
        this.server = server;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.auth = auth;
        this.sslSecurity = sslSecurity;
    }
}