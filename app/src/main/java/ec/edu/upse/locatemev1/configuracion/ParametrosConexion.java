package ec.edu.upse.locatemev1.configuracion;

/**
 * Created by gitwyn_pc on 29/09/2017.
 */

public class ParametrosConexion {
    private String puerto="8080";
    private String direccion="192.168.1.12";
    private String aplicacion="/WebServiceAlertasSpring/api/";



    public ParametrosConexion(){}

    public String urlcompeta(String ws,String metodo){
        String url="http://"+direccion+":"+puerto+aplicacion+ws+"/"+metodo;
        return url;
    }

    public String getPuerto() {
        return puerto;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getAplicacion() {
        return aplicacion;
    }
}
