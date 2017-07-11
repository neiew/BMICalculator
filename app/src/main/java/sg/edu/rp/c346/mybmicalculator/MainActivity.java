package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText txtWeight;
    EditText txtHeight;

    Button btnReset;
    Button btnCalculate;

    TextView tvBMI;
    TextView tvDate;
    TextView tvBMIDetail;

    @Override
    protected void onPause() {
        super.onPause();

        String strWeight = txtWeight.getText().toString();
        String strHeight = txtHeight.getText().toString();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("Weight", strWeight);
        prefEdit.putString("Height", strHeight);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String strWeight = prefs.getString("Weight", txtWeight.getText().toString());
        String strHeight = prefs.getString("Height", txtHeight.getText().toString());
        txtWeight.setText(strWeight);
        txtHeight.setText(strHeight);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWeight = (EditText)findViewById(R.id.txtWeight);
        txtHeight = (EditText)findViewById(R.id.txtHeight);

        btnReset = (Button)findViewById(R.id.btnReset);
        btnCalculate = (Button)findViewById(R.id.btnCalculate);

        tvBMI = (TextView)findViewById(R.id.tvBMI);
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvBMIDetail = (TextView)findViewById(R.id.tvBMIDetail);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculate BMI:0.0");
                txtHeight.setText("");
                txtWeight.setText("");
                tvBMIDetail.setText("");
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double weight = Double.parseDouble(txtWeight.getText().toString());
                double height = Double.parseDouble(txtHeight.getText().toString());

                double BMI = (weight / (height * height));

                String detail = "";

                Calendar now = Calendar.getInstance();

                String datetime = now.get(Calendar.DAY_OF_MONTH)+ "/"+
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY)+":"+
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last Calculated Date:" + datetime);
                tvBMI.setText("Last Calculate BMI: "+ String.format("%.3f", BMI));

                if(BMI < 18.5){
                    detail = "Underweight";
                }
                else if(BMI < 24.9){
                    detail = "Normal";
                }
                else if(BMI < 29.9){
                    detail = "Overweight";
                }
                else{
                    detail = "Obese";
                }

                tvBMIDetail.setText("You are " + detail);


            }
        });
    }


}
