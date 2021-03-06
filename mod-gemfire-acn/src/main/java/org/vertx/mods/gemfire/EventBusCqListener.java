/*
 * Copyright 2012 the original author or authors.
 *
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
package org.vertx.mods.gemfire;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.json.JsonObject;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.query.CqEvent;
import com.gemstone.gemfire.cache.query.CqListener;

public class EventBusCqListener implements CqListener, Declarable {

  private EventBus eventBus;

  private String address;

  public EventBusCqListener(EventBus eventBus, String address) {
    this.eventBus = eventBus;
    this.address = address;
  }

  @Override
  public void init(Properties properties) {
    // TODO Auto-generated method stub
  }

  @Override
  public void close() {
    // TODO Auto-generated method stub
  }

  @Override
  public void onError(CqEvent event) {

    Map<String, Object> map = new HashMap<>();
    map.put("base-operation", event.getBaseOperation());
    map.put("cq", event.getCq().getName());
    map.put("key", event.getKey());
    map.put("value", event.getNewValue());
    map.put("query-operation", event.getQueryOperation());
    map.put("throwable", event.getThrowable());

    JsonObject msg = new JsonObject(map);
    eventBus.send(address, msg);  }

  @Override
  public void onEvent(CqEvent event) {

    Map<String, Object> map = new HashMap<>();
    map.put("base-operation", event.getBaseOperation());
    map.put("cq", event.getCq().getName());
    map.put("key", event.getKey());
    map.put("value", event.getNewValue());
    map.put("query-operation", event.getQueryOperation());

    JsonObject msg = new JsonObject(map);
    eventBus.send(address, msg);
  }

}
