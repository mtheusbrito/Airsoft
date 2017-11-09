package nof.airsoft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Equipe;
import model.Usuario;

public class RegistroEquipeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registarEquipe;
    private EditText editText_nomeEquipe;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private Usuario endereco;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Object usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_equipe);


        registarEquipe = (Button) findViewById(R.id.registrarEquipe);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        editText_nomeEquipe = (EditText) findViewById(R.id.editText_nomeEquipe);
        registarEquipe.setOnClickListener(this);

    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }

    private void registerTeam() {
        mDatabase = FirebaseDatabase.getInstance().getReference("usuario");
        String nome = editText_nomeEquipe.getText().toString().trim();
        String idLider = databaseReference.push().getKey();

        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(this, "Por favor digite o nome", Toast.LENGTH_SHORT).show();
        }else {

            String id = mDatabase.push().getKey();
            Equipe equipe = new Equipe(id, nome, idLider);
            databaseReference.child("equipes").child(nome).setValue(equipe);
            equipe.adicionaJogador((Usuario) usuario);


            Toast.makeText(this, "Informações Salvas!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, MainActivity.class));
        }


    }

    @Override
    public void onClick(View view) {
        if(view == registarEquipe){
            registerTeam();

        }

    }
}