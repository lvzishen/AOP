package u.com.activitystarterannotation;


import android.app.Activity;
import android.os.Bundle;

import u.com.annotations.Builder;
import u.com.annotations.Optional;
import u.com.annotations.Required;


@Builder
public class MainActivity extends Activity {

    @Required
    private String name;

    @Required
    private int age;

    @Optional(stringValue = "company")
    private String company;

    @Optional(floatValue = 1.8f)
    private float height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
