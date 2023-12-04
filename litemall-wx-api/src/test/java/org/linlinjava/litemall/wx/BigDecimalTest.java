package org.linlinjava.litemall.wx;

import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BigDecimalTest {

  @Test
  public void test() {
    BigDecimal a = new BigDecimal(0);
    BigDecimal b = new BigDecimal(1);
    BigDecimal c = a.subtract(b);
    BigDecimal d = c.max(new BigDecimal(0));

    System.out.println(c);
    System.out.println(d);
  }
}
