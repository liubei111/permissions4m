package com.joker.permissions4m;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.joker.annotation.PermissionsCustomRationale;
import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsRationale;
import com.joker.annotation.PermissionsRequestSync;
import com.joker.api.Permissions4M;
import com.joker.api.util.PermissionsSettingContext;
import com.joker.permissions4m.other.ToastUtil;

import static com.joker.permissions4m.MainActivity.CALENDAR_CODE;
import static com.joker.permissions4m.MainActivity.LOCATION_CODE;
import static com.joker.permissions4m.MainActivity.SENSORS_CODE;

// TODO: 更改单个权限为权限数组
@PermissionsRequestSync(permission = {Manifest.permission.BODY_SENSORS, Manifest.permission
        .ACCESS_FINE_LOCATION, Manifest.permission.READ_CALENDAR},
        value = {SENSORS_CODE, LOCATION_CODE, CALENDAR_CODE})
public class MainActivity extends AppCompatActivity {
    public static final int CALENDAR_CODE = 7;
    public static final int SENSORS_CODE = 8;
    public static final int LOCATION_CODE = 9;
    private static final int STORAGE_CODE = 1;
    private static final int CALL_CODE = 2;
    private static final int SMS_CODE = 5;
    private static final int AUDIO_CODE = 6;
    private Button mCallButton;
    private Button mStorageButton;
    private Button mSmsButton;
    private Button mAudioButton;
    private Button mOneButton;
    private Button mSingleButton;
    private Button mManagerButton;
    private Button mPermissionPageButton;
    private Button mAndroidSettingPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCallButton = (Button) findViewById(R.id.btn_call);
        mStorageButton = (Button) findViewById(R.id.btn_storage);
        mAudioButton = (Button) findViewById(R.id.btn_audio);
        mSmsButton = (Button) findViewById(R.id.btn_sms);
        mOneButton = (Button) findViewById(R.id.btn_one);
        mSingleButton = (Button) findViewById(R.id.btn_single);
        mManagerButton = (Button) findViewById(R.id.btn_manager);
        mPermissionPageButton = (Button) findViewById(R.id.btn_permission_page);
        mAndroidSettingPageButton = (Button) findViewById(R.id.android_setting_page);

        mSingleButton = (Button) findViewById(R.id.btn_single);
        // 多个申请
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissions4M.requestPermission(MainActivity.this, Manifest.permission.CALL_PHONE,
                        CALL_CODE);
            }
        });
        mStorageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissions4M.requestPermission(MainActivity.this, Manifest.permission
                                .WRITE_EXTERNAL_STORAGE,
                        STORAGE_CODE);
            }
        });

        // 自定义多个申请
        mSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissions4M.requestPermission(MainActivity.this, Manifest.permission.READ_SMS, SMS_CODE);
            }
        });
        mAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissions4M.requestPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO,
                        AUDIO_CODE);
            }
        });

        // 同步申请
        mOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Permissions4M.requestPermission(MainActivity.this, Manifest.permission.BODY_SENSORS,
//                        SENSORS_CODE);
                Permissions4M.syncRequestPermissions(MainActivity.this);
            }
        });

        mSingleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SingleActivity.class));
            }
        });

        mManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PermissionsSettingContext.getIntent(MainActivity.this, false));
            }
        });

        mPermissionPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PermissionsSettingContext.getIntent(MainActivity.this, true));
            }
        });

        mAndroidSettingPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PermissionsSettingContext.getIntent());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //====================================================================
    @PermissionsGranted({LOCATION_CODE, SENSORS_CODE, CALENDAR_CODE})
    public void syncGranted(int code) {
        switch (code) {
            case LOCATION_CODE:
                ToastUtil.show("地理位置权限授权成功 in activity");
                break;
            case SENSORS_CODE:
                ToastUtil.show("传感器权限授权成功 in activity");
                break;
            case CALENDAR_CODE:
                ToastUtil.show("读取日历权限授权成功 in activity");
                break;
            default:
                break;
        }
    }

    @PermissionsDenied({LOCATION_CODE, SENSORS_CODE, CALENDAR_CODE})
    public void syncDenied(int code) {
        switch (code) {
            case LOCATION_CODE:
                ToastUtil.show("地理位置权限授权失败 in activity");
                break;
            case SENSORS_CODE:
                ToastUtil.show("传感器权限授权失败 in activity");
                break;
            case CALENDAR_CODE:
                ToastUtil.show("读取日历权限授权失败 in activity");
                break;
            default:
                break;
        }
    }

    @PermissionsRationale({LOCATION_CODE, SENSORS_CODE, CALENDAR_CODE})
    public void syncRationale(int code) {
        switch (code) {
            case LOCATION_CODE:
                ToastUtil.show("请开启地理位置权限 in activity");
                break;
            case SENSORS_CODE:
                ToastUtil.show("请开启传感器权限 in activity");
                break;
            case CALENDAR_CODE:
                ToastUtil.show("请开启读取日历权限 in activity");
                break;
            default:
                break;
        }
    }

    //====================================================================
    @PermissionsGranted({STORAGE_CODE, CALL_CODE})
    public void storageAndCallGranted(int code) {
        switch (code) {
            case STORAGE_CODE:
                ToastUtil.show("设备存储权限授权成功 in activity");
                break;
            case CALL_CODE:
                ToastUtil.show("通话权限授权成功 in activity");
                break;
            default:
                break;
        }
    }

    @PermissionsDenied({STORAGE_CODE, CALL_CODE})
    public void storageAndCallDenied(int code) {
        switch (code) {
            case STORAGE_CODE:
                ToastUtil.show("设备存储权限授权失败 in activity");
                break;
            case CALL_CODE:
                ToastUtil.show("通话权限授权失败 in activity");
                break;
            default:
                break;
        }
    }

    @PermissionsRationale({STORAGE_CODE, CALL_CODE})
    public void storageAndCallRationale(int code) {
        switch (code) {
            case STORAGE_CODE:
                ToastUtil.show("请开启设备存储权限授权 in activity");
                break;
            case CALL_CODE:
                ToastUtil.show("请开启通话权限授权 in activity");
                break;
            default:
                break;
        }
    }

    //====================================================================
    @PermissionsGranted({SMS_CODE, AUDIO_CODE})
    public void smsAndAudioGranted(int code) {
        switch (code) {
            case SMS_CODE:
                ToastUtil.show("短信权限申请成功 in activity");
                break;
            case AUDIO_CODE:
                ToastUtil.show("录音权限申请成功 in activity");
                break;
            default:
                break;
        }
    }

    @PermissionsDenied({SMS_CODE, AUDIO_CODE})
    public void smsAndAudioDenied(int code) {
        switch (code) {
            case SMS_CODE:
                ToastUtil.show("短信权限申请失败 in activity");
                break;
            case AUDIO_CODE:
                ToastUtil.show("录音权限申请失败 in activity");
                break;
            default:
                break;
        }
    }

    @PermissionsCustomRationale({SMS_CODE, AUDIO_CODE})
    public void smsAndAudioCustomRationale(int code) {
        switch (code) {
            case SMS_CODE:
                new AlertDialog.Builder(this)
                        .setMessage("短信权限申请：\n我们需要您开启短信权限(in activity)")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 请自行处理申请权限，两者方法等价
                                // 方法1.使用框架封装方法
                                Permissions4M.requestPermissionOnCustomRationale(MainActivity.this, new
                                        String[]{Manifest
                                        .permission.READ_SMS}, SMS_CODE);
                                // 方法2.使用自身方法
//                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest
//                                        .permission.READ_SMS}, SMS_CODE);
                            }
                        })
                        .show();
                break;
            case AUDIO_CODE:
                new AlertDialog.Builder(this)
                        .setMessage("录音权限申请：\n我们需要您开启录音权限(in activity)")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 请自行处理申请权限，两者方法等价
                                // 方法1.使用框架封装方法
                                Permissions4M.requestPermissionOnCustomRationale(MainActivity.this, new
                                        String[]{Manifest
                                        .permission.RECORD_AUDIO}, AUDIO_CODE);
                                // 方法2.使用自身方法
//                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest
//                                        .permission.RECORD_AUDIO}, AUDIO_CODE);
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
}
