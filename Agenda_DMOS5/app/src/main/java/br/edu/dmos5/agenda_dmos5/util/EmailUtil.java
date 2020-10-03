package br.edu.dmos5.agenda_dmos5.util;

public class EmailUtil {

    public static boolean isValidEmail(String email){
        if(email == null){
            return false;
        }
        return email.matches("^(([a-zA-Z0-9_\\.])+@([a-zA-Z0-9])+\\.([a-zA-z])+(\\.([a-zA-z])+)*)$");
    }
}
