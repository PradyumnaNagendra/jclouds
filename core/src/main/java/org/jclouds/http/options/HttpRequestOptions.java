/**
 *
 * Copyright (C) 2009 Adrian Cole <adrian@jclouds.org>
 *
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 */
package org.jclouds.http.options;

import com.google.common.collect.Multimap;

/**
 * Builds options that override or append to HttpRequests.
 * 
 * @author Adrian Cole
 * 
 */
public interface HttpRequestOptions {

    /**
     * Builds headers representing options.
     * 
     * @return object that may contain headers.
     */
    Multimap<String, String> buildRequestHeaders();

    /**
     * Builds a query string, ex. ?marker=toast
     * 
     * @return an http query string representing these options, or empty string
     *         if none are present.
     */
    String buildQueryString();

}