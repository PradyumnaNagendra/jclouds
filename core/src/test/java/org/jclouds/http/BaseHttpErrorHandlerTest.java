/**
 *
 * Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.http;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.reportMatcher;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import java.net.URI;

import org.easymock.IArgumentMatcher;
import org.jclouds.http.functions.config.SaxParserModule;
import org.jclouds.io.Payloads;

import com.google.inject.Guice;

/**
 * 
 * @author Adrian Cole
 */
public abstract class BaseHttpErrorHandlerTest {

   public static Exception classEq(final Class<? extends Exception> in) {
      reportMatcher(new IArgumentMatcher() {

         @Override
         public void appendTo(StringBuffer buffer) {
            buffer.append("classEq(");
            buffer.append(in);
            buffer.append(")");
         }

         @Override
         public boolean matches(Object arg) {
            return arg.getClass() == in;
         }

      });
      return null;
   }

   protected abstract Class<? extends HttpErrorHandler> getHandlerClass();

   protected void assertCodeMakes(String method, URI uri, int statusCode, String message,
            String content, Class<? extends Exception> expected) {

      HttpErrorHandler function = Guice.createInjector(new SaxParserModule()).getInstance(
               getHandlerClass());

      HttpCommand command = createMock(HttpCommand.class);
      HttpRequest request = new HttpRequest(method, uri);
      HttpResponse response = new HttpResponse(statusCode, message, Payloads.newStringPayload(content));

      expect(command.getCurrentRequest()).andReturn(request).atLeastOnce();
      command.setException(classEq(expected));

      replay(command);

      function.handleError(command, response);

      verify(command);
   }

}