// Generated code from Butter Knife. Do not modify!
package com.huan.myadt.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainFragment1$$ViewBinder<T extends com.huan.myadt.fragment.MainFragment1> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427425, "field 'mDropDownMenu'");
    target.mDropDownMenu = finder.castView(view, 2131427425, "field 'mDropDownMenu'");
  }

  @Override public void unbind(T target) {
    target.mDropDownMenu = null;
  }
}
