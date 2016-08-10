package com.xiongdi.recognition.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.xiongdi.recognition.R;
import com.xiongdi.recognition.bean.Account;
import com.xiongdi.recognition.db.AccountDao;
import com.xiongdi.recognition.util.ToastUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements OnClickListener {
    private RadioGroup mChooseTypeRG;
    private Button loginBT;
    private EditText nameET, passwordET;
    private View mSecondPasswordView;

    //连续按两次返回键后退出应用
    private boolean isExit = false;
    private boolean hasTask = false;
    private boolean isAdmin = false;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        initData();
        initView();
        saveOrReadAccount(false);
        setListener();
    }

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            ToastUtil.getInstance().showToast(this, getString(R.string.common_exit_app));
            if (!hasTask) {
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                        hasTask = false;
                        cancel();
                    }
                }, 2000);
            }
        } else {
            super.onBackPressed();
        }
    }

    private void initData() {
        mTimer = new Timer();
    }

    private void initView() {
        mChooseTypeRG = (RadioGroup) findViewById(R.id.choose_type_rg);
        nameET = (EditText) findViewById(R.id.editText_name);
        nameET.setSelection(nameET.getText().length());
        passwordET = (EditText) findViewById(R.id.first_psw_editText);
        passwordET.setSelection(passwordET.getText().length());
        nameET.requestFocus();
        loginBT = (Button) findViewById(R.id.login_bt);
        mSecondPasswordView = findViewById(R.id.second_password);
    }

    private void setListener() {
        mChooseTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.general_rb:
                        isAdmin = false;
                        mSecondPasswordView.setVisibility(View.GONE);
                        break;
                    case R.id.administrator_rb:
                        isAdmin = true;
                        mSecondPasswordView.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
        loginBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                if (true) {
                    Intent intent = new Intent();
                    if (isAdmin) {
                        intent.setClass(LoginActivity.this, AdminActivity.class);
                    } else {
                        if ("userc".equals(nameET.getText().toString())) {
                            intent.setClass(LoginActivity.this, FillInfoActivity.class);
                        } else if ("userv".equals(nameET.getText().toString())) {
                            intent.setClass(LoginActivity.this, VerifyResultActivity.class);
                            intent.putExtra("haveData", false);
                        } else {
                            intent.setClass(LoginActivity.this, FillInfoActivity.class);
                        }
                    }

                    saveOrReadAccount(true);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.login_failed_tips), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }

            default:
                break;
        }
    }

    /**
     * 保存或读取账户信息
     */
    private void saveOrReadAccount(boolean isSave) {
        SharedPreferences sp = getSharedPreferences("account", Activity.MODE_PRIVATE);
        if (isSave) {
            Editor editor = sp.edit();
            editor.putString("userName", nameET.getText().toString());
            editor.apply();
        } else {
            String userName = sp.getString("userName", null);
            if (userName != null) {
                nameET.setText(userName);
            }
        }

    }

    /**
     * 验证账户
     */
    private boolean verifyAccount() {
        String userName = nameET.getText().toString();
        String password = passwordET.getText().toString();

        if (0 == userName.length() || 0 == password.length()) {
            return false;
        }

        try {
            AccountDao accountDao = new AccountDao(getApplicationContext());
            QueryBuilder<Account, Integer> queryBuilder = accountDao.getQueryBuilder();
            Where<Account, Integer> where = queryBuilder.where();
            where.eq("name", userName);
            PreparedQuery<Account> preparedQuery = where.prepare();
            List<Account> actList = accountDao.query(preparedQuery);

            if (0 == actList.size()) {
                return false;
            }
            String correctPassword = actList.get(0).getPassword();

            if (correctPassword.equals(password)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
