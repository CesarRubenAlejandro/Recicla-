package itesm.mx.reciclamas.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import itesm.mx.reciclamas.Adapters.LideresListViewAdapter;
import itesm.mx.reciclamas.R;
import itesm.mx.reciclamas.Values.Database;

public class LideresActivity extends AppCompatActivity {
    private TextView userTextView;
    private Button volverButton;
    private ListView lideresListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lideres);
        userTextView = (TextView) findViewById(R.id.bienvenidoTV);
        volverButton = (Button) findViewById(R.id.volverBttn);
        lideresListView = (ListView) findViewById(R.id.lideresLV);

        // agregar el nombre del usuario al header
        userTextView.setText("Bienvenido, " + ParseUser.getCurrentUser().getUsername());
        // metodo para regresar a pagina de inicio
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // terminar la actividad
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        final LideresListViewAdapter miAdaptador = new LideresListViewAdapter(getApplicationContext(),R.layout.row,getOrderedUserList());
        lideresListView.setAdapter(miAdaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lideres, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<ParseUser> getOrderedUserList(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.addDescendingOrder(Database.User.CANTIDAD);
        List<ParseUser> users = new ArrayList<ParseUser>();
        try {
           users = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }
}
