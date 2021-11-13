package com.example.carapihelloworld;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VehiclePropertyView extends RelativeLayout {

    private Context mContext;
    private TextView mId;
    private TextView mName;
    private TextView mValue;
    private Button mEditButton;

    public VehiclePropertyView(Context context) {
        super(context);
    }

    public VehiclePropertyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflate(context, R.layout.vehicle_property_view, this);

        mId = findViewById(R.id.vehicle_property_id);
        mId.setText("");

        mName = findViewById(R.id.vehicle_property_name);
        mName.setText("");

        mValue = findViewById(R.id.vehicle_property_value);
        mValue.setText("");

        mEditButton = findViewById(R.id.vehicle_property_set);
    }

    public VehiclePropertyView setPropId(int id) {
        mId.setText(String.valueOf(id));
        return this;
    }

    public VehiclePropertyView setPropName(String text) {
        mName.setText(text);
        return this;
    }

    public VehiclePropertyView setPropValue(String text) {
        mValue.setText(text);
        return this;
    }

    public VehiclePropertyView enableSetValue(final SetValueCB cb) {
        mEditButton.setEnabled(true);

        mEditButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Set \'" + mName.getText() + "\' vehicle property value to:");

                final EditText input = new EditText(mContext);
                input.setText(mValue.getText());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cb.onSetValue(input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public interface SetValueCB {
        void onSetValue(String value_to_set);
    }
}

