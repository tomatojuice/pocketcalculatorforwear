package tomatojuice.sakura.ne.jp.pocketcalculatorforwear;

import android.util.Log;

import java.text.NumberFormat;

public class CalculatorFunctions {
	public double nums,sum,Mnum,WithoutTax,IncludeTax;
	private double coloncal,pmchange;
	private NumberFormat colnum,iodnum,pm;

	public CalculatorFunctions(double nums, double sum, double mnum,
			double withoutTax, double includeTax) {
		super();
		setNums(nums);
		setSum(sum);
		setMnum(mnum);
		setWithoutTax(withoutTax);
		setIncludeTax(includeTax);
	}

/************************ getterとsetterの設定 *********************/
	public double getNums() {
		return nums;
	}

	public void setNums(double nums) {
		this.nums = nums;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public double getMnum() {
		return Mnum;
	}

	public void setMnum(double mnum) {
		Mnum = mnum;
	}

	public double getWithoutTax() {
		return WithoutTax;
	}

	public void setWithoutTax(double withoutTax) {
		WithoutTax = withoutTax;
	}

	public double getIncludeTax() {
		return IncludeTax;
	}

	public void setIncludeTax(double includeTax) {
		IncludeTax = includeTax;
	}

/********************************* getterとsetterの設定はここまで ***************************************/

	/************** 表示メソッド 3桁ごとに「,」を入れて表示,桁数の指定をするためのメソッド 
	 * NumberFormatで自動で「,」を付加出来る事を知る前に試したメソッド。
	 * 実質NumberFormatで「,」の有無と桁数の指定をして、結果を表示するメソッドなのです。
	 * **************/
	public void colonCalc(){
		// numbers、つまり、入力された数字の桁数のログを表示
		Log.i("colonCalcのnumbersの値", TomaCalculatorActivity.numbers.toString());
		try{
			coloncal = Double.parseDouble(TomaCalculatorActivity.numbers.toString()); // 入力された文字をdouble型の変換し、変数に代入
			// ここで入力値の正負の反転を行っている
			if(TomaCalculatorActivity.pushpm == 1){
				coloncal = -coloncal;
			}
			else{
				coloncal = Math.abs(coloncal); // 絶対値を返すのです
			}
		}
		catch(Exception e){
			// 代入に失敗した時
		}
		colnum = NumberFormat.getIntegerInstance();
		colnum.setMaximumIntegerDigits(9); // 整数の最大桁数を設定
		colnum.setMaximumFractionDigits(9); // 小数の最大桁数を設定
		colnum.setGroupingUsed(false); // 3桁毎にカンマを入れない指定

		String coloncalstr = colnum.format(coloncal); // 値をセット。NumberFormatはString型の値を返す
		Log.i("coloncalの値", coloncalstr);
//		coloncalstr.replace(",","");
//		Log.i("coloncalの値、replace後", coloncalstr);
		setNums(Double.parseDouble(coloncalstr));
		TomaCalculatorActivity.dispNum.setText(coloncalstr);
		
//		if(TomaCalculatorActivity.numbers.length()>10){
//			TomaCalculatorActivity.numbers.setLength(9);
//			setNums(Double.parseDouble(TomaCalculatorActivity.numbers.toString()));
//			
//		}
//		TomaCalculatorActivity.dispNum.setText(TomaCalculatorActivity.numbers); // 画面に表示


//		Log.i(".の場所", Integer.toString(TomaCalculatorActivity.numbers.indexOf(TomaCalculatorActivity.NUM[11])));
//		Log.i("TomaCalculatorActivity.numbersの長さ2", Integer.toString(TomaCalculatorActivity.numbers.length()));
//		//	numbersの長さが4以上、5以下で「,」がない場合、4桁目に「,」を入れる。 (例)1000 → 1,000
//		if(TomaCalculatorActivity.numbers.indexOf(TomaCalculatorActivity.NUM[11])==-1 && TomaCalculatorActivity.numbers.length()>=4 && TomaCalculatorActivity.numbers.length()<=5 && TomaCalculatorActivity.numbers.indexOf(TomaCalculatorActivity.NUM[10])==-1){
//			TomaCalculatorActivity.numbers.insert(3, TomaCalculatorActivity.NUM[10]);
//		}
//		//	numbersの長さが8以上、9以下で4桁目に「,」がある場合、8桁目に「,」を入れる。 (例)1000,000 → 1,000,000
//		if(TomaCalculatorActivity.numbers.indexOf(TomaCalculatorActivity.NUM[11])==-1 && TomaCalculatorActivity.numbers.length()>=8 && TomaCalculatorActivity.numbers.length()<=9 && TomaCalculatorActivity.numbers.indexOf(TomaCalculatorActivity.NUM[10])==3){
//			TomaCalculatorActivity.numbers.insert(7, TomaCalculatorActivity.NUM[10]);
//		}
//		//	numbersの長さが12以上、13以下で8桁目に「,」がある場合、12桁目に「,」を入れる。 (例)1000,000,000 → 1,000,000,000
//		if(TomaCalculatorActivity.numbers.indexOf(TomaCalculatorActivity.NUM[11])==-1 && TomaCalculatorActivity.numbers.length()>=12 && TomaCalculatorActivity.numbers.length()<=13 && TomaCalculatorActivity.numbers.lastIndexOf(TomaCalculatorActivity.NUM[10])==7){
//			TomaCalculatorActivity.numbers.insert(11, TomaCalculatorActivity.NUM[10]);
//		}

	} // colonCalcはここまで


	/************** 「,」を消して普通のdouble型の整数に変換するメソッド
	 * NumberFormatで「,」の付加の有無を選択出来る事を知る前に「,」を削除するために作成したメソッド。
	 *  作成過程が分かるので残してるのです。実質StringBufferクラスのnumbersをリセットするだけのメソッドです。
	 *  **************/
	public void numbersConmaDelete(){

//		if(TomaCalculatorActivity.numbers.indexOf(TomaCalculatorActivity.NUM[10])==3 && TomaCalculatorActivity.numbers.length()>=4){
//			TomaCalculatorActivity.numbers.deleteCharAt(3);
//		}
//		if(TomaCalculatorActivity.numbers.indexOf(TomaCalculatorActivity.NUM[10])==6 && TomaCalculatorActivity.numbers.length()>=7){
//			TomaCalculatorActivity.numbers.deleteCharAt(6);	
//		}
//		if(TomaCalculatorActivity.numbers.indexOf(TomaCalculatorActivity.NUM[10])==9 && TomaCalculatorActivity.numbers.length()>=10){
//			TomaCalculatorActivity.numbers.deleteCharAt(9);
//		}

		if(TomaCalculatorActivity.numbers.length()!=0){ // numbersの文字列の長さが0でなければnumbersをdouble型に変換
//			setNums(Double.parseDouble(TomaCalculatorActivity.numbers.toString()));
			TomaCalculatorActivity.numbers.setLength(0); // TomaCalculatorActivity.numbersを初期化
		}
		Log.i(".numbersConmaDelete", "numsの値: "+Double.toString(getNums()));
		
	}

/************** 基本の計算メソッド　四則演算です **************/
	public void baseCalc(int h){
		Log.i("baseCalc()の呼び出し", "pushForBaseの値： " +Integer.toString(TomaCalculatorActivity.pushFourBase));
		numbersConmaDelete(); // numbersの文字列をdouble型に変換し、numsに代入　（実質numbersの値クリアするだけｗ）
		switch(TomaCalculatorActivity.pushFourBase){
			case 1:		// 足し算
				sum += nums;
				break;
			case 2:		// 引き算
				if(sum==0){ // 合計が0の時に合計=合計-入力値だと「-」になるので、それを防ぐために入力値を2倍にしている
					sum = nums*2;
				}
				sum -= nums;
				break;
			case 3:		// 乗算
				if(sum==0){
					sum = 1;
				}
				sum *= nums;
				break;
			case 4:		// 除算
				if(sum==0){
					sum = nums;
					sum *= nums;
				}
				sum /= nums;
				break;
			case 5:
				break;
			case 6:
				break;
			default :
				Log.i("baseCalc()","default内の実行" );
				break;
		} // switch文はここまで
		intOrdouble(); // 画面にintかdouble型で数字を表示するメソッド (実質)
		setNums(0); // 数値一時保管の変数初期化
	} // baseCalcはここまで


	/************** display表示メソッド(sum用) intかdouble型の表示判定
	 * 最初作成した時は、int型とdouble型で表示を分けようと思ったので
	 * if文で分ける処理をしていたけど、ぶっちゃけそんな必要はないと思った。
	 * でも、作成過程が分かるように残している。
	 * 実質数値を切り捨てて、結果を画面に表示するためのメソッドなのです。
	 *  **************/
	public void intOrdouble(){
		Log.i("intOrdouble()の呼び出し", "sumの値: "+Double.toString(getSum()));
		double iodSum = getSum();
		String strId,substrId,strgetSum;
		strgetSum = Double.toString(getSum());	

		iodnum = NumberFormat.getIntegerInstance();
		iodnum.setMaximumIntegerDigits(9); // 整数の最大桁数を設定
		iodnum.setMaximumFractionDigits(9); // 小数部分の桁数を設定
		iodnum.setGroupingUsed(false); // 3桁毎にカンマを入れない指定
		strId = iodnum.format(iodSum); // 上の設定をdoubleに反映させてstring型に代入
//		StrId = Double.toString(getSum());

		int last =strId.lastIndexOf(".0");	// .0の場所を後ろの数字からカウントして、場所をint型へ代入
		Log.i("lastの場所", Integer.toString(last));
		Log.i("getSumの値", Double.toString(getSum()));
		Log.i("strgetSumの長さ", Integer.toString(strgetSum.length()));
		Log.i("strIdの長さ", Integer.toString(strId.length()));
		Log.i("iodSumの値", Double.toString(iodSum));
		
		if(strId.length()>=14){ // 文字列の長さが14以上の時
			substrId = strId.substring(0, 13);
			TomaCalculatorActivity.dispNum.setText(substrId);
		}
		else if(iodSum>999999999){ // そうでなく、もし9億9千999万9999より大きい時
			TomaCalculatorActivity.dispNum.setText(R.string.errormessege);
		}
		else{ // そうでない時(文字列の長さが14より小さく、9億9千999万9999以下の時)
			TomaCalculatorActivity.dispNum.setText(strId);
		}
		
		TomaCalculatorActivity.pushpm = 0;

//		if(last != -1){ // int型の場合
//			Log.i("intOrdoubleの判定", "int型です");
////			TomaCalculatorActivity.dispNum.setText(Integer.toString((int)getSum()));
//			TomaCalculatorActivity.dispNum.setText(strId);
//		}
//		else{ // double型の場合
////			TomaCalculatorActivity.dispNum.setText(Double.toString(getSum()));
//			Log.i("intOrdoubleの判定", "double型です");
//			if(strId.length()>=14){
//				substrId = strId.substring(0, 13);
//				TomaCalculatorActivity.dispNum.setText(substrId);
//			}
//			else{
//				TomaCalculatorActivity.dispNum.setText(strId);
//			}
//		}
	}

	/************** display表示メソッド2(nums用) 
	 * こちらもint型とdouble型で表示を分けようと思って作成したメソッド。
	 * if文で分ける処理をしていたけど、ぶっちゃけそんな必要はないと思った。
	 * でも、作成過程が分かるように残している。
	 * **************/
	public void intOrdouble2(){
		Log.i("intOrdouble2()の呼び出し", "numsの値: "+Double.toString(getNums()));
		String strId = Double.toString(getNums());		
		int last =strId.lastIndexOf(".0");	//	.0の場所を後ろの数字からカウント
		// int型で画面に表示するかdouble型で表示するかの判定
		if(last != -1){ // -1、つまり".0"がある場合
			TomaCalculatorActivity.dispNum.setText(Integer.toString((int)getNums()));
		}
		else{
	        TomaCalculatorActivity.dispNum.setText(Double.toString(getNums()));
		}
		TomaCalculatorActivity.pushpm = 0;
	}


	/** イコール押下時の計算 **/
	public void equalCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[4]);
//		numbersConmaDelete();
//		colonCalc();
		switch(TomaCalculatorActivity.pushFourBase){
			case 1: // 足し算
				sum += nums;
				break;
			case 2: // 引き算
				sum -= nums;
				break;
			case 3: // 掛け算
				sum *= nums;
				break;
			case 4: // 割り算
				sum /= nums;
				break;
			case 5:
				// ここは後日別の機能を追加
			case 6: // ルートの計算
				sum = Math.sqrt(nums);
				break;
			default:
				break;
		}
		TomaCalculatorActivity.pushFourBase = 0;
		setNums(0);
		intOrdouble();
		TomaCalculatorActivity.numbers.setLength(0);
	} // equalCalc()はここまで


	/** パーセント押下時の計算 **/
	public void parsentCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[4]);
//		colonCalc();
		switch(TomaCalculatorActivity.pushFourBase){
			case 1:	// 足し算の場合
				sum = sum + (sum / 100 * nums);
				break;
			case 2: // 引き算の場合
				sum = sum - (sum / 100 * nums);
				break;
			case 3: //	かけ算の場合
				sum = sum / 100 * nums;
				break;
			case 4: //	割り算の場合
				sum = sum * 100 / nums;
				break;
			default:
				break;
		}
		TomaCalculatorActivity.pushFourBase = 0;
		setNums(0);
		intOrdouble();
		TomaCalculatorActivity.numbers.setLength(0);
	}


	/** ルート押下時の計算 **/
	public void rootCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[4]);
//		numbersConmaDelete(); // numbersの文字列をdoubule(int)型に変換するメソッド
		switch(TomaCalculatorActivity.pushFourBase){
			case 1: // 足し算の場合
				sum = Math.sqrt(sum + nums);
				break;
			case 2: // 引き算の場合
				sum = Math.sqrt(sum - nums);
				break;
			case 3: // 掛け算の場合
				sum = Math.sqrt(sum * nums);
				break;
			case 4: // 割り算の場合
				sum = Math.sqrt(sum / nums);
				break;
			case 5:
				break;
			case 6:
				break;
			default:
				break;
		}
		if(sum==0){
			sum = nums;
			sum = Math.sqrt(sum);
		}
		if(nums==0 && sum!=0){
			sum = Math.sqrt(sum);
		}
		TomaCalculatorActivity.pushFourBase = 0;
		setNums(0);
		intOrdouble();
		TomaCalculatorActivity.numbers.setLength(0);
	}


	/** 足し算押下時の計算 **/
	public void plusCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[0]); // +の記号を表示
		if(getSum()!=0){ // 初回は前回の計算がないので処理しない。以下同じ
			baseCalc(TomaCalculatorActivity.pushFourBase);
		}
		TomaCalculatorActivity.pushFourBase = 1; // 足し算ボタン押下のフラグ
		baseCalc(TomaCalculatorActivity.pushFourBase); // 前回のpushFourBaseの値に基づいて計算
	}


	/** 引き算押下時の計算 **/
	public void minusCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[1]); // -の記号を表示
		if(getSum()!=0){
			baseCalc(TomaCalculatorActivity.pushFourBase);
		}
		TomaCalculatorActivity.pushFourBase = 2;
		baseCalc(TomaCalculatorActivity.pushFourBase);
	}


	/** 乗算押下時の計算 **/
	public void multiplyCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[2]); // xの記号を表示
		if(getSum()==0){ // 初回のみ次に押される四則演算へ計算させるためにpushFourBaseへ掛け算のフラグを代入し、計算させている
			TomaCalculatorActivity.pushFourBase = 3;
			baseCalc(TomaCalculatorActivity.pushFourBase);
		}
		else{ // 2回目以降、前回押された四則演算に基づいて計算させ、次回四則演算が押された時に計算できるようにpushFourBaseへ掛け算のフラグを代入
			baseCalc(TomaCalculatorActivity.pushFourBase);
			TomaCalculatorActivity.pushFourBase = 3;
		}
	}


	/** 除算押下時の計算 **/
	public void divideCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[3]); // ÷の記号を表示
		if(getSum()==0){
			TomaCalculatorActivity.pushFourBase = 4;
			baseCalc(TomaCalculatorActivity.pushFourBase);
		}
		else{
			baseCalc(TomaCalculatorActivity.pushFourBase);
			TomaCalculatorActivity.pushFourBase = 4;
		}
	}


	/** 税込押下時の計算 **/
	public void includeTaxCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[4]);
		numbersConmaDelete();
		switch(TomaCalculatorActivity.pushFourBase){
			case 1:
				sum += nums;
				sum = sum * IncludeTax;
				break;
			case 2:
				sum -= nums;
				sum = sum * IncludeTax;
				break;
			case 3:
				sum *= nums;
				sum = sum * IncludeTax;
				break;
			case 4:
				sum /= nums;
				sum = sum * IncludeTax;
				break;
			case 5:
				break;
			case 6:
				sum = Math.sqrt(nums);
				sum = sum * IncludeTax;
				break;
			default:
				break;
		} // switch文はここまで
		if(sum==0){
			sum=nums;
			sum = sum * IncludeTax;
		}
		if(nums==0 && sum!=0){
			sum = sum * IncludeTax;
		}
		TomaCalculatorActivity.pushFourBase = 0;
		setNums(0);
		intOrdouble();
		TomaCalculatorActivity.numbers.setLength(0);
	}


	/** 税抜き押下時の計算はここまで **/
	public void withoutTaxCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[4]);
		numbersConmaDelete();
		switch(TomaCalculatorActivity.pushFourBase){
			case 1:
				sum += nums;
				sum = sum * WithoutTax;
				break;
			case 2:
				sum -= nums;
				sum = sum * WithoutTax;
				break;
			case 3:
				sum *= nums;
				sum = sum * WithoutTax;
				break;
			case 4:
				sum /= nums;
				sum = sum * WithoutTax;
				break;
			case 5:
				break;
			case 6:
				sum = Math.sqrt(nums);
				sum = sum * WithoutTax;
				break;
			default:
				break;
		}
		
		if(sum==0){
			sum=nums;
			sum = sum * WithoutTax;
		}
		if(nums==0 && sum!=0){
			sum = sum * WithoutTax;
		}
		
		TomaCalculatorActivity.pushFourBase = 0;
		setNums(0);
		intOrdouble();
		TomaCalculatorActivity.numbers.setLength(0);
	}


	/** Cキー押下時の計算 **/
	public void clearC(){
		TomaCalculatorActivity.numbers.setLength(0);
		setSum(0);
		setNums(0);
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[4]);
		TomaCalculatorActivity.dispNum.setText("0");
	}


	/** CEキー押下時の計算 **/
	public void clearCE(){
		TomaCalculatorActivity.numbers.setLength(0);
		setNums(0);
		TomaCalculatorActivity.dispNum.setText("0");
	}


	/** CAキー押下時の計算 **/
	public void clearCA(){
		TomaCalculatorActivity.numbers.setLength(0);
		setSum(0);
		setNums(0);
		setMnum(0);
		TomaCalculatorActivity.pushpm = 0;
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[4]);
		TomaCalculatorActivity.dispNum.setText("0");
		TomaCalculatorActivity.dispMemory.setText(TomaCalculatorActivity.SIGN[4]);
	}


	/** MPlus押下時の計算 **/
	public void mplusCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[4]);
		TomaCalculatorActivity.dispMemory.setText(TomaCalculatorActivity.SIGN[5]);
		numbersConmaDelete();
		switch(TomaCalculatorActivity.pushFourBase){
			case 1:
				sum += nums;
				setMnum(sum);
				break;
			case 2:
				sum -= nums;
				setMnum(sum);
				break;
			case 3:
				sum *= nums;
				setMnum(sum);
				break;
			case 4:
				sum /= nums;
				setMnum(sum);
				break;
			case 5:
				break;
			case 6:
				sum = Math.sqrt(nums);
				setMnum(sum);
				break;
			default:
				setMnum(sum);
				break;
		}
		
		if(getSum()==0){
			setMnum(nums);
		}
		TomaCalculatorActivity.pushFourBase = 0;
		TomaCalculatorActivity.pushpm = 0;
		setNums(0);
		setSum(0);
		Log.i("Mの値", Double.toString(getMnum()));
	}


	/** MMinus押下時の計算 **/
	public void mminusCalc(){
		TomaCalculatorActivity.dispFour.setText(TomaCalculatorActivity.SIGN[4]);
		TomaCalculatorActivity.dispMemory.setText(TomaCalculatorActivity.SIGN[6]);
		numbersConmaDelete();
		switch(TomaCalculatorActivity.pushFourBase){
			case 1:
				sum += nums;
				Mnum = sum*-1;
				break;
			case 2:
				sum -= nums;
				Mnum = sum*-1;
				break;
			case 3:
				sum *= nums;
				Mnum = sum*-1;
				break;
			case 4:
				sum /= nums;
				Mnum = sum*-1;
				break;
			case 5:
				break;
			case 6:
				sum = Math.sqrt(nums);
				Mnum = sum*-1;
				break;
			default:
				Mnum = sum*-1;
				break;
		}
		if(sum==0){
			Mnum = nums*-1;
		}
		TomaCalculatorActivity.pushFourBase = 0;
		TomaCalculatorActivity.pushpm = 0;
		setNums(0);
		setSum(0);
		Log.i("M-の値", Double.toString(getMnum()));
	}


	/** CM押下時の計算 **/
	public void clearCM(){
		setMnum(0);
		TomaCalculatorActivity.pushpm = 0;
		TomaCalculatorActivity.dispMemory.setText(TomaCalculatorActivity.SIGN[4]);
	}


	/** RM押下時の計算 **/
	public void reloadMemory(){
		Log.i("RMのMnumの値", Double.toString(getMnum()));
		setNums(getMnum());
		intOrdouble2();
	}

	/** 正負反転のメソッド **/
	public void plusMinus(){
		Log.i("colonCalcのnumbersの値", TomaCalculatorActivity.numbers.toString());
		String pmstr;

		if(nums!=0){ // numsが0でない場合（入力後の場合）
				nums = -nums;
				pm = NumberFormat.getIntegerInstance();
				pm.setMaximumIntegerDigits(9); // 整数の最大桁数を設定
				pm.setMaximumFractionDigits(9); // 小数の最大桁数を設定
				pm.setGroupingUsed(false); // 3桁毎にカンマを入れない指定
		
				pmstr = pm.format(nums); // 値をセット。NumberFormatはString型の値を返す
				Log.i("+-のnumsの値", pmstr);
				setNums(Double.parseDouble(pmstr));
				TomaCalculatorActivity.dispNum.setText(pmstr);
			}
		else{ // numsが0の場合（入力前の場合）
				try{
					pmchange = Double.parseDouble(TomaCalculatorActivity.numbers.toString()); // 入力された文字をdouble型の変換し、変数に代入
//					pmchange = -pmchange;
				}
				catch(Exception e){
					// 代入に失敗した時
				}
				pm = NumberFormat.getIntegerInstance();
				pm.setMaximumIntegerDigits(9); // 整数の最大桁数を設定
				pm.setMaximumFractionDigits(9); // 小数の最大桁数を設定
				pm.setGroupingUsed(false); // 3桁毎にカンマを入れない指定
				
				pmstr = pm.format(pmchange); // 値をセット。NumberFormatはString型の値を返す
				pmchange = Double.parseDouble(pmstr);
				pmchange = -pmchange; // 負の数へ変更
				setNums(pmchange);
//				setNums(Double.parseDouble(pmstr));
//				TomaCalculatorActivity.dispNum.setText("-"+pmstr);
				TomaCalculatorActivity.dispNum.setText("-"); // 入力前なので、0の数値を表示する必要なはい

		}

	}


} // CalculatorFunctionsクラスはここまで
