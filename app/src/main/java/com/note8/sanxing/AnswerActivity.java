package com.note8.sanxing;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.note8.sanxing.models.Question;
import com.note8.sanxing.utils.network.SanxingApiClient;
import com.note8.sanxing.utils.ui.CustomGradientDrawable;
import com.note8.sanxing.utils.ui.StatusBarUtils;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class AnswerActivity extends AppCompatActivity {

    private Context mContext;

    private RelativeLayout topPanel;
    private ImageButton returnBtn;
    private ImageButton saveBtn;
    private ImageButton publicStatueBtn;
    private ImageButton insertImg;
    private TextView moodDescribe;
    private SeekBar answerSeekBar;
    private TextView questionTxt;
    private TextView answerTxt;
    private ImageView answerImg;
    private ScrollView answerLayout;
    private View progressView;
    private UploadTask mUploadTask;

    // data
    private Question mQuestion;
    private String mAnswerContent;
    private Integer mMood;

    private static final int RESULT_CODE_SUCCESS_RECIEVED = 1;
    private static final int RESULT_CODE_FAILED_RECIEVED = -1;

    private Bitmap mBitmap;
    private int[] publicStatue = {0};
    private double intervalOfProgress = 20;

    private static final int REQUEST_CODE_TAKE_PHOTO = 0;// 拍照请求码
    private static final int REQUEST_CODE_CHOOSE_FROM_ALBUM = 1;// 相册选择请求码
    private static final int REQUEST_CODE_CLIP_PHOTO = 2;// 裁剪请求码
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 3; // 存储权限
    private static final int REQUEST_CODE_CREATE_POST = 4; // 发布图片
    private static final int RESULT_CODE_SUCCESS_SEND = 1;
    private static final int RESULT_CODE_FAILED = -1;
    private static final String CONTENT_PROVIDER = "com.note8.sanxing.takePhoto.provider";

    private File mOutputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = AnswerActivity.this;

        initView();

        loadData(); // load data from args
    }

    private void initView() {
        //隐藏标题栏并使状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_answer);
        //设置状态栏颜色为白色且图标为对比色
        setStatusBar();
        //获取页面控件
        findViewById();
        //answerImg.setVisibility(View.GONE);
        mBitmap = null;

        //点击返回按钮，结束当前页面
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //点击返回按钮时存在动效
        returnBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.return_press));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.return_release));
                }
                return false;
            }
        });

        //回答问题完后，点击保存按钮时存在动效
        saveBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.save_press));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    sendAnswer();
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.save_release));
                }
                return false;
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "保存中", Toast.LENGTH_SHORT).show();
                attemptUpload();
            }
        });

        //切换今日问题回答的公开状态
        publicStatueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(publicStatue[0] == 0)
                    publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_visible));
                else
                    publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_invisible));
                publicStatue[0] = 1 - publicStatue[0];
            }
        });

        //滑动杆一定区间的数值对应某种心情描述
        answerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch(seekBar.getId()) {
                    case R.id.answerSeekBar:{
                        if(seekBar.getProgress() == 0) moodDescribe.setText("#我感觉整个人都不好了#");
                        else if(seekBar.getProgress() == 100) moodDescribe.setText("#我现在兴奋到了极点#");
                        else if(seekBar.getProgress() < intervalOfProgress) moodDescribe.setText("#我现在有点烦躁#");
                        else if(seekBar.getProgress() < 2*intervalOfProgress) moodDescribe.setText("#我感觉到了一股淡淡的忧伤#");
                        else if(seekBar.getProgress() < 3*intervalOfProgress) moodDescribe.setText("#我现在内心毫无波澜#");
                        else if(seekBar.getProgress() < 4*intervalOfProgress) moodDescribe.setText("#我现在心情还算愉悦#");
                        else moodDescribe.setText("#我现在有点想笑#");
                        break;
                    }

                    default:
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //点击图片按钮，插入图片
        insertImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFAB();
            }
        });
        try {
            mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                    this.getIntent().getData());
            answerImg.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.d("fail", "fail to create bitmap");
        }
        answerImg.setImageBitmap(ThumbnailUtils.extractThumbnail(mBitmap, 1000, 1000));
    }

    /**
     * Load question data from args(intent extra)
     */
    private void loadData() {
        Intent intent = getIntent();
        mQuestion = (Question) intent.getSerializableExtra("question");
        questionTxt.setText(mQuestion.getContent());
    }

    /**
     * Send answer to the server
     */
    private void sendAnswer() {
        mAnswerContent = answerTxt.getText().toString();
        mMood = answerSeekBar.getProgress();
        attemptUpload();
    }

    //获取控件
    private void findViewById(){
        returnBtn = (ImageButton)findViewById(R.id.answerPageReturn);
        saveBtn = (ImageButton)findViewById(R.id.answerPageShare);
        publicStatueBtn = (ImageButton)findViewById(R.id.publicStatue);
        insertImg = (ImageButton)findViewById(R.id.insert_img);
        moodDescribe = (TextView)findViewById(R.id.moodDescribe);
        answerSeekBar = (SeekBar)findViewById(R.id.answerSeekBar);
        questionTxt = (TextView) findViewById(R.id.question);
        answerTxt = (TextView)findViewById(R.id.answer_txt);
        answerImg = (ImageView)findViewById(R.id.answer_img);
        answerLayout = (ScrollView)findViewById(R.id.answer_txt_img_layout);
        progressView = findViewById(R.id.answer_upload_progress);
    }

    //设置手机状态栏颜色为白色且通知图标为对比色
    private void setStatusBar(){
        ViewGroup decorViewGroup = (ViewGroup) getWindow().getDecorView();
        View statusBarView = new View(getWindow().getContext());
        int statusBarHeight = getStatusBarHeight(getWindow().getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        decorViewGroup.addView(statusBarView);
    }

    //获取手机状态栏高度
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }





    private void handleFAB() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发布图片");
        builder.setMessage("选择照片来源");
        builder.setNegativeButton("从相册中选择", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chooseFromAlbum();
            }
        });
        builder.setPositiveButton("拍照", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                takePhoto();
            }
        });
        builder.show();
    }

    private void chooseFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_FROM_ALBUM);
    }


    private void takePhoto() {
            /*+++++++针对6.0及其以上系统，读写外置存储权限的检测+++++++++*/
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String [] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(AnswerActivity.this, permissions, REQUEST_CODE_STORAGE_PERMISSION);
            return;
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String sdPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
            mOutputFile = new File(sdPath, System.currentTimeMillis() + ".jpg");//拍照之后照片的路径
            try {
                if (!mOutputFile.exists()) {
                    mOutputFile.createNewFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Uri uri= FileProvider.getUriForFile(this,
                    CONTENT_PROVIDER, mOutputFile);
            Log.i("take", "takePhoto: uri:===" + uri);
            Intent newIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            newIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//将拍取的照片保存到指定Uri
            startActivityForResult(newIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "并未获取到存储权限", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // permissons granted, try again
        takePhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            onPhotoTaken(resultCode, data);
        } else if (requestCode == REQUEST_CODE_CHOOSE_FROM_ALBUM) {
            onPhotoChosen(resultCode, data);
        } else if (requestCode == REQUEST_CODE_CLIP_PHOTO) {
            onPhotoCliped(resultCode, data);
        } else if (requestCode == REQUEST_CODE_CREATE_POST) {
            if (resultCode == RESULT_CODE_SUCCESS_SEND) {
                //Snackbar.make(mFab, "发布成功", Snackbar.LENGTH_LONG).show();
                //if (timelineFragment != null) {
                //    timelineFragment.refreshData();
                //}
            }
        }
    }

    /**
     * 拍照完成
     *
     * @param resultCode
     * @param data
     */
    private void onPhotoTaken(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "已取消", Toast.LENGTH_SHORT)
                    .show();
            return;
        } else if (resultCode != RESULT_OK) {
            Toast.makeText(this, "拍照失败", Toast.LENGTH_SHORT)
                    .show();
        } else {
            /*调用裁剪图片的方法进行裁剪图片*/
            Uri uri = FileProvider.getUriForFile(this,
                    CONTENT_PROVIDER, mOutputFile);
            clipPhoto(uri);
        }
    }

    /**
     * 选择照片完成
     *
     * @param resultCode
     * @param data
     */
    private void onPhotoChosen(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "已取消", Toast.LENGTH_SHORT)
                    .show();
            return;
        } else if (resultCode != RESULT_OK) {
            Toast.makeText(this, "拍照失败", Toast.LENGTH_SHORT)
                    .show();
        } else {
            // 写入新文件
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String [] permissions = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(AnswerActivity.this, permissions, REQUEST_CODE_STORAGE_PERMISSION);
                return;
            }
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String sdPath = Environment.getExternalStorageDirectory()
                        .getAbsolutePath();
                mOutputFile = new File(sdPath, System.currentTimeMillis() + ".jpg");//拍照之后照片的路径
                try {
                    if (!mOutputFile.exists()) {
                        mOutputFile.createNewFile();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // write the source to our new file
                final int chunkSize = 1024;  // We'll read in one kB at a time
                byte[] imageData = new byte[chunkSize];
                InputStream inputStream;
                OutputStream outputStream;
                try {
                    inputStream = getContentResolver().openInputStream(data.getData());
                    outputStream = new FileOutputStream(mOutputFile);

                    int bytesRead;
                    while ((bytesRead = inputStream.read(imageData)) > 0) {
                        outputStream.write(Arrays.copyOfRange(imageData, 0, Math.max(0, bytesRead)));
                    }
                    inputStream.close();
                    outputStream.close();
                } catch (Exception ex) {
                    Log.e("Something went wrong.", ex.getMessage());
                }
                /*调用裁剪图片的方法进行裁剪图片*/
                clipPhoto(FileProvider.getUriForFile(this,
                        CONTENT_PROVIDER, mOutputFile));
            }
        }
    }
    /**
     * 裁剪照片
     *
     * @param uri
     *
     */
    private void clipPhoto(Uri uri) {
        String[] imageSource = new String[] {
                "com.android.camera"
                //"com.android.externalstorage","com.android.providers.downloads","com.android.providers.media"
        };
        for (String packageName : imageSource) {
            this.grantUriPermission(packageName, uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//请求URI授权读取
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//请求URI授权写入
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE_CLIP_PHOTO);
    }
    /**
     * 裁剪照片完成
     *
     * @param resultCode
     * @param data
     */
    private void onPhotoCliped(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "已取消", Toast.LENGTH_SHORT)
                    .show();
            return;
        } else if (resultCode != RESULT_OK) {
            Toast.makeText(this, "裁剪失败", Toast.LENGTH_SHORT)
                    .show();
        }

        Uri uri= FileProvider.getUriForFile(this, CONTENT_PROVIDER, mOutputFile);
        this.getIntent().setData(uri);
        startActivityForResult(this.getIntent(), REQUEST_CODE_CREATE_POST);
        finish();
        /*
        Intent intent = new Intent(AnswerActivity.this, AnswerActivity.class);
        Uri uri= FileProvider.getUriForFile(this, CONTENT_PROVIDER, mOutputFile);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CODE_CREATE_POST);
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.answerPageShare) {
            Toast.makeText(this, "保存中", Toast.LENGTH_SHORT).show();
            attemptUpload();
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 将Bitmap转换成Base64字符串
     * @param bit
     * @return
     */
    public String Bitmap2StrByBase64(Bitmap bit){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    private void attemptUpload() {
        showProgress(true);
        mUploadTask = new UploadTask(AnswerActivity.this);
        mUploadTask.execute((Void) null);
    }

    public class UploadTask extends AsyncTask<Void, Void, Boolean> {

        Context context;

        UploadTask(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return SanxingApiClient.getInstance(mContext).createAnswer(mQuestion, mAnswerContent, mMood);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mUploadTask = null;

            if (success) {
                setResult(RESULT_CODE_SUCCESS_RECIEVED);
                finish();
            } else {
                showProgress(false);
                Toast.makeText(mContext, SanxingApiClient.getInstance(mContext).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mUploadTask = null;
            showProgress(false);
        }
    }

    /**
     * Shows the progress UI and hides the create post form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

/*            answerLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            answerLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    answerLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
*/
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            //answerLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
