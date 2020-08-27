package com.example.lattice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class DisplayUser extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Getting the list of all users list
        AppDatabase db = AppDatabase.getInstance(DisplayUser.this);
        List<User> Ulist = (List<User>) db.userDao().getUsersList();

        //Intialiazing recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayUser.this));
        recyclerView.setHasFixedSize(true);

        //Setting the  adapter for the recyclerview
        UserAdapter adapter = new UserAdapter(Ulist);
        recyclerView.setAdapter(adapter);

    }

    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.PSViewHolder>{

        private List<User> UserList;

        public UserAdapter(List<User> list) {
            this.UserList = list;
        }

        @NonNull
        @Override
        public UserAdapter.PSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(DisplayUser.this);
            View view = layoutInflater.inflate(R.layout.user_card , parent , false);
            return new UserAdapter.PSViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final PSViewHolder holder, int position) {
            final User user = UserList.get(position);

            progressBar.setVisibility(View.INVISIBLE);
            holder.name.setText(user.getName());
            holder.email.setText(user.getEmail());
            holder.phone.setText(user.getPhone());
            holder.password.setText(user.getPassword());
            holder.address.setText(user.getAddress());
        }



        @Override
        public int getItemCount() {
            return UserList.size();
        }

        class PSViewHolder extends RecyclerView.ViewHolder{

            ImageView image;
            TextView name, email, password , address , phone;

            public PSViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.map);
                name = itemView.findViewById(R.id.name);
                email = itemView.findViewById(R.id.email);
                password = itemView.findViewById(R.id.password);
                address = itemView.findViewById(R.id.address);
                phone = itemView.findViewById(R.id.phone);
            }
        }
    }

}