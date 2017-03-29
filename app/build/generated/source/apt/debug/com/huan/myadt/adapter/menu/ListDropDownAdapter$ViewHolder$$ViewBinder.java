// Generated code from Butter Knife. Do not modify!
package com.huan.myadt.adapter.menu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ListDropDownAdapter$ViewHolder$$ViewBinder<T extends com.huan.myadt.adapter.menu.ListDropDownAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427466, "field 'mText'");
    target.mText = finder.castView(view, 2131427466, "field 'mText'");
  }

  @Override public void unbind(T target) {
    target.mText = null;
  }
}
