package com.example.permisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumero;
    private EditText editText;
    private Button btnnumero;
    private  Button botonexplorador;

    private final int PHONE_CALL_CODE=100;
    private final int CAMERA_CALL_CODE=120;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumero=(EditText) findViewById(R.id.editTextPhone);
        editText=(EditText) findViewById(R.id.editTextTelefono);
        btnnumero=  (Button)  findViewById(R.id.btnllamar);
        botonexplorador=(Button) findViewById(R.id.btnbuscar);

        btnnumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num= editTextNumero.getText().toString();
                if(num!=null)
                {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                    {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_CODE);
                    }else {
                        versionesAnteriores(num);
                    }
                }
            }
            private  void  versionesAnteriores(String num){
                Intent intentllamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+num));
                if(verificarpermisos(Manifest.permission.CALL_PHONE))
                {
                    startActivity(intentllamada);
                }else {
                    Toast.makeText(MainActivity.this, "Configure los permisos", Toast.LENGTH_SHORT).show();
                }
            }
            private  void  versionNueva()
            {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_CALL_CODE:
                String permission = permissions[0];
                int result =grantResults[0];
                if(permission.equals(Manifest.permission.CALL_PHONE))
                {
                    if(result== PackageManager.PERMISSION_GRANTED)
                    {
                        String phonenumber = editTextNumero.getText().toString();
                        Intent llamada = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phonenumber));
                        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED) return;
                        startActivity(llamada);
                    }
                    else {
                        Toast.makeText(this, "No aceptaste el permiso", Toast.LENGTH_LONG).show();
                    }
                }
        }
    }

    private  boolean verificarpermisos (String permiso)
    {
        int resultado = this.checkCallingOrSelfPermission(permiso);
        return  resultado== PackageManager.PERMISSION_GRANTED;
    }
}