package scratch

case class DashIdClass(
  `a-b`: String
)

object DashIdClass {
  val empty = TestMacro.empty[DashIdClass]()
}
