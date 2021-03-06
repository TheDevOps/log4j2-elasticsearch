package org.appenders.log4j2.elasticsearch.bulkprocessor;

/*-
 * #%L
 * log4j-elasticsearch
 * %%
 * Copyright (C) 2018 Rafal Foltynski
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.appenders.log4j2.elasticsearch.Credentials;
import org.elasticsearch.common.settings.Settings;


@Plugin(name = BasicCredentials.PLUGIN_NAME, category = Node.CATEGORY, elementType = Credentials.ELEMENT_TYPE)
public final class BasicCredentials implements Credentials<Settings.Builder> {

    static final String PLUGIN_NAME = "BasicCredentials";
    static final String SHIELD_SECURITY_USER = "shield.user";

    private final String username;
    private final String password;

    protected BasicCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void applyTo(Settings.Builder clientSettings) {
        clientSettings.put(SHIELD_SECURITY_USER, username + ":" + password);
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<BasicCredentials> {

        @PluginBuilderAttribute
        @Required(message = "No username provided for " + BasicCredentials.PLUGIN_NAME)
        private String username;

        @PluginBuilderAttribute
        @Required(message = "No password provided for " + BasicCredentials.PLUGIN_NAME)
        private String password;

        @Override
        public BasicCredentials build() {
            if (username == null) {
                throw new ConfigurationException("No username provided for " + BasicCredentials.PLUGIN_NAME);
            }
            if (password == null) {
                throw new ConfigurationException("No password provided for " + BasicCredentials.PLUGIN_NAME);
            }
            return new BasicCredentials(username, password);
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

    }
}
