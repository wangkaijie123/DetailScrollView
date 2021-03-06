package com.levylin.detailscrollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.levylin.detailscrollview.views.DetailScrollView;
import com.levylin.detailscrollview.views.DetailX5WebView;

import java.util.ArrayList;
import java.util.List;

public class X5WebViewListViewActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> list;
    private ArrayAdapter<String> adapter;
    private String longUrl = "http://m.leju.com/tg/toutiao/info.html?city=xm&id=6253374704485472431&source=ttsy&source_ext=ttsy";
    private String shortUrl = "file:///android_asset/test";
    private DetailX5WebView webView;
    private DetailScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_x5webview_listview);

        mScrollView = (DetailScrollView) findViewById(R.id.test_sv);

        ListView listView = (ListView) findViewById(R.id.test_lv);
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("测试:" + i);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, list);
        listView.setAdapter(adapter);


        webView = (DetailX5WebView) findViewById(R.id.test_webview);
        webView.loadUrl(longUrl);

        findViewById(R.id.move_to_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_to_list:
                mScrollView.toggleScrollToListView();
                break;
        }
    }
}
