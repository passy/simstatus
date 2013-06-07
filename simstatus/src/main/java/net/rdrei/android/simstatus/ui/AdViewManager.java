package net.rdrei.android.simstatus.ui;

import android.view.ViewGroup;

public interface AdViewManager {
    void addToView(ViewGroup baseView);
    boolean addToViewIfRequired(ViewGroup baseView);
}
