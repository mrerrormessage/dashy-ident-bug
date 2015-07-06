package scratch

import
  scala.reflect.macros.blackbox.{ Context => BlackBoxContext }

object TestMacro {

  import scala.language.experimental.{ macros => enableMacros }

  def empty[T]: () => Option[T] = macro emptyInternal[T]

  def emptyInternal[T: c.WeakTypeTag](c: BlackBoxContext)(implicit scratchType: c.WeakTypeTag[T]): c.Tree = {
    import c.universe._

    val scratchTerms = withConstructorProperties(c)(scratchType.tpe) {
     termName =>
        val emptyString = ""
        (fq"$termName <- Some($emptyString)", q"$termName")
    }

    scratchTerms.map {
      namedConstructorParams =>
        q"""{ () =>
          for(..${namedConstructorParams.map(_._1)})
            yield new ${scratchType.tpe}(..${namedConstructorParams.map(_._2)})

        }"""
    } getOrElse c.abort(c.enclosingPosition, s"Could not find constructor for ${scratchType.tpe}")
  }

  private def withConstructorProperties[T](c: BlackBoxContext)(constructedType: c.Type)(f: c.TermName => T): Option[Seq[T]] =
    constructedType.decls.find(_.isConstructor).map(
      constructor =>
        constructor.asMethod.paramLists.head.map (
          p => f(p.name.toTermName)
        )
      )
}
