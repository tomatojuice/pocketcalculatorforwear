package tomatojuice.sakura.ne.jp.pocketcalculatorforwear;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TomaCalculatorActivity extends WearableActivity implements OnClickListener{
	public static int pushFourBase,pushpm;
	public static TextView dispFour,dispNum,dispMemory; // dispFour:四則演算の表示 dispNum:数字の表示 dispMemory:メモリーの表示
	public static final String[] NUM = {"0","1","2","3","4","5","6","7","8","9",",","."};
	public static final String[] SIGN = {"+","-","x","÷","","  M+","  M-"};
	public static StringBuffer numbers;
	private SharedPreferences editsp,listsp;
	private String edit_str,listsp_str;
	private Button buttons[] = new Button[30]; // ボタン
	private int sp;
	private CalculatorFunctions calfunc; // 計算機能を提供するクラス
	private static final int REQ_PREFE = 0;
	private Vibrator vib;

	private static final SimpleDateFormat AMBIENT_DATE_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());

	/** onCreate **/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setAmbientEnabled();
		setTheme();
		setButtons();
        
        dispFour = (TextView)findViewById(R.id.display1);
        dispNum = (TextView)findViewById(R.id.display2);
        dispMemory = (TextView)findViewById(R.id.display3);
        numbers = new StringBuffer();
        pushpm = 0; // 「+/-」ボタンのフラグ。0が「+」の状態
 
        makeTax(); // 税率設定を読み込むためのメソッドを読み込み

    } // onCreateはここまで

	/** Themeの設定 **/
	private void setTheme(){
		listsp = PreferenceManager.getDefaultSharedPreferences(this);
        listsp_str = listsp.getString("theme", "0");
        Log.i("listspの値", listsp_str);
        sp = Integer.parseInt(listsp_str);
        
        switch(sp){
		case 0:
				setTheme(R.style.IndigoTheme);
				setContentView(R.layout.main_indigo);
			break;
		case 1:
				setTheme(R.style.PinkTheme);
				setContentView(R.layout.main_pink);
			break;
		case 2:
				setTheme(R.style.TealTheme);
				setContentView(R.layout.main_teal);
			break;
		case 3:
				setTheme(R.style.OrangeTheme);
				setContentView(R.layout.main_orange);
			break;
		case 4:
				setTheme(R.style.BrownTheme);
				setContentView(R.layout.main_brown);
			break;
		case 5:
				setTheme(R.style.GreenTheme);
				setContentView(R.layout.main_green);
			break;
		case 6:
				setTheme(R.style.MikuTheme);
				setContentView(R.layout.main_miku);
			break;	
		} // switch
	} // setTheme

	/** Buttonの設定 **/
	private void setButtons(){
		// キーボタンのidをセット
        buttons[0] = (Button)findViewById(R.id.IncludeTaxes);
        buttons[1] = (Button)findViewById(R.id.WithoutTaxes);
        buttons[2] = (Button)findViewById(R.id.C);
        buttons[3] = (Button)findViewById(R.id.CE);
        buttons[4] = (Button)findViewById(R.id.CA);
        buttons[5] = (Button)findViewById(R.id.MPlus);
        buttons[6] = (Button)findViewById(R.id.MMinus);
        buttons[7] = (Button)findViewById(R.id.CM);
        buttons[8] = (Button)findViewById(R.id.RM);
        buttons[9] = (Button)findViewById(R.id.seven);
        buttons[10] = (Button)findViewById(R.id.eight);
        buttons[11] = (Button)findViewById(R.id.nine);
        buttons[12] = (Button)findViewById(R.id.Percent);
        buttons[13] = (Button)findViewById(R.id.root);
        buttons[14] = (Button)findViewById(R.id.four);
        buttons[15] = (Button)findViewById(R.id.five);
        buttons[16] = (Button)findViewById(R.id.six);
        buttons[17] = (Button)findViewById(R.id.Kakeru);
        buttons[18] = (Button)findViewById(R.id.Waru);
        buttons[19] = (Button)findViewById(R.id.one);
        buttons[20] = (Button)findViewById(R.id.two);
        buttons[21] = (Button)findViewById(R.id.three);
        buttons[22] = (Button)findViewById(R.id.Plus);
        buttons[23] = (Button)findViewById(R.id.Minus);
        buttons[24] = (Button)findViewById(R.id.zero);
        buttons[25] = (Button)findViewById(R.id.zerozero);
        buttons[26] = (Button)findViewById(R.id.dot);
        buttons[27] = (Button)findViewById(R.id.equal);
        buttons[28] = (Button)findViewById(R.id.plusminus);
		buttons[29] = (Button)findViewById(R.id.setting);

        for(int i=0; i<30; i++){ // キーボタンを配列にしたので、for文でリスナーにセット
        	buttons[i].setOnClickListener(this);
        }
	} // setButtons

	/** Preferencesに保存してる税率を読み込み、設定するためのメソッド **/
	private void makeTax(){
        // 税率設定
        editsp = PreferenceManager.getDefaultSharedPreferences(this);
        edit_str = editsp.getString("taxkey", "8"); // taxkeyがnullの場合は8をセット
        Log.i("eidtspの値", edit_str);
        double tax = Double.parseDouble(edit_str);
        double in = 1.00 + (tax/100); // 「税込」に使用する定数
        double out = 1.00 - (tax/100); // 「税抜き」に使用する定数
        
        calfunc = new CalculatorFunctions(0, 0, 0, out, in); // 初期値を「機能クラス」に受け渡し

        Log.i("初期の税率の値(include)", Double.toString(in));
        Log.i("初期の税率の値(without)", Double.toString(out));
	}

	/** onResume **/
	@Override
	protected void onResume() {
		super.onResume();
	} // onResumeはここまで

	/** onClickで各ボタンが押された時の振る舞いを設定
	 * checkLenth()メソッドは、入力文字数を制限するために作成した。
	 *　そうしないと何文字でも画面に表示されてしまうし、値が巨大になってしまうのです。
	 *  **/
	@Override
	public void onClick(View v) {
		Log.i("onClick押下時","numbersの値: "+numbers+" Mnumの値: "+Double.toString(calfunc.getNums())+
				" numsの値: "+Double.toString(calfunc.getNums())+" sumの値: "+Double.toString(calfunc.getSum()));
		vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
		vib.vibrate(10);

		int id = v.getId();

		switch(id){
		case R.id.one:
			checkLenth(NUM[1]); // ここで入力文字の長さを9文字以下に制限
			calfunc.colonCalc(); // 「,」を3桁毎にnumbersの文字列に追加するメソッド
			break;
		case R.id.two:
			checkLenth(NUM[2]);
			calfunc.colonCalc();
			break;
		case R.id.three:
			checkLenth(NUM[3]);
			calfunc.colonCalc();
			break;
		case R.id.four:
			checkLenth(NUM[4]);
			calfunc.colonCalc();
			break;
		case R.id.five:
			checkLenth(NUM[5]);
			calfunc.colonCalc();
			break;
		case R.id.six:
			checkLenth(NUM[6]);
			calfunc.colonCalc();
			break;
		case R.id.seven:
			checkLenth(NUM[7]);
			calfunc.colonCalc();
			break;
		case R.id.eight:
			checkLenth(NUM[8]);
			calfunc.colonCalc();
			break;
		case R.id.nine:
			checkLenth(NUM[9]);
			calfunc.colonCalc();
			break;
		case R.id.zero:
			checkLenth(NUM[0]);
			calfunc.colonCalc();
			break;
		case R.id.zerozero:
			checkLenth(NUM[0]);
			checkLenth(NUM[0]);
			calfunc.colonCalc();
			break;
		case R.id.dot:
			checkLenth(NUM[11]);
			calfunc.colonCalc();
			break;
		case R.id.equal:
			calfunc.equalCalc();
			break;
		case R.id.Percent:
			calfunc.parsentCalc();
			break;
		case R.id.root:
			calfunc.rootCalc();
			break;
		case R.id.Plus:
			calfunc.plusCalc();
			break;
		case R.id.Minus:
			calfunc.minusCalc();
			break;
		case R.id.Kakeru:
			calfunc.multiplyCalc();
			break;
		case R.id.Waru:
			calfunc.divideCalc();
			break;
		case R.id.IncludeTaxes:
			calfunc.includeTaxCalc();
			break;
		case R.id.WithoutTaxes:
			calfunc.withoutTaxCalc();
			break;
		case R.id.C:
			calfunc.clearC();
			break;
		case R.id.CE:
			calfunc.clearCE();
			break;
		case R.id.CA:
			calfunc.clearCA();
			break;
		case R.id.MPlus:
			calfunc.mplusCalc();
			break;
		case R.id.MMinus:
			calfunc.mminusCalc();
			break;
		case R.id.CM:
			calfunc.clearCM();
			break;
		case R.id.RM:
			calfunc.reloadMemory();
			break;
		case R.id.plusminus:
			if(pushpm==0){
				pushpm = 1; // 「-」の状態
				calfunc.plusMinus();
			}
			else{
				pushpm = 0;
				calfunc.colonCalc();
			}
			break;
			case R.id.setting:
				Intent intent_pref = new Intent(this,CalculatorPreference.class);
				startActivityForResult(intent_pref, REQ_PREFE);
				finish();
				break;
		} // switch

		Log.i("onClick終了時","numbersの値: "+numbers+" Mnumの値: "+Double.toString(calfunc.getMnum())+
				" numsの値: "+Double.toString(calfunc.getNums())+" sumの値: "+Double.toString(calfunc.getSum()));

	} // onclick

	/** 別のActivityから戻ってきた時に値を反映させる **/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("REQ_PREFEとrequestCodeの値", Integer.toString(REQ_PREFE) +" " + Integer.toString(requestCode));
		Log.i("resultCodeとRESULT_OKの値", Integer.toString(resultCode) +" " + Integer.toString(RESULT_OK));
		if(requestCode == REQ_PREFE){
			makeTax();
		}
		else{

		}
	} // onActivityResult

	/** 画面の向きが変わるとActivityが破棄されるので、破棄される前の状態を保存 **/
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putDouble("nums", calfunc.getNums());
		outState.putDouble("sum", calfunc.getSum());
		String svd1,svd2,svd3;
		svd1 = dispFour.getText().toString();
		svd2 = dispNum.getText().toString();
		svd3 = dispMemory.getText().toString();
		outState.putString("disp1", svd1);
		outState.putString("disp2", svd2);
		outState.putString("disp3", svd3);
		outState.putString("sb", numbers.toString());
	} // onSaveInstanceState


	/** 再度Activityが生成される時に呼び出される **/
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		calfunc.setNums(savedInstanceState.getDouble("nums")) ;
		calfunc.setSum(savedInstanceState.getDouble("sum"));
		String sv1,sv2,sv3,svs;
		sv1 = savedInstanceState.getString("disp1");
		sv2 = savedInstanceState.getString("disp2");
		sv3 = savedInstanceState.getString("disp3");
		svs = savedInstanceState.getString("sb");
		
		if(sv1 != null){
			dispFour.setText(sv1);
		}
		if(sv2 != null){
			dispNum.setText(sv2);
		}
		if(sv3 != null){
			dispMemory.setText(sv3);
		}
		if(svs != null){
			numbers.append(svs);
		}
	} // onRestoreInstanceState


	/** 入力文字列の長さをチェックするメソッド **/
	private void checkLenth(String check){
		if(numbers.toString().length()<9){ //9以下だったら連結
			numbers.append(check);
		}
	}


	@Override
	protected void onStop() {
		Log.i("MainのonStop","MainのonStopが呼ばれました");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i("MainのonDestroy","MainのonDestroyが呼ばれました");
		super.onDestroy();
	}

	@Override
	public void onEnterAmbient(Bundle ambientDetails) {
		super.onEnterAmbient(ambientDetails);
		updateDisplay();
	}

	@Override
	public void onUpdateAmbient() {
		super.onUpdateAmbient();
		updateDisplay();
	}

	@Override
	public void onExitAmbient() {
		updateDisplay();
		super.onExitAmbient();
	}

	private void updateDisplay() {
		if (isAmbient()) {
//			dispNum.setBackgroundColor(getResources().getColor(android.R.color.black));
//			dispMemory.setTextColor(getResources().getColor(android.R.color.white));
//			dispNum.setVisibility(View.VISIBLE);
//
//			mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
		} else {
//			mContainerView.setBackground(null);
//			mTextView.setTextColor(getResources().getColor(android.R.color.black));
//			mClockView.setVisibility(View.GONE);
		}
	}

} // TomaCalculatorActivity