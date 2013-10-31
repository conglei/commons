/**
 *  Copyright 2011 LiveRamp
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.liveramp.commons.collections;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruHashMap<K, V> extends LinkedHashMap<K, V> {

  private static final float LOAD_FACTOR = 0.75f;
  private final int sizeLimit;
  private Map.Entry<K, V> eldestRemoved;

  // A limit < 0 means no limit
  public LruHashMap(int initialCapacity, int sizeLimit) {
    // Note: the super constructor's third argument specifies
    // access-ordering rather than default insertion-ordering
    super(initialCapacity, LOAD_FACTOR, true);
    this.sizeLimit = sizeLimit;
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    if (sizeLimit < 0) {
      return false;
    } else {
      boolean remove = size() > sizeLimit;
      if (remove) {
        eldestRemoved = eldest;
      } else {
        eldestRemoved = null;
      }
      return remove;
    }
  }

  public Map.Entry<K, V> getAndClearEldestRemoved() {
    Map.Entry<K, V> result = eldestRemoved;
    eldestRemoved = null;
    return result;
  }
}
