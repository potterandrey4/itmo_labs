package beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultBeanTest {
    @Test
    void checkHit() {
        ResultBean resultBean = new ResultBean();
        resultBean.setY(1d);
        resultBean.setR(4d);
        resultBean.setX(1d);
        resultBean.prePersist();
        Assertions.assertTrue(resultBean.getIsHit());
    }
}