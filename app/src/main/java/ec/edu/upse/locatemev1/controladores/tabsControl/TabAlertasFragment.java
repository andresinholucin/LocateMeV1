package ec.edu.upse.locatemev1.controladores.tabsControl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.modelo.EmisionAlerta;

public class TabAlertasFragment extends Fragment {
   ParametrosConexion conexion = new ParametrosConexion();
    List<EmisionAlerta> listaAlertas= new ArrayList<EmisionAlerta>();
    List<String> lstAlertaString= new ArrayList<String>();
    ListView lista;
    ArrayAdapter<String> adaptador ;
    public TabAlertasFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_alertas, container, false);
        lista = (ListView) view.findViewById(R.id.listaAlerta);
        if(VariablesGenerales.getIntPeticionAlerta()==0)
        {
            new HttpListaAlertas().execute();
            adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lstAlertaString);
            lista.setAdapter(adaptador);
            VariablesGenerales.setIntPeticionAlerta(1);
        }
        else
        {

            adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lstAlertaString);
            lista.setAdapter(adaptador);
        }
        return  view;
    }

    private class HttpListaAlertas extends AsyncTask<Void, Void, Void > {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                for(int i = 0; i< VariablesGenerales.getListUsuario().size(); i++) {

                    System.out.println("=== Imprime ===");
                    final String url = conexion.urlcompeta("emisionAlerta","alertasUsuario2/"+ VariablesGenerales.getLonIdTutor());
                    //final String url = conexion.urlcompeta("emisionAlerta","listaAlerta/"+VariablesGenerales.getListUsuario().get(i).getIdusuario());
                    System.out.println(url);
                    RestTemplate restTemplate = new RestTemplate();
                    //System.out.println("Aqui2:" +json);
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    // System.out.println("Aqui3:" +json);
                    ResponseEntity<EmisionAlerta[]> response= restTemplate.getForEntity(url, EmisionAlerta[].class);
                    //System.out.println("Aqui4:" +json);
                    listaAlertas = Arrays.asList(response.getBody());
                    VariablesGenerales.setListEmisionAlerta(listaAlertas);
                    for(int j=0 ; i<listaAlertas.size();i++)
                    {
                        //System.out.println(listaAlertas.get(i).getIdusuario()+" "+listaUsuarios.get(i).getUsuUNombres()+" "+listaUsuarios.get(i).getUsuUApellidos());
                        lstAlertaString.add("Tutoriado: "+listaAlertas.get(j).getUsuario().getUsuUNombres()+" "+listaAlertas.get(i).getUsuario().getUsuUApellidos()+" Provincia: "+listaAlertas.get(j).getUsuAlerProvincia()+" - Ciudad: "+listaAlertas.get(i).getUsuAlerCiudad()+" - Fecha: "+listaAlertas.get(i).getUsuAlerFecha()+" - Estado: "+listaAlertas.get(i).getUsuAlerEstado());
                        System.out.println(listaAlertas.get(j).getIdemisionAlerta()+" "+listaAlertas.get(j).getUsuAlerCodigopais()+""+listaAlertas.get(j).getUsuAlerProvincia());
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lstAlertaString);
            //adaptador.notifyDataSetChanged();
            lista.setAdapter(adaptador);
        }
    }
}
