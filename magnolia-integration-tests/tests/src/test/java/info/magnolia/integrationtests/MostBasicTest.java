/**
 * This file Copyright (c) 2008-2012 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 *
 *
 * This file is dual-licensed under both the Magnolia
 * Network Agreement and the GNU General Public License.
 * You may elect to use one or the other of these licenses.
 *
 * This file is distributed in the hope that it will be
 * useful, but AS-IS and WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, or NONINFRINGEMENT.
 * Redistribution, except as permitted by whichever of the GPL
 * or MNA you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or
 * modify this file under the terms of the GNU General
 * Public License, Version 3, as published by the Free Software
 * Foundation.  You should have received a copy of the GNU
 * General Public License, Version 3 along with this program;
 * if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * 2. For the Magnolia Network Agreement (MNA), this file
 * and the accompanying materials are made available under the
 * terms of the MNA which accompanies this distribution, and
 * is available at http://www.magnolia-cms.com/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 *
 */
package info.magnolia.integrationtests;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import info.magnolia.testframework.htmlunit.AbstractMagnoliaIntegrationTest;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * We're just checking if the container started and the application is reachable, plus some simple assertions, checking
 * we're on the expected type of instance, for example.
 *
 * @author gjoseph
 * @version $Revision: $ ($Author: $)
 */
public class MostBasicTest extends AbstractMagnoliaIntegrationTest {

    @Test
    public void authorInstanceShouldBePasswordProtected() throws Exception {
        final Page root = openPage(Instance.AUTHOR.getURL(""), null);
        assertEquals(401, root.getWebResponse().getStatusCode());
    }

    @Test
    public void loginOnAuthorInstanceWithSuperuser() throws Exception {
        final HtmlPage root = openPage(Instance.AUTHOR.getURL("/.magnolia/adminCentral.html"), User.superuser);
        final HtmlPage adminCentralPage = assertRedirected("Old adminCentral redirect is not setup?", Instance.AUTHOR.getURL("/\\.magnolia/pages/adminCentral\\.html" + SESSION_ID_REGEXP), root, User.superuser);
        assertEquals(200, adminCentralPage.getWebResponse().getStatusCode());
        final String allContents = adminCentralPage.getWebResponse().getContentAsString();
        assertThat("We're not on a CE instance!?", allContents, containsString("Community Edition"));

        final HtmlElement footerDiv = adminCentralPage.getElementById("mgnlAdminCentralFooterDiv");
        assertNotNull(footerDiv);
        assertThat("We're not on a CE instance!?", footerDiv.getTextContent(), containsString("Community Edition"));
    }

    @Test
    public void reachThePublicInstanceWithoutCredentials() throws Exception {
        // can't get to root of since samples-module currently does not provide a working default redirect - if it did, this test would also check that
        // final Page page = openPage(Instance.PUBLIC, "", null);
        final Page page = openPage(Instance.PUBLIC.getURL("/testpages/test_freemarker.html"), null);
        assertEquals(200, page.getWebResponse().getStatusCode());
    }

}
