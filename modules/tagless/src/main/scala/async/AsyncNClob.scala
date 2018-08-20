// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

import doobie.tagless.{ RTS, Logger }
import doobie.tagless.jdbc._
import org.slf4j.{ Logger => JLogger }
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.io.Writer
import java.lang.String
import java.sql.Clob
import java.sql.NClob

/**
 * Implementation of `JdbcNClob` that wraps a `java.sql.NClob` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncNClob[F[_]](val value: NClob, val rts: RTS[F], val log: Logger[F]) extends JdbcNClob[F] {

  val id: String =
    s"${System.identityHashCode(value).toHexString.padTo(8, ' ')} NClob".padTo(28, ' ')

  private val jlog: JLogger =
    log.underlying

  val free: F[Unit] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id free()")
      value.free()
    }

  val getAsciiStream: F[InputStream] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getAsciiStream()")
      value.getAsciiStream()
    }

  val getCharacterStream: F[Reader] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getCharacterStream()")
      value.getCharacterStream()
    }

  def getCharacterStream(a: Long, b: Long): F[Reader] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getCharacterStream($a, $b)")
      value.getCharacterStream(a, b)
    }

  def getSubString(a: Long, b: Int): F[String] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getSubString($a, $b)")
      value.getSubString(a, b)
    }

  val length: F[Long] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id length()")
      value.length()
    }

  def position(a: Clob, b: Long): F[Long] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id position($a, $b)")
      value.position(a, b)
    }

  def position(a: String, b: Long): F[Long] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id position($a, $b)")
      value.position(a, b)
    }

  def setAsciiStream(a: Long): F[OutputStream] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id setAsciiStream($a)")
      value.setAsciiStream(a)
    }

  def setCharacterStream(a: Long): F[Writer] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id setCharacterStream($a)")
      value.setCharacterStream(a)
    }

  def setString(a: Long, b: String): F[Int] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id setString($a, $b)")
      value.setString(a, b)
    }

  def setString(a: Long, b: String, c: Int, d: Int): F[Int] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id setString($a, $b, $c, $d)")
      value.setString(a, b, c, d)
    }

  def truncate(a: Long): F[Unit] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id truncate($a)")
      value.truncate(a)
    }

}

