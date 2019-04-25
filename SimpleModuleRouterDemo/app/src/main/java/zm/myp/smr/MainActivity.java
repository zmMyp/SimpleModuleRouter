package zm.myp.smr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import zm.myp.smr.source.SmrParamstBody;
import zm.myp.smr.source.SmrRequestClient;
import zm.myp.smr.source.base.SmrResponseCallBack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       button=(Button) findViewById(R.id.btn);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==button){

            SmrRequestClient.call(this, "kb51://module1/fun1",
                    new SmrParamstBody().set("p1", "我是主壳传来的数据").set("p2","data2"),
                    new SmrResponseCallBack() {
                        @Override
                        public void response(Object data) throws Exception {

                            Log.v("smr1","response->"+((SmrParamstBody)data).get("md1"));
                        }
                    });

        }


    }


}
