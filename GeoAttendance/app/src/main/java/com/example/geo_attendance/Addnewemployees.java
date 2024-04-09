package com.example.geo_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Addnewemployees extends AppCompatActivity {
    FloatingActionButton add;
    RecyclerView recyclerView;
    FirebaseAuth mauth;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mydatabase;
    String currentuser;
    ProgressDialog progressDialog;
    FirebaseRecyclerAdapter<Employeemodel,EmployeeViewholder> adapter;
    FirebaseRecyclerOptions<Employeemodel> options ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewemployees);
        mauth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Addnewemployees.this);
        firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser().getUid();
        recyclerView = findViewById(R.id.allemployees);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Addnewemployees.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        fetch();
        add = findViewById(R.id.createnewemployee);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog();
            }
        });
    }
    public void fetch()
    {
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("employee");
        options = new FirebaseRecyclerOptions.Builder<Employeemodel>().setQuery(myref, Employeemodel.class).build();
        adapter = new FirebaseRecyclerAdapter<Employeemodel, EmployeeViewholder>(options) {
            @Override
            protected void onBindViewHolder( EmployeeViewholder holder, int position, Employeemodel model) {
                holder.surname.setText("Firstname"+ ":" +model.getSurname());
                holder.fname.setText("Surname" + ":" +model.getFname());
                holder.department.setText("Department" +":"+model.getDepartment());

            }


            @Override
            public EmployeeViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sampleemployee,parent,false);
                return  new EmployeeViewholder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }
    private void alertDialog() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(Addnewemployees.this);
        LayoutInflater layoutInflater = LayoutInflater.from(Addnewemployees.this);
        View myview = layoutInflater.inflate(R.layout.employeedialog, null);
        final AlertDialog dialog = mydialog.create();
        dialog.setView(myview);
        final EditText fname, lname, department, email, password;
        Button save;
        fname = myview.findViewById(R.id.fname);
        lname = myview.findViewById(R.id.lname);
        department = myview.findViewById(R.id.department);
        email = myview.findViewById(R.id.email);
        password = myview.findViewById(R.id.password);
        DatabaseReference myref;
        myref = FirebaseDatabase.getInstance().getReference();
        String key = myref.push().getKey();
        save = myview.findViewById(R.id.add);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String efname = fname.getText().toString();
                String elname = lname.getText().toString();
                String edepartment = department.getText().toString();
                String eemail = email.getText().toString();
                String epassword = password.getText().toString();

                if (TextUtils.isEmpty(efname)) {
                    fname.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(elname)) {
                    lname.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(edepartment)) {
                    department.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(eemail)) {
                    email.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(epassword)) {
                    password.setError("Required Field");
                    return;
                }
                progressDialog.setMessage("Adding Employees");
                progressDialog.show();
                mauth.createUserWithEmailAndPassword(eemail.trim(),epassword.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            FirebaseAuth newuser = FirebaseAuth.getInstance();
                            Employeemodel employeemodel= new Employeemodel(
                                    efname,
                                    elname,
                                    edepartment,
                                    eemail,
                                    epassword,
                                    newuser.getCurrentUser().getUid().toString(),
                                    "employee"
                            );
                            myref.child("employee").child(newuser.getCurrentUser().getUid().toString()).setValue(employeemodel);
                            progressDialog.dismiss();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });
            }
        });
        dialog.show();
    }
}
