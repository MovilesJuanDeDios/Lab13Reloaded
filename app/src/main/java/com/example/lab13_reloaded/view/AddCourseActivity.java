package com.example.lab13_reloaded.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab13_reloaded.R;
import com.example.lab13_reloaded.data.dao.CursoDAO;
import com.example.lab13_reloaded.model.Curso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Integer.parseInt;

public class AddCourseActivity extends AppCompatActivity {

    // UI references
    @BindView(R.id.title_course)
    TextView title;

    @BindView(R.id.idcourse_input_layout)
    TextInputLayout id_input;
    @BindView(R.id.desc_input_layout)
    TextInputLayout desc_input;
    @BindView(R.id.creditos_input_layout)
    TextInputLayout cred_input;

    @BindView(R.id.idcourse_editText)
    EditText id_edittext;
    @BindView(R.id.desc_editText)
    EditText desc_edittext;
    @BindView(R.id.creditos_editText)
    EditText creditos_edittext;

    @BindView(R.id.accept_cr_button)
    Button accept_button;
    @BindView(R.id.cancel_cr_button)
    Button cancel_button;

    private String id;
    private String desc;
    private String creditos;

    private CursoDAO cursoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        ButterKnife.bind(this);

        cursoDAO = new CursoDAO();

        if (getIntent().hasExtra("accion")) {
            title.setText("Modificar Curso");
            id_edittext.setText(getIntent().getStringExtra("id"));
            id_edittext.setEnabled(false);
            desc_edittext.setText(getIntent().getStringExtra("descripcion"));
            creditos_edittext.setText(Integer.toString(
                    getIntent().getIntExtra("creditos", 0)));
        }

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCourseActivity.this,
                        CourseListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAction();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCourseActivity.this,
                CourseListActivity.class);
        startActivity(intent);
        finish();
    }

    public void doAction() {
        if (!validate()) {
            onActionFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(AddCourseActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Actualizando información...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Curso curso = new Curso(id, desc, parseInt(creditos));
                        long result;
                        // inserta el usuario en la base de datos
                        if (getIntent().hasExtra("accion")) {
                            result = cursoDAO.update(curso);
                        } else {
                            result = cursoDAO.insert(curso);
                        }

                        if (result == 0) {
                            onActionFailed();
                        } else {
                            onActionSuccess();
                        }

                        progressDialog.dismiss();
                    }
                }, 1500);
    }

    public void onActionSuccess() {
        Toast.makeText(getBaseContext(), "Update Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddCourseActivity.this, CourseListActivity.class);
        startActivity(intent);
        finish();
    }

    public void onActionFailed() {
        Toast.makeText(getBaseContext(), "Update failed", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {

        boolean valid = true;

        id = id_edittext.getText().toString();
        desc = desc_edittext.getText().toString();
        creditos = creditos_edittext.getText().toString();

        if (creditos.isEmpty()) {
            cred_input.setError(getString(R.string.empty_cred));
            cred_input.requestFocus();
            valid = false;
        } else {
            cred_input.setError(null);
        }


        if (desc.isEmpty()) {
            desc_input.setError(getString(R.string.empty_desc));
            desc_input.requestFocus();
            valid = false;
        } else {
            desc_input.setError(null);
        }

        if (id.isEmpty()) {
            id_input.setError(getString(R.string.empty_id));
            id_input.requestFocus();
            valid = false;
        } else {
            id_input.setError(null);
        }

        return valid;
    }
}
