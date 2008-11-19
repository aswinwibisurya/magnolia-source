/**
 * This file Copyright (c) 2003-2008 Magnolia International
 * Ltd.  (http://www.magnolia.info). All rights reserved.
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
 * is available at http://www.magnolia.info/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 *
 */
package info.magnolia.integrationtests.rendering;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import info.magnolia.cms.util.ClasspathResourcesUtil;
import info.magnolia.integrationtests.AbstractMagnoliaIntegrationTest;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author gjoseph
 * @version $Revision: $ ($Author: $)
 */
public class SimpleRenderingTest extends AbstractMagnoliaIntegrationTest {

    @Test
    public void ensureWeCanReachResourcesFromTheTestModule() throws IOException {
        final URL resource = ClasspathResourcesUtil.getResource("/mgnl-files/templates/test/templating_test_expectedresults.txt");
        assertNotNull(resource);
        final InputStream stream = resource.openStream();
        assertNotNull(stream);
        final String allContents = IOUtils.toString(stream);
        assertTrue("Where is all the content gone ?", allContents.contains("This file is currently not used !"));
    }

    @Test
    public void renderFreemarker() throws Exception {
        simpleRenderingTests("/testpages/test_freemarker.html");
    }

    /**
     * Tests rendering with new "style" introduced in Magnolia 4.0
     */
    @Test
    public void renderJspNewSchool() throws Exception {
        simpleRenderingTests("/testpages/test_jsp_newschool.html");
    }

    @Test
    public void renderJspOldSchool() throws Exception {
        simpleRenderingTests("/testpages/test_jsp_oldschool.html");
    }

    private void simpleRenderingTests(String path) throws IOException {
        final HtmlPage page = openHtmlPage(Instance.AUTHOR, path, User.superuser);
        saveToFile(page, new Throwable().getStackTrace()[1]);

        final WebResponse webResponse = page.getWebResponse();
        assertEquals(200, webResponse.getStatusCode());

        final String allContents = webResponse.getContentAsString();

        // TODO : the following won't be valid if we change the freemarker error handler
        assertFalse("There was a freemarker error", allContents.contains("<!-- FREEMARKER ERROR MESSAGE STARTS HERE -->"));

        // TODO : better assert of contents !
        assertTrue(allContents.contains("no action result here"));
    }


    @Test
    public void ensureTheSimplePlainTextTestPageIsReachable() throws IOException {
        final HtmlPage page = openHtmlPage(Instance.AUTHOR, "/testpages/plain.txt", User.superuser);
        final String allContents = page.getWebResponse().getContentAsString();
        // see MAGNOLIA-2393
        assertTrue(allContents.contains("This is just one plain text page."));
    }

    @Test
    public void ensureTheSimplePlainTextTestPageIsReachableAndCorrrectOnAPublicInstance() throws IOException {
        final Page page = openPage(Instance.PUBLIC, "/testpages/plain.txt", User.superuser);
        final String allContents = page.getWebResponse().getContentAsString();
        assertEquals("This is just one plain text page.", allContents);
    }
}