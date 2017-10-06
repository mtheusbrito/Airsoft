package nof.airsoft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroEquipeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registarEquipe;
    private EditText editText_nome;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private EquipeInformation endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_equipe);

        registarEquipe = (Button) findViewById(R.id.registrarEquipe);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){

                    startActivity(new Intent(getApplicationContext(), MinhaEquipeActivity.class));

                }

            }
        };
        progressDialog = new ProgressDialog(this);
        editText_nome = (EditText) findViewById(R.id.editText_nome);
        registarEquipe.setOnClickListener(this);

    }

    private void registerTeam() {
        String nome = editText_nome.getText().toString().trim();

        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(this, "Por favor digite o nome", Toast.LENGTH_SHORT).show();
            return;
        }else {

            progressDialog.setMessage("Registrando Equipe...");
            progressDialog.show();

            EquipeInformation equipeInformation = new EquipeInformation(nome);
            equipeInformation.getNome(nome);
            mDatabase.child("Equipe").push();
            mDatabase.setValue(equipeInformation);

        }

    }






    @Override
    public void onClick(View view) {
        if(view == registarEquipe){
            registerTeam();

        }

    }
}