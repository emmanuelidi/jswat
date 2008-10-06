/*********************************************************************
 *
 *      Copyright (C) 2002 Nathan Fiedler
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * PROJECT:     JSwat
 * MODULE:      Unit Tests
 * FILE:        stepTest.java
 *
 * AUTHOR:      Nathan Fiedler
 *
 * REVISION HISTORY:
 *      Name    Date            Description
 *      ----    ----            -----------
 *      nf      08/01/02        Initial version
 *
 * $Id: stepTest.java 14 2007-06-02 23:50:55Z nfiedler $
 *
 ********************************************************************/

package com.bluemarsh.jswat.command;

import com.bluemarsh.jswat.Session;
import com.bluemarsh.jswat.SessionManager;
import com.bluemarsh.jswat.SessionSetup;
import junit.extensions.*;
import junit.framework.*;

/**
 * Tests the step command.
 */
public class stepTest extends CommandTestCase {

    public stepTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new SessionSetup(new TestSuite(stepTest.class));
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void test_step_stepi() {
        Session session = SessionManager.beginSession();
        SimpleSessionListener ssl = new SimpleSessionListener();
        session.addListener(ssl);

        // inactive session tested elsewhere

        // no current location tested elsewhere

        SessionManager.launchSimple("locals");

        runCommand(session, "clear all");
        runCommand(session, "stop locals:189");
        resumeAndWait(session, ssl);
        // should be several entries on the stack

        // must clear the breakpoints now or we encounter
        // spurious events which screw up the tests
        runCommand(session, "clear all");

        // usual cases
        runCommand(session, "step");
        waitForSuspend(ssl);
        runCommand(session, "step thread");
        waitForSuspend(ssl);
        runCommand(session, "stepi");
        waitForSuspend(ssl);
        runCommand(session, "stepi thread");
        waitForSuspend(ssl);
        // Do these last.
        runCommand(session, "step up");
        waitForSuspend(ssl);
        runCommand(session, "step up thread");
        waitForSuspend(ssl);
        runCommand(session, "step out");
        waitForSuspend(ssl);
        runCommand(session, "step out thread");
        waitForSuspend(ssl);

        SessionManager.deactivate(true);

        // test thread not suspended case
        SessionManager.launchSimple("tutorial");
        runCommand(session, "clear all");

        // stop when main thread dies since we know everything has
        // started up by the time that happens
        runCommand(session, "threadbrk main death");
        resumeAndWait(session, ssl);
        runCommand(session, "clear all");
        runCommand(session, "resume");
        try {
            // My Linux system needs this delay.
            Thread.sleep(1000);
        } catch (InterruptedException ie) { }
        // we assume this thread always exists
        runCommand(session, "thread Finalizer");
        try {
            runCommand(session, "step");
            fail("expected CommandException");
        } catch (CommandException ce) {
            // expected
        }

        SessionManager.deactivate(true);
        session.removeListener(ssl);
        SessionManager.endSession();
    }
}
