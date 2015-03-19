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

import net.jini.config.Configuration;
import net.jini.config.ConfigurationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBasedConfiguration implements Configuration, ApplicationContextAware, InitializingBean {

  private ApplicationContext applicationContext;
  private Configuration delegate;

  public SpringBasedConfiguration(String[] options) {
  }

  public SpringBasedConfiguration(String[] options, ClassLoader cl) {
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    delegate = new ApplicationContextAdapter(applicationContext);
  }
  
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public Object getEntry(String string, String string1, Class type) throws ConfigurationException {
    return delegate.getEntry(string, string1, type);
  }

  @Override
  public Object getEntry(String string, String string1, Class type, Object o) throws ConfigurationException {
    return delegate.getEntry(string, string1, type, o);
  }

  @Override
  public Object getEntry(String string, String string1, Class type, Object o, Object o1) throws ConfigurationException {
    return delegate.getEntry(string, string1, type, o, o1);
  }
}
