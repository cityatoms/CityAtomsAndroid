package com.github.abdularis.trackmylocation.common;

import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for alert dialogs
 */
public class DialogViewModel {
    private String title;
    private String message;
    private boolean cancelable;
    private List<Button> buttons;

    public DialogViewModel() {
        buttons = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public static class Button {
        private int whichButton;
        private String text;
        private DialogInterface.OnClickListener onClickListener;

        public Button(int whichButton, String text, DialogInterface.OnClickListener onClickListener) {
            this.whichButton = whichButton;
            this.text = text;
            this.onClickListener = onClickListener;
        }

        public int getWhichButton() {
            return whichButton;
        }

        public String getText() {
            return text;
        }

        public DialogInterface.OnClickListener getOnClickListener() {
            return onClickListener;
        }
    }
}
