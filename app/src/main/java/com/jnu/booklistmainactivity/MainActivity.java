package com.jnu.booklistmainactivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.booklistmainactivity.data.Book;
import com.jnu.booklistmainactivity.data.DataBank;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_CODE_ADD_DATA = 996;
    private List<Book> books;
    private MyRecyclerViewAdapter recycleViewAdapter;
    private DataBank dataBank;

     ActivityResultLauncher<Intent> launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        public void onActivityResult(ActivityResult result){
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_CODE_ADD_DATA) {
                if(null==data) return;
                String name = data.getStringExtra("name");  //String类型的，为空系统自己为我们设置为NULL
                int position = data.getIntExtra("position", books.size());  //将传过去的位置传回来
                books.add(position, new Book(name, R.drawable.book_no_name));
                dataBank.saveData();
                recycleViewAdapter.notifyItemInserted(position);
            }
        }
    });
     ActivityResultLauncher<Intent> launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        public void onActivityResult(ActivityResult result){
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_CODE_ADD_DATA) {
                if(null==data) return;
                String name = data.getStringExtra("name");  //String类型的，为空系统自己为我们设置为NULL
                int position = data.getIntExtra("position", books.size());  //将传过去的位置传回来
                books.get(position).setName(name);
                dataBank.saveData();
                recycleViewAdapter.notifyItemChanged(position);
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        RecyclerView mainRecycleView = findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);
        recycleViewAdapter = new MyRecyclerViewAdapter(books);
        mainRecycleView.setAdapter(recycleViewAdapter);
    }

    public void initData() {   //直接创建一个文件夹用于存储数据
        dataBank= new DataBank(MainActivity.this);
        books=dataBank.loadDate();
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<Book> books;

        public MyRecyclerViewAdapter(List<Book> books) {
            this.books = books;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemlayout, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyViewHolder Holder = (MyViewHolder) holder;

            Holder.getImageViewBook().setImageResource(books.get(position).getCoverResourceId());
            Holder.getTextViewLookTitle().setText(books.get(position).getTitle() + "");
        }

        @Override
        public int getItemCount() {
            return books.size();
        }


        private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            public static final int CONTEXT_MENU_ID_ADD = 1;
            public static final int CONTEXT_MENU_ID_UPDATE =CONTEXT_MENU_ID_ADD+1;
            public static final int CONTEXT_MENU_ID_DELETE = CONTEXT_MENU_ID_ADD+2;
            private final ImageView imageviewbook;
            private final TextView textviewlooktitle;

            public MyViewHolder(View view) {
                super(view);
                this.imageviewbook = view.findViewById(R.id.image_view_book_cover);
                this.textviewlooktitle = view.findViewById(R.id.text_view_book_title);

                view.setOnCreateContextMenuListener(this);
            }

            public ImageView getImageViewBook() {
                return imageviewbook;
            }

            public TextView getTextViewLookTitle() {
                return textviewlooktitle;
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                int position = getAdapterPosition();
                MenuItem menuItemAdd = menu.add(Menu.NONE,  CONTEXT_MENU_ID_ADD,  CONTEXT_MENU_ID_ADD, "Add" + position);
                MenuItem menuItemEdit = menu.add(Menu.NONE, CONTEXT_MENU_ID_UPDATE, CONTEXT_MENU_ID_UPDATE, "Edit" + position);
                MenuItem menuItemDelete = menu.add(Menu.NONE, CONTEXT_MENU_ID_DELETE, CONTEXT_MENU_ID_DELETE, "Delete");

                menuItemAdd.setOnMenuItemClickListener(this);
                menuItemEdit.setOnMenuItemClickListener(this);
                menuItemDelete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int position = getAdapterPosition();
                switch (item.getItemId()) {
                    case CONTEXT_MENU_ID_ADD:
                        Intent intent=new Intent(MainActivity.this,inputActivity.class);  //直接在一个新的窗口里面添加，添加按钮的界面
                        intent.putExtra("position",position);
                        launcherAdd.launch(intent);
                       // MainActivity.this.startActivityForResult(intent, REQUEST_CODE_ADD_ID);
                        break;
//                        books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
//                        MyRecyclerViewAdapter.this.notifyItemInserted(position+1);
                    case CONTEXT_MENU_ID_UPDATE:
                        intent=new Intent(MainActivity.this,inputActivity.class);  //直接在一个新的窗口里面添加，添加按钮的界面
                        intent.putExtra("position",position);
                        intent.putExtra("name",books.get(position).getTitle());
                        launcherEdit.launch(intent);
                        break;
                    case CONTEXT_MENU_ID_DELETE:
                        books.remove(position);
                        dataBank.saveData();
                        MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                        break;
                }
                Toast.makeText(MainActivity.this, "点击了" + item.getItemId(), Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
}

