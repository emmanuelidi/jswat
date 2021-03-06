/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is JSwat. The Initial Developer of the Original
 * Software is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2001-2010. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id$
 */
package com.bluemarsh.jswat.core.breakpoint;

import com.sun.jdi.event.Event;
import java.beans.PropertyChangeListener;
import java.util.ListIterator;

/**
 * Interface Breakpoint defines the methods applicable to a breakpoint.
 *
 * @author  Nathan Fiedler
 */
public interface Breakpoint {

    /** Name of 'breakpointGroup' property. */
    String PROP_BREAKPOINTGROUP = "breakpointGroup";
    /** Name of 'classFilter' property. */
    String PROP_CLASSFILTER = "classFilter";
    /** Name of 'description' property. */
    String PROP_DESCRIPTION = "description";
    /** Name of 'enabled' property. */
    String PROP_ENABLED = "enabled";
    /** Name of 'resolved' property. */
    String PROP_RESOLVED = "resolved";
    /** Name of 'suspendPolicy' property. */
    String PROP_SUSPENDPOLICY = "suspendPolicy";
    /** Name of 'threadFilter' property. */
    String PROP_THREADFILTER = "threadFilter";
    /** Name of the 'uniqueNumber' property. */
    String PROP_NUMBER = "uniqueNumber";

    /**
     * Add a breakpoint listener to this breakpoint.
     *
     * @param  listener  listener to be added.
     */
    void addBreakpointListener(BreakpointListener listener);

    /**
     * Add the given condition to this breakpoint. That is, when the
     * <code>shouldStop()</code> method is called, this breakpoint
     * should check if this condition is satisfied or not.
     *
     * @param  condition  condition for this breakpoint to stop.
     */
    void addCondition(Condition condition);

    /**
     * Add the given monitor to this breakpoint. That is, when the
     * <code>stopped()</code> method is called, this breakpoint
     * will execute this monitor.
     *
     * @param  monitor  monitor for this breakpoint to execute.
     */
    void addMonitor(Monitor monitor);

    /**
     * Add a PropertyChangeListener to the listener list.
     *
     * @param  listener  the PropertyChangeListener to be added.
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Indicates if this type of breakpoint supports filtering events
     * by the name of a class (inclusive only).
     *
     * @return  true if this breakpoint supports class filters.
     */
    boolean canFilterClass();

    /**
     * Indicates if this type of breakpoint supports filtering events
     * by the name or number of a thread (inclusive only).
     *
     * @return  true if this breakpoint supports thread filters.
     */
    boolean canFilterThread();

    /**
     * Returns an iterator of the conditions associated with this
     * breakpoint.
     *
     * @return  ListIterator of <code>Condition</code> objects.
     */
    ListIterator<Condition> conditions();

    /**
     * Generates a description of this breakpoint suitable for the user,
     * to be displayed when the breakpoint has been hit.
     *
     * @param  e  JDI event.
     * @return  the description of this breakpoint.
     */
    String describe(Event e);

    /**
     * Tear down this breakpoint in preparation for deletion.
     */
    void destroy();

    /**
     * Gets the breakpoint group to which this breakpoint belongs.
     *
     * @return  parent breakpoint group, always non-null.
     * @see #setBreakpointGroup
     */
    BreakpointGroup getBreakpointGroup();

    /**
     * Retrieve the class filter, if any. Class filter format is described
     * in the JDI documentation for several of the event request classes.
     *
     * @return  class filter, or null if none.
     */
    String getClassFilter();

    /**
     * Generates a description of this breakpoint suitable for the user,
     * to be displayed in the breakpoint management interface.
     *
     * @return  description of this breakpoint.
     */
    String getDescription();

    /**
     * Returns the number of times this breakpoint has been hit.
     *
     * @return  hit count.
     */
    int getHitCount();

    /**
     * Returns the property value previously set using the method
     * <code>setProperty()</code>, or null if the property is not found.
     *
     * @param  name  the property name.
     * @return  the value of the property, or null if not found.
     */
    Object getProperty(String name);

    /**
     * Retrieve the suspend policy for this breakpoint. The returned value
     * will be one of the <code>com.sun.jdi.request.EventRequest</code>
     * constants for suspending threads.
     *
     * @return  suspend policy, one of the EventRequest suspend constants.
     * @see  #setSuspendPolicy
     */
    int getSuspendPolicy();

    /**
     * Retrieve the thread filter, if any. A thread filter may be either
     * the name of a thread, or the thread identifier (usually an integer
     * generated at runtime).
     *
     * @return  thread filter, or null if none.
     */
    String getThreadFilter();

    /**
     * Returns true if and only if the breakpoint is enabled and the
     * group containing this breakpoint is also enabled.
     *
     * @return  true if this breakpoint is enabled, false otherwise.
     * @see #setEnabled
     */
    boolean isEnabled();

    /**
     * Returns true if the breakpoint has been resolved against the
     * intended object in the debuggee VM. How a breakpoint resolves
     * itself depends on the type of the breakpoint.
     *
     * @return  true if this breakpoint has resolved, false otherwise.
     */
    boolean isResolved();

    /**
     * Returns an iterator of the monitors associated with this
     * breakpoint.
     *
     * @return  ListIterator of <code>Monitor</code> objects.
     */
    ListIterator<Monitor> monitors();

    /**
     * Remove a BreakpointListener from the listener list.
     *
     * @param  listener  listener to be removed.
     */
    void removeBreakpointListener(BreakpointListener listener);

    /**
     * Remove the given condition from this breakpoint. This condition
     * should no longer be associated with this breakpoint. If the
     * condition is not a part of this breakpoint, nothing happens.
     *
     * @param  condition  condition to remove from this breakpoint.
     */
    void removeCondition(Condition condition);

    /**
     * Remove the given monitor from this breakpoint. This monitor
     * should no longer be associated with this breakpoint. If the
     * monitor is not a part of this breakpoint, nothing happens.
     *
     * @param  monitor  monitor to remove from this breakpoint.
     */
    void removeMonitor(Monitor monitor);

    /**
     * Remove a PropertyChangeListener from the listener list.
     *
     * @param  listener  the PropertyChangeListener to be removed.
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Resets the breakpoint so it may be re-used on another debuggee.
     */
    void reset();

    /**
     * Sets the breakpoint group to which this breakpoint will belong.
     *
     * @param  group  new parent breakpoint group.
     * @see #getBreakpointGroup
     */
    void setBreakpointGroup(BreakpointGroup group);

    /**
     * Sets the class filter for this breakpoint. See the JDI documentation
     * of the event request classes for the format of the filter.
     *
     * @param  filter  class name filter.
     */
    void setClassFilter(String filter);

    /**
     * Cause this breakpoint to be deleted when it has been hit.
     *
     * @param  delete  true to delete when hit, false to retain.
     */
    void setDeleteWhenHit(boolean delete);

    /**
     * Enables or disables this breakpoint, according to the parameter.
     *
     * @param  enabled  True if breakpoint should be enabled, false
     *                  if breakpoint should be disabled.
     * @see #isEnabled
     */
    void setEnabled(boolean enabled);

    /**
     * No longer supported. See setDeleteWhenHit(boolean) instead.
     *
     * @param  expireCount  unused value.
     */
    void setExpireCount(int expireCount);

    /**
     * Stores a value to be associated with the given key in the properties
     * list maintained by this breakpoint instance.
     *
     * @param  name   the property name.
     * @param  value  the property value.
     * @return  the previously set property value, or null if none.
     */
    Object setProperty(String name, Object value);

    /**
     * No longer supported. Use HitCountCondition instead.
     *
     * @param  skipCount  unused value.
     */
    void setSkipCount(int skipCount);

    /**
     * Set the suspend policy for the request. Use one of the
     * <code>com.sun.jdi.request.EventRequest</code> constants
     * for suspending threads.
     *
     * @param  policy  one of the EventRequest suspend constants.
     */
    void setSuspendPolicy(int policy);

    /**
     * Sets the thread filter for this breakpoint. A thread filter may be
     * either the name of a thread, or the thread identifier (usually an
     * integer generated at runtime).
     *
     * @param  filter  thread filter.
     */
    void setThreadFilter(String filter);
}
