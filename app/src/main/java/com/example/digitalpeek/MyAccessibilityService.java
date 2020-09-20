package com.example.digitalpeek;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        String eventText = "";

        eventText = event.getText().toString();

        if(MainActivity.notificationIssued && eventText!="")
        MainActivity.restart();

        if(!eventText.matches(""));
        MainActivity.container.add(eventText);

    }

    @Override
    public void onInterrupt() {

    }

    public void onServiceConnected() {
        //configure our Accessibility service
        AccessibilityServiceInfo info=getServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 500;
        this.setServiceInfo(info);
    }
}
