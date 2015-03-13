package com.kiory.jsonparserexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kiory.jsonparserexample.datastructure.Armor;
import com.kiory.jsonparserexample.datastructure.GameCharacter;
import com.kiory.jsonparserexample.utils.JsonParser;


public class MainActivity extends ActionBarActivity {
    private static String LOG_TAG = MainActivity.LOG_TAG;

    private final static String json = "{\n" +
            "character: \"Arakina\",\n" +
            "class: \"Death Knigth\",\n" +
            "level: \"100\",\n" +
            "race: \"Gobblin\",\n" +
            "gold: \"823759375435\",\n" +
            "armor: \n" +
            "[\n" +
            "{\n" +
            "name: \"Molten Edge Eviscerator\",\n" +
            "ilevel: \"676\"\n" +
            "},\n" +
            "{\n" +
            "name: \"Thunder maze\",\n" +
            "ilevel: \"456\"\n" +
            "},\n" +
            "{\n" +
            "name: \"The Black Hand\",\n" +
            "ilevel: \"668\"\n" +
            "},\n" +
            "{\n" +
            "name: \"Gar'an's Brutal Spearlauncher\",\n" +
            "ilevel: \"778\"\n" +
            "},\n" +
            "{\n" +
            "name: \"Oregorger's Acid-Etched Gutripper\",\n" +
            "ilevel: \"566\"\n" +
            "}\n" +
            "]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            GameCharacter c = new GameCharacter();
            c = (GameCharacter) JsonParser.parse(c, json);

            Log.e(LOG_TAG, "Character name: "+c.getCharacter());
            Log.e(LOG_TAG, "Character class: "+c.getClassCharacter());
            Log.e(LOG_TAG, "Character race: "+c.getRace());
            Log.e(LOG_TAG, "Character level: "+c.getLevel());
            Log.e(LOG_TAG, "Character gold: "+c.getGold());
            Log.e(LOG_TAG, "Character armor:");

            for(Armor a : c.getArmor()){
                Log.e(LOG_TAG, a.getName() + " (iLevel: "+a.getIlevel()+")");
            }
            return rootView;
        }
    }
}
