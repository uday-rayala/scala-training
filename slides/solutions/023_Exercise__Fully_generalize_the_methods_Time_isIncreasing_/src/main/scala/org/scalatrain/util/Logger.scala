/*
 * Copyright 2011 Typesafe Inc.
 * 
 * This work is based on the original contribution of WeigleWilczek.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.scalatrain
package util

import org.slf4j.{ Logger => Slf4jLogger, LoggerFactory }

trait Logger {

  protected val slf4jLogger: Slf4jLogger

  def error(message: => String) {
    if (slf4jLogger.isErrorEnabled) slf4jLogger.error(message)
  }

  def error(message: => String, cause: Throwable) {
    if (slf4jLogger.isErrorEnabled) slf4jLogger.error(message, cause)
  }

  def warn(message: => String) {
    if (slf4jLogger.isWarnEnabled) slf4jLogger.warn(message)
  }

  def warn(message: => String, cause: Throwable) {
    if (slf4jLogger.isWarnEnabled) slf4jLogger.warn(message, cause)
  }

  def info(message: => String) {
    if (slf4jLogger.isInfoEnabled) slf4jLogger.info(message)
  }

  def info(message: => String, cause: Throwable) {
    if (slf4jLogger.isInfoEnabled) slf4jLogger.info(message, cause)
  }

  def debug(message: => String) {
    if (slf4jLogger.isDebugEnabled) slf4jLogger.debug(message)
  }

  def debug(message: => String, cause: Throwable) {
    if (slf4jLogger.isDebugEnabled) slf4jLogger.debug(message, cause)
  }

  def trace(message: => String) {
    if (slf4jLogger.isTraceEnabled) slf4jLogger.trace(message)
  }

  def trace(message: => String, cause: Throwable) {
    if (slf4jLogger.isTraceEnabled) slf4jLogger.trace(message, cause)
  }
}

trait Logging {
  self =>

  @transient protected lazy val logger: Logger = new Logger {
    override protected val slf4jLogger: Slf4jLogger = LoggerFactory getLogger self.getClass
  }
}
