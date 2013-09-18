package edu.berkeley.cs160.utkarshdalal.prog1;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Spinner mUnitSpinner;
	private Spinner mResultSpinner;
	private String mFromUnitString;
	private String mToUnitString;
	private String[] list = null;
	private EditText mEditText;
	private TextView mTextView;
	private boolean hasValue = false;
	private Button mButton, mSwapButton;
	private double from, to;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mUnitSpinner = (Spinner) findViewById(R.id.unit_spinner);
		mResultSpinner = (Spinner) findViewById(R.id.convert_to_spinner);
		mEditText = (EditText) findViewById(R.id.enter_edit_text);
		mTextView = (TextView) findViewById(R.id.result_text_view);
		mButton = (Button) findViewById(R.id.convert_button);
		mSwapButton = (Button) findViewById(R.id.swap_button);
		
		//The code below this is the listener for the EditText
		
		mEditText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if(s.length() == 0){
					from = 0;
				}
				else{
					from = Double.parseDouble(s.toString());
					hasValue = true;
				}
			}
			
		});
		
		//The code below is the listener for the first spinner.
		
		mUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			String[] distance = {"Km", "Miles"};
			String[] temperature = {"Degrees C", "Degrees F"};
			String[] weight = {"Pounds", "Grams"};

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mFromUnitString = mUnitSpinner.getItemAtPosition(position).toString();
				if (mFromUnitString.equalsIgnoreCase("Km") || mFromUnitString.equalsIgnoreCase("Miles")){
					if (list == distance){
						return;
					}
					list = distance;
				}
				else if (mFromUnitString.equalsIgnoreCase("Degrees C") || mFromUnitString.equalsIgnoreCase("Degrees F")){
					if (list == temperature){
						return;
					}
					list = temperature;
				}
				else if (mFromUnitString.equalsIgnoreCase("Pounds") || mFromUnitString.equalsIgnoreCase("Grams")){
					if (list == weight){
						return;
					}
					list = weight;
				}
				update();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		mButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (!hasValue){
					Toast.makeText(MainActivity.this, R.string.illegal_value, Toast.LENGTH_SHORT).show();
					return;
				}
				convert();
				mTextView.setText(String.valueOf(to));
			}			
		});
		
		mSwapButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				int tempResult = java.util.Arrays.asList(list).indexOf(mUnitSpinner.getSelectedItem().toString());
				int tempUnit = Arrays.asList((getResources().getStringArray(R.array.units_array))).indexOf(mResultSpinner.getSelectedItem().toString());
				mUnitSpinner.setSelection(tempUnit);
				mResultSpinner.setSelection(tempResult);
			}			
		});

	}
	
	public void update(){
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mResultSpinner.setAdapter(dataAdapter);
	}
	
	public void convert(){
		mToUnitString = mResultSpinner.getSelectedItem().toString();
		
		if (mFromUnitString.equalsIgnoreCase("Km")){
			if (mToUnitString.equals("Km")){
				to = from;
			}
			else if (mToUnitString.equals("Miles")){
				to = from * 0.62137;
			}
		}
		
		else if (mFromUnitString.equalsIgnoreCase("Miles")){
			if (mToUnitString.equals("Km")){
				to = from * 1.609344;
			}
			else if (mToUnitString.equals("Miles")){
				to = from;
			}
		}
		
		else if (mFromUnitString.equalsIgnoreCase("Degrees C")){
			if (mToUnitString.equals("Degrees C")){
				to = from;
			}
			else if (mToUnitString.equals("Degrees F")){
				to = from * 1.8 + 32;
			}
		}
		
		else if (mFromUnitString.equalsIgnoreCase("Degrees F")){
			if (mToUnitString.equals("Degrees C")){
				to = (from - 32)/1.8;
			}
			else if (mToUnitString.equals("Degrees F")){
				to = from;
			}
		}

		else if (mFromUnitString.equalsIgnoreCase("Grams")){
			if (mToUnitString.equals("Grams")){
				to = from;
			}
			else if (mToUnitString.equals("Pounds")){
				to = from * 0.0022046;
			}
		}

		else if (mFromUnitString.equalsIgnoreCase("Pounds")){
			if (mToUnitString.equals("Grams")){
				to = from / 0.0022046;
			}
			else if (mToUnitString.equals("Pounds")){
				to = from;
			}
		}
	}

	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
