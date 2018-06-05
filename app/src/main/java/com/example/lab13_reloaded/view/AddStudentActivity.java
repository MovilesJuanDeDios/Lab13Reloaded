package com.example.lab13_reloaded.view;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab13_reloaded.R;
import com.example.lab13_reloaded.data.dao.CursoEstudianteDAO;
import com.example.lab13_reloaded.data.dao.EstudianteDAO;
import com.example.lab13_reloaded.model.Estudiante;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Integer.parseInt;

public class AddStudentActivity extends AppCompatActivity {

    // UI references
    @BindView(R.id.title_student)
    TextView title;

    @BindView(R.id.id_input_layout)
    TextInputLayout id_input;
    @BindView(R.id.name_input_layout)
    TextInputLayout name_input;
    @BindView(R.id.lastname_input_layout)
    TextInputLayout lastname_input;
    @BindView(R.id.age_input_layout)
    TextInputLayout age_input;

    @BindView(R.id.id_editText)
    EditText id_edittext;
    @BindView(R.id.name_editText)
    EditText name_edittext;
    @BindView(R.id.lastname_editText)
    EditText lastname_edittext;
    @BindView(R.id.age_editText)
    EditText age_edittext;

    @BindView(R.id.spinner_layout)
    LinearLayout spinner_layout;
    @BindView(R.id.course_spinner)
    Spinner spinner;

    @BindView(R.id.next_st_button)
    Button next_button;
    @BindView(R.id.cancel_st_button)
    Button cancel_button;

    private String id;
    private String name;
    private String lastname;
    private String age;

    private EstudianteDAO estudianteDAO;

    private CursoEstudianteDAO cursoEstudianteDAO;

    private List<String> cursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        ButterKnife.bind(this);

        estudianteDAO = new EstudianteDAO();
        cursoEstudianteDAO = new CursoEstudianteDAO();

        if (getIntent().hasExtra("accion")) {
            next_button.setText("Aceptar");
            spinner_layout.setVisibility(View.VISIBLE);
            title.setText("Modificar Estudiante");
            id_edittext.setText(getIntent().getStringExtra("id"));
            id_edittext.setEnabled(false);
            name_edittext.setText(getIntent().getStringExtra("nombre"));
            lastname_edittext.setText(getIntent().getStringExtra("apellidos"));
            age_edittext.setText(Integer.toString(
                    getIntent().getIntExtra("edad", 0)));

            cursos = cursoEstudianteDAO.selectAll(getIntent().getStringExtra("id"));

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, cursos);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
        }

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddStudentActivity.this,
                        StudentListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAction();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddStudentActivity.this,
                StudentListActivity.class);
        startActivity(intent);
        finish();
    }

    public void doAction() {
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Estudiante estudiante = new Estudiante(id, name, lastname, parseInt(age));

        long result;

        // inserta al estudiante en la base de datos
        if (getIntent().hasExtra("accion")) {
            result = estudianteDAO.update(estudiante);
            if (result == 0) {
                Toast.makeText(getBaseContext(), "Update failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "Update successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddStudentActivity.this, StudentListActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            result = estudianteDAO.insert(estudiante);
            if (result == 0) {
                Toast.makeText(getBaseContext(), "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(AddStudentActivity.this, SelectCourseActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        }
    }

    private boolean validate() {

        boolean valid = true;

        id = id_edittext.getText().toString();
        name = name_edittext.getText().toString();
        lastname = lastname_edittext.getText().toString();
        age = age_edittext.getText().toString();

        // Check for a valid name
        if (age.isEmpty()) {
            age_input.setError(getString(R.string.empty_age));
            age_input.requestFocus();
            valid = false;
        } else {
            age_input.setError(null);
        }

        // Check for a valid lastname
        if (lastname.isEmpty()) {
            lastname_input.setError(getString(R.string.empty_lastname));
            lastname_input.requestFocus();
            valid = false;
        } else {
            lastname_input.setError(null);
        }

        // Check for a valid name
        if (name.isEmpty()) {
            name_input.setError(getString(R.string.empty_name));
            name_input.requestFocus();
            valid = false;
        } else {
            name_input.setError(null);
        }

        // Check for a valid name
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
