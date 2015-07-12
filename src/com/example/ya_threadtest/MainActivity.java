package com.example.ya_threadtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {
	private EditText et;
	private TextView tv;
	private Button btn;
	private int count;
	Thread thread;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button)this.findViewById(R.id.btn);
		
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				btn.setEnabled(false);
				//�o�̬O�b OnClickListener�� class���A�]���ϥ� MainActivity.this.�h�s�� EditText.
				et = (EditText)MainActivity.this.findViewById(R.id.et);
				//�`�N count����ܻP�ƼƦ촼�C
				count = Integer.parseInt(et.getText().toString()); 
				
				thread = new Thread(MainActivity.this);
				thread.start();
				
			}
			
		});
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {			
			//���B�b Handler�� class���C�s��  MainActivity�� TextView.
			tv = (TextView)MainActivity.this.findViewById(R.id.tv);
			
			count--;
			tv.setText(String.valueOf(count));
			//���B�b Handler�� class���C�s��  MainActivity�� Button.
			btn = (Button)MainActivity.this.findViewById(R.id.btn);
			
			if(count <= 0){
				tv.setText("�ɶ���!!!");
				btn.setEnabled(true);
			//�٥��ƨ� 0�ɡA���s�i�J thread���� run()�C
			}else{
				thread = new Thread(MainActivity.this);
				thread.start();
			}
		}
	};
	

	@Override
	public void run() {
		try{
			Thread.sleep(1000);
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
	}
	
}//end class MainActivity.
