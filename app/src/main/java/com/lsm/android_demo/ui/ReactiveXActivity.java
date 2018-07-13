package com.lsm.android_demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lsm.android_demo.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ReactiveXActivity extends AppCompatActivity {

    private final  static String TAG = "ReactiveXActivity";

    Observable<String> observable = Observable.just("Hello", "Hi", "Aloha");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reactive_x);
        //observable.subscribe(observer);

        Observable.create(new ObservableOnSubscribe<String>(){

                              @Override
                              public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                  emitter.onNext("Hello");
                                  emitter.onNext("world");
                                  emitter.onComplete();
                                  emitter.onNext("gg");
                              }
            }).subscribe(new Observer<String>(){


            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }



//    观察者
    Observer<String> observer = new Observer<String>() {

        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "onSubscribe: ");
        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item: " + s);
        }


        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete: ");
        }

    };



}
