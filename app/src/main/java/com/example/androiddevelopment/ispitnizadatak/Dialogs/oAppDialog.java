package com.example.androiddevelopment.ispitnizadatak.Dialogs;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.androiddevelopment.ispitnizadatak.R;


public class oAppDialog extends AlertDialog.Builder{

	public oAppDialog(Context context) {
		super(context);
		
		setTitle(R.string.dialog_oApp_naziv);
	    setMessage(R.string.dialog_oApp_tekst);
	    setCancelable(false);
	    
	    setPositiveButton(R.string.dialog_oApp_Uredu, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
	    
	    setNegativeButton(R.string.dialog_oApp_Ponisti, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
	}
	
	
	public AlertDialog prepareDialog(){
		AlertDialog dialog = create();
		dialog.setCanceledOnTouchOutside(false);
		
		return dialog;
	}
	
}
