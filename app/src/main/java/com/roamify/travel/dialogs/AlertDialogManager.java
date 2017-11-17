package com.roamify.travel.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.roamify.travel.R;
import com.roamify.travel.utils.Validations;


public class AlertDialogManager {
    public void showAlartDialog(Activity context, String titleString, String msg) {
        try {
            final Dialog mdialog = new Dialog(context);
            mdialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mdialog.setContentView(R.layout.alertdialog_ok);
            //mdialog.getWindow().getAttributes().windowAnimations = R.style.PaaswordDilaogAnimation;
            mdialog.setCancelable(false);
            mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = mdialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            //wlp.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(wlp);
            //mdialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView okButton = (TextView) mdialog.findViewById(R.id.okButton);
            TextView title = (TextView) mdialog.findViewById(R.id.title);
            if (Validations.isNotNullNotEmptyNotWhiteSpace(titleString)) {
                title.setVisibility(View.VISIBLE);
                title.setText(titleString);
            }

            TextView message = (TextView) mdialog.findViewById(R.id.msg);
            message.setText(msg);

            okButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mdialog.dismiss();
                }
            });

            //dialog = builder.create();
            //dialog.setCancelable(false);
            mdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showConfirmationDialog(Activity context, String titleString, String msg) {
        try {
            final Dialog mdialog = new Dialog(context);
            mdialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mdialog.setContentView(R.layout.alert_confirmation_dialog);
            //mdialog.getWindow().getAttributes().windowAnimations = R.style.PaaswordDilaogAnimation;
            mdialog.setCancelable(false);
            mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = mdialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            //wlp.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(wlp);
            //mdialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView okButton = (TextView) mdialog.findViewById(R.id.okButton);
            TextView cancelButton = (TextView) mdialog.findViewById(R.id.cancelButton);
            TextView title = (TextView) mdialog.findViewById(R.id.title);
            if (Validations.isNotNullNotEmptyNotWhiteSpace(titleString)) {
                title.setVisibility(View.VISIBLE);
                title.setText(titleString);
            }

            TextView message = (TextView) mdialog.findViewById(R.id.msg);
            message.setText(msg);

            okButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mdialog.dismiss();
                }
            });

            cancelButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mdialog.dismiss();
                }
            });

            //dialog = builder.create();
            //dialog.setCancelable(false);
            mdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showQueryDialog(Activity context) {
        try {
            final Dialog mdialog = new Dialog(context);
            mdialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mdialog.setContentView(R.layout.query_submition_dialog);
            mdialog.setCancelable(false);
            mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = mdialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
            TextView okButton = (TextView) mdialog.findViewById(R.id.submitButton);
            TextView cancelButton = (TextView) mdialog.findViewById(R.id.cancelButton);

            /*TextView title = (TextView) mdialog.findViewById(R.id.title);
            TextView message = (TextView) mdialog.findViewById(R.id.msg);
            message.setText(msg);*/

            okButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mdialog.dismiss();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mdialog.dismiss();
                }
            });
            mdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}