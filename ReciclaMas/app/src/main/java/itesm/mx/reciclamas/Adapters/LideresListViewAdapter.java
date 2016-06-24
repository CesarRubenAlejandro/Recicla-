package itesm.mx.reciclamas.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

import java.text.DecimalFormat;
import java.util.List;

import itesm.mx.reciclamas.R;
import itesm.mx.reciclamas.Values.Database;

/**
 * Created by Cesar on 6/23/2016.
 */
public class LideresListViewAdapter extends ArrayAdapter<ParseUser> {
    private Context context;
    int layoutResourceId;
    List<ParseUser> listaUsers;


    public LideresListViewAdapter(Context context, int resource, List<ParseUser> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.listaUsers = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
        }
        TextView posUsuarioTextView = (TextView) row.findViewById(R.id.pos_usuarioTV);

        posUsuarioTextView.setText((position + 1) + "   |   " + this.listaUsers.get(position).getUsername()
                + "   | kg reciclados: " + new DecimalFormat("#.##").format(this.listaUsers.get(position).get(Database.User.CANTIDAD)));
        return row;
    }
}
