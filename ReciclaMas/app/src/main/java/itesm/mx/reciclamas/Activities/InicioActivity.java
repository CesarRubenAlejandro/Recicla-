package itesm.mx.reciclamas.Activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import itesm.mx.reciclamas.R;
import itesm.mx.reciclamas.Values.Database;
import itesm.mx.reciclamas.Values.Keys;

public class InicioActivity extends AppCompatActivity {
    private Button nuevoButton;
    private Button lideresButton;
    private Button logoutButton;
    private TextView userTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        nuevoButton = (Button) findViewById(R.id.nuevoRegistroBttn);
        lideresButton = (Button) findViewById(R.id.lideresBttn);
        logoutButton = (Button) findViewById(R.id.logoutBttn);
        userTextView = (TextView) findViewById(R.id.bienvenidoTV);

        // agregar el nombre del usuario al header
        userTextView.setText("Bienvenido, " + ParseUser.getCurrentUser().getUsername());

        // funcionalidad de logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.getCurrentUser().logOut();
                Intent intent = new Intent();
                setResult(Keys.RESULT_CODE_LOGOUT, intent);
                finish();
            }
        });

        // ir a activity de nuevo registro
        nuevoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, NuevoActivity.class);
                startActivityForResult(intent, Keys.REQUEST_CODE_NUEVO_ACTIVITY);
            }
        });

        // ir a activity de lideres
        lideresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, LideresActivity.class);
                startActivityForResult(intent, Keys.REQUEST_CODE_LIDERES_ACTIVITY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == Keys.REQUEST_CODE_NUEVO_ACTIVITY) {
            Toast.makeText(getApplicationContext(), "Registro de reciclaje guardado", Toast.LENGTH_LONG).show();
        }

        if (resultCode == RESULT_OK && requestCode == Keys.REQUEST_CODE_LIDERES_ACTIVITY) {
            // todo
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
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
}
