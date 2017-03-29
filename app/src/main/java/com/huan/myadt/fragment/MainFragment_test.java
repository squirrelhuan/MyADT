package com.huan.myadt.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.huan.myadt.R;
import com.huan.myadt.activity.MainActivity;
import com.huan.myadt.fragment.base.BaseFragment;
import com.huan.myadt.service.MyADTService;

public class MainFragment_test extends BaseFragment implements OnClickListener {

	MainActivity mainActivity;

	EditText et_serviceId;
	Button btn_save;

	@Override
	public View setContentUI(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_main_4, container, false);
	}
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void init() {
		/*mainActivity = (MainActivity) getActivity();
		et_serviceId = (EditText) findViewById(R.id.et_serviceId);
		et_serviceId.setText(MyADTService.SERVER_ADDRESS);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);*/
	}
	@Override
	public void onClick(View v) {
		/*switch (v.getId()) {
		case R.id.btn_save:
			MyADTService.SERVER_ADDRESS = et_serviceId.getText().toString();
			break;

		default:
			break;
		}*/
	}

	
}
