package com.example.lab13_reloaded.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.lab13_reloaded.R;
import com.example.lab13_reloaded.data.dao.CursoDAO;
import com.example.lab13_reloaded.model.Curso;
import com.example.lab13_reloaded.utils.SharedPref;

import java.util.List;

public class CourseListActivity extends BaseActivity {

    private ArrayAdapter<Curso> adapter;
    private SwipeMenuListView listview;
    private FloatingActionButton fab;

    private CursoDAO cursoDAO;

    private List<Curso> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setDisplayUseLogoEnabled(false);
        //actionBar.setDisplayShowHomeEnabled(false);

        cursoDAO = new CursoDAO();

        listview = (SwipeMenuListView) findViewById(R.id.course_list);
        fab = (FloatingActionButton) findViewById(R.id.fab_course);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseListActivity.this, AddCourseActivity.class);
                intent.putExtra("action", "insert");
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
                editItem.setWidth(150);
                // set a icon
                editItem.setIcon(R.mipmap.ic_action_edit);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(R.color.colorPrimary);
                // set item width
                deleteItem.setWidth(150);
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_action_delete);
                // add to menu
                menu.addMenuItem(deleteItem);

            }
        };

        listview.setMenuCreator(creator);

        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String id = courseList.get(position).getId();
                String descripcion = courseList.get(position).getDescripcion();
                int creditos = courseList.get(position).getCreditos();

                switch (index) {
                    case 0:  // update
                        Intent intent = new Intent(CourseListActivity.this, AddCourseActivity.class);

                        intent.putExtra("id", id);
                        intent.putExtra("descripcion", descripcion);
                        intent.putExtra("creditos", creditos);
                        intent.putExtra("accion", "update");

                        startActivity(intent);
                        finish();
                        break;

                    case 1: // delete
                        courseList.remove(position);
                        cursoDAO.delete(id);
                        adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app_bar, menu);

        // si el usuario ya inicio sesion
        if (!SharedPref.getString(this, getString(R.string.pref_username)).equals("")) {
            MenuItem logout = menu.findItem(R.id.icon_logout);
            logout.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.icon_logout:
                SharedPref.clear(this);
                Intent intent = new Intent(CourseListActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getBaseContext(), "Logout", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
