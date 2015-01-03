package br.com.tairoroberto.testeapiyoutubeyoutubeintents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,AdapterView.OnItemClickListener {

    //comando para ver a fingerkey do android do mau pc
    //keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android

    //Key de acesso a API do Youtube
    private static final String API_KEY = "AIzaSyD4Js_iq2RGckd0qKFktvBQVKHhjIvnHX4";

    //Id do video do youtube que será carregado
    //https://www.youtube.com/watch?v=VZoImzA5iO4
    private String ID_VIDEO = "nQxTmPqGFF0";

    //Id do canal do youtube
    private String ID_CHANNEL = "panicobass";

    //Id da Playlist do youtube
    private String ID_PLAYLIST = "RDnQxTmPqGFF0";

    //Lista de videos
    List<YouTubeItem> list;

    //Constatantes para os videos
    private static final int PLAY_VIDEO = 1;
    private static final int OPEN_PLAYLIST = 2;
    private static final int PLAY_PLAYLIST = 3;
    private static final int OPEN_CHANNEL = 4;
    private static final int SEARCH_IN_CHANNEL = 5;
    private static final int UPLOAD_VIDEO = 6;


    private YouTubePlayerView youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayer = (YouTubePlayerView)findViewById(R.id.youTubePalyer);
        youTubePlayer.setVisibility(View.GONE);
        //youTubePlayer.initialize(API_KEY,this);

        //inicializa a lista de videos
        list = new ArrayList<YouTubeItem>();
        list.add(new YouTubeItem(PLAY_VIDEO,"Play Video YouTube"));
        list.add(new YouTubeItem(OPEN_PLAYLIST,"Open PlayList YouTube"));
        list.add(new YouTubeItem(PLAY_PLAYLIST,"Play PlayList YouTube"));
        list.add(new YouTubeItem(OPEN_CHANNEL,"Open Channel YouTube"));
        list.add(new YouTubeItem(SEARCH_IN_CHANNEL,"Search in Channel YouTube"));
        list.add(new YouTubeItem(UPLOAD_VIDEO,"Upload Video YouTube"));

        //Chama o listview e adiciona um alista a ele
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new YouTubeItemAdapter(this,list));
        listView.setOnItemClickListener(this);
    }




    public void callYouTube(View view){
        //Uri do video
        Uri uri = Uri.parse("https://www.youtube.com/watch?v=VZoImzA5iO4");

        //pega o paramentro que separa o id do video
        uri = Uri.parse("vnd.youtube:"+uri.getQueryParameter("v"));

        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //quando tem sucesso
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean loadAgain) {
        Log.i("Script", "Teste com api do YouTube Raiz 1");
        //verfica se vide já foi carregado
        if (!loadAgain){
            Log.i("Script","Teste com api do YouTube Raiz 2");
            youTubePlayer.cueVideo(ID_VIDEO);
        }
    }

    //quando falha
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "onInitializationFailure()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (list.get(position).getIntentId()){
            case PLAY_VIDEO:
                if (YouTubeIntents.canResolvePlayVideoIntent(this)){
                    intent = YouTubeIntents.createPlayVideoIntentWithOptions(this, ID_VIDEO, true, true);
                    startActivity(intent);
                }
                break;
            case  OPEN_PLAYLIST:
                if (YouTubeIntents.canResolveOpenPlaylistIntent(this)){
                    intent = YouTubeIntents.createOpenPlaylistIntent(this, ID_PLAYLIST);
                    startActivity(intent);
                }
                break;
            case  PLAY_PLAYLIST:
                if (YouTubeIntents.canResolvePlayPlaylistIntent(this)){
                    intent = YouTubeIntents.createPlayPlaylistIntent(this, ID_PLAYLIST);
                    startActivity(intent);
                }
                break;
            case  OPEN_CHANNEL:
                if (YouTubeIntents.canResolveUserIntent(this)){
                    intent = YouTubeIntents.createUserIntent(this, ID_CHANNEL);
                    startActivity(intent);
                }
                break;
            case  SEARCH_IN_CHANNEL:
                if (YouTubeIntents.canResolveSearchIntent(this)){
                    intent = YouTubeIntents.createSearchIntent(this, ID_CHANNEL);
                    startActivity(intent);
                }
                break;
            case  UPLOAD_VIDEO:
                if (YouTubeIntents.canResolveUploadIntent(this)){
                    intent = new Intent(Intent.ACTION_PICK,null);
                    intent.setType("video/*");
                    startActivityForResult(intent,1);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Intent intent = YouTubeIntents.createUploadIntent(this,data.getData());
            startActivity(intent);
        }
    }
}
