/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.context.beanstore;

import static org.jboss.weld.logging.messages.ContextMessage.DELIMITER_IN_PREFIX;

import org.jboss.weld.ForbiddenArgumentException;



/**
 * Simple prefix-based implementation of a BeanStore naming scheme
 * 
 * @author Nicklas Karlsson
 */
public class NamingScheme
{
   public String prefix;
   public String delimiter;

   public NamingScheme(String prefix, String delimiter)
   {
      if (prefix.indexOf(delimiter) >= 0)
      {
         throw new ForbiddenArgumentException(DELIMITER_IN_PREFIX, delimiter, prefix);
      }
      this.prefix = prefix;
      this.delimiter = delimiter;
   }

   public boolean acceptKey(String key)
   {
      return key.startsWith(prefix);
   }

   public String getId(String key)
   {
      return new StringBuilder().append(key.substring(prefix.length() + delimiter.length())).toString();
   }

   public String getKey(String id)
   {
      return new StringBuilder().append(prefix).append(delimiter).append(id).toString();
   }
}
