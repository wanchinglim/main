package seedu.address.commons.events;

public abstract class BaseEvent {

    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     */
    public abstract String toString();

}
