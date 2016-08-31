package zhou.floatlayout;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends SherlockFragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            addViews(view);
        }

        void addViews(View root) {
            String characters = "Now, if you do need it to be random, we first note a generated sequence of numbers that contain no duplicates can not be called random. :p Now that we get that out of the way, the fastest way to do this is to have a Hashtable or HashMap containing all the IDs already generated. Whenever a new ID is generated, check it against the hashtable, re-generate if the ID already occurs. This will generally work well if the number of students is much less than the range of the IDs. If not, you're in deeper trouble as the probability of needing to regenerate an ID increases, P(generate new ID) = number_of_id_already_generated / number_of_all_possible_ids. In this case, check back the first paragraph (do you need the ID to be random?).";
            FlowFrameLayout flowLayout = (FlowFrameLayout) root.findViewById(R.id.flow_layout);
            Random r = new Random(13);
            for (int i = 0; i < 20; i++) {
                int len = r.nextInt(10);
                String s = generateString(r, characters, len);
                System.out.println(s);
                s = s + " " + i + " ";
                TextView text = new TextView(getActivity());
                text.setText(s);
                text.setTextSize(20);
                text.setBackgroundColor(Color.DKGRAY);
                flowLayout.setupChild(text, i);
            }
            flowLayout.requestLayout();
        }

        public static String generateString(Random rng, String characters, int length) {
            char[] text = new char[length];
            for (int i = 0; i < length; i++) {
                text[i] = characters.charAt(rng.nextInt(characters.length()));
            }
            return new String(text);
        }
    }

}
