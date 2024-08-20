package com.passpack.api.sdk.model.encryption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EncodedStringTest {

    @Test
    public void testVersion3Decode() throws Exception {
        String input = "3#3CqgqScg0cTCUeyh8w+haw==#XoJ4RrE1rfMlyZDuOTZy0g==#W8BoOGIMqCYLjhRox3AwiAV8gzSSoWrSHrzV1frXyhrBzzcxFcKV1oc=";
        EncodedString encodedString = EncodedStringFactory.createFromInputString(input, "#");
        Assertions.assertEquals(3, encodedString.getVersion());
        Assertions.assertEquals("3CqgqScg0cTCUeyh8w+haw==", encodedString.getIv());
        Assertions.assertEquals("XoJ4RrE1rfMlyZDuOTZy0g==", encodedString.getSalt());
        Assertions.assertEquals("W8BoOGIMqCYLjhRox3AwiAV8gzSSoWrSHrzV1frXyhrBzzcxFcKV1oc=", encodedString.getCipherText());
    }


    @Test
    public void testDelimiter() throws Exception {
        String input = "3#3CqgqScg0cTCUeyh8w+haw==#XoJ4RrE1rfMlyZDuOTZy0g==#W8BoOGIMqCYLjhRox3AwiAV8gzSSoWrSHrzV1frXyhrBzzcxFcKV1oc=";
        EncodedString encodedString = EncodedStringFactory.createFromInputString(input);
        Assertions.assertEquals(3, encodedString.getVersion());
        Assertions.assertEquals("3CqgqScg0cTCUeyh8w+haw==", encodedString.getIv());
        Assertions.assertEquals("XoJ4RrE1rfMlyZDuOTZy0g==", encodedString.getSalt());
        Assertions.assertEquals("W8BoOGIMqCYLjhRox3AwiAV8gzSSoWrSHrzV1frXyhrBzzcxFcKV1oc=", encodedString.getCipherText());


        // Test with Pipe delimiter
        input = "3|3CqgqScg0cTCUeyh8w+haw==|XoJ4RrE1rfMlyZDuOTZy0g==|W8BoOGIMqCYLjhRox3AwiAV8gzSSoWrSHrzV1frXyhrBzzcxFcKV1oc=";
        encodedString = EncodedStringFactory.createFromInputString(input);
        Assertions.assertEquals(3, encodedString.getVersion());
        Assertions.assertEquals("3CqgqScg0cTCUeyh8w+haw==", encodedString.getIv());
        Assertions.assertEquals("XoJ4RrE1rfMlyZDuOTZy0g==", encodedString.getSalt());
        Assertions.assertEquals("W8BoOGIMqCYLjhRox3AwiAV8gzSSoWrSHrzV1frXyhrBzzcxFcKV1oc=", encodedString.getCipherText());
    }

    @Test
    public void testVersion2Decode() throws Exception {
        String input = "2|0So9vwNHhMdM/o63hTojxclMsuYYFW3z4EtkZ0ENSYVla4nA2pdU8u0byfm+zAgQm6cVmJxamdgm/QONC8PVIV1VtYfLHVUSCc9yHCey+1sXc/DZGnYAebsmigviOEkqokYNoz6g6J4Ssj7Xb/qeVh816eV5nx65C+fRleHqfj0|AQAB|Iw5ZWvJJwk9E8EUrLQEZjcS3PiSiVLUg8q6wHT4h4/ifju5/jCNg8XYb+jYJbFjNr8jJLdLGvJIltrIpqZcg2TEePbKPIHHwqRTMCSa82vgzq7FHy6vXKhCvwGJMDNU40PUlyr38yHBuUuAU3WRoh2CXUiBkDjLuMPfEE6AR1wE|7C3v9BFhlFm5UScYE2QI73TDxSk6GeID5OMX2aa0CrF1N9n+mfiJ63RD7VzgVQMyyZJy+tdH7e+82DTMehxSmQ|4rftdZgaErvJX8gseZlNSqo98i2d5dd4c2apJvEkYKYnUqi8+v8bc//eaekADGPcFVezhT7+dmrfbFk2MonzRQ|ljY5UQ/k8geG19h3dY3AKRwojzNDYsbfFypgNuvRwqNPHDCnv6HVhk0yDrttamRYXt+oBWX6t5gskY4zJs7wsQ|1VkP9GE15/se/St57tP8kA60THnw5KFnj/FDrImwGeNBjd+MNwZp5zcZLcE9cmtmaA8nuHQXMYhQx70L63M1oQ|HG3XbGmqU/u/sGP21F5gXqtqKeGC8QS7Xxa6cG7yoCGXNksGpe0lTJZ6YmyO/L8SIOXvXqBywSwWheMZEzOBRA";
        EncodedString encodedString = EncodedStringFactory.createFromInputString(input);
        Assertions.assertEquals(2, encodedString.getVersion());
        Assertions.assertEquals("0So9vwNHhMdM/o63hTojxclMsuYYFW3z4EtkZ0ENSYVla4nA2pdU8u0byfm+zAgQm6cVmJxamdgm/QONC8PVIV1VtYfLHVUSCc9yHCey+1sXc/DZGnYAebsmigviOEkqokYNoz6g6J4Ssj7Xb/qeVh816eV5nx65C+fRleHqfj0", encodedString.getIv());
        Assertions.assertEquals("AQAB", encodedString.getSalt());
    }
}
