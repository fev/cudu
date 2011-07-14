/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.services.models;

/**
 *
 * @author gaxp
 */
public class PasswordUsuario {
    private int id;
    private String username;
    private String anteriorPassword;
    private String password;
    private String confirmarPassword;

    public PasswordUsuario(String username) {
        this.username = username;
    }
    public PasswordUsuario() {
        this.username = username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the anteriorPassword
     */
    public String getAnteriorPassword() {
        return anteriorPassword;
    }

    /**
     * @param anteriorPassword the anteriorPassword to set
     */
    public void setAnteriorPassword(String anteriorPassword) {
        this.anteriorPassword = anteriorPassword;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmarPassword
     */
    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    /**
     * @param confirmarPassword the confirmarPassword to set
     */
    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
}
