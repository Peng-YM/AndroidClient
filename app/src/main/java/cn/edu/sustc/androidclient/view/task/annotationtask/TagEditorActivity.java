package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.common.utils.FileUtils;
import cn.edu.sustc.androidclient.databinding.ActivityTagEditorBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.task.annotationtask.form.FormField;

import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AnnotationTag;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.Attribute;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.BOOLEAN;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.MULTI_OPTION;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.NUMBER;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.SINGLE_OPTION;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.STRING;

public class TagEditorActivity extends BaseActivity<AnnotationTaskViewModel, ActivityTagEditorBinding> {
    private AnnotationTag tag;
    private AnnotationTaskViewModel viewModel;
    private ActivityTagEditorBinding binding;
    private AwesomeValidation awesomeValidation;
    private ArrayList<FormField> formFields;

    public static void start(Context context, AnnotationTag tag){
        Intent intent = new Intent(context, TagEditorActivity.class);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    @Override
    protected Class<AnnotationTaskViewModel> getViewModel() {
        return AnnotationTaskViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, AnnotationTaskViewModel viewModel, ActivityTagEditorBinding binding) {
        this.viewModel = viewModel;
        this.binding = binding;
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);//创建一个做验证的对象
        formFields = new ArrayList<>();

        Intent intent = getIntent();
        tag = (AnnotationTag) intent.getSerializableExtra("task");

        binding.tagName.setText(tag.name);
        binding.tagDescription.setText(tag.description);
        binding.previewTagButton.setOnClickListener(v -> {});
        binding.saveTagButton.setOnClickListener(v -> { // 点击提交按钮的时候引用awesome validation验证
            // check all field is filled
            for(FormField field: formFields){
                if (!field.filled()){
                    Logger.d("Field is not filled!" + field.getValues());
                    showAlertDialog(getString(R.string.alert), getString(R.string.alert_field_empty));
                    return;
                }
            }
            if(awesomeValidation.validate()){
                getResults();
                showAlertDialog("", getString(R.string.add_success));
            }
        });
        addAttributes();
    }

    private void addAttributes(){
        for(Attribute attribute: tag.attributes){
            TextView nameTv = new TextView(this);
            nameTv.setText(attribute.name);
            nameTv.setTextSize(20);
            TextView descriptionTv = new TextView(this);
            descriptionTv.setText(attribute.description);
            binding.tagEditorLayout.addView(nameTv);
            binding.tagEditorLayout.addView(descriptionTv);
            FormField field;
            switch(attribute.type){
                case SINGLE_OPTION:{
                    field = new FormField.SingleOptionField(
                            this, binding.tagEditorLayout, attribute.options);
                    break;
                }
                case MULTI_OPTION: {
                    field = new FormField.MultiOptionsField(
                            this, binding.tagEditorLayout, attribute.options);
                    break;
                }
                case BOOLEAN: {
                    field = new FormField.BooleanField(
                            this, binding.tagEditorLayout);
                    break;
                }
                case NUMBER:{
                    field = new FormField.NumberField(
                            this, awesomeValidation, binding.tagEditorLayout);
                    break;
                }
                default:
                case STRING:{
                    field = new FormField.StringField(
                            this, awesomeValidation, binding.tagEditorLayout);
                    break;
                }
            }
            formFields.add(field);
        }
    }

    private void getResults(){
        for (int i = 0; i < tag.attributes.size(); i++){
            FormField field = formFields.get(i);
            tag.attributes.get(i).values = field.getValues();
        }
        Logger.json(new Gson().toJson(tag));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tag_editor;
    }

}
