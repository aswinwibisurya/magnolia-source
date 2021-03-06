/**
 * This file Copyright (c) 2008-2015 Magnolia International
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import info.magnolia.testframework.htmlunit.AbstractMagnoliaHtmlUnitTest;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.Page;

/**
 * We're just checking if the public instance shows the Magnolia badge.
 * In a CE edition, the badge and the generator tag should always be rendered.
 */
public class STKBadgeTest extends AbstractMagnoliaHtmlUnitTest {

    @Test
    public void checkOnPublicInstanceWhetherBadgeIsRendered() throws Exception {
        // GIVEN
        final String url = "/demo-project/about/subsection-articles/article.html";

        // WHEN
        final Page page = openPage(Instance.PUBLIC.getURL(url), null, false, false);
        final String contents = page.getWebResponse().getContentAsString();

        // THEN
        assertEquals(200, page.getWebResponse().getStatusCode());
        assertTrue(contents.contains("<meta name=\"generator\" content=\"Powered by Magnolia - "));
        assertTrue(contents.contains("<p id=\"copyright-magnolia\"><span>Powered by</span> <a href=\"http://www.magnolia-cms.com\"><strong>Magnolia</strong> - "));
    }

}
