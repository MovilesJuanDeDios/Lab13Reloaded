package com.example.lab13_reloaded.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.lab13_reloaded.R;
import com.example.lab13_reloaded.data.dao.CursoDAO;
import com.example.lab13_reloaded.data.dao.CursoEstudianteDAO;
import com.example.lab13_reloaded.model.Curso;

import java.util.List;

public class SelectCourseActivity extends AppCompatActivity {

    private ArrayAdapter<Curso> adapter;
    private SwipeMenuListView listview;
    private TextView title;
    private Button add;

    private CursoDAO cursoDAO;
    private CursoEstudianteDAO cursoEstudianteDAO;

    private List<Curso> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);

        cursoDAO = new CursoDAO();
        cursoEstudianteDAO = new CursoEstudianteDAO();

        listview = (SwipeMenuListView) findViewById(R.id.select_course_list);
        title = (TextView) findViewById(R.id.select_title);
        add = (Button) findViewById(R.id.add_student);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectCourseActivity.this, StudentListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        courseList = cursoDAO.selectAll();

        adapter = new ArrayAdapter<Curso>(this, android.R.layout.simple_list_item_1, courseList);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "edit" item
                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                editItem.setBackground(R.color.colorPrimary);
                // set item width
                editItem.setWidth(140);
                // set a icon
                editItem.setIcon(R.mipmap.ic_add);
                // add to menu
                menu.addMenuItem(editItem);
            }
        };

        listview.setMenuCreator(creator);

        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        if (getIntent().hasExtra("id")) {
                            String idc = courseList.get(position).getId();
                            String ide = getIntent().getStringExtra("id");
                            cursoEstudianteDAO.insert(ide, idc);
                            courseList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                }
                return false;
            }
        });
    }
}
