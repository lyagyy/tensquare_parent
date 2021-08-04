import com.tensquare.encrypt.EncryptApplication;
import com.tensquare.encrypt.rsa.RsaKeys;
import com.tensquare.encrypt.service.RsaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EncryptApplication.class)
public class EncryptTest {

    @Autowired
    private RsaService rsaService;

    @Before
    public void before() throws Exception{
    }

    @After
    public void after() throws Exception {
    }

    //加密
    @Test
    public void genEncryptDataByPubKey() {
        String data = "{\"title\":\"文章测试5\"}";

        try {

            String encData = rsaService.RSAEncryptDataPEM(data, RsaKeys.getServerPubKey());

            System.out.println("data: " + data);
            System.out.println("encData: " + encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解密
    @Test
    public void  test() throws Exception {
        String dataPEM = rsaService.RSADecryptDataPEM("foZvRErwvVyEnzbuMl5/Mm5lzMJBliLzDn6dPhuE+M2zowXIwpvJkc7E8ZGcsszH5EOMTXmh9lcqdTaQLTCVeM6uffi9Mp+xPk12jqSXLEANypHWHZjXmaEK0VIdwqnHJMhoDOOhLXTEz+ox0WbqHiJ7jq3FTwuw3r0HuY4XEw/RJ5ducYGfJx0jFuOFAJrhkz7cOqM2iiRK28y+He76SBJDtxh8TR6NLdDhdgW0u49kUIb9I9I/1r+nIQFCWJL+FCEUvNisiFIVoxGhi9RiwhsftK2d67wvzc4lDBnmRYVkNK0tYtehD5xDsU7gXPNQ6c6AALiWNw5x7A1xlWcsOA==", RsaKeys.getServerPrvKeyPkcs8());
        System.out.println(dataPEM);
    }

}
