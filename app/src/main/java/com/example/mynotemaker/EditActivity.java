package com.example.mynotemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class EditActivity extends AppCompatActivity {

    EditText etName, etText;
    ImageButton btnSave;

    MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName=findViewById(R.id.etName);
        etText=findViewById(R.id.etText);
        btnSave=findViewById(R.id.btnSave);

        dbHelper=new MyDbHelper(this);

        String fileName=getIntent().getStringExtra("fileName");
        if(!fileName.equals("new")){

            etName.setText(fileName);
            try {
                FileInputStream fis=openFileInput(fileName);
                int c;
                StringBuilder sb=new StringBuilder();
                while( (c=fis.read())!=-1 ){
                    sb.append((char)c);
                }
                etText.setText(sb.toString());
            }
            catch(Exception e){
                Toast.makeText(EditActivity.this, "Error Reading Note", Toast.LENGTH_SHORT).show();
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=etName.getText().toString();
                String text=etText.getText().toString();

                if(name.equals("") || text.equals("")){
                    Toast.makeText(EditActivity.this, "Enter Data!", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.update(name);
                try{
                    FileOutputStream fos=openFileOutput(name, MODE_PRIVATE);
                    fos.write(text.getBytes());
                    fos.close();
                }
                catch(Exception e){
                    Toast.makeText(EditActivity.this, "Error Saving", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(EditActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
