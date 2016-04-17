package com.educar.actividad2_2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eduardo on 19/12/2015.
 */
public class CustomAdapter extends ArrayAdapter<Libro> implements Filterable {

    private int viewResourceId;
    private ArrayList<Libro> originalValues; //datos originales
    private ArrayList<Libro> filterList;



    public CustomAdapter(Activity context, int textViewResourceId, ArrayList<Libro> l) {
        super(context,textViewResourceId, l);
        viewResourceId = textViewResourceId;
        filterList = l;

    }

    /**
     * This method is executed for each row
     *
     * @param position   Number of the row 0,1,2
     * @param view is the View of row
     * @param parent     is the group of elements
     */
    // @Override
    public View getView(int position, View view, ViewGroup parent) {

        View item = view; // el View de una fila, puede haberse creado antes o no
        ViewHolder holder; // recipiente de Views

        if (view == null) {
            // Creamos un elemento inflater
            LayoutInflater inflater = ((Activity) this.getContext()).getLayoutInflater();

            // Creamos un objeto View que sea el resultado de inflar el layout de nuestra fila

            item = inflater.inflate(viewResourceId, null); //si sale

            //se crea un recipiente de views
            holder = new ViewHolder();
            holder.title=(TextView)item.findViewById(R.id.tv_tituloLista);
            holder.image=(ImageView)item.findViewById(R.id.iv_portadaLibro);
            holder.genre =(TextView)item.findViewById(R.id.tv_generoLista);
            holder.author=(TextView)item.findViewById(R.id.tv_autorLista);
            item.setTag(holder);

        }
        else
        {
            holder =(ViewHolder)item.getTag();
        }
        String t =getContext().getResources().getString(R.string.tv_titulo);
        String g =getContext().getResources().getString(R.string.tv_genero);
        String a = getContext().getResources().getString(R.string.tv_autor);
        holder.title.setText(t + "" + this.getItem(position).getTitulo());
        holder.image.setImageResource(this.getItem(position).getCodImage());
        holder.genre.setText(g + "" + this.getItem(position).getGenero());
        holder.author.setText(a + "" + this.getItem(position).getAutor());

        return item;




    }

    static class ViewHolder{
        protected TextView title;
        protected ImageView image;
       protected TextView genre;
        protected TextView author;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                clear();
                addAll((ArrayList<Libro>)results.values);  //se incorporan al adapter los valores filtrados
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                if(originalValues == null)
                {
                    originalValues = new ArrayList<>(filterList); //se guardan los datos originales
                }
                if (constraint != null && constraint.length() > 0) {
                    constraint = constraint.toString().toLowerCase();
                    ArrayList<Libro> filters = new ArrayList<>();
                    //se obtienen los objetos especificados por parametro
                    for (int i = 0; i < originalValues.size(); i++) {
                        if (originalValues.get(i).getTitulo().toLowerCase().contains(constraint.toString())
                                || originalValues.get(i).getGenero().toLowerCase().contains(constraint.toString())) {
                            filters.add(originalValues.get(i));
                        }
                    }
                    results.count = filters.size();
                    results.values = filters;

                } else {  //no se introduce ningun parametro de filtrado
                    results.count = originalValues.size();
                    results.values = originalValues;
                }

                return results;
            }


        };
        return filter;
    }
}

