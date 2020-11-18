package com.lugopa.juegoelectiva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lugopa.juegoelectiva.Model.Puntaje;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PuntajeListAdapter extends ArrayAdapter<Puntaje> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;

    public PuntajeListAdapter(Context context, int resource, ArrayList<Puntaje> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // obtengo la informacion sobre el puntaje / persona
        String nombre = getItem(position).getNombre();
        String dificultad = getItem(position).getDificultad();
        String puntaje = getItem(position).getPuntaje();
        String ubicacion = getItem(position).getUbicacion();// solo se muestra el pais
        Double latitud = getItem(position).getLatitud(); // no se muestra
        Double longitud = getItem(position).getLongitud(); // no se muestra

        String paisP = obtenerPais(ubicacion);

        //creamos el objeto Puntaje con la informacion
        Puntaje p = new Puntaje(nombre, puntaje, dificultad, paisP, latitud, longitud);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNombre = (TextView) convertView.findViewById(R.id.textView2_numero);
        TextView tvDificultad = (TextView) convertView.findViewById(R.id.textView3_descripcion);
        TextView tvPuntaje = (TextView) convertView.findViewById(R.id.textView1);
        //TextView tvUbicacion = (TextView) convertView.findViewById(R.id.textView4);
        ImageView imageViewPais = (ImageView) convertView.findViewById(R.id.imageView1);


        tvNombre.setText(nombre);
        tvDificultad.setText(dificultad);
        tvPuntaje.setText(puntaje);
        //tvUbicacion.setText(paisP);
        //imageViewPais.setImageResource(R.mipmap.argentina_flag_round);
        seleccionarImagenPais(imageViewPais, paisP);
        return convertView;
    }

    private String obtenerPais(String ubic){
        String pais = null;
        for (int i=ubic.length()-1; i!=0; i--){
            if(ubic.charAt(i)==','){
                pais=Character.toString(ubic.charAt(i+2));
                for(int j=i+3; j< ubic.length(); j++){
                    pais = pais + ubic.charAt(j);
                }
                break;
            }
        }
        return pais;
    }

    private void seleccionarImagenPais(ImageView imageView, String pais){
        switch (pais){
            case "Argentina":
                imageView.setImageResource(R.mipmap.argentina_flag_round);
                break;
            case "Brasil":
                imageView.setImageResource(R.mipmap.brasil_flag_round);
                break;
            case "Uruguay":
                imageView.setImageResource(R.mipmap.uruguay_flag_round);
                break;
            case "Paraguay":
                imageView.setImageResource(R.mipmap.paraguay_flag_round);
                break;
            case "Bolivia":
                imageView.setImageResource(R.mipmap.bolivia_flag_round);
                break;
            case "Peru":
                imageView.setImageResource(R.mipmap.peru_flag_round);
                break;
            case "Colombia":
                imageView.setImageResource(R.mipmap.colombia_flag_round);
                break;
            case "Ecuador":
                imageView.setImageResource(R.mipmap.ecuador_flag_round);
                break;
            case "Surinam":
                imageView.setImageResource(R.mipmap.surinam_flag_round);
                break;
            case "Guyana":
                imageView.setImageResource(R.mipmap.guyana_flag_round);
                break;
            case "Venezuela":
                imageView.setImageResource(R.mipmap.venezuela_flag_round);
                break;
            case "Chile":
                imageView.setImageResource(R.mipmap.chile_flag_round);
                break;
            default:
                imageView.setImageResource(R.mipmap.ic_launcher_round);
                break;
        }
    }
}
