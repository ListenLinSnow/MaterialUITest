package com.example.lc.materialuitest.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.util.FileUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileURITestActivity extends AppCompatActivity {

    @BindView(R.id.btn_file_uri_pick)
    Button btnPick;
    @BindView(R.id.tv_file_uri_info)
    TextView tvInfo;

    public static final int PICK_FILE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_uri);
        ButterKnife.bind(this);

        
    }

    @OnClick({R.id.btn_file_uri_pick})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_file_uri_pick:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent, PICK_FILE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_FILE:
                if (resultCode == RESULT_OK && data != null){
                    Uri fileUri = data.getData();
                    String path = fileUri.getPath();
                    String authority = fileUri.getAuthority();
                    String scheme = fileUri.getScheme();
                    String host = fileUri.getHost();
                    String fragment = fileUri.getFragment();
                    String lastPathSegment = fileUri.getLastPathSegment();
                    String query = fileUri.getQuery();
                    int port = fileUri.getPort();
                    String userInfo = fileUri.getUserInfo();
                    List<String> segmentList = fileUri.getPathSegments();
                    //Log.d("JsonList", "path:" + path + "\nauthority:" + authority + "\nscheme:" + scheme);
                    /*tvInfo.setText("path:" + path + "\nauthority:" + authority + "\nscheme:" + scheme + "\nhost:" + host + "\nfragment:" + fragment
                                    + "\nlastPathSegment:" + lastPathSegment + "\nquery:" + query + "\nport" + port + "\nuserInfo:" + userInfo);*/
                    String info = fileUri.toString() + "\npath:" + path + "\nauthority:" + authority + "\nscheme:" + scheme +"\n" ;
                    for (String s : segmentList){
                        info += s + "\n";
                    }
                    String realPath = FileUtil.getFilePathByUri(this, fileUri);
                    info += "文件真实路径：" + realPath;
                    tvInfo.setText(info);
                }
                break;
        }
    }
}
