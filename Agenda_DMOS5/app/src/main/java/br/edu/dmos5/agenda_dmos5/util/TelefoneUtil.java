package br.edu.dmos5.agenda_dmos5.util;

public class TelefoneUtil {

    public static boolean isValidFixo(String fixo){
        if (fixo == null){
            return false;
        }
        return fixo.matches("^(\\(?\\d{2}\\)?\\s)?(\\d{5}\\-\\d{4})$");
    }

    public static boolean isValidCelular(String celular){
        if (celular == null){
            return false;
        }
        return celular.matches("^(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})$");
    }
}
