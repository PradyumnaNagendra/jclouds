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

package org.jclouds.cloudservers.binders;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.net.URI;

import javax.ws.rs.HttpMethod;

import org.jclouds.http.HttpRequest;
import org.jclouds.json.config.GsonModule;
import org.jclouds.cloudservers.domain.RebootType;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Tests behavior of {@code BindRebootTypeToJsonPayload}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit")
public class BindRebootTypeToJsonPayloadTest {

   Injector injector = Guice.createInjector(new GsonModule());

   @Test(expectedExceptions = IllegalStateException.class)
   public void testPostIsIncorrect() {
      BindRebootTypeToJsonPayload binder = new BindRebootTypeToJsonPayload();
      injector.injectMembers(binder);
      HttpRequest request = new HttpRequest(HttpMethod.POST, URI.create("http://localhost"));
      binder.bindToRequest(request, ImmutableMap.of("adminPass", "foo"));
   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testMustBeRebootType() {
      BindRebootTypeToJsonPayload binder = new BindRebootTypeToJsonPayload();
      injector.injectMembers(binder);
      HttpRequest request = new HttpRequest(HttpMethod.POST, URI.create("http://localhost"));
      binder.bindToRequest(request, new File("foo"));
   }

   @Test
   public void testHard() {
      BindRebootTypeToJsonPayload binder = new BindRebootTypeToJsonPayload();
      injector.injectMembers(binder);
      HttpRequest request = new HttpRequest(HttpMethod.POST, URI.create("http://localhost"));
      binder.bindToRequest(request, RebootType.HARD);
      assertEquals("{\"reboot\":{\"type\":\"HARD\"}}", request.getPayload().getRawContent());
   }

   @Test
   public void testSoft() {
      BindRebootTypeToJsonPayload binder = new BindRebootTypeToJsonPayload();
      injector.injectMembers(binder);
      HttpRequest request = new HttpRequest(HttpMethod.POST, URI.create("http://localhost"));
      binder.bindToRequest(request, RebootType.SOFT);
      assertEquals("{\"reboot\":{\"type\":\"SOFT\"}}", request.getPayload().getRawContent());
   }

   @Test(expectedExceptions = { NullPointerException.class, IllegalStateException.class })
   public void testNullIsBad() {
      BindRebootTypeToJsonPayload binder = new BindRebootTypeToJsonPayload();
      injector.injectMembers(binder);
      HttpRequest request = new HttpRequest(HttpMethod.POST, URI.create("http://localhost"));
      binder.bindToRequest(request, null);
   }
}
