/*
   RAW -- High-performance querying over raw, never-seen-before data.
   
                         Copyright (c) 2013
      Data Intensive Applications and Systems Labaratory (DIAS)
               École Polytechnique Fédérale de Lausanne
   
                         All Rights Reserved.
   
   Permission to use, copy, modify and distribute this software and
   its documentation is hereby granted, provided that both the
   copyright notice and this permission notice appear in all copies of
   the software, derivative works or modified versions, and any
   portions thereof, and that both notices appear in supporting
   documentation.
   
   This code is distributed in the hope that it will be useful, but
   WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. THE AUTHORS
   DISCLAIM ANY LIABILITY OF ANY KIND FOR ANY DAMAGES WHATSOEVER
   RESULTING FROM THE USE OF THIS SOFTWARE.
*/
package raw.code.storage

import raw.code.Instruction
import raw.code.Reference

import raw.schema.Field
import raw.schema.Schema

abstract class AccessPathCapability
case object SequentialAccessPath extends AccessPathCapability
case object KeyAccessPath extends AccessPathCapability

abstract class AccessPath(val ref: Reference, val fields: Schema) {
  def getAccessPathCapability
  
  def init(): List[Instruction]  
  def done(): List[Instruction]

  def getNextTuple(): List[Instruction]
  def getField(field: Field): Reference
}

abstract class SequentialAccessPath(ref: Reference, fields: Schema) extends AccessPath(ref, fields) {
  def getAccessPathCapability = SequentialAccessPath
  
  def open(): List[Instruction]
  def close(): List[Instruction]
}

abstract class KeyAccessPath(ref: Reference, fields: Schema) extends AccessPath(ref, fields) {
  def getAccessPathCapability = KeyAccessPath

  def open(key: Reference): List[Instruction]
  def close(key: Reference): List[Instruction]
}
