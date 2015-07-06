package scratch

case class DashIdClass(
  `a-b`: String,
  `c-d`: String
)

object DashIdClass {
  val empty = TestMacro.empty[DashIdClass]()
}
