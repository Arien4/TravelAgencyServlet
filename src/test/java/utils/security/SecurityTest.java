package utils.security;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.olenazaviriukha.travel.common.utils.SecurityUtils.validatePassword;

public class SecurityTest {
    private List<String> passwords = List.of("123", "1234567890", "poiuy99)");
    private List<String> hashedExpected = List.of(
            "1000:5b42403135636137383839:c9b3b554f97379f0593f2c420bd8226d84d1f363e512b4c78afa2eb20ae8b0643744474561a4149aae7a39d1e0f621c147f301dd22f3642144bdc4a162672469",
            "1000:5b42403135626266343266:4ef8caa41f4448fc452ce6c1893101bd59cd2b1764f97b7593d4776f6c54bfeb6f5ebdda073c2f8e07ec07c7edc2a818dd39c1bd6565b38d498668d485076c29",
            "1000:5b42403535306565376535:36fd7c37ad36cadbf47211edb1e5d5d6c69203232fd5ea9c92844483f4ae9f55549fda12b067854f4ad75d6f2d19bf1bca438cdaedc5f7e960fbf839c3743a8d"
    );


    @Test
    public void shouldRGeneratePassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        for (int i=0; i < passwords.size(); i++) {
            assertTrue(validatePassword(passwords.get(i), hashedExpected.get(i)));

        }
    }
}
