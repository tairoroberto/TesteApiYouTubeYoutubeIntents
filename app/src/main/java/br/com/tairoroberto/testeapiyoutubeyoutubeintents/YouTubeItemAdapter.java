package br.com.tairoroberto.testeapiyoutubeyoutubeintents;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tairo on 03/01/15.
 */
public class YouTubeItemAdapter extends BaseAdapter {

    private Context context;
    private List<YouTubeItem> list;

    public YouTubeItemAdapter(Context context, List<YouTubeItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(list.get(position).getLabel());

        return textView;
    }
}
