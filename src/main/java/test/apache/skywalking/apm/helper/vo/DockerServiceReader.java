package test.apache.skywalking.apm.helper.vo;

import java.util.List;

public interface DockerServiceReader {

    List<String> environment();

    String image();
}
