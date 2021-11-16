package tcnr.com.tw.myprocessdialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

@SuppressLint("StaticFieldLeak")
public class MyProcessDialog {
    /**
     * 仿照 單例模式 的概念實作，
     * 初始化時 建立物件 並暫存，
     * 使用時 依需求改變 並顯示，
     * 但是這個做法可能較為吃 記憶體
     */
    private static AlertDialog myAlertDialog;
    private static ProgressBar bar;
    private static TextView t001;
    private static ProgressBar circular;

    private MyProcessDialog() {
    }

    public static void initialize(Context context) {
        myAlertDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogTheme).create();
        View loadView = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        myAlertDialog.setView(loadView);
        myAlertDialog.setCanceledOnTouchOutside(false);
        circular = loadView.findViewById(R.id.progressCircular);
        t001 = loadView.findViewById(R.id.textView);
        bar = loadView.findViewById(R.id.progressBar);
        bar.setMax(100);
        bar.setProgress(0);
    }

    /**
     * 必須在主執行緒上使用，不然會跳錯誤
     */
    public static void showMessage(String strText) {
        circular.setVisibility(View.VISIBLE);
        bar.setVisibility(View.GONE);
        if (!strText.equals("")) {
            t001.setText(strText);  //寫成區域變數的話，文字不會跟著變
        }
        myAlertDialog.show();
    }

    /**
     * 必須在主執行緒上使用，不然會跳錯誤
     */
    public static void showProgressBar(int size) {
        circular.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
        bar.setMax(size);
        bar.setProgress(0);
        myAlertDialog.show();
    }

    public static void addProgressBar(int size) {
        bar.incrementProgressBy(size);
    }

    public static void dismiss() {
        if (myAlertDialog != null && myAlertDialog.isShowing()) {
            myAlertDialog.dismiss();
        }
    }
}
