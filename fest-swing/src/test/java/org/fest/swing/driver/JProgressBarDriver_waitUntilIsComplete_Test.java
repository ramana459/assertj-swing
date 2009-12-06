/*
 * Created on Dec 4, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JProgressBarValueQuery.valueOf;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.timing.Pause.pause;

import javax.swing.JProgressBar;

import org.fest.swing.exception.WaitTimedOutError;
import org.junit.Test;

/**
 * Tests for <code>{@link JProgressBarDriver#waitUntilIsComplete(JProgressBar)}</code>.
 *
 * @author Alex Ruiz
 */
public class JProgressBarDriver_waitUntilIsComplete_Test extends JProgressBarDriver_TestCase {

  @Test
  public void should_wait_until_value_is_equal_to_maximum() {
    Thread t = new Thread() {
      @Override public void run() {
        pause(1000);
        updateValueTo(10);
        pause(1000);
        updateValueTo(60);
        pause(1000);
        updateValueTo(100);
      }
    };
    t.start();
    driver.waitUntilIsComplete(progressBar);
    assertThat(valueOf(progressBar)).isEqualTo(100);
  }

  @Test
  public void should_time_out_if_maximum_is_never_reached() {
    try {
      driver.waitUntilIsComplete(progressBar);
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertThat(e.getMessage()).contains("Timed out waiting for value")
                                .contains("to be equal to 100");
    }
  }
}