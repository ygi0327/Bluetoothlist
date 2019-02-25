package com.example.yang.bluetoothlist;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;                                                       // 블루투스 어댑터
    BluetoothAdapter checkBluetooth = BluetoothAdapter.getDefaultAdapter();                  // 블루투스 지원 체크
    IntentFilter intentFilter = new IntentFilter();                                          // 안드로이드 시스템으로 부터 메세지를 받을수 있게 설정하는 것 ( "intent"의 한 종류 )

    private static final int REQUEST_ENABLE_BT = 0;                                          // 블루투스 요청 액티비티 코드

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.serch_button);       // 검색 버튼
        ListView listview =  findViewById(R.id.listDevice);          // 리스트 뷰
        ListAdapter listAdapter;                                     // 리스트뷰 어댑터


        // SDK 23 이상 버전을 위한 퍼미션 관리 소스 (없으면 검색 장치 목록이 안뜸) --------------------------------------------------------------

        if(Build.VERSION.SDK_INT>=23){
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(this, "ble_need", Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            }else{
            }
        }else{
        }

        // 디바이스가 지원 여부 확인 --------------------------------------------------------------------------------------------------------------
        if(checkBluetooth == null) {  // 지원 안하면?
            Toast.makeText(MainActivity.this, "블루투스를 지원하지 않는 디바이스 입니다.",Toast.LENGTH_SHORT).show();
            button.setEnabled(false);    // 버튼 비활성화
        } else { // 지원 하면?
            Toast.makeText(MainActivity.this, "블루투스를 지원합니다.",Toast.LENGTH_SHORT).show();

            if(!checkBluetooth.isEnabled()) {
                Intent onBtn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);    // 블루투스 설정 팝업
                startActivityForResult(onBtn, REQUEST_ENABLE_BT);
            }
        }

        // 버튼 클릭 이벤트 ------------------------------------------------------------------------------------------------------------------------
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 블루투스 안켜질시
                if(!checkBluetooth.isEnabled()) {
                    Toast.makeText(MainActivity.this, "블루투스를 켜야합니다.",Toast.LENGTH_SHORT).show();
                } else {  // 블루투스 켜질시 블루투스 검색
                    button.setText("장치 검색중..");

                    btn_search();

                }
            }
        });

    }

    // 블루투스 장치 검색 ---------------------------------------------------------------------------------------------------------------------------
    public void btn_search() {
        // 장치 검색 함수
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction(); // 행동 확인 변수

                /*
                switch (action) {
                    //블루투스 디바이스 검색 종료
                    case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                        dataDevice.clear();
                        Toast.makeText(MainActivity.this, "블루투스 검색 시작", Toast.LENGTH_SHORT).show();
                        break;
                    //블루투스 디바이스 찾음
                    case BluetoothDevice.ACTION_FOUND:
                        //검색한 블루투스 디바이스의 객체를 구한다
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        //데이터 저장
                        map.put("name", device.getName()); //device.getName() : 블루투스 디바이스의 이름
                        map.put("address", device.getAddress()); //device.getAddress() : 블루투스 디바이스의 MAC 주소
                        dataDevice.add(map);
                        //리스트 목록갱신
                        adapterDevice.notifyDataSetChanged();
                        break;
                    //블루투스 디바이스 검색 종료
                    case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                        Toast.makeText(MainActivity.this, "블루투스 검색 종료", Toast.LENGTH_SHORT).show();
                        btnSearch.setEnabled(true);
                        break;
                }
                */

            }
        };
    }
}
