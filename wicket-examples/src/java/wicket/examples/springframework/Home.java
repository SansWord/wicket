/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.examples.springframework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.PageParameters;
import wicket.examples.util.NavigationPanel;
import wicket.markup.html.HtmlPage;
import wicket.markup.html.basic.Label;

/**
 * Everybody's favorite example.
 * @author Jonathan Locke
 */
public class Home extends HtmlPage
{
	private static Log log = LogFactory.getLog(Home.class);
	
    /**
     * Constructor
     * @param parameters Page parameters
     */
    public Home(final PageParameters parameters)
    {
        add(new NavigationPanel("mainNavigation", "Spring integration example"));
        add(new Label("message", "Hello world! Test: ���"));
    }
}

///////////////////////////////// End of File /////////////////////////////////
