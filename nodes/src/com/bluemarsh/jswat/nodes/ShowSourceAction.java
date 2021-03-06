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
 * are Copyright (C) 2004-2012. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 */

package com.bluemarsh.jswat.nodes;

import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

/**
 * Implements the action of showing the source code for an object.
 *
 * @author  Nathan Fiedler
 */
public class ShowSourceAction extends CookieAction {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;
    /** The cookies this action operates on. */
    private static final Class[] COOKIE_CLASSES = new Class[] {
        ShowSourceCookie.class
    };

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    protected Class[] cookieClasses() {
        return COOKIE_CLASSES;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(ShowSourceAction.class,
                "LBL_ShowSourceAction_name");
    }

    @Override
    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    protected void performAction(Node[] activatedNodes) {
        ShowSourceCookie cookie = activatedNodes[0].getLookup().lookup(ShowSourceCookie.class);
        cookie.showSource();
    }
}
