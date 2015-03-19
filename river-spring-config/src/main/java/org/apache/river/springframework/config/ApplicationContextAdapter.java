/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.river.springframework.config;

import java.util.Objects;
import net.jini.config.Configuration;
import net.jini.config.ConfigurationException;
import net.jini.config.NoSuchEntryException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public class ApplicationContextAdapter implements Configuration {

  private final ApplicationContext adaptee;

  public ApplicationContextAdapter(ApplicationContext adaptee) {
    this.adaptee = adaptee;
  }

  public ApplicationContext getAdaptee() {
    return adaptee;
  }

  @Override
  public Object getEntry(String component, String name, Class type) throws ConfigurationException {
    return getEntry(component, name, type, Configuration.NO_DEFAULT);
  }

  @Override
  public Object getEntry(String component, String name, Class type, Object defaultValue) throws ConfigurationException {
    return getEntry(component, name, type, defaultValue, Configuration.NO_DATA);
  }

  @Override
  public Object getEntry(String component, String name, Class type, Object defaultValue, Object data) throws ConfigurationException {
    Objects.requireNonNull(name, "The name parameter must not be null.");
    Objects.requireNonNull(name, "The type parameter must not be null.");

    Object value = null;

    try {
      if (component != null && !component.isEmpty()) {
        try {
          value = adaptee.getBean(component + "." + name);
        } catch (NoSuchBeanDefinitionException e) {
        }
      }
      if (value == null) {
        try {
          value = adaptee.getBean(name);
        } catch (NoSuchBeanDefinitionException e) {
        }
      }
    } catch (BeansException e) {
      throw new ConfigurationException("Unable to get configuration value.", e);
    }

    if (value == null) {
      if (defaultValue == Configuration.NO_DEFAULT) {
        throw new NoSuchEntryException("No configuration value found.");
      } else {
        value = defaultValue;
      }
    }

    if (value != null) {
      if ((type.isPrimitive() &&
           (type == boolean.class && value instanceof Boolean) ||
           (type == char.class && value instanceof Character) ||
           (type == byte.class && value instanceof Byte) ||
           (type == short.class && value instanceof Short) ||
           (type == int.class && value instanceof Integer) ||
           (type == long.class && value instanceof Long) ||
           (type == float.class && value instanceof Float) ||
           (type == double.class && value instanceof Double)
          ) || 
          type.isInstance(value)) {
        
      } else {
        throw new IllegalArgumentException("Configuration value cannot be converted to type.");
      }
    }

    return value;
  }
}
