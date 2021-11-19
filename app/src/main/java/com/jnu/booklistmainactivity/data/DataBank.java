package com.jnu.booklistmainactivity.data;

import android.content.Context;

import com.jnu.booklistmainactivity.MainActivity;
import com.jnu.booklistmainactivity.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBank{
    public static final String DATA_FILE_NAME = "data";
    private final Context context;
    List<Book> BookList;

    public DataBank(Context context) {
        this.context=context;
    }

    public List<Book> loadDate() {    //加载报春的文件/数据
        BookList=new ArrayList<>();
        BookList.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        BookList.add(new Book("创新工程实践", R.drawable.book_no_name));
        BookList.add(new Book("信息安全数学基础（第二版）" , R.drawable.book_1));
        try{
            ObjectInputStream objectInputStream=new ObjectInputStream(context.openFileInput(DATA_FILE_NAME));
            BookList=(ArrayList<Book>)objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return BookList;
    }

    public void saveData() {   //保存当前的数据，下一次打开显示前面修改过的数据
        ObjectOutputStream objectOutputStream=null;
        try{
            objectOutputStream=new ObjectOutputStream(context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(BookList);
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if (objectOutputStream!=null){
                    objectOutputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
