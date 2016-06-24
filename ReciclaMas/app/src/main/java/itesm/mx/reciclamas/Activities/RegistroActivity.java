package itesm.mx.reciclamas.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import itesm.mx.reciclamas.R;
import itesm.mx.reciclamas.Values.Database;
import itesm.mx.reciclamas.Values.Keys;

public class RegistroActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText correoEditText;
    private Button registroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        usernameEditText = (EditText)findViewById(R.id.usuarioET);
        passwordEditText = (EditText)findViewById(R.id.passwordET);
        correoEditText = (EditText)findViewById(R.id.emailET);
        registroButton = (Button) findViewById(R.id.registroBttn);

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // borrar alguna sesion existente
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.logOut();

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                final ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(correo);
                user.put(Database.User.CANTIDAD, 0);
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegistroActivity.this, InicioActivity.class);
                            startActivityForResult(intent, Keys.REQUEST_CODE_INICIO_ACTIVITY);
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No se pudo registrar", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Keys.REQUEST_CODE_INICIO_ACTIVITY && resultCode == Keys.RESULT_CODE_LOGOUT) {
            Toast.makeText(getApplicationContext(), "Sesión cerrada exitosamente", Toast.LENGTH_LONG).show();
            Intent intent = new Intent( RegistroActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
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
