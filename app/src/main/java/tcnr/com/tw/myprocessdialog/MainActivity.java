package tcnr.com.tw.myprocessdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyProcessDialog.initialize(MainActivity.this);

        EditText ePDM = findViewById(R.id.editProcessDialogMax);
        EditText ePDA = findViewById(R.id.editProcessDialogAdd);
        EditText ePDT = findViewById(R.id.editProcessDialogText);

        Button showDialog = findViewById(R.id.showDialog);
        showDialog.setOnClickListener(v -> {
            Log.d("showDialog", "ePDM:" + ePDM.getText().toString());
            Log.d("showDialog", "ePDA:" + ePDA.getText().toString());
            Log.d("showDialog", "ePDT:" + ePDT.getText().toString());

            String inputText = getString(R.string.loading);

            int inputMax = 100;
            int inputProcess = 1;
            boolean useProcessBar = false;

            if (!ePDT.getText().toString().trim().equals("")) {
                inputText = ePDT.getText().toString();
            }
            if (!ePDM.getText().toString().trim().equals("")) {
                inputMax = Integer.parseInt(ePDM.getText().toString());
                useProcessBar = true;
            }
            if (!ePDA.getText().toString().trim().equals("")) {
                inputProcess = Integer.parseInt(ePDA.getText().toString());
                useProcessBar = true;
            }

            /*如果都沒有輸入，仍然要會顯示，而且只要有改變文字，就要顯示改變後的文字*/
            MyProcessDialog.showMessage(inputText);
            if (useProcessBar) {
                MyProcessDialog.showProgressBar(inputMax);

                int intLoop = inputMax / inputProcess + 1;
                int finalInputProcess = inputProcess;
                Log.d("showDialog", "intLoop:" + intLoop);

                new Thread(() -> {
                    try {
                        for (int i = 0; i < intLoop; i++) {
                            MyProcessDialog.addProgressBar(finalInputProcess);

                            Thread.sleep(50);//暫停0.05秒，表現差異
                        }
                    } catch (Exception e) {
                        Log.e("showDialog", "Exception:" + e.toString());
                    }
                    MyProcessDialog.dismiss();
                }).start();
            } else {
                new Thread(() -> {
                    try {
                        Thread.sleep(5 * 1000);//Dialog 開啟後，暫停 5秒，再關閉 Dialog
                    } catch (Exception e) {
                        Log.e("showDialog", "Exception:" + e.toString());
                    }
                    MyProcessDialog.dismiss();
                }).start();
            }
        });
    }
}