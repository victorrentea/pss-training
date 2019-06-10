package victor.training.spring;

import junit.framework.Assert;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RunWith(MockitoJUnitRunner.class)
public class MockitoDoesItTheSameAsSpring {
    @InjectMocks
   private BigBallOfCode code;

   @Mock
   private OtherDependency dep;
    @Test
    public void testM() {
        Mockito.when(dep.n()).thenReturn("b");
        Assert.assertEquals("B", code.m());
    }
}


@Service
@RequiredArgsConstructor
class BigBallOfCode {
//    @Autowired
//    private OtherDependency dep;

    private final OtherDependency dep;
//    private BigBallOfCode(OtherDependency dep) {
////        this.dep = dep;
////    }


    public String m() {
        return dep.n().toUpperCase();
    }

}

@Service
class OtherDependency {

    public String n() {
        return "ERROR";
    }
}