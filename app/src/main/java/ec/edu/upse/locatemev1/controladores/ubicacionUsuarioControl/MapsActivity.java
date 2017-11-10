package ec.edu.upse.locatemev1.controladores.ubicacionUsuarioControl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.modelo.UbicacionUsuario;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static int MILISEGUNDOS_ESPERA = 5000;

    private GoogleMap mMap;
    private Marker marcador;
    UbicacionUsuario ubicacionUsuario = new UbicacionUsuario();

    String Pais = null , Provincia = null, Ciudad = null, ViaPublica = null, Calles = null, codigo = null;

    double lat = 0.0;
    double lon = 0.0;

    //Guardar preferencias
    double latini = 0.0;
    double lonini = 0.0;

    Long idubicacion_g;

    //Cargar preferencias
    String lat_r = "";
    String lon_r = "";
    String idubicacion_r="";



    Location location;
    String ubicacion;
    float distancias;
    boolean estado = false;
    public static final String MyPREFERENCES = "MyPrefs" ;

    //Presentacion presentacion = Presentacion.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }else{
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,(Activity)getApplicationContext(),10);
            dialog.show();
        }
        if (CargarPreferenciasIdubicacion()==true) Toast.makeText(this, "id ubicacion : " + idubicacion_r ,Toast.LENGTH_LONG).show();

        estado = CargarPreferencias();
        if(estado == true)Toast.makeText(this, "Cargar LATITUD: "+lat_r + " LONGITUD: "+ lon_r ,Toast.LENGTH_LONG).show();
        else Toast.makeText(this, "No hay Datos",Toast.LENGTH_LONG).show();
       /* Toast.makeText(this, "Usuario : "+presentacion.getId_usuario(),
                Toast.LENGTH_LONG).show();*/

    }

    public void datos_mapa(){
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());

        List<Address> addresses ;
        try {
            addresses = geoCoder.getFromLocation(lat, lon, 5);
            Pais = addresses.get(0).getCountryName() + "";
            Provincia = addresses.get(0).getAdminArea() + "";
            Ciudad = addresses.get(0).getLocality() + "";
            ViaPublica = addresses.get(0).getThoroughfare() + "";
            Calles = addresses.get(0).getFeatureName() + "";
            codigo = addresses.get(0).getCountryCode() + "";

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
/*
    public void onClick_AccionTrasSegundos(View view) {
        esperarYCerrar(MILISEGUNDOS_ESPERA);
    }

    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler ();
        handler.postDelayed(new Runnable() {
            public void run() {
                //registroAlerta();
            }
        }, milisegundos);
    }

    String SOAP_ACTION =  presentacion.SOAP_ACTION + "registroAlerta" ;
    String METODO = "registroAlerta";

    SoapObject request = new SoapObject(presentacion.NAMESPACE, METODO);

    public void registroAlerta(View view) {

        miUbicacion();
        datos_mapa();
        String longitud = "" + lon + "";
        String latitud = "" + lat + "";

        request.addProperty("request1", 6);
        request.addProperty("request2", 5);
        request.addProperty("request3", Pais);
        request.addProperty("request4", Provincia);
        request.addProperty("request5", Ciudad);
        request.addProperty("request6", ViaPublica);
        request.addProperty("request7", codigo);
        request.addProperty("request8", longitud);
        request.addProperty("request9", latitud);

        SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        Envoltorio.setOutputSoapObject(request);

        HttpTransportSE TransporteHttp = new HttpTransportSE(presentacion.URL);

        try {
            TransporteHttp.call(SOAP_ACTION, Envoltorio);

            SoapObject result = (SoapObject) Envoltorio.bodyIn;

            /*if (result != null) {
                String json = result.getProperty(0).toString();
                Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }

        //Toast.makeText(getApplicationContext(), "llego!", Toast.LENGTH_SHORT).show();
    }
*/

    public boolean CargarPreferencias(){
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        lat_r = miPreferencia.getString("latitud","");
        lon_r = miPreferencia.getString("longitud","");

        if (lat_r.length() <= 0 && lon_r.length() <= 0 )return false;
        else return true;
        //Toast.makeText(this, "Cargar LATITUD: "+lat_r + " LONGITUD: "+ lon_r,Toast.LENGTH_LONG).show();
    }

    public void GuardarPreferencias(){
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencia.edit();
        Double lat_g  = latini;
        Double long_g  = lonini;

        if (lat_g != 0.0 && long_g != 0.0){
            editor.putString("latitud", lat_g.toString());
            editor.putString("longitud", long_g.toString());

            editor.commit();
            Toast.makeText(this, "Guardado : LATITUD: "+Double.toString(lat_g) + " LONGITUD: "+Double.toString(long_g),
                    Toast.LENGTH_LONG).show();
        }

    }

    public boolean CargarPreferenciasIdubicacion(){
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        idubicacion_r = miPreferencia.getString("idubicacion","");
        if (idubicacion_r.length() <= 0)return false;
        else return true;
        //Toast.makeText(this, "idubicacion : "+ubi_usu,Toast.LENGTH_LONG).show();
    }

    public void GuardarPreferenciasIdubicacion(){
        SharedPreferences miPreferencia = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencia.edit();
        idubicacion_g = VariablesGenerales.getLonIdUbicacion();
        editor.putString("idubicacion",String.valueOf(idubicacion_g));
        editor.commit();
        Toast.makeText(this," idubicacion: " +idubicacion_g,Toast.LENGTH_LONG).show();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        //miUbicacion();
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    private void agregarMarcador(double lat, double lon) {
        ubicacion = "Longitud :" + lat + " Latitud : "+lon;
        LatLng coordenadas = new LatLng(lat, lon);
        CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(ubicacion)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.animateCamera(miubicacion);
    }

    private void actualizarUbicacionPunto1(Location location) {

        if (location != null) {
            latini = location.getLatitude();
            lonini = location.getLongitude();
            agregarMarcador(latini, lonini);
        }
        //latini = -2.233016; lonini =  -80.878432; //agregarMarcador(latini, lonini);
    }
    private void actualizarUbicacionPunto2(Location location) {
        //lat = -2.232817;
        //lon = -80.878752;
        // agregarMarcador(latini, lonini);
        ///Toast.makeText(this, "FIN : LATITUD: "+Double.toString(lat) + " LONGITUD: "+Double.toString(lon),
        //        Toast.LENGTH_LONG).show();
        if (location != null) {
            //lat =-2.233005;
            //lon = -80.878384;
            lat = location.getLatitude();
            lon = location.getLongitude();
            agregarMarcador(lat, lon);
            Toast.makeText(this, "FIN : LATITUD: "+Double.toString(lat) + " LONGITUD: "+Double.toString(lon),
                    Toast.LENGTH_LONG).show();

        }
    }
    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            agregarMarcador(lat, lon);
        }
    }

    public float distancia(double latitud, double longitud,double latitudini, double longitudini){
        //float [] dist = new float[1];
        Location mylocation = new Location("");
        Location dest_location = new Location("");
        //String lat = latitud.getText().toString();
        // String lon = longitud.getText().toString();
        dest_location.setLatitude(latitud);
        dest_location.setLongitude(longitud);

        //Double my_loc = 0.00;
        mylocation.setLongitude(longitudini);
        mylocation.setLatitude(latitudini);

        dest_location.distanceTo(mylocation);//in meters

        /*Toast.makeText(this, "Distancia : "+ String.format("%.2f", distancias)+ " m",
                Toast.LENGTH_LONG).show();*/
        return dest_location.distanceTo(mylocation);
        //Toast.makeText(getApplicationContext(),coordenadas,Toast.LENGTH_LONG).show();
    }

    /* public void distancia(double latitud, double longitud){
         double r1,r2;
         Location mylocation = new Location("");
         Location dest_location = new Location("");
         //String lat = latitud.getText().toString();
        // String lon = longitud.getText().toString();
         dest_location.setLatitude(latitud);
         dest_location.setLongitude(longitud);
         //Double my_loc = 0.00;
         mylocation.getLatitude();
         mylocation.getLongitude();

         float distance = mylocation.distanceTo(dest_location);//in meters
         Toast.makeText(this, "Distance : "+Double.toString(distance) + " m",
                 Toast.LENGTH_LONG).show();
         //Toast.makeText(getApplicationContext(),coordenadas,Toast.LENGTH_LONG).show();
     }
 */
    public String GuardarUbicacion(){
        String json,json1,json2 = null;
        Gson gson = new Gson();
        Usuario usuario = new Usuario();
        datos_mapa();
        //lat =-2.233005;
        //lon = -80.878384;
        String longitud = "" + lonini + "";
        String latitud = "" + latini + "";


        usuario.setIdusuario(VariablesGenerales.getLonIdUsuario());

        ubicacionUsuario.setUsuario(usuario);
        ubicacionUsuario.setUsuUbiCiudad(Ciudad);
        ubicacionUsuario.setUsuUbiCodigopais(Pais);
        ubicacionUsuario.setUsuUbiEstado("A");
        ubicacionUsuario.setUsuUbiLatitud(longitud);
        ubicacionUsuario.setUsuUbiLongitud(latitud);
        ubicacionUsuario.setUsuUbiPais(Pais);
        ubicacionUsuario.setUsuUbiProvincia(Provincia);
        ubicacionUsuario.setUsuUbiViapublica(ViaPublica);

        String jsoncompleto = gson.toJson(ubicacionUsuario,UbicacionUsuario.class);
        JSONObject jsonObject = new JSONObject();

           /*try {

               jsonObject.put("usuario",ubicacionUsuario.getUsuario());
               jsonObject.put("usuUbiCiudad",ubicacionUsuario.getUsuUbiCiudad());
               jsonObject.put("usuUbiCodigopais",ubicacionUsuario.getUsuUbiCodigopais());
               jsonObject.put("usuUbiEstado",ubicacionUsuario.getUsuUbiEstado());
               jsonObject.put("usuUbiHoraFin", ubicacionUsuario.getUsuUbiHoraFin());
               jsonObject.put("usuUbiHoraInicio",ubicacionUsuario.getUsuUbiHoraInicio());
               jsonObject.put("usuUbiLatitud",ubicacionUsuario.getUsuUbiLatitud());
               jsonObject.put("usuUbiLongitud",ubicacionUsuario.getUsuUbiLongitud());
               jsonObject.put("usuUbiPais",ubicacionUsuario.getUsuUbiPais());
               jsonObject.put("usuUbiProvincia",ubicacionUsuario.getUsuUbiProvincia());
               jsonObject.put("usuUbiViapublica",ubicacionUsuario.getUsuUbiViapublica());

               json = jsonObject.toString().replaceAll(" ","&");
               json1 = json.replace(".","*");
               System.out.println(json);
           } catch (JSONException error) {

           }*/
        System.out.println(jsoncompleto);
        json1 = jsoncompleto.toString().replaceAll(" ","&");
        json2 = json1.replace(".","*");

        System.out.println(json2);
        return json2;
    }

    /*
    HttpClient recuperaIdUbicacion = new HttpClient(new OnHttpRequestComplete() {
        @Override
        public void onComplete(Response status) {
            if(status.isSuccess()){
                Gson gson = new GsonBuilder().create();
                try {
                    //JSONObject jsono = new JSONObject(status.getResult());
                    //JSONArray jsonarray = jsono.getJSONArray("UbicacionUsuario");
                    String id = status.getResult();
                    VariablesGenerales.setLonIdUsuario(Long.parseLong(id));
                    GuardarPreferenciasIdubicacion();

                    //Toast.makeText(MapsActivity.this,"idUbicacion: "+Long.parseLong(id), Toast.LENGTH_LONG).show();
                    //System.out.println("id: "+Long.parseLong(id));


                }catch (Exception e){
                    System.out.println("Fallo! "+e.toString());
                    e.printStackTrace();
                }

            }
        }
    });
*/

  /*
    HttpClient actualizarUbicacion = new HttpClient(new OnHttpRequestComplete() {
        @Override
        public void onComplete(Response status) {
            if(status.isSuccess()){
                try {
                    System.out.println("Actualizado");
                }catch (Exception e){
                    System.out.println("Fallo! "+e.toString());
                    e.printStackTrace();
                }

            }
        }
    });
*/


    /*
    public void registrarUbicacion(Double ubi_lat,  Double ubi_long) {

        String longitud = "" + ubi_long + "";
        String latitud = "" + ubi_lat + "";

        String SOAP_ACTION =  presentacion.SOAP_ACTION + "registroAlmacenaUbicacion" ;
        String METODO = "registroAlmacenaUbicacion";

        SoapObject request = new SoapObject(presentacion.NAMESPACE, METODO);
        datos_mapa();
        request.addProperty("request1", presentacion.getId_usuario());
        request.addProperty("request2", Pais);
        request.addProperty("request3", Provincia);
        request.addProperty("request4", Ciudad);
        request.addProperty("request5", ViaPublica);
        request.addProperty("request6", codigo);
        request.addProperty("request7", longitud);
        request.addProperty("request8", latitud);
        request.addProperty("request9", h_inicio);*/
       /*if (lon_r.toString()!="")request.addProperty("request7", lon_r.toString());
       else request.addProperty("request7", longitud);

       if (lat_r.toString()!="")request.addProperty("request8", lat_r.toString());
       else request.addProperty("request8", latitud);


       if (r_horainicio.toString() !="") request.addProperty("request9", r_horainicio.toString());
       else request.addProperty("request9", h_inicio);*/
     /*
       request.addProperty("request10", h_fin );
       request.addProperty("request11", "A");

       SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope(SoapEnvelope.VER11);
       Envoltorio.setOutputSoapObject(request);

       HttpTransportSE TransporteHttp = new HttpTransportSE(presentacion.URL);

       try {
           TransporteHttp.call(SOAP_ACTION, Envoltorio);

           SoapObject result = (SoapObject) Envoltorio.bodyIn;
           if (result != null) {
               String  json =result.getProperty(0).toString();
               if (Integer.parseInt(json) > 0 ){
                   presentacion.setId_ubicacion(Integer.parseInt(json));
                   GuardarPreferenciasIdubicacion();
                   Toast.makeText(this, "idubicacion : "+presentacion.getId_ubicacion(),
                           Toast.LENGTH_LONG).show();
               }
               else{
                   Toast.makeText(getApplicationContext(), "No se recupero", Toast.LENGTH_SHORT).show();
               }

           } else {
               Toast.makeText(getApplicationContext(), "! No Responde !", Toast.LENGTH_SHORT).show();
           }

       } catch (Exception e) {
           e.printStackTrace();
           Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

       }

   }
*/
/*
    public void ActualizarUbicacion(String fecha,  int idubicacion) {

        String SOAP_ACTION =  presentacion.SOAP_ACTION + "editaUbicacionRecienteEncontrada" ;
        String METODO = "editaUbicacionRecienteEncontrada";

        Toast.makeText(getApplicationContext(), "Abrir ...", Toast.LENGTH_SHORT).show();

        SoapObject request = new SoapObject(presentacion.NAMESPACE, METODO);
        request.addProperty("request1", fecha);
        request.addProperty("request2", idubicacion);

        SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        Envoltorio.setOutputSoapObject(request);

        HttpTransportSE TransporteHttp = new HttpTransportSE(presentacion.URL);

        try {
            TransporteHttp.call(SOAP_ACTION, Envoltorio);

            SoapObject result = (SoapObject) Envoltorio.bodyIn;


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }

    }
*/
    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    public void punto1(View view) throws ParseException {

        //String mytime = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
        //String horai = presentacion.setHorainicio(mytime);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacionPunto1(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locListener);
        //presentacion.setFecha(getDatePhone());


        //h_inicio  = getDatePhone()+" "+ horai;


        //Toast.makeText(this,presentacion.getFecha().toString() ,Toast.LENGTH_LONG).show();
        GuardarPreferencias();

        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
*/

    }

    public void punto2(View view){
        //String mytime = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
        //String horaf = presentacion.setHorafin(mytime);

        //Toast.makeText(this,"hola",Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacionPunto2(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locListener);
        //h_fin = getDatePhone()+" "+horaf;
        // presentacion.setFecha(getDatePhone());
        // Toast.makeText(this,presentacion.getFecha().toString(),Toast.LENGTH_LONG).show();
    }
    /*
    private String getDatePhone()

    {
        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formatteDate = df.format(date);
        return formatteDate;
    }
    */
    /*private Date getDatePhone(){
        java.util.Date FechaActual = new java.util.Date(System.currentTimeMillis());
        java.sql.Date FechaSql = new java.sql.Date(FechaActual.getTime());
        return FechaSql;
    }*/
    /*
    private Date ConvertToDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }*/

    public void calculadistancia(View view){

        //distancias =  distancia(lat,lon, Double.parseDouble(lat_r),Double.parseDouble(lon_r));
        if  (estado == true)
        {
            distancias =  distancia(lat,lon, Double.parseDouble(lat_r),Double.parseDouble(lon_r));
            Toast.makeText(this,""+distancias,Toast.LENGTH_LONG).show();

            if( distancias <= VariablesGenerales.intRango && distancias > 0.00)
            {
               // actualizarUbicacion.excecute("http://192.168.1.125:8080/WebServiceAlertasSpring/api/ubicacionusuario/actualizarUbicacion/"+idubicacion_r);
                //Toast.makeText(this,Integer.parseInt(idubicacion_r),Toast.LENGTH_LONG).show();
                //Toast.makeText(this,"Actualizar Ubicacion",Toast.LENGTH_LONG).show();
                //ActualizarUbicacion(h_fin,Integer.parseInt(idubicacion_r));
            }
            else if(distancias > VariablesGenerales.intRango && distancias > 0.00){
                Toast.makeText(this,"Nuevo registro",Toast.LENGTH_LONG).show();
                //recuperaIdUbicacion.excecute("http://192.168.1.125:8080/WebServiceAlertasSpring/api/ubicacionusuario/guardarUbicacion/"+GuardarUbicacion());

                // registrarUbicacion(Double.parseDouble(lat_r),Double.parseDouble(lon_r));
            }

        }
        else if(estado == false && idubicacion_r.toString()=="" /*|| estado == true && idubicacion_r.toString()==""*/)
        {

            distancias =  distancia(lat,lon, latini,lonini);
            Toast.makeText(this,""+distancias,Toast.LENGTH_LONG).show();
            if(distancias !=0.00 ) {
               // recuperaIdUbicacion.excecute("http://192.168.1.101:8080/WebServiceAlertasSpring/api/ubicacionusuario/guardarUbicacion/"+GuardarUbicacion());
                //registrarUbicacion(latini, lonini);
                Toast.makeText(this, "Guardado con Exito", Toast.LENGTH_LONG).show();
                // Toast.makeText(this, "Lat: "+posicionInicial+" Long: "+posicionFinal,Toast.LENGTH_LONG).show();
            }
        }

    }
   /*
    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locListener);
    }
   */
}

