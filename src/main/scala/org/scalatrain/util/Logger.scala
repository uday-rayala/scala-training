package org.scalatrain.util
import org.slf4j.{Logger => Slf4jLogger}
import org.slf4j.LoggerFactory

case class Logger(logger: Slf4jLogger) {
    def debug(msg: => String) = 
      if(logger.isDebugEnabled())
    	  logger.debug(msg) 

}

trait Logging {
  lazy val logger = new Logger(LoggerFactory.getLogger(getClass()))
}