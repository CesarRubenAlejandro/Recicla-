package itesm.mx.reciclamas.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import itesm.mx.reciclamas.R;
import itesm.mx.reciclamas.Values.Keys;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registroTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = (EditText)findViewById(R.id.usuarioET);
        passwordEditText = (EditText)findViewById(R.id.passwordET);
        loginButton = (Button) findViewById(R.id.loginBttn);
        registroTextView = (TextView) findViewById(R.id.registroTV);

        SpannableString content = new SpannableString("Registrate");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        registroTextView.setText(content);

        // metodo login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // logear al usuario
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                            startActivityForResult(intent, Keys.REQUEST_CODE_INICIO_ACTIVITY);
                        } else {
                            // Signup failed. Look at the ParseException to see what happened.
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error al iniciar sesion", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        // enviar a actividad de registro al hacer click en text view
        registroTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Keys.REQUEST_CODE_INICIO_ACTIVITY && resultCode == Keys.RESULT_CODE_LOGOUT) {
            Toast.makeText(getApplicationContext(), "Sesión cerrada exitosamente", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
