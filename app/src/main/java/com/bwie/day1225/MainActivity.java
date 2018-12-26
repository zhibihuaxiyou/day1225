package com.bwie.day1225;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{


    @InjectOnClickAnnotation(R.id.txt_view)
    private TextView mTextView1;

    @InjectOnClickAnnotation(value = R.id.txt_view, onClick = "click")
    public TextView mTextView2;

    /**
     * 点击事件定义
     *
     * @param view
     */
    public void click(View view) {
        mTextView1.setText("1607C");
        Toast.makeText(this, "点击了：" + mTextView1.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注解绑定
        ButterKnife.bind(this);
        // 使用下控件，么么哒
        mTextView1.setText("点击我改变当前值,并弹出吐司");
    }
}
