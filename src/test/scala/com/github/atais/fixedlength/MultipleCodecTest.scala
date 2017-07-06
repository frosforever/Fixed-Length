package com.github.atais.fixedlength

import org.scalatest.{FlatSpec, Matchers}

class MultipleCodecTest extends FlatSpec with Matchers {

  case class Example1(a: String, b: String, c: String)

  object Example1 {

    import Codec._
    import cats.implicits._
    import com.github.atais.util.Read._
    import com.github.atais.util.Write._

    implicit val example1 = {
      fixed[String](0, 1) <<:
        fixed[String](1, 2) <<:
        fixed[String](2, 3)
    }
  }

  case class Example2(a: String, b: String, c: String, d: String)

  object Example2 {

    import Codec._
    import cats.implicits._
    import com.github.atais.util.Read._
    import com.github.atais.util.Write._

    implicit val example2 = {
      fixed[String](0, 1) <<:
        fixed[String](1, 2) <<:
        fixed[String](2, 3) <<:
        fixed[String](3, 4)
    }
  }


  "Parser" should "serialize each class properly" in {

    val ex1 = Example1("a", "b", "c")
    val ex1S = "abc"

    val ex2 = Example2("a", "b", "c", "d")
    val ex2S = "abcd"

    // implicits should be working here
        import Example1._
        import Example2._
    {
      // separately they work
//      import Example1._
      Parser.decode[Example1](ex1S).right.get shouldEqual ex1
      Parser.encode[Example1](ex1) shouldEqual ex1S
    }
    {
//      import Example2._
      Parser.decode[Example2](ex2S).right.get shouldEqual ex2
      Parser.encode[Example2](ex2) shouldEqual ex2S
    }
  }


}




