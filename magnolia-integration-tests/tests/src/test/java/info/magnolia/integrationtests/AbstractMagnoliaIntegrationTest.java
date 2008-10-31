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
package info.magnolia.integrationtests;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Collections;

/**
 * @author gjoseph
 * @version $Revision: $ ($Author: $)
 */
public abstract class AbstractMagnoliaIntegrationTest {
//    @After
//    public void afterEachTest() {
    // ...
//    }

    protected HttpURLConnection openConnection(String urlStr, final String username, final String password) throws IOException {
        return openConnection(urlStr, username, password, Collections.<String, String>emptyMap());
    }

    protected HttpURLConnection openConnection(String urlStr, final String username, final String password, Map<String, String> headers) throws IOException {
        final URL url = new URL(urlStr);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (username != null) {
            final String authString = username + ":" + password;
            final String encodedAuthStr = new String(Base64.encodeBase64(authString.getBytes()));
            connection.setRequestProperty("Authorization", "Basic " + encodedAuthStr);
        }

        for (String header : headers.keySet()) {
            connection.setRequestProperty(header, headers.get(header));
        }

        connection.connect();
        return connection;
    }
}