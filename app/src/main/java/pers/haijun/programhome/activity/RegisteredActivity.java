package pers.haijun.programhome.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import pers.haijun.programhome.R;
import pers.haijun.programhome.base.BaseActivity;
import pers.haijun.programhome.mvp.register.IRegisteredView;
import pers.haijun.programhome.mvp.register.RegisteredPresenter;
import pers.haijun.programhome.utils.LoadingUtils;
import pers.haijun.programhome.utils.ToastUtil;

/**
 *                     _ooOoo_
 *                    o8888888o
 *                    88" . "88
 *                    (| -_- |)
 *                    O\  =  /O
 *                 ____/`---'\____
 *               .'  \\|     |//  `.
 *              /  \\|||  :  |||//  \
 *             /  _||||| -:- |||||-  \
 *             |   | \\\  -  /// |   |
 *             | \_|  ''\---/''  |   |
 *             \  .-\__  `-`  ___/-. /
 *           ___`. .'  /--.--\  `. . __
 *        ."" '<  `.___\_<|>_/___.'  >'"".
 *       | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *       \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                     `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *              佛祖保佑       永无BUG
 * Created by HaiJun on 2017/11/13 0013 21:39
 */
public class RegisteredActivity extends BaseActivity implements IRegisteredView{
    @ViewInject(R.id.imgBtnLeft)
    private ImageButton imageButton;
    @ViewInject(R.id.txtTitle)
    private TextView txtTitle;
    @ViewInject(R.id.edtUserPhone)
    private EditText edtUserPhone;
    @ViewInject(R.id.edtPassword)
    private EditText edtPassword;
    @ViewInject(R.id.edtQueryPassword)
    private EditText edtQueryPassword;
    @ViewInject(R.id.edtMsgCode)
    private EditText edtMsgCode;

    @ViewInject(R.id.btnSendMsg)
    private Button btnSendMsg;

    private Context mContext;
    private boolean isCanClick = false;
    private boolean isCanRegister = false;
    private RegisteredPresenter presenter = new RegisteredPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        x.view().inject(this);

        mContext = this;

        imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.back_btn));
        txtTitle.setText(getString(R.string.register));
    }

    @Override
    public void showLoading() {
        LoadingUtils.showLoading(mContext);
    }

    @Override
    public void hideLoading() {
        LoadingUtils.hideLoading();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void requestSuccessful() {
        isCanRegister = false;
        ToastUtil.showShort(mContext, getString(R.string.register_successful));
        this.finish();
    }

    @Override
    public void requestError(String errMsg) {
        isCanClick = false;
        isCanRegister = false;
        ToastUtil.showShort(mContext, errMsg);
    }

    @Override
    public String getUserPhone() {
        return edtUserPhone.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return edtPassword.getText().toString().trim();
    }

    @Override
    public String getQueryPassword() {
        return edtQueryPassword.getText().toString().trim();
    }

    @Override
    public String getMsgCode() {
        return edtMsgCode.getText().toString().trim();
    }

    @Override
    public void sendMsgSuccessful(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    @Override
    public void setTime(String time) {
        if (time.equals(getString(R.string.send_msg))) {
            isCanClick = false;
        }
        btnSendMsg.setText(time);
    }

    @Event({R.id.imgBtnLeft, R.id.btnSubmit, R.id.btnSendMsg})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnLeft:
                this.finish();
                break;
            case R.id.btnSubmit:
                if (!isCanRegister) {
                    isCanRegister = true;
                    presenter.registered();
                }
                break;
            case R.id.btnSendMsg:
                if (!isCanClick) {
                    isCanClick = true;
                    presenter.sendMsg();
                }
                break;
        }
    }
}
