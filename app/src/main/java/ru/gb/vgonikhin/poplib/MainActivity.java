package ru.gb.vgonikhin.poplib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = "Main";
    @BindView(R.id.ll_main)
    LinearLayout mainLayout;
    @BindView(R.id.btn_just)
    Button justButton;
    @BindView(R.id.text_view)
    TextView textView;
    @BindView(R.id.edit_text)
    EditText editText;

    Observable<String> justObservable;
    Observable<String> iterableObservable;
    Observer<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        justObservable = Observable.just("hello");
        justObservable.subscribe(observer);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("item1");
        arrayList.add("item2");
        arrayList.add("item3");
        arrayList.add("item4");
        iterableObservable = Observable.fromIterable(arrayList);
        iterableObservable.subscribe(observer);

        Disposable d = RxTextView.textChanges(editText).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence value) {
                textView.setText(value);
            }
        });
    }

    @OnClick(R.id.btn_just)
    public void justButtonClick(Button button)
    {
        Log.d(TAG, "just");
    }


}
