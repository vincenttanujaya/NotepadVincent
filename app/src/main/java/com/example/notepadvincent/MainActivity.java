package com.example.notepadvincent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private String isiText = "";
    private FileOutputStream fileOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputEditText isiNotepad = findViewById(R.id.isiNotepad);
        final Button btnSimpan = findViewById(R.id.btnSimpan);
        final Button btnCatatan = findViewById(R.id.btnCatatan);

        final View.OnClickListener NotepadListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int id = view.getId();
                switch (id){
                    case R.id.btnSimpan:
                        isiText = isiNotepad.getText().toString();
                        //Digunakan untuk membuat dan menulis File/Data pada Penyimpanan
                        try {
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("data.txt", Context.MODE_PRIVATE));
//                            outputStreamWriter.write(isiText);
                            outputStreamWriter.append(isiText);
                            outputStreamWriter.append("\n");
                            outputStreamWriter.close();
                            Toast.makeText(getApplicationContext(), "Berhasil Disimpan",Toast.LENGTH_LONG).show();
                        }
                        catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Gagal Menyimpan",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.btnCatatan:
                        String ret = "";
                        try {
                            InputStream inputStream = openFileInput("data.txt");
                            if ( inputStream != null ) {
                                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                String receiveString = "";
                                StringBuilder stringBuilder = new StringBuilder();

                                while ( (receiveString = bufferedReader.readLine()) != null ) {
                                    stringBuilder.append(receiveString);
                                }

                                inputStream.close();
                                ret = stringBuilder.toString();
                                Toast.makeText(getApplicationContext(), ret,Toast.LENGTH_LONG).show();
                                isiNotepad.setText(ret);
                            }
                        }
                        catch (FileNotFoundException e) {
                            Toast.makeText(getApplicationContext(), "Gagal Membuka",Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Gagal IO",Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        };

        btnSimpan.setOnClickListener(NotepadListener);
        btnCatatan.setOnClickListener(NotepadListener);

    }
}
