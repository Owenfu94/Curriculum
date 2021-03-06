package com.caoyanming.curriculum.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.caoyanming.curriculum.R;
import com.caoyanming.curriculum.data.DataManager;
import com.caoyanming.curriculum.data.bean.Note;
import com.caoyanming.curriculum.ui.AlertWindow;
import com.caoyanming.curriculum.ui.UIUtils;
import com.caoyanming.curriculum.ui.view.DrawLine;
import com.caoyanming.util.T;
import com.caoyanming.util.TimeUtil;
/**
 * 
 * @author saymagic
 *
 */
public class EditActivity extends BaseActivity {
	private Context context = this;
	private EditText editText;
	private TextView textView;
	private Note note;
	private String text;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentLayout(R.layout.showedit);
		this.note = (Note) getIntent().getSerializableExtra("note");
		this.textView = ((TextView) findViewById(R.id.editdate));
		this.editText = ((DrawLine) findViewById(R.id.edittexttwo));
		this.editText.setSelection(this.editText.length());
		this.editText.setText(this.note.getContent());
		this.textView.setText(TimeUtil.getDate());
		this.setTitle("修改笔记");
		this.text = note.getContent() == null ? "" : note.getContent();
	}

	@Override
	protected void onTitleLeftButtonClicked(View view) {
		final String strContent = editText.getText().toString();
		if(!this.text.equals(strContent)){
			UIUtils.showAlertWindowWithDeleteOnRight(EditActivity.this, null, "您需要保存刚才的修改吗", "是", new AlertWindow.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					note.setContent(strContent);
					String strTitle=strContent.length()>11?" "+strContent.substring(0, 11):strContent;
					note.setTitle(strTitle);
					note.setDate(TimeUtil.getDate());
					DataManager.getDataManager(context).updateNote(note);
					T.show(context, "修改成功",1000);
					finish();					
				}
			}, "否", new AlertWindow.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					note.setDate(TimeUtil.getDate());
					DataManager.getDataManager(context).updateNote(note);
					UIUtils.dismissAlertWindow();
					finish();
				}
			});
		}
	}

	@Override
	protected void onTitleRightButtonClicked(View view) {
		super.onTitleRightButtonClicked(view);
	}

}
