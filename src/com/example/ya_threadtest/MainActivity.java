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
				//這裡是在 OnClickListener的 class中，因此使用 MainActivity.this.去連接 EditText.
				et = (EditText)MainActivity.this.findViewById(R.id.et);
				//注意 count的顯示與數數位智。
				count = Integer.parseInt(et.getText().toString()); 
				
				thread = new Thread(MainActivity.this);
				thread.start();
				
			}
			
		});
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {			
			//此處在 Handler的 class中。連接  MainActivity的 TextView.
			tv = (TextView)MainActivity.this.findViewById(R.id.tv);
			
			count--;
			tv.setText(String.valueOf(count));
			//此處在 Handler的 class中。連接  MainActivity的 Button.
			btn = (Button)MainActivity.this.findViewById(R.id.btn);
			
			if(count <= 0){
				tv.setText("時間到!!!");
				btn.setEnabled(true);
			//還未數到 0時，重新進入 thread執行 run()。
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
