package org.appenders.log4j2.elasticsearch.jest;

import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.appenders.log4j2.elasticsearch.Auth;
import org.appenders.log4j2.elasticsearch.Credentials;

import io.searchbox.client.config.HttpClientConfig;

/*-
 * #%L
 * %%
 * Copyright (C) 2018
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
/**
 * This is a reduced version of XPackAuth from the log4j2 elastic appender. It
 * no longer enforces ssl when using http basic auth (yes this is a bad idea but
 * it is what we are doing currently)
 *
 */
@Plugin(name = BasicAuth.PLUGIN_NAME, category = Node.CATEGORY, elementType = Auth.ELEMENT_TYPE)
public class BasicAuth implements Auth<HttpClientConfig.Builder> {

    static final String PLUGIN_NAME = "BasicAuth";

    private final Credentials<HttpClientConfig.Builder> credentials;

    protected BasicAuth(Credentials<HttpClientConfig.Builder> credentials) {
        this.credentials = credentials;
    }

    @Override
    public void configure(HttpClientConfig.Builder builder) {
        credentials.applyTo(builder);
    }

    @PluginBuilderFactory
    public static BasicAuth.Builder newBuilder() {
        return new BasicAuth.Builder();
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<BasicAuth> {

        @PluginElement("credentials")
        @Required(message = "No credentials provided for " + BasicAuth.PLUGIN_NAME)
        private Credentials<HttpClientConfig.Builder> credentials;

        @Override
        public BasicAuth build() {
            if (credentials == null) {
                throw new ConfigurationException("No credentials provided for " + BasicAuth.PLUGIN_NAME);
            }
            return new BasicAuth(credentials);
        }

        public Builder withCredentials(Credentials<HttpClientConfig.Builder> credentials) {
            this.credentials = credentials;
            return this;
        }

    }

}