package com.shui.nasor.Presenter;

import com.shui.nasor.Base.BaseRxPresenter;
import com.shui.nasor.Http.RetrofitHelper;
import com.shui.nasor.Http.RxHelper;
import com.shui.nasor.Model.Bean.Relax.IMGJokerEntity;
import com.shui.nasor.Presenter.Contract.IMGJokerContract;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * 作者： max_Shui on 2016/12/16.
 * 邮箱：shuicz0505@qq.com
 * ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ
 * ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ
 * ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ
 */


public class IMGJokerPresenter extends BaseRxPresenter<IMGJokerContract.View> implements IMGJokerContract.Presenter {
    RetrofitHelper retrofitHelper;
    private int page=1;
    private int maxSize=20;
    @Inject
    public IMGJokerPresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void getData() {
        mView.showLoading();
        Subscription subscription=retrofitHelper.loadIMGJoker(page,maxSize)
                .compose(RxHelper.<IMGJokerEntity>RxTransformer())
                .subscribe(new Action1<IMGJokerEntity>() {
                    @Override
                    public void call(IMGJokerEntity entity) {
                        mView.showData(entity);
                        mView.hiddenLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("什么鬼！加载不出来");
                    }
                });
        beSubscribe(subscription);
    }

    @Override
    public void getNextData() {
        mView.showLoading();
        page++;
        Subscription subscription=retrofitHelper.loadIMGJoker(page,maxSize)
                .compose(RxHelper.<IMGJokerEntity>RxTransformer())
                .subscribe(new Action1<IMGJokerEntity>() {
                    @Override
                    public void call(IMGJokerEntity entity) {
                        mView.showNextPage(entity);
                        mView.hiddenLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("加载失败了。。");
                    }
                });
        beSubscribe(subscription);
    }

    @Override
    public void getPreData() {
        if (page==1)
        {
            mView.showNoPrePage();
        }
        else
        {
            mView.showLoading();
            page--;
            Subscription subscription=retrofitHelper.loadIMGJoker(page,maxSize)
                    .compose(RxHelper.<IMGJokerEntity>RxTransformer())
                    .subscribe(new Action1<IMGJokerEntity>() {
                        @Override
                        public void call(IMGJokerEntity entity) {
                            mView.showPrePage(entity);
                            mView.hiddenLoading();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mView.showError("出错了、、");
                        }
                    });
            beSubscribe(subscription);
        }
    }
}
