package org.karazin.kateryna;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoggerConfigurationProperties {

    private String format;
    private String filePath;
    private String jdbcUrl;
    private String username;
    private String password;
    private String tableName;

    public void setFormat(LogOutputFormat format) {
        this.format = format.getValue();
    }
}
