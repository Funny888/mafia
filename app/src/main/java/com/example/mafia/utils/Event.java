package com.example.mafia.utils;

public class Event<T> {

    private T mContent;
    private boolean hasBeenHandled = false;

    public Event(T content) {
        mContent = content;
    }

    /**
     * Returns content status.
     */
    public boolean isHasBeenHandled() {
        return hasBeenHandled;
    }

    /**
     * Returns the content and prevents its use again.
     */
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return mContent;
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    public T peekContent() {
        return mContent;
    }

}