/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projects;

import core.Password;

/**
 *
 * @author yannick
 */
public class ConnectionInfo 
{
    private String login;
    private Password password;
    private boolean useKey;
    private String keyPath;
    private String host;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public boolean isUseKey() {
        return useKey;
    }

    public void setUseKey(boolean useKey) {
        this.useKey = useKey;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    
}
