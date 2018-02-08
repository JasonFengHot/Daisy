package tv.ismar.daisy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by huibin on 30/01/2018.
 */

public class BaseActivity extends AppCompatActivity {
    protected SkyService mSkyHostService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeService();

    }

    private void initializeService() {
        mSkyHostService = SkyService.ServiceManager.getInstance().getSkyHostService();
    }
}
