package com.keertech.myandroid.activity;

import com.keertech.myandroid.activity.base.AbstractBarActivity;


public class LoginActivity extends AbstractBarActivity {//implements Validator.ValidationListener

//    @Required(order = 1, message = "用户名不能为空")
//    private EditText username_et = null;
//
//    @Password(order = 2)
//    @TextRule(order = 3, minLength = 6, message = "Enter at least 6 characters.")
//    private EditText password_et;
//
//    @ConfirmPassword(order = 4)
//    private EditText passwordConfirm_et;
//
//    @DateRule(order = 8, pattern = "yyyy-MM-dd", messageParse = "格式不正确")//暂时这种方式不起作用
//    private EditText birthday_et;
//
//    @Required(order = 5)
//    @Email(order = 6, message = "电子邮件格式不正确")
//    private EditText email_et;
//
//    @Required(order = 7, message = "性别不能为空")//暂时这种方式不起作用
//    private RadioGroup sex_rg;
//
//    @Checked(order = 12, message = "You must agree to the terms.")
//    private CheckBox iAgree_cb;
//
//    private Validator validator;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        //初始化监听
//        initListener();
//        //实例化校验类库
//        validator = new Validator(this);
//        validator.setValidationListener(this);
//        validator.put(birthday_et, Rules.formatDate("生日格式不正确", new SimpleDateFormat("yyyy-MM-dd")));
//        validator.put(sex_rg, Rules.requiredRadioGroup("性别不能为空"));//2选1
//    }
//
//    /**
//     * 初始化监听
//     */
//    private void initListener() {
//        Button btn = (Button) findViewById(R.id.login_btn);
//        username_et = (EditText) findViewById(R.id.username_et);
//        password_et = (EditText) findViewById(R.id.password_et);
//        passwordConfirm_et = (EditText) findViewById(R.id.passwordConfirm_et);
//        birthday_et = (EditText) findViewById(R.id.birthday_et);
//        email_et = (EditText) findViewById(R.id.email_et);
//        sex_rg = (RadioGroup) findViewById(R.id.sex_rg);
//        // female_rb = (RadioButton) findViewById(R.id.female_rb);
//        iAgree_cb = (CheckBox) findViewById(R.id.iAgree_cb);
//        btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                validator.validate();
//            }
//        });
//    }
//
//    private void login() {
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onValidationSucceeded() {
//
//    }
//
//    @Override
//    public void onValidationFailed(View failedView, Rule<?> failedRule) {
//        String message = failedRule.getFailureMessage();
//        if (failedView instanceof EditText) {
//            failedView.requestFocus();
//            ((EditText) failedView).setError(message);
//        } else {
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        }
//    }
}
