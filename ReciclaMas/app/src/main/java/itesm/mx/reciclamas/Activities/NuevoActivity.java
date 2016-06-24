package itesm.mx.reciclamas.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

import itesm.mx.reciclamas.R;
import itesm.mx.reciclamas.Values.Database;
import itesm.mx.reciclamas.Values.Keys;

public class NuevoActivity extends AppCompatActivity {
    private TextView userTextView;
    private Spinner tipoMaterialSpinner;
    private ImageView evidenciaImageView;
    private ImageButton evidenciaButton;
    private Button guardarButton;
    private EditText cantidadEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        userTextView = (TextView) findViewById(R.id.bienvenidoTV);
        tipoMaterialSpinner = (Spinner) findViewById(R.id.materialesSpnr);
        evidenciaImageView = (ImageView) findViewById(R.id.evidenciaIV);
        evidenciaButton = (ImageButton) findViewById(R.id.evidenciaBttn);
        guardarButton = (Button) findViewById(R.id.guardarbttn);
        cantidadEditText = (EditText) findViewById(R.id.cantidadET);

        // agregar el nombre del usuario al header
        userTextView.setText("Bienvenido, " + ParseUser.getCurrentUser().getUsername());

        // poblar el spinner de material
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.materiales_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoMaterialSpinner.setAdapter(adapter);

        // metodo para tomar la foto de evidencia
        evidenciaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // validar que el dispositivo tenga camara
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, Keys.REQUEST_CODE_NUEVO_ACTIVITY);
                }
            }
        });

        // metodo para guardar el registro
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cantidad = Double.parseDouble(cantidadEditText.getText().toString());
                String tipoMaterial = tipoMaterialSpinner.getSelectedItem().toString();

                // guardar en la BD el registro
                ParseObject registroObject = new ParseObject(Database.REGISTRO);
                registroObject.put(Database.Registro.CANTIDAD, cantidad);
                registroObject.put(Database.Registro.TIPO, tipoMaterial);
                registroObject.put(Database.Registro.USUARIO, ParseUser.getCurrentUser());

                // convertir la imagen bitmap a file de Parse
                Bitmap imagenEvidencia =  ((BitmapDrawable)evidenciaImageView.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagenEvidencia.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytearray = stream.toByteArray();
                ParseFile evidencia = new ParseFile(bytearray);
                try {
                    evidencia.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                registroObject.put(Database.Registro.EVIDENCIA, evidencia);
                // guardar el registro
                try {
                    registroObject.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // actualizar la cantidad registrada de el usuario actual
                double cantidadAnterior;
                try{
                    cantidadAnterior = (double) ParseUser.getCurrentUser().get(Database.User.CANTIDAD);
                } catch (ClassCastException e){
                    cantidadAnterior = (int) ParseUser.getCurrentUser().get(Database.User.CANTIDAD) + 0.0;
                }

                ParseUser.getCurrentUser().put(Database.User.CANTIDAD, cantidadAnterior + cantidad);
                try {
                    ParseUser.getCurrentUser().save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // terminar la actividad
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Keys.REQUEST_CODE_NUEVO_ACTIVITY && resultCode == RESULT_OK) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            evidenciaImageView.setImageBitmap(bp);
        }
    }
}
